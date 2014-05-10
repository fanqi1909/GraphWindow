package io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class GraphMatrixLoader {
	
	public static byte[][] loadMatrixFromFile(String filename) {
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine(); //skip the first line, since it contains "graph for greach"
			line = br.readLine();
			int num_of_vertices = Integer.parseInt(line);
			byte[][] graph = new byte[num_of_vertices][num_of_vertices];
			String[] firstparser,secondparser;
			int v, u;
			while(num_of_vertices--> 0) {
				line = br.readLine();
				firstparser = line.split(": ");
				v = Integer.parseInt(firstparser[0]);
				secondparser = firstparser[1].split(" ");
				//ignore the last string, since it is "#"
				for(int i = 0; i < secondparser.length - 1; i++) {
					u = Integer.parseInt(secondparser[i]);
					graph[v][u] = 1;
				//	graph[u][v] = 1;
				}
			}
			br.close();
			return graph;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		byte[][] graph = loadMatrixFromFile("data/n100_d20.gra");
		Consoles.printMatrix(graph);
	}
}
