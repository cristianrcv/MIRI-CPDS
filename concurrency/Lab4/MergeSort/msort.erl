-module(msort).
-export([ms/1, pms/1, p_ms/2]).

% Part A
% N is at most the length of L
% Post: {L1,L2} so that length(L1) == N and L1++L2==L
sep(L,0) -> {[], L};
sep([H|T], N) -> {Lleft, Lright} = sep(T, N - 1),
                 {[H|Lleft], Lright}.
                 
% Part B
% merge function
merge([], Q) -> Q;
merge(P, []) -> P;
merge([X|P], [Y|Q]) when X =< Y -> R = merge(P, [Y|Q]), [X|R];
merge([X|P], [Y|Q]) -> R = merge([X|P], Q), [Y|R].

% Part C
% Sequential mergesort
ms([]) -> [];
ms([A]) -> [A];
ms(L) -> {L1, L2} = sep(L, length(L) div 2),
         Lsort1 = ms(L1),
         Lsort2 = ms(L2),
         R = merge(Lsort1, Lsort2), R.
         

% Part D
% Concurrent mergesort version
rcvp(Pid) -> receive
                {Pid, L} -> L
             end.

pms(L) -> Pid = spawn(msort, p_ms, [self(), L]),
          rcvp(Pid).

p_ms(Pid, L) when length(L) < 100 -> Pid ! {self(), ms(L)};
p_ms(Pid, L) -> {Lleft, Lright} = sep(L, length(L) div 2),
                Pid1 = spawn(msort, p_ms, [self(), Lleft]),
                Pid2 = spawn(msort, p_ms, [self(), Lright]),
                L1 = rcvp(Pid1),
                L2 = rcvp(Pid2),
                Pid ! {self(), merge(L1,L2)}.
