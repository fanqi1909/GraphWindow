package algo;

import utils.MatrixOps;
import data.GMatrix;

public class VectorMatrixAggregator implements Aggregator{
	private GMatrix graph;
	
	public VectorMatrixAggregator(GMatrix igraph) {
		graph = igraph;
	}
	
	@Override
	public int[] aggregateWith(int[] vector) {
		return MatrixOps.MVMultiply(graph.getGraph(), vector);
	}
	
	public static void main(String[] args) {
		byte [][] matrix = new byte[][] {{1,0,0,1},
				   {1,1,1,1},
				   {0,0,0,1},
				   {0,1,1,0},  
				   {1,0,0,1}
		};
		GMatrix graph = new GMatrix(matrix);
		VectorMatrixAggregator vma = new VectorMatrixAggregator(graph);
		
		int[] v= {1,2,2,1};
		int[] r = vma.aggregateWith(v);
		io.Consoles.printVector(r);
	}
}
