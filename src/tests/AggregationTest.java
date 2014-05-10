package tests;
import io.GraphLoader;
import io.GraphMatrixLoader;

import java.util.Random;

import agg.ApproxAggregator;
import agg.NaiveAggregator;
import algo.Optimizer;
import data.Graph;
import data.GraphAdj;
import data.SimpleVector;

public class AggregationTest {
	
	public static void main(String[] args) {
		String filename = "data/n3000_d300.gra";
		//prepare data
		byte [][] matrix = GraphMatrixLoader.loadMatrixFromFile(filename);
		
//		int[] v = randInt(matrix.length);
//		VectorMatrixAggregator vma = new VectorMatrixAggregator(gm);
		long total_time;
		
		
//		
//		ReducedMatrixAggregator rma = new ReducedMatrixAggregator(gm);
//		time = System.currentTimeMillis();
//		rma.aggregateWith(v);
//		System.out.println(System.currentTimeMillis()-time);
		
		SimpleVector sv ;
		
		long time = System.currentTimeMillis();
		NaiveAggregator na = new NaiveAggregator(matrix);
		total_time = 0;
		
		for(int i = 0; i < 100; i++) {
			sv = new SimpleVector(randInt(matrix.length));
			time = System.currentTimeMillis();
			na.aggregateWith(sv);
			total_time+= System.currentTimeMillis() - time;
		}
		System.out.println("SV:" +  (total_time)/100.0);
		
		ApproxAggregator aa = new ApproxAggregator(matrix, Optimizer.getBestK(matrix.length, matrix.length));
		total_time = 0;
		for(int i = 0; i < 100;i++) {
			sv = new SimpleVector(randInt(matrix.length));
			time = System.currentTimeMillis();
			aa.aggregateWith(sv);
			total_time += System.currentTimeMillis()-time;
		}
		System.out.println("OP:" + (total_time)/100.0);
	
		Graph g = GraphLoader.loadGraphFromFile(filename);
		total_time = 0;
		for(int i = 0; i < 100;i++) {
			sv = new SimpleVector(randInt(matrix.length));
			time = System.currentTimeMillis();
			g.aggregateWith(sv);
			
			total_time += System.currentTimeMillis()-time;
		}
		System.out.println("TBL:" + (total_time)/100.0);
		
		GraphAdj ga = GraphLoader.loadGraphAdjFromFile(filename);
		total_time = 0;
		for(int i = 0; i < 100;i++) {
			sv = new SimpleVector(randInt(matrix.length));
			time = System.currentTimeMillis();
			ga.aggregateWith(sv);
			total_time += System.currentTimeMillis()-time;
		}
		System.out.println("Ad:" + (total_time)/100.0);
	}

	// create a random integer vector served attribute values
	private static int[] randInt(int length) {
		int[] result = new int[length];
		Random r = new Random();
		r.setSeed(System.nanoTime());
		for(int i = 0; i < length; i++) {
			result[i] = r.nextInt(10);
		}
		return result;
	}
}
