/**
 * @file TicTacToe.java
 * @author kmurphy
 * @practical lab-07
 * @brief
 */

import java.awt.Font;
import java.util.ArrayList;

public class NumericTicTacToe {
	// This flag is to control the level of debug messages generated
	final static boolean DEBUG = true;
	final static int EMPTY = 0;
	static ArrayList<Integer> X_SHAPE = new ArrayList<Integer>();
	static ArrayList<Integer> O_SHAPE = new ArrayList<Integer>();
	static int[][] board = new int[3][3];
	static int row, col;
	// static int move;
	private static int move;

	public static boolean checkWin() {
		/*
		 * Check for win again using the absolute value to end the game if a
		 * wining move is played who is the winner depends on whose turn was it
		 */
		if (board[0][0] != EMPTY && board[0][1] != EMPTY && board[0][2] != EMPTY 
				&& (Math.abs(board[0][0] + board[0][1] + board[0][2]) == 15))
			return true;
		if (board[1][0] != EMPTY && board[1][1] != EMPTY && board[1][2] != EMPTY 
				&& (Math.abs(board[1][0] + board[1][1] + board[1][2]) == 15))
			return true;
		if (board[2][0] != EMPTY && board[2][1] != EMPTY && board[2][2] != EMPTY 
				&& (Math.abs(board[2][0] + board[2][1] + board[2][2]) == 15))
			return true;
		if (board[0][0] != EMPTY && board[1][0] != EMPTY && board[2][0] != EMPTY 
				&& (Math.abs(board[0][0] + board[1][0] + board[2][0]) == 15))
			return true;
		if (board[0][1] != EMPTY && board[1][1] != EMPTY && board[2][1] != EMPTY 
				&& (Math.abs(board[0][1] + board[1][1] + board[2][1]) == 15))
			return true;
		if (board[0][2] != EMPTY && board[1][2] != EMPTY && board[2][2] != EMPTY 
				&& (Math.abs(board[0][2] + board[1][2] + board[2][2]) == 15))
			return true;
		if (board[0][0] != EMPTY && board[1][1] != EMPTY && board[2][2] != EMPTY 
				&& (Math.abs(board[0][0] + board[1][1] + board[2][2]) == 15))
			return true;
		if (board[0][2] != EMPTY && board[1][1] != EMPTY && board[2][0] != EMPTY 
				&& (Math.abs(board[0][2] + board[1][1] + board[2][0]) == 15))
			return true;
			
		else
			return false;
	}

	public static void boardDisplay() {
		
	}

