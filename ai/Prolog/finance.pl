% Define dynamic predicates for balance and transactions
:- dynamic(balance/1).
:- dynamic(income/2).
:- dynamic(expense/2).

% Initialize balance
initialize_balance(InitialBalance) :-
    assertz(balance(InitialBalance)).

% Add income
add_income(Description, Amount) :-
    balance(Balance),
    NewBalance is Balance + Amount,
    retract(balance(Balance)),
    assertz(balance(NewBalance)),
    assertz(income(Description, Amount)),
    write('Income added. New balance: $'), write(NewBalance), nl.

% Add expense
add_expense(Description, Amount) :-
    balance(Balance),
    NewBalance is Balance - Amount,
    retract(balance(Balance)),
    assertz(balance(NewBalance)),
    assertz(expense(Description, Amount)),
    write('Expense added. New balance: $'), write(NewBalance), nl.

% View balance
view_balance :-
    balance(Balance),
    write('Current balance: $'), write(Balance), nl.

% View transaction history
view_transactions :-
    write('Transaction History:'), nl,
    findall(income(Description, Amount), income(Description, Amount), IncomeList),
    findall(expense(Description, Amount), expense(Description, Amount), ExpenseList),
    print_transactions(IncomeList),
    print_transactions(ExpenseList).

print_transactions([]).
print_transactions([income(Description, Amount) | T]) :-
    write('Income: '), write(Description), write(', Amount: $'), write(Amount), nl,
    print_transactions(T).
print_transactions([expense(Description, Amount) | T]) :-
    write('Expense: '), write(Description), write(', Amount: $'), write(Amount), nl,
    print_transactions(T).

% Entry point for finance management
start_finance_management :-
    write('Welcome to Finance Management System.'), nl,
    write('Enter the initial balance: $'),
    read(InitialBalance),
    initialize_balance(InitialBalance),
    repeat,
    write('Choose an option:'), nl,
    write('1. Add income'), nl,
    write('2. Add expense'), nl,
    write('3. View balance'), nl,
    write('4. View transaction history'), nl,
    write('5. Exit'), nl,
    read(Option),
    process_option(Option),
    Option = 5,
    !.

process_option(1) :-
    write('Enter income description: '),
    read(Description),
    write('Enter income amount: $'),
    read(IncomeAmount),
    add_income(Description, IncomeAmount).

process_option(2) :-
    write('Enter expense description: '),
    read(Description),
    write('Enter expense amount: $'),
    read(ExpenseAmount),
    add_expense(Description, ExpenseAmount).

process_option(3) :-
    view_balance.

process_option(4) :-
    view_transactions.

process_option(5) :-
    write('Exiting Finance Management System.'), nl.

process_option(_) :-
    write('Invalid option. Please choose a valid option.'), nl.

% Run the finance management system
:- initialization(start_finance_management).
