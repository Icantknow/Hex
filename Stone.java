/**
 * Hex game piece
 *
 */
public class Stone {

	public Chain chain;
	public State state;
	public int liberties;
	public int row;
	public int col;

	public Stone(int row, int col, State state) {
		chain = null;
		this.state = state;
		liberties = 4;
		this.row = row;
		this.col = col;
	}
}