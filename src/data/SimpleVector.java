package data;
/**
 * vector is integer based!!
 * @author a0048267
 *
 */
public class SimpleVector {
	private int[] data;
	private final int size;
	
	public SimpleVector() {
		size = 10;
		data = new int[size];
	}
	
	
	public SimpleVector(int size) {
		this.size =size;
		data = new int[size];
	}
	
	public SimpleVector(int[] data2) {
		size = data2.length;
		data = new int[size];
		System.arraycopy(data2, 0, data, 0, size);
	}
	
	public void set(int index, int value) {
		data[index] = value;
	}
	
	public int get(int index) {
		return data[index];
	}
	
	public int getSize() {
		return size;
	}
	
	public int[] getData() {
		return data;
	}
	
	public void print() {
		for(int i = 0; i < size; i++) {
			System.out.printf("%3d", data[i]);
		}
		System.out.println();
	}
}
 