package data;

import java.util.HashMap;
import java.util.HashSet;

import agg.Aggregator;

public class Graph implements Aggregator {
	int[][] edges;
	int size;
	int current;
	int numOfVertices;
	HashSet<Integer> vertices;
	HashMap<Integer, HashSet<Integer>> veInIndex;
	HashMap<Integer, HashSet<Integer>> veOutIndex;

	public Graph(int isize) {
		size = isize;
		edges = new int[size][2];
		current = 0;
		numOfVertices = 0;
		vertices = new HashSet<Integer>();
		veInIndex = new HashMap<>();
		veOutIndex = new HashMap<>();
	}
	
	public Graph(int[][] edge) {
		this(edge.length);
		for(int i = 0; i < edge.length; i++){
			addEdge(edge[i][0], edge[i][1]);
		}
	}
	
	public void addEdge(int src, int dest) {
		edges[current][0] = src;
		edges[current][1] = dest;
		current++;
		if(!vertices.contains(src)) {
			vertices.add(src);
			numOfVertices++;
		} 
		if(!vertices.contains(dest)) {
			vertices.add(dest);
			numOfVertices++;
		}
		if(! veOutIndex.containsKey(src)) {
			veOutIndex.put(src, new HashSet<Integer>());
		}
		if(! veOutIndex.containsKey(dest)) {
			veOutIndex.put(dest, new HashSet<Integer>());
		}
		
		if(! veInIndex.containsKey(dest)) {
			veInIndex.put(dest, new HashSet<Integer>());
		}
		if(! veInIndex.containsKey(src)) {
			veInIndex.put(src, new HashSet<Integer>());
		}
		
		veOutIndex.get(src).add(dest);
		veInIndex.get(dest).add(src);
		
	}
	
	public HashSet<Integer> getNeighbor(int v) {
		HashSet<Integer> inNeighbour = veInIndex.get(v);
		HashSet<Integer> outNeighbour = veOutIndex.get(v);
		HashSet<Integer> result = new HashSet<>();
		for(Integer i : inNeighbour) {
			result.add(i);
		}
		for(Integer i : outNeighbour) {
			result.add(i);
		}
		return result;
	}
	
	/**
	 * get k neighborhood
	 * @param v
	 * @param k
	 * @return
	 */
	public HashSet<Integer> getNeighbor(int v, int k) {
		HashSet<Integer> inNeighbour = veInIndex.get(v);
		HashSet<Integer> outNeighbour = veOutIndex.get(v);
		HashSet<Integer> result = new HashSet<>();
		for(Integer i : inNeighbour) {
			result.add(i);
		}
		for(Integer i : outNeighbour) {
			result.add(i);
		}
		return result;
	}
	
	public void print() {
		for(int i = 0; i < size; i++) {
			System.out.printf("%d: %3d%3d\n",i, edges[i][0], edges[i][1]);
		}
		System.out.println("-----------------");
		System.out.println(veInIndex);
		System.out.println("-----------------");
		System.out.println(veOutIndex);
	}

	@Override
	public SimpleVector aggregateWith(SimpleVector attributes) {
		SimpleVector sv = new SimpleVector(numOfVertices);
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
		int[][] edges = new int[][] {
				{0,1},
				{0,2},
				{0,3},
				{1,2},
				{1,3},
				{1,4},
				{1,5},
				{2,1},
				{2,3},
				{2,5},
				{3,1},
				{3,4},
				{3,5},
				{4,5},
				{4,2},
				{4,1},
				{5,1},
				{5,2},
		};
		
		Graph g = new Graph(edges);
		g.print();
		SimpleVector sv = new SimpleVector(new int[]{1,2,1,2,2,1});
		g.aggregateWith(sv).print();
	}
	
}
