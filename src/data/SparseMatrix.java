package data;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


/**
 *  use compressed column form to store the upper/lower matrix
 *  the matrix itself should be immutable
 *  The CRS format is described in  {@link <a href="http://netlib.org/linalg/html_templates/node92.html#SECTION00931200000000000000"> CRS format}
 * @author a0048267
 *
 */
public class SparseMatrix implements MatrixTemplate<SparseMatrix> {
	private byte values[];
    private int rowIndices[];
    
    //# of elements in column [j] is columnPointers[j+1] - columnPointers[j];
    private int columnPointers[];
    
    private int row, column;
    
    public SparseMatrix(int row, int column, byte[] values, int rows[], int[] columns) {
    	this.row = row;
    	this.column = column;
    	this.values = values;
    	this.rowIndices = rows;
    	this.columnPointers = columns;
    }
   
    public SparseMatrix(byte[][] matrix) {
    	row = matrix.length;
    	column = matrix[0].length;
    	columnPointers = new int[column+1];
    	
    	//this may be inefficient, but only in terms of time
    	int count = 0;
    	for(int i = 0; i< row; i++){
    		for(int j = 0;j < column; j++) {
    			if(matrix[i][j] != 0) {
    				count++;
    			}
    		}
    	}
    	values = new byte[count];
    	rowIndices = new int[count];
    	
    	count=0;
    	for(int j = 0; j < column; j++) {
    		for(int i = 0; i < row; i++) {
    			if(matrix[i][j] != 0) {
    				values[count] = matrix[i][j];
    				rowIndices[count] = i;
    				count++;
    			}
    		}
    		columnPointers[j+1] = count;
    	}
    }
    
//	public SparseMatrix(SimpleMatrix matrix) {
//
//    }
    
    public SparseMatrix(SimpleMatrix simpleMatrix) {
		this(simpleMatrix.to2DArray());
	}

	/**
     * find the transformed index based on row, column
     * @param r
     * @param c
     * @return
     */
    private int findIndex(int r, int c) {
    	int rb = columnPointers[c];
    	int re = columnPointers[c+1];
    	return Arrays.binarySearch(rowIndices, rb, re, r);
    }
    
	@Override
	public byte get(int r, int c) {
		int index = findIndex(r,c);
		if(index >= 0) {
			return values[index];
		} else {
			return 0;
		}
	}
	@Override
	public byte set(int r, int c, int v) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public byte update(int r, int c, int v) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void add(int r, int c, int v) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void subtract(int r, int c, int v) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void multiply(int r, int c, int v) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void divide(int r, int c, int v) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void addAll(int v) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void subtractAll(int v) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void scaleWith(int scale) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void divideWith(int div) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void swapRow(int i, int j) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int getNumOfRows() {
		return row;
	}
	@Override
	public int getNumOfCols() {
		return column;
	}
	@Override
	public SparseMatrix multiplyWith(SparseMatrix sm) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public SparseMatrix multiplyWith01(SparseMatrix sm) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public SparseMatrix transpose() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void print() {
		System.out.println("Values:");
		for(int i = 0 ; i< values.length; i++) {
			System.out.printf("%3d", values[i]);
		}
		System.out.println("\nRowIndex:");
		for(int i = 0 ; i< rowIndices.length; i++) {
			System.out.printf("%3d", rowIndices[i]);
		}
		System.out.println("\nColumnIndex:");
		for(int i = 0 ; i< columnPointers.length; i++) {
			System.out.printf("%3d", columnPointers[i]);
		}
		System.out.println();
	}
	@Override
	public byte[] getRow(int i) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public byte[][] to2DArray() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setRow(byte[] bs, int bs_pos, int row, int length) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	public SparseMatrix clone() {
		return null;
	}
	
	/**
	 * get all the values in j'th column
	 * @param j
	 * @return
	 */
	public byte[] getColumn(int j){
		int rb = columnPointers[j];
		int re = columnPointers[j+1];
		byte[] result = new byte[row];
		for(int i = rb; i < re; i++) {
			result[rowIndices[i]] = values[i];
		}
		return result;
	}
	
	
	public List<Integer> getColumnIndex(int j) {
		int rb = columnPointers[j];
		int re = columnPointers[j+1];
		List<Integer> result = new LinkedList<>();
		for(int i = rb; i < re; i++) {
			result.add(rowIndices[i]);
		}
		return result;
	}
	
	
	public static void main(String[] args) {
		byte[][] matrix = new byte[][] {
				{10,0,0,0,-2,0},
				{3,9,0,0,0,3},
				{0,7,8,7,0,0},
				{3,0,8,7,5,0},
				{0,8,0,9,9,13},
				{0,4,0,0,2,-1}
		};
		
		SparseMatrix sm = new SparseMatrix(matrix);
		
		System.out.println(sm.column +"\t" + sm.row);
		
		io.Consoles.printVector(sm.values);
		io.Consoles.printVector(sm.rowIndices);
		io.Consoles.printVector(sm.columnPointers);
		
		io.Consoles.printVector(sm.getColumn(5));
		System.out.println(sm.getColumnIndex(5));
		
//		for(int i = 0; i < matrix.length; i++) {
//			for(int j = 0; j < matrix[0].length; j++) {
//				System.out.printf("%5d", sm.get(i, j));
//			}
//			System.out.println();
//		}
//		System.out.println(true);
		
		SparseMatrix[] sm2 = sm.stripSplit(2);
		System.out.println("--------------");
		//sm2[0].print();
		
		for(int i = 0; i < sm2[0].row; i++) {
			for(int j = 0; j < sm2[0].column; j++) {
				System.out.printf("%5d", sm2[0].get(i, j));
			}
			System.out.println();
		}
		
		System.out.println("--------------");
		for(int i = 0; i < sm2[1].row; i++) {
			for(int j = 0; j < sm2[1].column; j++) {
				System.out.printf("%5d", sm2[1].get(i, j));
			}
			System.out.println();
		}
	}
	
	public SparseMatrix[] stripSplit(int firstK) {
		assert firstK < columnPointers.length -1;
		
		//compute the first matrix
		int row, col;
		byte[] values;
		int[] rowIndex, colIndex;
		
		int vs, rows, cols;
		vs = columnPointers[firstK];
		rows = vs;
		cols = firstK + 1;
		
		values = new byte[vs];
		System.arraycopy(this.values, 0, values, 0, vs);
		rowIndex = new int[vs];
		System.arraycopy(this.rowIndices, 0, rowIndex, 0, vs);
		colIndex = new int[cols];
		System.arraycopy(this.columnPointers, 0, colIndex, 0, cols);
		SparseMatrix m1 = new SparseMatrix(this.row, firstK, values, rowIndex, colIndex);
		
		values = new byte[this.values.length - vs];
		System.arraycopy(this.values, vs, values, 0, values.length);
		rowIndex = new int[this.rowIndices.length - vs];
		System.arraycopy(this.rowIndices, vs, rowIndex, 0, rowIndex.length);
		colIndex = new int[this.columnPointers.length - cols + 1];
		for(int i = 1 ; i < colIndex.length; i++) {
			colIndex[i] = columnPointers[i + cols -1] - columnPointers[cols - 1];
		}
		SparseMatrix m2 = new SparseMatrix(this.row, this.column - firstK, values, rowIndex, colIndex);
		
		return  new SparseMatrix[] {m1, m2};
	}
}
