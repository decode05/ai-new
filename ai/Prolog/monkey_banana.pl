% Facts
on(floor, monkey).
on(floor, chair).
in(room, monkey).
in(room, chair).
in(room, banana).
at(ceiling, banana).
strong(monkey).

% Rules
grasp(monkey).
climb(monkey, chair) :- strong(monkey).

push(monkey, chair) :-
    strong(monkey).

under(banana, chair) :-
    push(monkey, chair).

canreach(banana, monkey) :-
    (at(floor, banana); at(ceiling, banana)),
    under(banana, chair),
    climb(monkey, chair).

canget(banana, monkey) :-
    canreach(banana, monkey),
    grasp(monkey).

% Main method
main :-
    (canget(banana, monkey) -> 
        write('The monkey can get the banana!'), nl
    ; 
        write('The monkey cannot get the banana.'), nl
    ).

% Query
:- main.
