package agg;

import java.util.ArrayList;
import java.util.LinkedList;

import data.CondensedMatrix;
import data.SimpleVector;


import utils.MatrixOps;

public class ApproxAggregator implements Aggregator {
	private LinkedList<CondensedMatrix> condensed;
	
	private int total_row;
	//, total_col;
	public ApproxAggregator(byte[][] totalMatrix, int share) {
		int rows= totalMatrix.length;
		int cols = totalMatrix[0].length;
		total_row = rows;
		condensed = new LinkedList<>();
		
		int p  = 0, l = rows/share;
		while(rows - p >= l) {
			byte[][] d = new byte[cols][l];
			for(int i = 0; i < l && p + i < rows; i++) {
				for(int k = 0; k < cols; k++) {
					d[k][i] = totalMatrix[p+i][k];
				}
			}
			p += l;
			condensed.add(MatrixOps.condense(d));
		//	decomposed.add(MatrixOps.condense(d, new LinkedList[10]));
		//	decomposed.add(d);
		}
		int remain = rows-p;
		if(remain > 0) {
			byte[][] d = new byte[cols][remain];
			for(int i = 0; i < remain; i++) {
				for(int k = 0; k < cols; k++) {
					d[k][i] = totalMatrix[p+i][k];
				}
			}
		//	decomposed.add(MatrixOps.condense(d));
		//	decomposed.add(d);
			condensed.add(MatrixOps.condense(d));
		}
	}
	
	
	public static void main(String[] args) {
		byte[][] totalMatrix = new byte[][] {
				{1,1,1,0,1},
				{1,1,1,1,1},
				{0,1,1,0,1},
				{1,1,0,0,1},
				{0,0,1,1,0},
				{1,1,0,1,0},
				{1,1,1,0,0},
				{1,1,1,0,1},
				{0,0,1,0,1}
		};
		int[] ve = new int[]{1,2,1,2,3};
		
		io.Consoles.printVector(MatrixOps.MVMultiply(totalMatrix, ve));
		
		ApproxAggregator am = new ApproxAggregator(totalMatrix, 3);
		for(Object o : am.condensed){
			byte[][] m = ((CondensedMatrix)o).matrix;
			System.out.println("------------");
			io.Consoles.printMatrix(m);
		}
		
		am.aggregateWith(new SimpleVector(ve)).print();
	}


	@Override
	public SimpleVector aggregateWith(SimpleVector attributes) {
		CondensedMatrix matrix;
		byte[][] m;
		ArrayList<Integer>[] indices;
		int[] result = new int[total_row], pre, temp;
		int[] data = attributes.getData();
		int c = 0;
		for(Object o : condensed) {
			matrix = (CondensedMatrix) o;
			m = matrix.matrix;
			indices = matrix.index;
			//preaggregate attributes;
			pre = new int[indices.length];
			for(int i = 0; i < indices.length; i++) {
				int sum = 0;
				for(Integer index : indices[i]) {
					sum +=data[index];
				}
				pre[i] = sum;
			}
			temp = MatrixOps.VMMultiply(pre, m);
			System.arraycopy(temp, 0, result, c, temp.length);
			c+=temp.length;
		}
		return new SimpleVector(result);
	}
}
