# TidemanMethod-Java
Tideman Method or Ranked Pairs Voting System Implementation in Java 
# Introduction
1.`VotingSystem.java` contains classes to implement Tideman Method.  
2.Examples can be found in `Main.java` which has tests that can be considered as Examples.
# How To Use ?
1.First Import the `VotingSystem`.  
2.Create a new class in `VotingSystem`.
>Remainder : Constructors and methods are in `VotingSystem.java`.

3.Declare and Initialize a `String []` representing Candidates and contains String values of `Candidate Names`.  
4.Declare and Initialize a `String [][]` representing voters preferences and contains Candidate Name as per voters preference.
>Syntax : [voter_index][preference] = CandidateName

5.Then use `setCandidates()` and `setVoters()` methods of the VotingSystem class to set candidates and votes.  
6.Then use `findWinner()` method of VotingSystem Class to find Winner.
>findWinner() returns a String value if it returns `Tie` then the voting ended in a tie.

## The VotingSystem.java contains a Helper class and a import of Scanner for debugging purposes it can be removed if neccessary.
