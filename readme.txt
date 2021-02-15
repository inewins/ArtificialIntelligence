Anh Nguyen
ID 1001172804
Language used: Java

Code structure:
There are 3 classes, find_route, Node, and NodeInform. Node is used to store and print data for the
uninformed portion of the code. NodeInform is the same as Node but for the informed part. The class
fine_route is my main chunk. Inside the main function is all the algorithm to import file, create
nodes, store data into nodes and calling other functions/classes. I also have two other functions
inside find_route which are printPass and printFail. PrintPass prints necessary information for
search algorithm with an end goal. PrintFail prints information in the case there is no end goal.

How to run the code:
I am running my code on a computer that is running Windows 10. Go to https://www.oracle.com/java/technologies/javase-jdk15-downloads.html
and download jdk-15.0.2 for Windows x64. Go to https://git-scm.com/ and download Git Bash. Open Git Bash
and open move to the directory where find_route.java is in. Note that ALL neccessary files (input.txt,
h_kassel.txt) have to be in the same directory. Once in the directory, type command:

java find_route.java INPUT ROOT GOAL <H>

where INPUT is for the input file, ROOT is a starting state, GOAL is the final state, and <H> is
an optional input file for informed search. Note that without <H>, the program will run an uninformed
search algorithm.