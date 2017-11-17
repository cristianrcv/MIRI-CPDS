-module(control).
-export([go/2, node/1]).

go(N, M) -> L = generate(M, M),		%Generate list
	    io:format("~p\n", [L]),
	    RING = setUpRing(N),	%SetUp ring
	    hd(RING) ! {token, L},	%Send first token
	    getEats(M, [], RING).		%Wait all

	   
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Generation of list L
generate(0, _) -> [];
generate(N, M) -> X = random:uniform(M), R = generate(N-1, M), 
		  [X|R].


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Set up ring of N processes and connects them
setUpRing(N) when is_integer(N), N >= 1 ->
    Nodes = [spawn(?MODULE, node, [self()]) || _ <- lists:seq(1,N)],
    connect(Nodes),
    Nodes.

% Set connection between nodes (and loop last to first)
connect(N = [H | _]) -> connect_(N ++ [H]).

connect_([]) -> connected;
connect_([_]) -> connected;
connect_([N1, N2 | Nodes]) ->
    N1 ! {self(), connect, N2},
    connect_([N2 | Nodes]).
    
% Collect eat messages from the nodes
getEats(0, RESULT, RING) -> 
    lists:foreach(fun(Node) -> Node!{die} end, RING),
    io:format("~p\n", [RESULT]),
    stop;
getEats(M, RESULT, RING) ->
    receive
      {Node, eat, RES} ->
        NEWRESULT = RESULT ++ [{Node,RES}],
        getEats(M-1, NEWRESULT, RING)
    end.
      
    
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Node function. Waits for the initialization or recieves the list token
node(ParentId) ->
    receive
	{ParentId, connect, NextNodeId} -> node(ParentId, NextNodeId)
    end.

node(ParentId, NextNodeId) ->
    receive
	{token, L} ->
	  [X|L2] = L,
	  io:format("~p eats\n", [self()]),
	  ParentId ! {self(), eat, X},
	  case L2 of
	    [] -> done;
	    _ -> NextNodeId ! {token, L2}
	  end,
	  node(ParentId, NextNodeId);
	{die} -> done
    end.

