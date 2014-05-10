package io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

import data.SparseMatrix;

public class SparseMatrixLoader {
	public static SparseMatrix loadSparseMatrixFromFile(String filename) {
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine(); //skip the first line, since it contains "graph for greach"
			line = br.readLine();
			int num_of_vertices = Integer.parseInt(line);
			int nv = num_of_vertices;
			ArrayList<Pair> pairs = new ArrayList<>();
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
					pairs.add(new Pair(i,u));
				}
			}
			br.close();
			Collections.sort(pairs); // sort everything in column order;
			int row = nv;
			int column = nv;
			byte[] value = new byte[pairs.size()];
			
			
			//TODO:: THIS IS REALLY REDUNDENT, REFACTOR!!
			for(int i = 0; i < value.length; i++) {
				value[i] = 1;
			}
			
			int[] rowid = new int[pairs.size()];
			for(int i = 0 ; i < rowid.length; i++) {
				rowid[i] = pairs.get(i).r;
			}
			
			int[] colid = new int[nv + 1];
			int count = 1;
			for(int i = 1; i < pairs.size(); i++) {
				if(pairs.get(i).c != pairs.get(i-1).c) {
					colid[pairs.get(i).c] = count;
				}
				count++;
			}
			
			return new SparseMatrix(row, column, value, rowid, colid);
		} catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	private static class Pair implements Comparable<Pair>{
		int r,c;
		public Pair(int ir, int ic) {
			r = ir;
			c = ic;
		}
		@Override
		public int compareTo(Pair arg0) {
			if(c == arg0.c) {
				return r - arg0.r;
			} else {
				return c- arg0.c;
			}
		}
	}
	
	public static void main(String[] args) {
		SparseMatrix sm  = loadSparseMatrixFromFile("data/n100_d20.gra");
		sm.print();
		for(int i = 0; i < sm.getNumOfRows(); i++) {
			for(int j = 0; j < sm.getNumOfCols(); j++) {
				System.out.printf("%3d", sm.get(i, j));
			}
			System.out.println();
		}
	}
}
