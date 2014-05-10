package agg;

import utils.MatrixOps;
import utils.SimpleMatrixOps;
import data.SimpleMatrix;
import data.SimpleVector;
import data.SparseMatrix;

public class ReducedAggregator implements Aggregator{

	private SimpleMatrix U;
	
	private SparseMatrix compressedL;
	
	public ReducedAggregator(byte[][] input) {
		U = LevelTwo(levelOne(input));
		
	}
	
	private SimpleMatrix LevelTwo(SimpleMatrix input) {
//		input.print();
		//given a matrix, compute LU decomposition
		SimpleMatrix transposed = input.transpose();
//		System.out.println("transposed");
//		transposed.print();
		SimpleMatrix[] lu = SimpleMatrixOps.LUDecompositor(transposed);
//		System.out.println("L");
//		lu[0].print();
//		System.out.println("U");
//		lu[1].print();
		compressedL = new SparseMatrix(lu[0]);
		return lu[1];
	}

	private SimpleMatrix levelOne(byte[][] input) {
		return SimpleMatrixOps.ReduceRow(input); 
	}
	
	
	@Override
	public SimpleVector aggregateWith(SimpleVector attributes) {
		SimpleVector step1 = MatrixOps.vectorBySparsematrix(attributes, compressedL);
//		System.out.println("Attributes");
//		attributes.print();
//		System.out.println("Step1");
//		step1.print();
		return SimpleMatrixOps.VMMultiply(step1, U);
//		return step1;
	}

	
	public static void main(String[] args) {
		byte [][] matrix = new byte[][] {{1,0,0,1},
				   {1,1,1,1},
				   {0,0,0,1},
				   {0,1,1,0},  
				   {1,0,0,1}
		};
		int[] v= {1,2,2,1};
		SimpleVector attributes = new SimpleVector(v);
		ReducedAggregator ra = new ReducedAggregator(matrix);
		ra.aggregateWith(attributes).print();
		
	}
}
