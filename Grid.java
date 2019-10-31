public class Grid {

	private final int SIZE;
	private Stone[][] stones;

	public Grid(int size) {
		SIZE = size;
		stones = new Stone[SIZE][SIZE];
	}

	/**
	 * adds stone to grid.
	 * 
	 * @param row  is row number
	 * @param col  is column number
	 */
	public void addStone(int row, int col, State state) {
		Stone newStone = new Stone(row, col, state);
		stones[row][col] = newStone;
		// Check neighbors
		Stone[] neighbors = new Stone[4];
		// don't check outside the board
		if (row > 0) {
			neighbors[0] = stones[row - 1][col];
		}
		if (row < SIZE - 1) {
			neighbors[1] = stones[row + 1][col];
		}
		if (col > 1) {
			neighbors[2] = stones[row][col - 1];
		}
		if (col < SIZE - 1) {
			neighbors[3] = stones[row][col + 1];
		}
		// Prepare Chain for this new Stone
		Chain finalChain = new Chain(newStone.state);
		for (Stone neighbor : neighbors) {
			// do nothing if no adjacent stone
			if (neighbor == null) {
				continue;
			}

			newStone.liberties--;
			neighbor.liberties--;

			// ff it's different color than newStone check him
			if (neighbor.state != newStone.state) {
				checkStone(neighbor);
				continue;
			}

			if (neighbor.chain != null) {
				finalChain.join(neighbor.chain);
			}
		}
		finalChain.addStone(newStone);
	}

	/**
	 * check liberties of stone
	 * 
	 * @param stone  is the stone we are checking
	 */
	public void checkStone(Stone stone) {
		if (stone.chain.getLiberties() == 0) {
			for (Stone s : stone.chain.stones) {
				s.chain = null;
				stones[s.row][s.col] = null;
			}
		}
	}

	/**
	 * returns true if given position is occupied by any stone
	 * 
	 * @param row  row number
	 * @param col  column number
	 */
	public boolean isOccupied(int row, int col) {
		return stones[row][col] != null;
	}

	/**
	 * returns state (black/white) of given position or null if it's unoccupied.
	 * 
	 * @param row  row number
	 * @param col  column number
	 */
	public State getState(int row, int col) {
		Stone stone = stones[row][col];
		if (stone == null) {
			return null;
		} else {
			return stone.state;
		}
	}
}