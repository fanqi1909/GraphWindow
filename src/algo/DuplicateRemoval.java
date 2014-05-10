package algo;

import java.util.Arrays;
import java.util.Comparator;

public class DuplicateRemoval {
	private byte[][] data;
	
	public DuplicateRemoval(byte[][] input) {
		io.Consoles.printMatrix(input);
		data = removeDuplicateOn(input);
		System.out.println();
		io.Consoles.printMatrix(data);
	}

	private byte[][] removeDuplicateOn(byte[][] data2) {
		Comparator<byte[]> cmp =  new Comparator<byte[]>(){
			@Override
			public int compare(byte[] arg0, byte[] arg1) {
				for(int i = 0; i < arg0.length; i++) {
					if(arg0[i] == arg1[i]) {
						continue;
					} 
					if(arg0[i] > arg1[i]) 
						return 1;
					return -1;
				}
				return 0;
			}};
		Arrays.sort(data2, cmp);
		
		//duplicate removal
		int count = 1;
		for(int i = 1; i < data2.length; i++) {
			if(cmp.compare(data2[i], data2[i-1]) != 0) {
				count++;
			}
		}
		byte[][] result = new byte[count][data2[0].length];
		System.arraycopy(data2[0], 0, result[0], 0, data2[0].length);
		for(int i = 1, j = 1; i < data2.length; i++) {
			if(cmp.compare(data2[i], data2[i-1]) != 0) {
				System.arraycopy(data2[i], 0, result[j], 0, data2[0].length);
				j++;
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		byte[][] input = new byte[][]{
				{1,0,0,1},
				{0,1,1,0},
				{1,1,1,0},
				{0,1,0,1},
				{1,1,0,0},
				{1,0,0,1}
		};
		new DuplicateRemoval(input);
	}


}
