public class Ex1 {
	public static int Ones(int[] arr, int k) {
		return BinarySearchOnes(arr, k, 0, arr.length-1);
	}
	
	public static int BinarySearchOnes(int[] arr, int k, int start, int end) {
		if (start > end) {return 0; }
		
		int mid = (start + end) / 2;
		if(k == arr[mid]) {
			return 1 + BinarySearchOnes(arr,k,mid+1,end) + BinarySearchOnes(arr,k,start,mid-1);
		}
		else if (k > arr[mid]) {
			return BinarySearchOnes(arr,k,mid+1,end);
		}
		// else: (k < arr[mid])
		return BinarySearchOnes(arr,k,start,mid-1);
	}
}
