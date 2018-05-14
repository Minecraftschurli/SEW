package api;

import java.util.List;

public class BubbleSort {
	
	/**
	 * 
	 * @param arr
	 * @return
	 */
	public static int[] sort(int[] arr){
		boolean vertauscht;
		int hObj;
		do{
			vertauscht = false;
			for(int i = 0;i<arr.length-1;i++){
				if(arr[i]>arr[i+1]){
					hObj = arr[i];
					arr[i] = arr[i+1];
					arr[i+1] = hObj;
					vertauscht = true;
				}
			}
		}while(vertauscht);
		return arr;
	}
	
	/**
	 * 
	 * @param list
	 * @return
	 */
	public static List<Integer> sort(List<Integer> list){
		boolean vertauscht;
		int hObj;
		do{
			vertauscht = false;
			for(int i = 0;i<list.size()-1;i++){
				if(list.get(i)>list.get(i+1)){
					hObj = list.get(i);
					list.add(i, list.get(i+1));
					list.add(i, hObj);
					vertauscht = true;
				}
			}
		}while(vertauscht);
		return list;
	}
	
}
