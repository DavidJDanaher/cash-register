#Cash Register
This is a simple cash register program to be run from the command line.
This was developed to be run from an IDE for debugging and code analysis,
therefore some considerations were made to optimize for that rather than
compiling and running from terminal.

##List of commands:

> show 						| Display register value and inventory
> put 	20s 10s 5s 2s 1s 	| Deposit bills, a number for each denomination is required
> take 	20s 10s 5s 2s 1s 	| Withdraw bills, a number for each denomination is required
> change [amount]			| Display change in available denominations and withdraw
> help 						| Display these commands
> quit 						| Exit the program