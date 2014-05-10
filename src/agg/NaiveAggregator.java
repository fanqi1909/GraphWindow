package agg;

import utils.SimpleMatrixOps;
import data.SimpleMatrix;
import data.SimpleVector;

public class NaiveAggregator implements Aggregator {
	
	private SimpleMatrix sm; 
	
	public NaiveAggregator(byte[][] input){
		sm = new SimpleMatrix(input);
	}
	
	@Override
	public SimpleVector aggregateWith(SimpleVector attributes) {
		return SimpleMatrixOps.MVMultiply(sm, attributes);
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
		NaiveAggregator na = new NaiveAggregator(matrix);
		na.aggregateWith(attributes).print();
		
	}
}
