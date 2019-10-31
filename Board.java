import java.io.*;
import java.util.*;

public class Board {
	int size;
	Player p1;
	Player p2;
	boolean turn = true; // if turn is true, it's player 1's turn, else it's player 2's turn

	Board(int n) {
		size = n;
		p1 = new Player(n, true);
		p2 = new Player(n, false);
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
