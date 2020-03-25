# SokobanSolver
TP SIA 2020C1
## Heuristics
### Trivial
Implementation of the trivial heuristic, mainly for testing purposes. Assigns boards a value of 1 unless they are a solution, in which case it returns 0.
### Hamming
Simple heuristic that counts how many boxes are placed in a goal.
### Manhattan 
Measures the Manhattan distance from every box to the closest goal and adds them all up, no matter if two or more boxes are using the same goal.
### Improved Manhattan
Works like Manhattan but two boxes can not use the same goal. It calculates every possibility and returns the minimum possible combination.
## Config file options:
### searchMethod
- 1 - DFS
- 2 - BFS (Default)
- 3 - IDDFS
- 4 - Global Greedy
- 5 - A*
- 6 - IDA*

### heuristic
- 1 - Trivial (Default)
- 2 - Hamming
- 3 - Manhattan
- 4 - Improved Manhattan

### deadlockCheck
- 1 - Activate checks to prevent deadlocks (Default)
- 2 - Deadlock scenarios are considered valid

### depth
- Maximum depth used for IDDFS

### boardx/boardy
- Size of the map along the x and y axis respectively
### playerx/playery
- Player position in the x/y axis respectively. Note that first row/column is 0
### board
- The level that will we solved, empty spaces outside the playing area need to be marked with 'x' and newLines preceded by a '\\', here's an example of a valid input:<br>
        
        xxxxxx###xxxxxx\
        xxxxxx#.#xxxxxx\
        xx#####.#####xx\
        x##         ##x\
        ##  # # # #  ##\
        #  ##     ##  #\
        # ##  # #  ## #\
        #     $@$     #\
        ####  ###  ####\
        xxx#### ####xxx 
For this example:<br>
- boardx = 15
- boardy = 10
- playerx = 7
- playery = 7
