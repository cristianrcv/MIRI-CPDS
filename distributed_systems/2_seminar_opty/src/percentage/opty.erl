-module(opty).
-export([start/6, stop/1]).

%% Clients: Number of concurrent clients in the system
%% Entries: Number of entries in the store
%% Reads: Number of read operations per transaction
%% Writes: Number of write operations per transaction
%% Time: Duration of the experiment (in secs)

start(Clients, Entries, Reads, Writes, SubsetSize, Time) ->
    register(s, server:start(Entries)),
    ListEntries = lists:seq(1, Entries),
    L = startClients(Clients, [], ListEntries, SubsetSize, Reads, Writes),
    io:format("Starting: ~w CLIENTS, ~w ENTRIES, ~w RDxTR, ~w WRxTR, ~w SUBSETSIZE, DURATION ~w s~n", 
         [Clients, Entries, Reads, Writes, SubsetSize, Time]),
    timer:sleep(Time*1000),
    stop(L).

stop(L) ->
    io:format("Stopping...~n"),
    stopClients(L),
    waitClients(L),
    s ! stop,
    io:format("Stopped~n").

startClients(0, L, _, _, _, _) -> L;
startClients(Clients, L, ListEntries, SubsetSize, Reads, Writes) ->
    ClientEntries = getSubset(ListEntries, SubsetSize),
    Pid = client:start(Clients, ClientEntries, Reads, Writes, s),
    io:format("Client ~w has entries ~w~n", [Pid, ClientEntries]),
    startClients(Clients-1, [Pid|L], ListEntries, SubsetSize, Reads, Writes).

getSubset(_, 0) -> [];
getSubset(Entries, Size) -> Index = random:uniform(length(Entries)),
                            Selected = lists:nth(Index, Entries),
                            NewEntries = lists:delete(Selected, Entries),
                            Res = getSubset(NewEntries, Size - 1),
                            [Selected|Res].
    
stopClients([]) ->
    ok;
stopClients([Pid|L]) ->
    Pid ! {stop, self()},	
    stopClients(L).

waitClients([]) ->
    ok;
waitClients(L) ->
    receive
        {done, Pid} ->
            waitClients(lists:delete(Pid, L))
    end.
