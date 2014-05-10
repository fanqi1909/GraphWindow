package data;

import java.util.Random;

/**
 * a simplified implementation of Trie, the content is 1 byte only and the 1 byte is the 0-1
 * @author a0048267
 *
 */
public class Trie {
	private int col_size;
	Node root;
	public Trie() {
		col_size = -1;
		root = new Node();
	}
	public void addBytes(byte[] string){
		if(col_size == -1) {
			col_size = string.length;
		} else{
			assert string.length == col_size;
		}
		root.add(string);
	}
	
	public boolean contains(byte[] string) {
		return root.checkConent(string);
	}
	
	public byte[][] traverse() {
		return root.traverse(col_size);
	}
	
	public static void main(String[] args) {
		byte[][] b = new byte[3000][3000];
		
		Random r = new Random();
		for(int i = 0; i < 3000; i++) {
			for(int j = 0; j < 3000; j++) {
				b[i][j] = (byte) r.nextInt(2);
			}
		}
		long time = System.currentTimeMillis();
		Trie t = new Trie();
		for(byte[] bb : b) {
			t.addBytes(bb);
		}
		System.out.println(System.currentTimeMillis() - time);
		@SuppressWarnings("unused")
		byte[][] b3 = t.traverse();
	//	io.Consoles.printMatrix(b3);
	}
}

class Node {
	public Node[] children;
	public boolean term;
	public int count = 0;
	
	public Node() {
		term = false;
		children = new Node[2];
	}

	public boolean checkConent(byte[] string) {
		return checkContent(string, 0); 
	}
	
	public boolean checkContent(byte[] string, int len) {
		if(len > string.length -1) {
			return term;
		} else if(children[string[len]] == null) {
			return false;
		} else {
			return children[string[len]].checkContent(string, len + 1);
		}
	}

	public void add(byte[] string) {
		if(add(string, 0)) {
			count++;
		}
	}
	
	
	public int getCount(){
		return count;
	}
	
	public boolean add(byte[] string, int len) {
		if(len == string.length) {
			if(!term) {
				term = true;
				return true;
			} else {
				return false; //already exists
			}
		} 
		if(children[string[len]] == null) { // cleared
			children[string[len]] = new Node();
		} 
		return children[string[len]].add(string, len + 1);
	}
	
	@Override
	public String toString() {
		String s = "";
		for(int i= 0; i < 2; i++) {
			if(children[i] != null) {
				s += i+":" + children[i].toString();
			}
		}
		return s + " "+ term;
	}
	
	/**
	 * a global variable is used to record the row 
	 */
	static int index = 0;
	
	/**
	 * traverse the entire Trie to ouput a 2-D array
	 * use DFS traversal
	 * input n is the column length of the result
	 * @return
	 */
	public byte[][] traverse(int n) {
		int m = count;
		//prepare the result placeholder, this is to ensure that no big chunk of data is moving around
		byte[][] result = new byte[m][n];
		byte[] s = new byte[n];
		//reset output index
		index = 0;
		traverse(s, 0 , result);
		return result;
	}
	/**
	 * a more memory-efficient way to traverse
	 * by predicting the result size;
	 * DFS + backtracking, possible stack overflow, but can resize the stack. It grows linearly
	 * @param s
	 * @param i
	 * @param result
	 */
	private void traverse(byte[] s, int i, byte[][] result) {
		if(term){
			System.arraycopy(s, 0, result[index++], 0, i);
		} else {
			if(children[0] != null) {
				s[i++] = 0;
				children[0].traverse(s,i, result);
				i--;
			}
			if(children[1] != null) {
				s[i++] = 1;
				children[1].traverse(s, i, result);
				i--;
			}
		}
	}
}