	public static int userInput() {
		int number = 0;
		StdDraw.setCanvasSize(256, 512);
		StdDraw.setPenRadius(0.04); // draw thicker lines
		StdDraw.line(0, 0.0, 1, 0.0);
		StdDraw.line(0, 0.25, 1, 0.25);
		StdDraw.line(0, 0.50, 1, 0.50);
		StdDraw.line(0, 0.75, 1, 0.75);
		StdDraw.line(0, 1, 1, 1);
		StdDraw.line(0, 0, 0, 1.0);
		StdDraw.line(1, 0, 1, 1.0);

		StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 100));
		StdDraw.text(.5, .1, String.valueOf(8));
		StdDraw.text(.5, .35, String.valueOf(6));
		StdDraw.text(.5, .6, String.valueOf(4));
		StdDraw.text(.5, .85, String.valueOf(2));

		while (true) {
			if (StdDraw.mousePressed()) {
				number = (int) (StdDraw.mouseY() * 4);
				StdOut.println("row " + number);
				StdOut.println("number " + (8 - (number * 2)));
				break;
			}
		}
		return (8 - (number * 2));
	}

	public static int playersTurn() {
		if (DEBUG)
			System.out.println("\tHuman move ...");
		
		StdOut.println("Enter a valid number");
		int number = StdIn.readInt();

		if (O_SHAPE.contains(number)) {
			// use mouse position to get slot
			while (true) {
				if (StdDraw.mousePressed()) {
					col = (int) (StdDraw.mouseX() * 3);
					row = (int) (StdDraw.mouseY() * 3);
					if (board[row][col] == EMPTY) { // valid move (empty slot)
						board[row][col] = number;
						O_SHAPE.remove(O_SHAPE.indexOf(number));
						break;
					}
				}
			}
		} else {
			StdOut.println("Wrong input...try again !!");
			playersTurn();
		}
		return board[row][col];
	}

	public static int computersTurn() {
		int number = 0;
		if (DEBUG)
			System.out.println("\tComputer finding move ...");

		// strategy - first free slot found at random
		while (true) {
			boolean turnTaken = false; // variable to check if turn has been
										// taken or not

			// checking each row
			for (int i = 0; i < 3; i++) {
				if (!turnTaken) {
					int count = 0;
					for (int j = 0; j < 3; j++) {
						if (board[i][j] != EMPTY)
							count++;
						else {
							row = i;
							col = j;
						}
					}
					if (count == 2) {
						for (int winNumber : X_SHAPE) {
							if (board[i][0] + board[i][1] + board[i][2]
									+ winNumber == 15) {
								StdOut.println("AI");
								board[row][col] = winNumber;
								turnTaken = true;
								break;
							}
						}
					}
				} else
					break;
			}

			// checking each column
			if (!turnTaken) {
				for (int j = 0; j < 3; j++) {
					if (!turnTaken) {
						int count = 0;
						for (int i = 0; i < 3; i++) {
							if (board[i][j] != EMPTY)
								count++;
							else {
								row = i;
								col = j;
							}
						}
						if (count == 2) {
							for (int winNumber : X_SHAPE) {
								if (board[0][j] + board[1][j] + board[2][j]
										+ winNumber == 15) {
									StdOut.println("AI");
									board[row][col] = winNumber;
									turnTaken = true;
									break;
								}
							}
						}
					} else
						break;
				}
			}

			// checking one diagonal
			if (!turnTaken) {
				int count = 0;
				for (int i = 0; i < 3; i++) {
					if (board[i][i] != EMPTY)
						count++;
					else {
						row = i;
						col = i;
					}

					if (count == 2) {
						for (int winNumber : X_SHAPE) {
							if (board[0][0] + board[1][1] + board[2][2]
									+ winNumber == 15) {
								StdOut.println("AI");
								board[row][col] = winNumber;
								turnTaken = true;
								break;
							}
						}
					}
				}
			}

			// checking one diagonal
			if (!turnTaken) {
				int count = 0;
				for (int i = 0; i < 3; i++) {
					if (board[i][2 - i] != EMPTY)
						count++;
					else {
						row = i;
						col = i;
					}

					if (count == 2) {
						for (int winNumber : X_SHAPE) {
							if (board[0][2] + board[1][1] + board[2][0]
									+ winNumber == 15) {
								StdOut.println("AI");
								board[row][col] = winNumber;
								turnTaken = true;
								break;
							}
						}
					}
				}
			}

			// using the random number generator to get a random position
			while (!turnTaken) {
				row = (int) (Math.random() * 3);
				col = (int) (Math.random() * 3);
				if (board[row][col] == EMPTY) { // valid move (empty slot)
					int randomNum = (int) (Math.random() * X_SHAPE.size());
					number = X_SHAPE.get(randomNum);
					board[row][col] = number;
					X_SHAPE.remove(X_SHAPE.indexOf(number));
					StdOut.println("place found at " + row + " " + col);
					turnTaken = true;
				}
			}
			break;
		}
		return board[row][col];
	}

	public static void main(String[] args) {
		for (int i = 1; i < 10; i++) {
			if (i % 2 == 0)
				O_SHAPE.add(i);
			else
				X_SHAPE.add(i);
		}

		// Setup graphics and draw empty board
		StdDraw.setPenRadius(0.04); // draw thicker lines
		StdDraw.line(0, 0.33, 1, 0.33);
		StdDraw.line(0, 0.66, 1, 0.66);
		StdDraw.line(0.33, 0, 0.33, 1.0);
		StdDraw.line(0.66, 0, 0.66, 1.0);

		StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 124));
		int number = 0;

		// Game loop - repeat until win or board is full
		move = 0;
		// number of moves played

		while (move < 9) {

			if (DEBUG)
				System.out.println("move = " + move);
			
			if (move % 2 == 0)
				number = computersTurn(); // Computer's move

			// the code below is for the human turn
			else {
				number = playersTurn();
			}
			
			if (DEBUG)
				System.out.println("\tMove at row = " + row + "\t col = " + col);

			// output the board
			if (DEBUG) {
				for (int r = 2; r >= 0; r--) {
					System.out.print("\t\t");
					for (int c = 0; c < 3; c++) {
						System.out.print(" " + board[r][c]);
					}
					System.out.println();
				}
			}
			
			double x = col * .33 + 0.15;
			double y = row * .33 + 0.15;
			StdDraw.text(x, y, String.valueOf(board[row][col]));
			
			if (checkWin())
				break;
			
			move++; // next move
		}

		// Output win/lose/draw message
		StdDraw.setPenColor(StdDraw.RED);
		if (move == 9) { // A draw
			StdDraw.text(0.5, 0.5, "A Draw");
		} else { // last player won
			StdDraw.text(0.5, 0.5, (move % 2 == 0 ? "I" : "You") + " Win");
		}
	}
}