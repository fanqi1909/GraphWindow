package data;

public class SimpleMatrix implements MatrixTemplate<SimpleMatrix> {
	//store a 2-d matrix using 1-D array
	private byte data[];
	private final int col, row;
	
	public SimpleMatrix() {
		initData(16);
		col = 4; row =4;
	}
	
	public SimpleMatrix(int size) {
		col = row = size;
		initData(size * size);
	}
	
	public SimpleMatrix(int r, int c) {
		row = r; col = c;
		initData(r*c);
	}
	
	public SimpleMatrix(byte[][] data2) {
		row = data2.length;
		col = data2[0].length;
		initData(row * col);
		for(int i = 0; i < row; i++) {
			System.arraycopy(data2[i], 0, data, i*col, col);
		}
	}
	

	private void initData(int size) {
		data = new byte[size];
	}
	
	public byte get(int r, int c) {
		return data[r* col + c];
	}
	
	public byte set(int r, int c, int v) {
		return data[r*col+c] = (byte)v;
	}
	
	public byte update(int r, int c, int v) {
		return set(r,c,v);
	}
	
	public void add(int r, int c, int v) {
		data[r*col+c] +=v;
	}
	
	public void subtract(int r, int c, int v) {
		add(r,c,-v);
	}
	
	public void multiply(int r, int c, int v) {
		data[r * col + c] *= v;
	}
	
	public void divide(int r, int c, int v) {
		data[r*col + c] /= v;
	}
	
	public void addAll(int v) {
		for(int i = 0; i < data.length; i++) {
			data[i] += v;
		}
	}
	
	public void subtractAll(int v) {
		addAll(-v);
	}
	
	public void scaleWith(int scale) {
		for(int i = 0; i < data.length; i++) {
			data[i] *= scale;
		}
	}
	
	public void divideWith(int div) {
		for(int i = 0; i < data.length; i++) {
			data[i] /= div;
		}
	}
	
	public void swapRow(int i , int j) {
		byte[] tmp = new byte[col];
		int is = i * col, js = j * col, size = col;
		System.arraycopy(data, is, tmp, 0, size);
		System.arraycopy(data, js, data, is, size);
		System.arraycopy(tmp, 0, data, js, size);
	}
	
	public int getNumOfRows() {
		return row;
	}
	
	public int getNumOfCols() {
		return col;
	}
	
	public SimpleMatrix multiplyWith(SimpleMatrix sm) {
		assert col == sm.getNumOfRows();
		
		SimpleMatrix result = new SimpleMatrix(row, sm.getNumOfCols());
		int sum = 0;
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < sm.getNumOfCols(); j++) {
				sum = 0;
				for(int k = 0; k < col; k++) {
					sum += data[i*col + k] * sm.get(k, j);
				}
				result.set(i, j, sum);
			}
		}
		return result;
	}
	
	
	
	public SimpleMatrix multiplyWith01(SimpleMatrix sm) {
		assert col == sm.getNumOfRows();
		SimpleMatrix result = new SimpleMatrix(row, sm.getNumOfCols());
		int sum = 0;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < sm.getNumOfCols(); j++) {
				sum = 0;
				for (int k = 0; k < col && sum == 0; k++) {
					sum += data[i * col + k] * sm.get(k, j);
				}
				result.set(i, j, sum > 0? 1 : 0);
			}
		}
		return result;
	}
	
	public SimpleMatrix clone() {
		SimpleMatrix sm = new SimpleMatrix(row, col);
		System.arraycopy(data, 0, sm.data, 0, row*col);
		return sm;
	}
	
	public SimpleMatrix transpose() {
		SimpleMatrix sm = new SimpleMatrix(col, row);
		//has to copy 1 by 1
		for(int i = 0; i < data.length; i++) {
			sm.data[i%col * row + i /col] = data[i];
		}
		
		//System.arraycopy(data, 0, sm.data, 0, row*col);
		return sm;
	}
	
	public void print(){
		System.out.println("<"+ row+ "\t" + col +">");
		for(int i = 0 ; i < row; i++) {
			for(int j = 0; j < col; j++) {
				System.out.printf("%3d", data[i*col+j]);
			}
			System.out.println();
		}
	}
	
	//some tests
	public static void main(String[] args) {
		byte[][] data = new byte[7][7];
		java.util.Random r= new java.util.Random();
		for(int i =0 ;i < 7; i++) {
			for(int j = 0; j < 7; j++) {
				data[i][j] = (byte) r.nextInt(2);
			}
		}
		
		SimpleMatrix sm = new SimpleMatrix(data);
		sm.print();
		
		System.out.println();
		sm.scaleWith(2);
		sm.print();
		System.out.println();
		
		sm.divideWith(2);
		sm.print();
		System.out.println();
		
		sm.swapRow(0, 3);
		sm.print();
		System.out.println();
		
		sm.addAll(50);
		sm.print();
		System.out.println();
		
		sm.subtractAll(50);
		sm.print();
		System.out.println();
		
		for(int i = 0; i < 7; i++) {
			for(int j = 0; j < 7; j++) {
				assert sm.get(i, j) == data[i][j];
			}
		}
		
		SimpleMatrix id = SimpleMatrix.IdentityMatrix(5);
		id.print();
		
		SimpleMatrix mul = sm.multiplyWith(sm);
		mul.print();
		
		mul = sm.multiplyWith01(sm);
		mul.print();
	}
	
	public byte[] getRow(int i) {
		byte[] result = new byte[col];
		System.arraycopy(data, i*col, result, 0, col);
		return result;
	}
	
	public byte[][] to2DArray() {
		byte[][] result = new byte[row][col];
		for(int i = 0; i < col; i++) {
			System.arraycopy(data, i*col, result[i], 0, col);
		}
		return result;
	}
	
	/**
	 * identity matrix of size length
	 * @param length
	 * @return
	 */
	public static SimpleMatrix IdentityMatrix(int length) {
		SimpleMatrix s = new SimpleMatrix(length);
		for(int i = 0; i < length; i++) {
			s.set(i, i, 1);
		}
		return s;
	}
	
	public void setRow(byte[] bs, int bs_pos, int row, int length) {
		System.arraycopy(bs, bs_pos, data, row*col, length);
	}
}
