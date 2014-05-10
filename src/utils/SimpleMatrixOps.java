package utils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import data.SimpleMatrix;
import data.SimpleVector;
import data.SparseMatrix;

public class SimpleMatrixOps {
	/**
	 * a simple L-U decomposition implemented
	 * 
	 * @param matrix
	 * @return result, result[0] is l, result[1] = u
	 */
	private static SimpleMatrix[] LUDecomposition(byte[][] matrix) {
		SimpleMatrix lu = new SimpleMatrix(matrix);
		SimpleMatrix p = SimpleMatrix.IdentityMatrix(matrix.length);

		for (int j = 0; j < lu.getNumOfCols(); j++) {
			for (int i = 0; i < lu.getNumOfRows(); i++) {
				int kmax = Math.min(i, j);
				byte s = 0;
				for (int k = 0; k < kmax; k++) {
					s += lu.get(i, k) * lu.get(k, j);
				}
				lu.subtract(i, j, s);
			}
			int pivot = j;
			for (int i = j + 1; i < lu.getNumOfRows(); i++) {
				if (Math.abs(lu.get(i, j)) > Math.abs(lu.get(pivot, j))) {
					pivot = i;
				}
			}
			if (pivot != j) {
				// swap row <pivot j> on lu and p
				lu.swapRow(pivot, j);
				p.swapRow(pivot, j);

			}

			if (j < lu.getNumOfRows() && Math.abs(lu.get(j, j)) != 0) {
				for (int i = j + 1; i < lu.getNumOfRows(); i++) {
					lu.set(i, j, lu.get(i, j) / lu.get(j, j));
				}
			}
		}

//		System.out.println(lu.getNumOfCols() + "\t" + lu.getNumOfRows());
//		System.out.println(p.getNumOfCols() + "\t" + p.getNumOfRows());

		return new SimpleMatrix[] { lu, p };
	}

	public static SimpleMatrix[] LUDecompositor(SimpleMatrix input) {
		return LUDecompositor(input.to2DArray());
	}
	
	
	public static SimpleMatrix[] LUDecompositor(byte[][] data) {
		SimpleMatrix[] sm = LUDecomposition(data);
		SimpleMatrix lu = sm[0];
		SimpleMatrix p = sm[1];

		SimpleMatrix l = new SimpleMatrix(lu.getNumOfRows(), lu.getNumOfCols());
		for (int i = 0; i < l.getNumOfRows(); i++) {
			for (int j = 0; j <= i && j < l.getNumOfCols(); j++) {
				if (i > j) {
					l.set(i, j, lu.get(i, j));
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
		
		SimpleMatrix nl = p.multiplyWith(l);
		return new SimpleMatrix[] {nl, u};
	}

	public static SimpleVector MVMultiply(SimpleMatrix matrix,
			SimpleVector vector) {
		int dim1 = matrix.getNumOfRows();
		int dim2 = matrix.getNumOfCols();
		int dim3 = vector.getSize();
		assert dim2 == dim3;
		SimpleVector result = new SimpleVector(dim1);
		int sum = 0;
		for (int i = 0; i < dim1; i++) {
			sum = 0;
			for (int j = 0; j < dim2; j++) {
				sum += matrix.get(i, j) * vector.get(j);
			}
			result.set(i, sum);
		}
		return result;
	}

	
	public static SimpleMatrix ReduceRow(byte[][] input) {
		byte[][] matrix = input.clone();
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
		
		SimpleMatrix result = new SimpleMatrix(count, dim);
		result.setRow(matrix[0], 0, 0, dim);
		
		for (int i = 1, j = 1; i < matrix.length; i++) {
			if (cmp.compare(matrix[i], matrix[i - 1]) != 0) {
				result.setRow(matrix[i], 0, j, dim);
				j++;
			}
		}
		return result;
	}
	
	/**
	 * vector simple matrix multiplication
	 * not sparse matrix multiplication
	 * @param step1
	 * @param u
	 * @return
	 */
	public static SimpleVector VMMultiply(SimpleVector step1, SimpleMatrix u) {
		int dim1 = step1.getSize();
		int dim2 = u.getNumOfRows();
		int dim3 = u.getNumOfCols();
		assert dim1 == dim2 :"Vector Dimesion does not match with Matrix Dimension";
		
		SimpleVector result = new SimpleVector(dim3);
		int sum = 0;
		for(int i = 0; i < dim3; i++) {
			sum = 0;
			for(int j = 0; j < dim2; j++) {
				sum += step1.get(j) * u.get(j, i);
			}
			result.set(i, sum);
		}
		return result;
	}
	
	
	public static SimpleMatrix ReduceRow(SimpleMatrix sm) {
		return ReduceRow(sm.to2DArray());
	}

	public static void main(String[] args) {
		byte[][] data = new byte[][]{
				{1,0,0,1,1,0},
				{1,1,1,0,1,1},
				{0,0,0,1,1,0},
				{1,0,1,0,1,0},
				{1,0,1,0,1,0},
				{1,1,1,1,0,0},
				{1,1,0,1,0,1},
				{1,1,1,1,1,1},
				{1,0,1,0,0,1},
				{1,1,1,1,1,0}
				
		};
		
		SimpleMatrix[] result = LUDecompositor(data);
		result[0].print();
		System.out.println();
		
		result[1].print();
		System.out.println();
	}
	
}
