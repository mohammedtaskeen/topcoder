/*
Problem Statement
    	Problem contains images. Plugin users can view them in the applet.

In the city, roads are arranged in a grid pattern. Each point on the grid represents a corner where two blocks meet. The points are connected by line segments which represent the various street blocks. Using the cartesian coordinate system, we can assign a pair of integers to each corner as shown below. 



 

You are standing at the corner with coordinates 0,0. Your destination is at corner width,height. You will return the number of distinct paths that lead to your destination. Each path must use exactly width+height blocks. In addition, the city has declared certain street blocks untraversable. These blocks may not be a part of any path. You will be given a String[] bad describing which blocks are bad. If (quotes for clarity) "a b c d" is an element of bad, it means the block from corner a,b to corner c,d is untraversable. For example, let's say
width  = 6
length = 6
bad = {"0 0 0 1","6 6 5 6"}
The picture below shows the grid, with untraversable blocks darkened in black. A sample path has been highlighted in red.



 

 
Definition
    	
Class:	AvoidRoads
Method:	numWays
Parameters:	int, int, String[]
Returns:	long
Method signature:	long numWays(int width, int height, String[] bad)
(be sure your method is public)
    
 
Constraints
-	width will be between 1 and 100 inclusive.
-	height will be between 1 and 100 inclusive.
-	bad will contain between 0 and 50 elements inclusive.
-	Each element of bad will contain between 7 and 14 characters inclusive.
-	Each element of the bad will be in the format "a b c d" where,
a,b,c,d are integers with no extra leading zeros,
a and c are between 0 and width inclusive,
b and d are between 0 and height inclusive,
and a,b is one block away from c,d.
-	The return value will be between 0 and 2^63-1 inclusive.
 
Examples
0)	
    	
6
6
{"0 0 0 1","6 6 5 6"}
Returns: 252
Example from above.
1)	
    	
1
1
{}
Returns: 2
Four blocks aranged in a square. Only 2 paths allowed.
2)	
    	
35
31
{}
Returns: 6406484391866534976
Big number.
3)	
    	
2
2
{"0 0 1 0", "1 2 2 2", "1 1 2 1"}
Returns: 0

*/

public class AvoidRoads{

		//Paths can only go 2 ways, up and right, for the total length to be width+height
		public long numWays(int width,int height, String[] bad){
			long[][] grid=new long [width+1][height+1];

			//Place bad ways - -2 is cannot go right, -1 cannot go up
			for(String s: bad){
				String[] badElem=s.split(" ");
				int x1=Integer.parseInt(badElem[0]);
				int x2=Integer.parseInt(badElem[2]);
				int y1=Integer.parseInt(badElem[1]);
				int y2=Integer.parseInt(badElem[3]);
				if(x1 !=x2){
					//x coord are diff so is horizontal street, cannot go right
					grid[Math.min(x1,x2)][Math.min(y1,y2)]=-2;
				} else if (y1 !=y2){
					//Cannot go up
					grid[Math.min(x1,x2)][Math.min(y1,y2)]=-1;

				}
			}


			//Start exploring ways, in each road overwrite number and sum adjacent intersections
			for(int i=width;i>=0;i--){
				for (int j=height;j>=0;j--){
					if(i==width && j==height){
						grid[i][j]=1;
					}else {
						long t=0;
						if(j<height && grid[i][j]!=-1)
							t+=grid[i][j+1];
						if(i<width && grid[i][j]!=-2)
							t+=grid[i+1][j];
						grid[i][j]=t;
					}
				}
			}
			return grid[0][0];	
		}

		public static void testMethod(int width, int height, String [] bad, long expected, int num){
		final long MEGABYTE = 1024L * 1024L;

		Runtime runtime = Runtime.getRuntime();
		long startTime = System.currentTimeMillis();
		long initialMemory=runtime.totalMemory() - runtime.freeMemory();

		//CHANGE
		long result=new AvoidRoads().numWays(width,height,bad);
		//:::::

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
		int numTests=4;

		int [] width={6,1,35,2};
		int [] height={6,1,31,2};
		String [][] bad={{"0 0 0 1","6 6 5 6"},
						{},
						{},
						{"0 0 1 0", "1 2 2 2", "1 1 2 1"}};
		long [] expected={252,2,6406484391866534976L,0};				
		for (int i=0; i<numTests;i++){
			testMethod(width[i],height[i], bad[i], expected[i], i);
		}

	}
}