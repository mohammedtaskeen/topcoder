/*
Problem Statement
    	
The old song declares "Go ahead and hate your neighbor", and the residents of Onetinville have taken those words to heart. Every resident hates his next-door neighbors on both sides. Nobody is willing to live farther away from the town's well than his neighbors, so the town has been arranged in a big circle around the well. Unfortunately, the town's well is in disrepair and needs to be restored. You have been hired to collect donations for the Save Our Well fund.
Each of the town's residents is willing to donate a certain amount, as specified in the int[] donations, which is listed in clockwise order around the well. However, nobody is willing to contribute to a fund to which his neighbor has also contributed. Next-door neighbors are always listed consecutively in donations, except that the first and last entries in donations are also for next-door neighbors. You must calculate and return the maximum amount of donations that can be collected.
 
Definition
    	
Class:	BadNeighbors
Method:	maxDonations
Parameters:	int[]
Returns:	int
Method signature:	int maxDonations(int[] donations)
(be sure your method is public)
    
 
Constraints
-	donations contains between 2 and 40 elements, inclusive.
-	Each element in donations is between 1 and 1000, inclusive.
 
Examples
0)	
    	
 { 10, 3, 2, 5, 7, 8 }
Returns: 19
The maximum donation is 19, achieved by 10+2+7. It would be better to take 10+5+8 except that the 10 and 8 donations are from neighbors.
1)	
    	
{ 11, 15 }
Returns: 15
2)	
    	
{ 7, 7, 7, 7, 7, 7, 7 }
Returns: 21
3)	
    	
{ 1, 2, 3, 4, 5, 1, 2, 3, 4, 5 }
Returns: 16
4)	
    	
{ 94, 40, 49, 65, 21, 21, 106, 80, 92, 81, 679, 4, 61,  
  6, 237, 12, 72, 74, 29, 95, 265, 35, 47, 1, 61, 397,
  52, 72, 37, 51, 1, 81, 45, 435, 7, 36, 57, 86, 81, 72 }
Returns: 2926
*/

public class BadNeighbors{

	public int maxDonations(int [] donations){
		//For each neighbor see combination that maximizes based on previous neighbors
		//maxDonation[i][1]=includes first element or not

		int [][] maxDonation= new int[donations.length][2];
		maxDonation[0][0]=donations[0]; maxDonation[0][1]=0;
		maxDonation[1][0]=donations[1]; maxDonation[1][1]=-1;
		int max=Math.max(donations[0],donations[1]);

		for(int i=2; i<donations.length;i++){
			maxDonation[i][0]=donations[i]; maxDonation[i][1]=-1;
			for(int j=i-2;j>=0;j--){
				if(maxDonation[j][0]>(maxDonation[i][0]-donations[i])){
					if(i<(donations.length-1) || maxDonation[j][1]==-1){
						maxDonation[i][0]=maxDonation[j][0]+donations[i];
						maxDonation[i][1]=maxDonation[j][1];
					} else if((maxDonation[j][0]-donations[0])>(maxDonation[i][0]-donations[i])){
						maxDonation[i][0]=maxDonation[j][0]+donations[i]-donations[0];
					}
				}
			}
			if(max<maxDonation[i][0])
				max=maxDonation[i][0];
		}
		return max;
	}

	public static void testMethod(int [] donations, int expected, int num){
		final long MEGABYTE = 1024L * 1024L;

		Runtime runtime = Runtime.getRuntime();
		long startTime = System.currentTimeMillis();
		long initialMemory=runtime.totalMemory() - runtime.freeMemory();

		//CHANGE
		int result=new BadNeighbors().maxDonations(donations);
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
		int numTests=5;

		
		int [][] sequence={{ 10, 3, 2, 5, 7, 8 },
							{ 11, 15 },
							{ 7, 7, 7, 7, 7, 7, 7 },
							{ 1, 2, 3, 4, 5, 1, 2, 3, 4, 5 },
							{ 94, 40, 49, 65, 21, 21, 106, 80, 92, 81, 679, 4, 61,  
  6, 237, 12, 72, 74, 29, 95, 265, 35, 47, 1, 61, 397,
  52, 72, 37, 51, 1, 81, 45, 435, 7, 36, 57, 86, 81, 72 }};
		int [] expected={19,15,21,16,2926};

		for (int i=0; i<numTests;i++){
			testMethod(sequence[i], expected[i], i);
		}

	}	

}
