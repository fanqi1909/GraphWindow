package algo;

import java.util.Arrays;
import java.util.Comparator;

import utils.MatrixOps;
import data.GMatrix;
import data.Trie;

public class ReducedMatrixAggregator implements Aggregator {
	private GMatrix reducedGraph;

	
	/**
	 * Process the input graph to generate a reduced graph
	 * @param igraph
	 * @return
	 */
	private GMatrix reduceGraph(GMatrix igraph) {
		byte [][] inputMatrix = igraph.getGraph();
		Trie duplicate_removal = new Trie();
		for(int i = 0; i < inputMatrix.length; i++) {
			duplicate_removal.addBytes(inputMatrix[i]);
		}
		return new GMatrix(duplicate_removal.traverse());
	}
	
	private GMatrix reduceGraphSort(GMatrix igraph) {
		byte[][] inputMatrix = igraph.getGraph();
		Comparator<byte[]> cmp =  new Comparator<byte[]>(){
			@Override
			public int compare(byte[] arg0, byte[] arg1) {
				for(int i = 0; i < arg0.length; i++) {
					if(arg0[i] == arg1[i]) {
						continue;
					} 
					if(arg0[i] > arg1[i]) 
						return -1;
					return 1;
				}
				return 0;
			}};
		Arrays.sort(inputMatrix, cmp);
		//duplicate removal
		int count = 1, dim = inputMatrix[0].length;
		for(int i = 1; i < inputMatrix.length; i++) {
			if(cmp.compare(inputMatrix[i], inputMatrix[i-1]) != 0) {
				count++;
			}
		}
		byte[][] result = new byte[count][inputMatrix[0].length];
		System.arraycopy(inputMatrix[0], 0, result[0], 0,dim);
		for(int i = 1, j = 1; i < inputMatrix.length; i++) {
			if(cmp.compare(inputMatrix[i], inputMatrix[i-1]) != 0) {
				System.arraycopy(inputMatrix[i], 0, result[j], 0, dim);
				j++;
			}
		}
		return new GMatrix(result);
	}
	
	
	public ReducedMatrixAggregator(GMatrix igraph) {
		//object creating is time consuming, use the naive hash way
		//reducedGraph = reduceGraph(igraph); 
		reducedGraph = reduceGraphSort(igraph);
		
	}
	
	public byte[][] SingularDecomposition() {
		//operate on redcuedGraph
		//reduced Graph is sorted already
		
		
		return null;
	}
	
	public GMatrix getGrpah(){
		return reducedGraph;
	}
	
	@Override
	public int[] aggregateWith(int[] attr_vector) {
		return MatrixOps.MVMultiply(reducedGraph.getGraph(), attr_vector);
	}
	
	public static void main(String[] args) {
		byte [][] matrix = new byte[][] {{1,0,0,1},
				   {1,1,1,1},
				   {0,0,0,1},
				   {0,1,1,0},  
				   {1,0,0,1}
		};
		GMatrix graph = new GMatrix(matrix);
		ReducedMatrixAggregator rma = new ReducedMatrixAggregator(graph);
		
		int[] v= {1,2,2,1};
		int[] r = rma.aggregateWith(v);
		io.Consoles.printVector(r);
	}
}
