package data;

public class GMatrix {
	//TODO:: optimizing by utilizing 0-1 matrix property. Using bit matrix for compact store
	/**
	 * the major data structure used in matrix format
	 */
	protected byte[][] graph;
	
	public GMatrix (byte[][] igraph) {
		graph = igraph;
	}
	
	public byte[][] getGraph() {
		return graph;
	}
}
