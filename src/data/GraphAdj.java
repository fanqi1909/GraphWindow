package data;

import java.util.HashSet;

import agg.Aggregator;

public class GraphAdj implements Aggregator {
	private HashSet<Integer> [] adjacentList;
	private HashSet<Integer> vertices;
	
	public GraphAdj(int size) {
		adjacentList = new HashSet[size];
		for(int i = 0; i < size; i++) {
			adjacentList[i] = new HashSet<Integer>();
		}
		vertices = new HashSet<Integer>();
	}
	
	public void addEdge(int src, int dest) {
		adjacentList[src].add(dest);
		vertices.add(src);
		vertices.add(dest);
	}
	
	public void addVertex(int v) {
		vertices.add(v);
	}
	
	public HashSet<Integer> getNeighbor(int i ) {
		return adjacentList[i];
	}
	
	
	public void print() {
		for(int i = 0; i < adjacentList.length; i++) {
			System.out.println(adjacentList[i]);
		}
		System.out.println(vertices);
	}
	
	@Override
	public SimpleVector aggregateWith(SimpleVector attributes) {
		SimpleVector sv = new SimpleVector(adjacentList.length);
		int sum = 0;
		for(Integer i : vertices) {
			//compute the aggregation
			HashSet<Integer> neighors = getNeighbor(i);
			sum = 0;
			for(Integer t : neighors) {
				sum += attributes.get(t);
			}
			sv.set(i, sum);
		}
		return sv;
	}
	
	public static void main(String[] args) {
		GraphAdj gaj = io.GraphLoader.loadGraphAdjFromFile("data/n100_d20.gra");
		gaj.print();
	}

}
