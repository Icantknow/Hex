/**
 * Class Name: GameBoard
 * 
 * this is a class actually for game board to be set up
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Provides I/O.
 */
public class GameBoard extends JPanel {
	// number of rows/columns. Refers to number of intersections per row/column
	public static int SIZE; 

	public static int N_OF_TILES;
	public static final int TILE_SIZE = 40;
	public static final int BORDER_SIZE = TILE_SIZE;

	private Point lastMove;

	private Board board;
	private boolean cantMove = false;
	private boolean firstMove = true;
	private static final String[] PLAYER_NAME  = {"RED", "BLUE"};
	private JTextField statusField;

	/**
	 * display the status for each player
	 * @param textField status field to display status for each player
	 */
	public void setStatusField(JTextField textField)
	{
		statusField = textField;
	}

	/**
	 * set up game board
	 * 
	 * @param number board size
	 */
	public GameBoard(int number) {
		SIZE = number;
		N_OF_TILES = SIZE-1;
		this.setBackground(Color.WHITE);

		board = new Board(SIZE);

		this.addMouseListener(new MouseAdapter() {

			/**
			 * once the mouse is released, determines the coordinates of the closest intersection
			 * and places a tile on the hidden player boards. Also determines when someone has won.
			 */
			@Override
			public void mouseReleased(MouseEvent e) {

				// if someone's already won, this won't respond any more
				if (cantMove) {
					return;
				}

				// determines coordinates of nearest intersection
				int row = Math.round((float) (e.getY() - BORDER_SIZE) / TILE_SIZE);
				int col = Math.round((float) (e.getX() - BORDER_SIZE) / TILE_SIZE);

				// checks to see if position is valid
				if (row >= SIZE || col >= SIZE || row < 0 || col < 0) {
					return;
				}

				// checks to see if a piece has already been placed there
				// and switches player if it's been placed, but only on the
				// firstmove
				if (board.isOccupied(row, col)) {
					if (firstMove) {
						Player temp = board.p1;
						board.p1 = board.p2;
						board.p2 = temp;
					}
					return;
				}

				board.addStone(row, col);
				firstMove = false;
				lastMove = new Point(col, row);

				// converts row and column to position in board
				board.move(col + SIZE*(row)); 

				repaint();

				// once a player has won, displays a new pop-up with the victor
				if (board.win()) {
					cantMove = true;
					if (board.p1.win()) {
						statusField.setForeground(Color.blue);
						statusField.setText("Player 1 (Blue) Wins!");
					}
					else {
						statusField.setForeground(Color.red);
						statusField.setText("Player 2 (Red) Wins!");
					}
				}
				else {
					statusField.setForeground(Color.black);
					statusField.setText(PLAYER_NAME[board.getTurn()] + " to play");
				}
			}
		} );
	}

	/**
	 * reset the board
	 */
	public void doover() {
		board = new Board(SIZE);
		lastMove = null;
		cantMove = false;
		statusField.setText("");
	}

	// standard
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setColor(Color.BLACK);
		// draw rows
		for (int i = 0; i < SIZE; i++) {
			g2.drawLine(BORDER_SIZE, i * TILE_SIZE + BORDER_SIZE, TILE_SIZE
					* N_OF_TILES + BORDER_SIZE, i * TILE_SIZE + BORDER_SIZE);
		}
		// draw columns
		for (int i = 0; i < SIZE; i++) {
			g2.drawLine(i * TILE_SIZE + BORDER_SIZE, BORDER_SIZE, i * TILE_SIZE
					+ BORDER_SIZE, TILE_SIZE * N_OF_TILES + BORDER_SIZE);
		}

		// draw 
		for (int i = 0; i < SIZE; i++) {
			g2.drawLine(BORDER_SIZE, i * TILE_SIZE + BORDER_SIZE,
					i * TILE_SIZE + BORDER_SIZE, BORDER_SIZE);
			g2.drawLine(i * TILE_SIZE + BORDER_SIZE, BORDER_SIZE+(SIZE-1)*TILE_SIZE,
					BORDER_SIZE+(SIZE-1)*TILE_SIZE, i * TILE_SIZE + BORDER_SIZE);
		}

		//draw thick line
		g2.setStroke(new BasicStroke(4f));
		g2.setColor(Color.RED);
		g2.drawLine(BORDER_SIZE, BORDER_SIZE, (SIZE-1) * TILE_SIZE
				+ BORDER_SIZE, BORDER_SIZE);
		g2.drawLine(BORDER_SIZE, (SIZE-1) * TILE_SIZE + BORDER_SIZE, (SIZE-1) * TILE_SIZE
				+ BORDER_SIZE, (SIZE-1) * TILE_SIZE
				+ BORDER_SIZE);

		g2.setColor(Color.BLUE);
		g2.drawLine(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, (SIZE-1) * TILE_SIZE + BORDER_SIZE);
		g2.drawLine((SIZE-1) * TILE_SIZE + BORDER_SIZE, BORDER_SIZE, (SIZE-1) * TILE_SIZE
				+ BORDER_SIZE, (SIZE-1) * TILE_SIZE	+ BORDER_SIZE);

		g2.setColor(Color.BLACK);

		//set back to default value
		g2.setStroke(new BasicStroke());
		// iterate over intersections
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				Boolean state = board.getState(row, col);
				if (state != null) {
					if (state) {
						g2.setColor(Color.BLUE);
					} else {
						g2.setColor(Color.RED);
					}
					g2.fillOval(col * TILE_SIZE + BORDER_SIZE - TILE_SIZE / 4,
							row * TILE_SIZE + BORDER_SIZE - TILE_SIZE / 4,
							TILE_SIZE/2, TILE_SIZE/2);
				}
			}
		}

		// highlight last move
		if (lastMove != null) {
			g2.setColor(Color.RED);
			g2.drawOval(lastMove.x * TILE_SIZE + BORDER_SIZE - TILE_SIZE / 4,
					lastMove.y * TILE_SIZE + BORDER_SIZE - TILE_SIZE / 4,
					TILE_SIZE/2, TILE_SIZE/2);
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(N_OF_TILES * TILE_SIZE + BORDER_SIZE * 2,
				N_OF_TILES * TILE_SIZE + BORDER_SIZE * 2);
	}
}
