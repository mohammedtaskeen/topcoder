/*
Problem Statement
    	Suppose you had an n by n chess board and a super piece called a kingknight. Using only one move the kingknight denoted 'K' below can reach any of the spaces denoted 'X' or 'L' below:
   .......
   ..L.L..
   .LXXXL.
   ..XKX..
   .LXXXL.
   ..L.L..
   .......
In other words, the kingknight can move either one space in any direction (vertical, horizontal or diagonally) or can make an 'L' shaped move. An 'L' shaped move involves moving 2 spaces horizontally then 1 space vertically or 2 spaces vertically then 1 space horizontally. In the drawing above, the 'L' shaped moves are marked with 'L's whereas the one space moves are marked with 'X's. In addition, a kingknight may never jump off the board.
Given the size of the board, the start position of the kingknight and the end position of the kingknight, your method will return how many possible ways there are of getting from start to end in exactly numMoves moves. start and finish are int[]s each containing 2 elements. The first element will be the (0-based) row position and the second will be the (0-based) column position. Rows and columns will increment down and to the right respectively. The board itself will have rows and columns ranging from 0 to size-1 inclusive. 
Note, two ways of getting from start to end are distinct if their respective move sequences differ in any way. In addition, you are allowed to use spaces on the board (including start and finish) repeatedly during a particular path from start to finish. We will ensure that the total number of paths is less than or equal to 2^63-1 (the upper bound for a long).
 
Definition
    	
Class:	ChessMetric
Method:	howMany
Parameters:	int, int[], int[], int
Returns:	long
Method signature:	long howMany(int size, int[] start, int[] end, int numMoves)
(be sure your method is public)
    
 
Notes
-	For C++ users: long long is a 64 bit datatype and is specific to the GCC compiler.
 
Constraints
-	size will be between 3 and 100 inclusive
-	start will contain exactly 2 elements
-	finish will contain exactly 2 elements
-	Each element of start and finish will be between 1 and size-1 inclusive
-	numMoves will be between 1 and 50 inclusive
-	The total number of paths will be at most 2^63-1.
 
Examples
0)	
    	
3
{0,0}
{1,0}
1
Returns: 1
Only 1 way to get to an adjacent square in 1 move
1)	
    	
3
{0,0}
{1,2}
1
Returns: 1
A single L-shaped move is the only way
2)	
    	
3
{0,0}
{2,2}
1
Returns: 0
Too far for a single move
3)	
    	
3
{0,0}
{0,0}
2
Returns: 5
Must count all the ways of leaving and then returning to the corner
4)	
    	
100
{0,0}
{0,99}
50
Returns: 243097320072600
*/

import java.util.Arrays;
public class ChessMetric{
	long [][][] grid;
	

	public long howMany(int size, int [] start, int [] end, int numMoves){
		grid=new long [size][size][numMoves+1];
		grid[start[0]][start[1]][numMoves]=1;

		for(;numMoves>0;numMoves--){
			for (int i=0;i<size;i++){
				for(int j=0;j<size;j++){
					if(grid[i][j][numMoves]>0){
						getPlaces(numMoves,i,j,size);
					}
				}
			}
		}
		return grid[end[0]][end[1]][0];
	}

	//For one position where can I go
	private void getPlaces(int numMoves,int x, int y, int size){
		long n=grid[x][y][numMoves];
		numMoves--;

		//-1 left
		if(x>0){
			grid[x-1][y][numMoves]+=n;

			//up
			if(y>0){
				grid[x-1][y-1][numMoves]+=n;
				if(y>1)
					grid[x-1][y-2][numMoves]+=n;
			}

			//Down
			if(y<size-1){
				grid[x-1][y+1][numMoves]+=n;
				if(y<size-2)
					grid[x-1][y+2][numMoves]+=n;

			}
		}

		//1 right
		if(x<size-1){
			grid[x+1][y][numMoves]+=n;

			//up
			if(y>0){
				grid[x+1][y-1][numMoves]+=n;
				if(y>1)
					grid[x+1][y-2][numMoves]+=n;
			}

			//Down
			if(y<size-1){
				grid[x+1][y+1][numMoves]+=n;
				if(y<size-2)
					grid[x+1][y+2][numMoves]+=n;

			}
		}

		if(y>0)
			grid[x][y-1][numMoves]+=n;
		if(y<size-1)
			grid[x][y+1][numMoves]+=n;

		if(x>1){
			if(y>0)
				grid[x-2][y-1][numMoves]+=n;
			if(y<size-1)
				grid[x-2][y+1][numMoves]+=n;
		}
		if(x<size-2){
			if(y>0)
				grid[x+2][y-1][numMoves]+=n;
			if(y<size-1)
				grid[x+2][y+1][numMoves]+=n;
		}
	}

	public static void testMethod(int size, int [] start, int [] end, int numMoves, long expected, int num){
		final long MEGABYTE = 1024L * 1024L;

		Runtime runtime = Runtime.getRuntime();
		long startTime = System.currentTimeMillis();
		long initialMemory=runtime.totalMemory() - runtime.freeMemory();

		long result=new ChessMetric().howMany(size, start, end, numMoves);

		long finalMemory=runtime.totalMemory() - runtime.freeMemory();
		long finalTime=System.currentTimeMillis();

		long time=finalTime- startTime;
		long memory=(finalMemory - initialMemory)/MEGABYTE;
		runtime.gc();
		boolean match=(result==expected);
		//For arrays Arrays.equals(result, expected);		

		System.out.println("----Test " + num + "----");
		System.out.println("Time: "+ time + "ms");
		System.out.println("Memory: "+ memory + "MB");
		System.out.println("Passed: "+ match);
		//For arrays Arrays.toString(arr) or Arrays.deepToString(arr) for arrays within arrays
		System.out.println("Result: "+ result);
		System.out.println("Expected: "+ expected);
		
	} 


	public static void main (String [] args){
		int numTests=5;

		
		int [] size={3,3,3,3,100};
		int [][] start={{0,0},{0,0},{0,0},{0,0},{0,0}};
		int [][] end={{1,0},{1,2},{2,2},{0,0},{0,99}};
		int [] numMoves={1,1,1,2,50};
		long [] expected={1,1,0,5,243097320072600L};

		for (int i=0; i<numTests;i++){
			testMethod(size[i], start[i], end[i], numMoves[i], expected[i], i);
		}




	}
}