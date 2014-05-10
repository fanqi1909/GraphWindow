package data;

import java.util.ArrayList;

public class CondensedMatrix {
	public byte[][] matrix;
	public ArrayList<Integer>[] index;
	
	public CondensedMatrix(byte[][] matrix, ArrayList<Integer>[] index) {
		this.matrix = matrix;
		this.index = index;
	}
}
