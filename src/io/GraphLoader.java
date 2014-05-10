package io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import data.Graph;
import data.GraphAdj;

public class GraphLoader {
	public static Graph loadGraphFromFile(String filename) {
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine(); //skip the first line, since it contains "graph for greach"
			line = br.readLine();
			int num_of_vertices = Integer.parseInt(line);
			ArrayList<Integer> flatedges = new ArrayList<>();
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
					flatedges.add(v);
					flatedges.add(u);
				}
			}
			br.close();
			
			Graph graph = new Graph(flatedges.size() >> 1);
			for(int i = 0;  i < flatedges.size(); i += 2) {
				graph.addEdge(flatedges.get(i), flatedges.get(i+1));
			}
			
			return graph;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static GraphAdj loadGraphAdjFromFile(String filename) {
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine(); //skip the first line, since it contains "graph for greach"
			line = br.readLine();
			int num_of_vertices = Integer.parseInt(line);
			GraphAdj graph = new GraphAdj(num_of_vertices);
			String[] firstparser,secondparser;
			int v, u;
			while(num_of_vertices--> 0) {
				line = br.readLine();
				firstparser = line.split(": ");
				v = Integer.parseInt(firstparser[0]);
				graph.addVertex(v);
				secondparser = firstparser[1].split(" ");
				//ignore the last string, since it is "#"
				for(int i = 0; i < secondparser.length - 1; i++) {
					u = Integer.parseInt(secondparser[i]);
					graph.addEdge(v, u);
					graph.addEdge(u, v);
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
}
