package data;

/**
 * The purpose of this interface is to separate implementation with underlying storage
 * T is parameterized by a the matrix itself
 * @author a0048267
 *
 */
public interface MatrixTemplate <T> {
	
	/**
	 * get the element at [row][column]
	 * @param r the row index;
	 * @param c the column index;
	 * @return
	 */
	public byte get(int r, int c);
	
	/**
	 *  set the value of element at [row][column]
	 *  value should not exceed 128, since the element is in byte  
	 * @param r the row index;
	 * @param c the column index;
	 * @param v the new value
	 * @return
	 */
	public byte set(int r, int c, int v);
	
	/**
	 *  update the value of element at [row][column]
	 * @param r the row index
	 * @param c the column index
	 * @param v the updated value
	 * @return
	 */
	public byte update(int r, int c, int v) ;
	
	/**
	 *  add new value to the element at [row][column]
	 * @param r the row index
	 * @param c the column index
	 * @param v the increment value
	 */
	public void add(int r, int c, int v);
	
	/**
	 *  subtract value from element at [row][column]
	 * @param r the row index
	 * @param c the column index
	 * @param v the subtracted value
	 */
	public void subtract(int r, int c, int v);
	
	/**
	 *  multiply a constant to element at [row][column]
	 * @param r
	 * @param c
	 * @param v
	 */
	public void multiply(int r, int c, int v) ;
	
	/**
	 *  division on element at [row][column] of a constant
	 * @param r
	 * @param c
	 * @param v
	 */
	public void divide(int r, int c, int v);
	
	/**
	 *  matrix addition, every element is added with value v
	 * @param v the value to be added
	 */
	public void addAll(int v);
	
	/**
	 * matrix subtraction, every element is subtracted with value v
	 * @param v
	 */
	public void subtractAll(int v);
	
	/**
	 * matrix scale, every element is scaled with scale
	 * @param scale
	 */
	public void scaleWith(int scale);
	
	/**
	 *  matrix division, every element is divided 
	 * @param div
	 */
	public void divideWith(int div);
	
	/**
	 * swap the row of i,j
	 * @param i
	 * @param j
	 */
	public void swapRow(int i , int j);
	
	/**
	 * the number of rows of this matrix
	 * @return
	 */
	public int getNumOfRows();
	
	/**
	 * the number of columns of this matrix
	 * @return
	 */
	public int getNumOfCols();
	
	/**
	 * matrix-matrix multiplication
	 * @param sm
	 * @return
	 */
	public T multiplyWith(T sm);
	
	/**
	 * the output is the 0-1 matrix
	 * @param sm
	 * @return
	 */
	public T multiplyWith01(T sm);
	
	/**
	 * copy the matrix to a new matrix
	 * @return
	 */
	public T clone();
	
	/**
	 * perform transpose
	 * @return
	 */
	public T transpose();
	
	/**
	 * properly print matrix
	 */
	public void print();
	
	/**
	 * return the ith row
	 * @param i
	 * @return
	 */
	public byte[] getRow(int i);
	
	/**
	 * return a two-d array
	 * @return
	 */
	public byte[][] to2DArray();
	
	/**
	 * copy entire row to the specific row at matri
	 * @param bs data
	 * @param bs_pos starting position
	 * @param row row index
	 * @param length  size to copy
	 */
	public void setRow(byte[] bs, int bs_pos, int row, int length);
	
	
}
