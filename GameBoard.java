import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Provides I/O.
 * 
 *
 */
public class GameBoard extends JPanel {

	public static final int SIZE = 9; // number of rows/columns. Refers to number of intersections per row/column
	
	public static final int N_OF_TILES = SIZE - 1;
	public static final int TILE_SIZE = 40;
	public static final int BORDER_SIZE = TILE_SIZE;



	private State current_player;
	private Grid grid;
	private Point lastMove;

	private Board b;
	private boolean cantMove = false;

	public GameBoard() {
		this.setBackground(Color.WHITE);
		grid = new Grid(SIZE);
		// Black always starts
		current_player = State.BLACK;

		b = new Board(SIZE);
		
		this.addMouseListener(new MouseAdapter() {
			
			/**
			 * once the mouse is released, determines the coordinates of the closest intersection
			 * and places a tile on the hidden player boards. Also determines when someone has won.
			 * 
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
				
				if (grid.isOccupied(row, col)) {
					return;
				}

				grid.addStone(row, col, current_player);
				lastMove = new Point(col, row);

				b.move(col + SIZE*(row)); // converts row and column to position in board

				// Switch current player
				if (current_player == State.BLACK) {
					current_player = State.WHITE;
				} else {
					current_player = State.BLACK;
				}
				repaint();
				
				// once a player has won, displays a new pop-up with the victor
				
				if (b.win()) {
					cantMove = true;
					JFrame win = new JFrame();

					if (b.p1.win()) {
						JOptionPane.showMessageDialog(win, "Player 1 wins!");
					}
					else {
						JOptionPane.showMessageDialog(win, "Player 2 wins!");
					}
				}
			}
		} );
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setColor(Color.BLACK);
		// Draw rows.
		for (int i = 0; i < SIZE; i++) {
			g2.drawLine(BORDER_SIZE, i * TILE_SIZE + BORDER_SIZE, TILE_SIZE
					* N_OF_TILES + BORDER_SIZE, i * TILE_SIZE + BORDER_SIZE);
		}
		// Draw columns.
		for (int i = 0; i < SIZE; i++) {
			g2.drawLine(i * TILE_SIZE + BORDER_SIZE, BORDER_SIZE, i * TILE_SIZE
					+ BORDER_SIZE, TILE_SIZE * N_OF_TILES + BORDER_SIZE);
		}
		// Iterate over intersections
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				State state = grid.getState(row, col);
				if (state != null) {
					if (state == State.BLACK) {
						g2.setColor(Color.BLUE);
					} else {
						// g2.setColor(Color.WHITE);
						g2.setColor(Color.RED);
					}
					g2.fillOval(col * TILE_SIZE + BORDER_SIZE - TILE_SIZE / 2,
							row * TILE_SIZE + BORDER_SIZE - TILE_SIZE / 2,
							TILE_SIZE, TILE_SIZE);
				}
			}
		}
		// Highlight last move
		if (lastMove != null) {
			g2.setColor(Color.RED);
			g2.drawOval(lastMove.x * TILE_SIZE + BORDER_SIZE - TILE_SIZE / 2,
					lastMove.y * TILE_SIZE + BORDER_SIZE - TILE_SIZE / 2,
					TILE_SIZE, TILE_SIZE);
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(N_OF_TILES * TILE_SIZE + BORDER_SIZE * 2,
				N_OF_TILES * TILE_SIZE + BORDER_SIZE * 2);
	}

}