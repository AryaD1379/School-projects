## _Cryptography_
In the republic of Santa Cruz some words are not allowed to be said and if the user uses them they will be sent to a joycamp. In addition, there are some words that should be replaced by new words and if the user says they will be given a warning.
## OPTIONS 

- -h prints out the program usage. Refer to the reference program in the resources repository
for what to print.
- -t size specifies that the hash table will have size entries (the default will be 216).
- -f size specifies that the Bloom filter will have size entries (the default will be 220).
- -s will enable the printing of statistics to stdout. The statistics to calculate are:
Average binary search tree size
Average binary search tree height
Average branches traversed
Hash table load
Bloom filter load
## Makefile
Will be used to compile the program and run:
- make format: will format the files.
- make: will be used for compiling the files and linking all the libraries that are necessary for our program to run.
- make clean: will delete the object files created by the compiler and the executable files.
