package algo;

public class Optimizer {
	/**
	 * find the best k according to g', which is guaranteed to be greater the g(the real cost)
	 * 
	 * the function is 
	 * g'(k) = n * k + f(m/k)(m-k);
	 * where n is the # rows, m is # columns, f(.) is the max function of {2^(m/k), n}
	 * @return
	 */
	public static int getBestK(int n, int m) {
		double min = Double.MAX_VALUE, kscore;
		int bestk =1;
		for(int k = 1; k <= m; k++) {
			kscore = computeK(k, n,m);
		//	System.out.println(k+"\t"+kscore);
			if(min > kscore) {
				min = kscore;
				bestk = k;
			}
		}
		return bestk;
	}

	private static double computeK(int k, int n, int m) {
		double score = 0;
		score = k * n + f(m/k + 1, n) * (m - k);
		return score;
	}

	private static int f(int col, int row) {
		int lg2 = 1;
		while((1 << lg2) < row) lg2++;
		
		if(col > lg2) {
			return row;
		} else {
			return 1 << col;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(getBestK(2000,2000));
	}
}
