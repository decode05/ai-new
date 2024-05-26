parent(shyam, ram).
parent(shyam, priya).
parent(ram, anil).
parent(ram, veena). 
parent(priya, amita).
parent(anil, anjali).

sibling(X, Y) :-
    parent(Z, X),
    parent(Z, Y),
    X \= Y.

grandparent(X, Y) :-
    parent(X, Z),
    parent(Z, Y).

:- initialization(main).
main :-
    write('Parent-child relationships:'), nl,
    forall(parent(X, Y), (write(X), write(' is a parent of '), write(Y), nl)),
    write('Sibling relationships:'), nl,
    forall(sibling(X, Y), (write(X), write(' is a sibling of '), write(Y), nl)),
    write('Grandparent relationships:'), nl,
    forall(grandparent(X, Y), (write(X), write(' is a grandparent of '), write(Y), nl)),
    halt.
