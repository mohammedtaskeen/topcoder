/*
Problem Statement
   	
A sequence of numbers is called a zig-zag sequence if the differences between successive numbers strictly alternate between positive and negative. The first difference (if one exists) may be either positive or negative. A sequence with fewer than two elements is trivially a zig-zag sequence.
For example, 1,7,4,9,2,5 is a zig-zag sequence because the differences (6,-3,5,-7,3) are alternately positive and negative. In contrast, 1,4,7,2,5 and 1,7,4,5,5 are not zig-zag sequences, the first because its first two differences are positive and the second because its last difference is zero.
Given a sequence of integers, sequence, return the length of the longest subsequence of sequence that is a zig-zag sequence. A subsequence is obtained by deleting some number of elements (possibly zero) from the original sequence, leaving the remaining elements in their original order.
 
Definition
    	
Class:	ZigZag
Method:	longestZigZag
Parameters:	int[]
Returns:	int
Method signature:	int longestZigZag(int[] sequence)
(be sure your method is public)
    
 
Constraints
-	sequence contains between 1 and 50 elements, inclusive.
-	Each element of sequence is between 1 and 1000, inclusive.
 
Examples
0)	
    	
{ 1, 7, 4, 9, 2, 5 }
Returns: 6
The entire sequence is a zig-zag sequence.
1)	
    	
{ 1, 17, 5, 10, 13, 15, 10, 5, 16, 8 }
Returns: 7
There are several subsequences that achieve this length. One is 1,17,10,13,10,16,8.
2)	
    	
{ 44 }
Returns: 1
3)	
    	
{ 1, 2, 3, 4, 5, 6, 7, 8, 9 }
Returns: 2
4)	
    	
{ 70, 55, 13, 2, 99, 2, 80, 80, 80, 80, 100, 19, 7, 5, 5, 5, 1000, 32, 32 }
Returns: 8
5)	
    	
{ 374, 40, 854, 203, 203, 156, 362, 279, 812, 955, 
600, 947, 978, 46, 100, 953, 670, 862, 568, 188, 
67, 669, 810, 704, 52, 861, 49, 640, 370, 908, 
477, 245, 413, 109, 659, 401, 483, 308, 609, 120, 
249, 22, 176, 279, 23, 22, 617, 462, 459, 244 }
Returns: 36
*/
public class ZigZag{
	public int longestZigZag(int [] sequence){
		if(sequence.length<3){
			if(sequence.length<2 || sequence[0]==sequence[1])
				return 1;
			return 2;
		}

		int [][] length= new int[sequence.length][2];
		length[0][0]=1; length[0][1]=0;

		if(sequence[0]>sequence[1]){
			length[1][0]=2; length[1][1]=-1;
		} else if (sequence[0]<sequence[1]){
			length[1][0]=2; length[1][1]=1;
		} else {
			length[1]=length[0];
		}

		int max=length[1][0];
		for(int i=2;i<sequence.length;i++){
			length[i][0]=1; length[i][1]=0;
			for(int j=i-1;j>=0;j--){
				if(length[i][0]<length[j][0]){
					if(length[j][1]<1 && sequence[i]>sequence[j]){
						length[i][0]=length[j][0]+1; length[i][1]=1;
					} else if(length[j][1]>-1 && sequence[i]<sequence[j]){
						length[i][0]=length[j][0]+1; length[i][1]=-1;
					}
				}
			}
			if(max<length[i][0])
				max=length[i][0];
		}
		return max;
	}


	public static void testMethod(int [] sequence, int expected, int num){
		final long MEGABYTE = 1024L * 1024L;

		Runtime runtime = Runtime.getRuntime();
		long startTime = System.currentTimeMillis();
		long initialMemory=runtime.totalMemory() - runtime.freeMemory();

		//CHANGE
		int result=new ZigZag().longestZigZag(sequence);
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
		int numTests=6;

		
		int [][] sequence={{ 1, 7, 4, 9, 2, 5 },
							{ 1, 17, 5, 10, 13, 15, 10, 5, 16, 8 },
							{ 44 },
							{ 1, 2, 3, 4, 5, 6, 7, 8, 9 },
							{ 70, 55, 13, 2, 99, 2, 80, 80, 80, 80, 100, 19, 7, 5, 5, 5, 1000, 32, 32 },
							{ 374, 40, 854, 203, 203, 156, 362, 279, 812, 955, 
600, 947, 978, 46, 100, 953, 670, 862, 568, 188, 
67, 669, 810, 704, 52, 861, 49, 640, 370, 908, 
477, 245, 413, 109, 659, 401, 483, 308, 609, 120, 
249, 22, 176, 279, 23, 22, 617, 462, 459, 244 }};
		int [] expected={6,7,1,2,8, 36};

		for (int i=0; i<numTests;i++){
			testMethod(sequence[i], expected[i], i);
		}

	}
}