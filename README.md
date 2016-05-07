# Dancing-Fingers-Android-game

This is an android game to be played by two players chance by chance.
There is a limit to how many fingers can be used.

To play just download the apk file and install it (install from external source should be checked).


# How to play

Choose the size of the matrix and click on play button to start playing. It starts with highlighting green color for first player chance and red color for second player chance. let's say if max 2 fingers are allowed for each player then after two chances of each player their oldest tile will be deactivated and a new tile will be activated . if you are not able press and hold the highlited button in given time you lose .

if both of you manages to complete all the tiles, game is tied and you should procede to next level(i.e. larger matrix size).


# Implementation

I create a matrix of nXn buttons dynamically and allot them unique id and then generate random numbers and highlight the specific tile and disable the tile if it is older than the max allowed touch to player.

I generate random nos by creating array of 1 to n^2 and then randomly pick an index and then remove this value from the array, so that this no does not get picked again.

