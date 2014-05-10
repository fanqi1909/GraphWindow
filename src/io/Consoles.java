package io;

public class Consoles {
	public static void printMatrix(byte[][] matrix) {
		if(matrix == null) return;
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix[i].length; j++) {
				System.out.printf("%3d", matrix[i][j]);
			}
			System.out.println();
		}
	}
	
	public static void printVector(int[] v) {
		if(v == null) return;
		for(int i = 0; i < v.length; i++) {
			System.out.printf("%3d", v[i]);
		}
		System.out.println();
	}
	
	public static void printVector(byte[] v) {
		if(v == null) return;
		for(int i = 0; i < v.length; i++) {
			System.out.printf("%3d", v[i]);
		}
		System.out.println();
	}
}
