package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import data.CondensedMatrix;
import data.SimpleMatrix;
import data.SimpleVector;
import data.SparseMatrix;

public class MatrixOps {
	
	public static int[] VMMultiply(int[] vector, byte[][] matrix) {
		int dim1 = vector.length;
		int dim2 = matrix.length;
		// 1 by n vector times n by m matrix
		assert dim1 == dim2;
		
		int dim3 = matrix[0].length;
		int[] result = new int[dim3];
		for(int i = 0 ; i < dim3; i++) {
			for(int j = 0; j < dim1; j++) {
				result[i] += vector[j] * matrix[j][i];
			}
		}
		return result;
	}
	
	
	public static int[] MVMultiply(byte[][] matrix, int[] vector) {
		int dim1 = matrix.length;
		int dim2 = matrix[0].length;
		int dim3 = vector.length;
		assert dim2 == dim3;
		int[] result = new int[dim1];
		for(int i = 0; i< dim1; i++) {
			for(int j = 0; j < dim2; j++) {
				result[i] += matrix[i][j] * vector[j];
			}
		}
		return result;
	}
	
	/**
	 * a simple L-U decomposition implemented
	 * @param matrix
	 * @return result, result[0] is l, result[1] = u
	 */
	private static SimpleMatrix[] LUDecomposition(byte[][] matrix) {
		SimpleMatrix lu = new SimpleMatrix(matrix);
		SimpleMatrix p = SimpleMatrix.IdentityMatrix(matrix.length);
		
		for(int j = 0; j < lu.getNumOfCols();j++) {
			for(int i = 0; i < lu.getNumOfRows(); i++) {
				int kmax = Math.min(i, j);
				byte s = 0;
				for(int k = 0;  k < kmax; k++) {
					s += lu.get(i, k) * lu.get(k, j);
				}
				lu.subtract(i, j, s);
			}
			int pivot = j;
			for(int i = j+1; i < lu.getNumOfRows(); i++) {
				if(Math.abs(lu.get(i,j)) > Math.abs(lu.get(pivot,j))) {
					pivot = i;
				}
			}
			if(pivot != j) {
				//swap row <pivot j> on lu and p
				lu.swapRow(pivot, j);
				p.swapRow(pivot, j);
				
			}
			
			if(j < lu.getNumOfRows() && Math.abs(lu.get(j,j)) != 0) {
				for(int i = j + 1; i < lu.getNumOfRows(); i++) {
					lu.set(i, j, lu.get(i, j) / lu.get(j, j));
				}
			}
		}

		System.out.println(lu.getNumOfCols()+"\t"+lu.getNumOfRows());
		System.out.println(p.getNumOfCols()+"\t"+p.getNumOfRows());
		
		return new SimpleMatrix[]{lu, p};
	}
	
	
	public static SimpleMatrix[] LUDecompositor(byte[][] data) {
		SimpleMatrix[] sm = LUDecomposition(data);
		SimpleMatrix lu = sm[0];
		SimpleMatrix p = sm[1];
				
		SimpleMatrix l = new SimpleMatrix(lu.getNumOfRows(), lu.getNumOfCols());
		for(int i = 0; i < l.getNumOfRows(); i++) {
			for(int j = 0; j <= i && j < l.getNumOfCols(); j++) {
				if(i > j) {
					l.set(i, j, lu.get(i,j));
				} else {
					l.set(i, j, 1);
				}
			}
		}
		
		SimpleMatrix u = new SimpleMatrix(lu.getNumOfCols(), lu.getNumOfCols());

		for (int i = 0; i < u.getNumOfRows(); i++) {
			for (int j = i; j < u.getNumOfCols(); j++) {
				u.set(i, j, lu.get(i, j));
			}
		}
	    return new SimpleMatrix[] {l, u, p };
	}
	
	
	
