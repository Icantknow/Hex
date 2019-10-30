import java.io.*;
import java.util.*;

/* note the players play on cells labeled with (assuming the board is size 11) a-k and 1-11. The kth cell of the mth row (like a6 is the first cell of the 6th row) is element (k-1)+n*(m-1)
 * the board looks like
 * a1 b1 c1 d1 e1
 * 	a2 b2 c2 d2 e2
 *   a3 b3 c3 d3 e3
 *    a4 b4 c4 d4 e4
 *     a5 b5 c5 d4 e4
 *  0  1  2  3  4
 *   5  6  7  8  9
 *   10 11 12 13 14
 *    15 16 17 18 19
 *     20 21 22 23 24
 */

public class Player {
	int size;
	UnionFind p;
	boolean[] board;
	
	/**
	 * constructor to create a player and set up their underlying board structure
	 * 
	 * @param n  determines the size of the board (how long a row is)
	 * @param first  determines whether this player plays first (so is trying to connect sides labeled with numbers) or second (so tries to connect sides labeled with letters)
	 */
	
	Player(int n, boolean first) {
		size = n;
		p = new UnionFind(n*n+2);
		board = new boolean[n];
		
		/* the first n^2 elements represent the n^2 cells of the board
		 * the last two represent the edges of the board, since we want to see when two edges are connected
		 * we automatically connect each of the edges to their constituent cells
		 */
		for (int i = 0; i < n; ++i) {
			if (first) {
				p.connected(n*n, n*i);
				p.connected(n*n+1, n*i + n-1);
			}
			else {
				p.connected(n*n, i);
				p.connected(n*n, n*(n-1) + i);
			}
		}
		
		/* booleans initialize at false anyway, but this just makes sure
		 */
		
		for (int i = 0; i < n; ++i) {
			board[i] = false;
		}
	}
	
	int[] around(int p) {
		ArrayList<Integer> tmp = new ArrayList<Integer>();
		int row, col;
		col = p%size;
		row = (int) (p-col)/size;
		
		if (!(col == 0)) {
			tmp.add(p-1);
		}
		if (!(col == size-1)) {
			tmp.add(p+1);
		}
		if (!(row == 0)) {
			tmp.add(p-size);
		}
		if (!(row == size-1)) {
			tmp.add(p+size);
		}
		if ((!(row == 0) && !(col == size-1))) {
			tmp.add(p-size+1);
		}
		if ((!(row == size-1) && !(col == 0))) {
			tmp.add(p+size-1);
		}
	}
	
	void put(int p) {
		
	}
}
