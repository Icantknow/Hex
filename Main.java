/**
 * Class Name: Main
 * 
 * a controller class to set up the game board. board size can be any number but want to
 * limit it to be between 1 and 25 to fit the screen better.
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javax.swing.JOptionPane;

/**
 * Builds UI and starts the game.
 */
public class Main {
	public static final String TITLE = "Hex";
	public static final int BORDER_SIZE = 15;

	private JTextField textField = new JTextField();
	GameBoard board;

	/**
	 * main entry
	 * @param args
	 */
	public static void main(String[] args) {
		// asks players for board size and passes it into the making of the board
		int boardSize;
		do {
			String boardSizeInput = JOptionPane.showInputDialog("Input an integer from 1 to 25 for the board size:");
			if (boardSizeInput == null)
				return;
			boardSize = Integer.parseInt(boardSizeInput);
		} 
		while (boardSize < 1 || boardSize > 25);

		new Main().init(boardSize);
	}

	/**
	 * initialize the game board
	 * @param n board size
	 */
	private void init(int n) {
		JFrame frame = new JFrame();
		frame.setTitle(TITLE);

		//set up new game button
		JButton newGameButton = new JButton("New Game");
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());
		controlPanel.add(newGameButton);

		// creates game window
		JPanel container = new JPanel();
		container.setBackground(Color.GRAY);
		container.setLayout(new BorderLayout());
		frame.add(container);
		container.setBorder(BorderFactory.createEmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));

		board = new GameBoard(n);
		container.add(controlPanel , BorderLayout.NORTH);
		container.add(board, BorderLayout.CENTER);
		textField.setHorizontalAlignment(JTextField.CENTER);
		container.add(textField , BorderLayout.SOUTH);
		board.setStatusField(textField);
		newGameButton.addActionListener(new NewGameAction());


		frame.pack();
		frame.setResizable(false);
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
	}

	/**
	 * new game button handler
	 */
	private class NewGameAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			board.doover();
			board.repaint();
		}
	}
}