/**
 * Class Name: Board
 * 
 *
 */

public class Board {
	int size;
	Player p1;
	Player p2;
	// if turn is true, it's player 1's turn, else it's player 2's turn
	boolean turn = true; 
	// true means blue, false means red
	Boolean[][] colors; 

	Board(int n) {
		size = n;
		p1 = new Player(n, true);
		p2 = new Player(n, false);
		colors = new Boolean[size][size];
		for (int i = 0; i < size; ++i) {
			for (int j = 0; j < size; ++j) {
				colors[i][j] = false;
			}
		}
	}

	/**
	 * place piece on hidden board of the current player and alternates turn
	 * 
	 * @param p  position where piece is placed
	 */
	void move(int p) {
		if (turn) {
			p1.put(p);
		}
		else {
			p2.put(p);
		}
		turn = !turn;
	}

	/**
	 * determines if someone has won
	 */
	boolean win() {
		if (p1.win() || p2.win()) {
			return true;
		}
		return false;
	}

	/**
	 * returns true if given position is occupied by any stone
	 * 
	 * @param row  row number
	 * @param col  column number
	 */
	boolean isOccupied(int row, int col) {
		int location = col + size*(row);
		return (p1.played[location] || p2.played[location]);
	}

	/**
	 * returns state (blue/red) of given position or null if it's unoccupied.
	 * 
	 * @param row  row number
	 * @param col  column number
	 */
	public Boolean getState(int row, int col) {
		if (!isOccupied(row, col)) {
			return null;
		} else {
			return colors[row][col];
		}
	}

	/**
	 * adds stone to grid.
	 * 
	 * @param row  is row number
	 * @param col  is column number
	 */
	public void addStone(int row, int col) {
		if (turn) {
			colors[row][col] = true;
		}
		else {
			colors[row][col] = false;
		}
	}

	/**
	 * get next player
	 * @return 1 player 1, 0 player 2
	 */
	public int getTurn()
	{
		return turn == true ? 1 : 0;
	}

	/*
	public static void main(String[] args) {

		Board b = new Board(2);
		System.out.println(Boolean.toString(b.p1.win()) + " " + Boolean.toString(b.p2.win()));

		for (int i = 0; i < 4; ++i) {
			b.move(i);
			System.out.println(Boolean.toString(b.p1.win()) + " " + Boolean.toString(b.p2.win()));
		}

		Board b1 = new Board(9);
		for (int i = 0; i < 9; ++i) {
			for (int j = 0; j < 2; ++j) {
				b1.move(i + 9*j);
			}
		}
		System.out.println(b1.p1.win());
	}
	 */
}