	public static SimpleVector vectorBySparsematrix(SimpleVector sv, SparseMatrix sm) {
		assert sv.getSize() == sm.getNumOfRows();
		int rdim = sv.getSize();
		int cdim = sm.getNumOfCols();
		List<Integer> indices;
		SimpleVector result = new SimpleVector(cdim);
		int sum = 0;
		for(int i = 0; i < cdim; i++) {
			indices = sm.getColumnIndex(i);
			sum = 0;
			for(Integer index : indices) {
				sum += sv.get(index); 
			}
			result.set(i, sum);
		}
		return result;
	}
	
	
	/**
	 * condense the input matrix
	 * @param input
	 * @return
	 */
	public static byte[][] condense(byte[][] input, LinkedList<Integer>[] indices) {
		byte[][] matrix = input;
		Comparator<byte[]> cmp = new Comparator<byte[]>() {
			@Override
			public int compare(byte[] arg0, byte[] arg1) {
				for (int i = 0; i < arg0.length; i++) {
					if (arg0[i] == arg1[i]) {
						continue;
					}
					if (arg0[i] > arg1[i])
						return -1;
					return 1;
				}
				return 0;
			}
		};
		Arrays.sort(matrix, cmp);
		
		// duplicate removal
		int count = 1, dim = matrix[0].length;
		for (int i = 1; i < matrix.length; i++) {
			if (cmp.compare(matrix[i], matrix[i - 1]) != 0) {
				count++;
			}
		}
		byte[][] result = new byte[count][dim];
		System.out.println(count);
		indices = new LinkedList[count];
		for(int i = 0; i < count; i++) {
			indices[i] = new LinkedList<>();
		}
		System.arraycopy(matrix[0], 0, result[0], 0, dim);
		indices[0].add(0);
		for (int i = 1, j = 1, k= 0; i < matrix.length; i++) {
			if (cmp.compare(matrix[i], matrix[i - 1]) != 0) {
				System.arraycopy(matrix[i], 0, result[j], 0, dim);
				j++;
				k++;
			}
			indices[k].add(i);
		}
		
		System.out.println("EEEE");
		for(int i = 0; i < indices.length; i++) {
			System.out.println(indices[i]);
		}
		return result;
	}
	
	
	
	public static CondensedMatrix condense(byte[][] input) {
		byte[][] matrix = input;
		Comparator<byte[]> cmp = new Comparator<byte[]>() {
			@Override
			public int compare(byte[] arg0, byte[] arg1) {
				for (int i = 0; i < arg0.length; i++) {
					if (arg0[i] == arg1[i]) {
						continue;
					}
					if (arg0[i] > arg1[i])
						return -1;
					return 1;
				}
				return 0;
			}
		};
		Arrays.sort(matrix, cmp);
		int count = 0;
		boolean all0 = true;
		while(all0 && count < matrix.length) {
			all0 = true;
			for(int i = 0; i < matrix[count].length; i++) {
				if(matrix[count][i] != 0) {
					all0 = false;
					break;
				}
			}
			if(all0) {
				count++;
			}
		}
		
		// duplicate removal, remove 0 0 0 as well
		int dim = matrix[0].length;
		int total  = 1;
		for (int i = count + 1; i < matrix.length; i++) {
			if (cmp.compare(matrix[i], matrix[i - 1]) != 0) {
				total++;
			}
		}
		byte[][] result = new byte[total][dim];
		ArrayList<Integer>[] indices = new ArrayList[total];
		for(int i = 0; i < total; i++) {
			indices[i] = new ArrayList<>();
		}
		System.arraycopy(matrix[0], 0, result[0], 0, dim);
		indices[0].add(0);
		for (int i = count+1, j = 1, k= 0; i < matrix.length; i++) {
			if (cmp.compare(matrix[i], matrix[i - 1]) != 0) {
				System.arraycopy(matrix[i], 0, result[j], 0, dim);
				j++;
				k++;
			}
			indices[k].add(i);
		}
		return new CondensedMatrix(result, indices);
	}
	
	

	
	public static void main(String[] args) {
	//	int [] v = {1,2,2,1};
		byte [][] matrix = new byte[][] {{1,1,0,1},
									   {1,0,0,1},
									   {0,1,1,1},
									   {0,1,0,1},  
									   {0,1,0,1}
		};
		
		SimpleMatrix[] lup = LUDecompositor(matrix);
		
		lup[0].print();
		System.out.println();
		lup[1].print();
		System.out.println();
		lup[2].print();
		System.out.println();
		
		SparseMatrix sm = new SparseMatrix(matrix);
		SimpleVector sv = new SimpleVector(new int[]{1,2,3,4,5});
		vectorBySparsematrix(sv, sm).print();
	}
	
}
