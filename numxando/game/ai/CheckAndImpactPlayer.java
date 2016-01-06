/**
 * @file        CheckAndImpactPlayer.java
 * @author      Abhiroop Tandon 20061667
 * @assignment  XandO
 * @brief       AI class for the computer player for numeric tic tac toe 
 * 				computer goes for the winning move if any or else 
 * 				goes for the blocking move if any or else chooses a 
 * 				randomly generated move.    
 */

package wit.cgd.numxando.game.ai;

import java.util.Random;

import wit.cgd.numxando.game.BasePlayer;
import wit.cgd.numxando.game.Board;

public class CheckAndImpactPlayer extends BasePlayer {

	private Random randomGen;
	public String numbersEven = "2468";
	public String numbersOdd = "13579";

	public CheckAndImpactPlayer(Board board) {
		super(board);

		randomGen = new Random();
		name = "Check And Impact Player";
	}

	@Override
	public int move() {
		
		//int dragNumeral =Character.getNumericValue((numbersEven.charAt(randomGen.nextInt(4))));

		// Step 1 - check for a win in next move for me => go for the win
		for(int i = 0; i < numbersEven.length(); i++)
		{
			int dragNumeral = Character.getNumericValue(numbersEven.charAt(i));
			if(!board.used[dragNumeral]){
				for (int r = 0; r < 3; r++) {
					for (int c = 0; c < 3; c++) {
						if (board.cells[r][c] == board.EMPTY && !board.used[dragNumeral]) {
							if (board.hasWon(dragNumeral, r, c)){
								System.out.println("Ai 1 " + dragNumeral + r + c);
								return ((r * 3 + c)*10) + dragNumeral;
							}
						}
					}
				}
			}
		}

		// Step 2 - check for a win in next move for other player => block the
		// fucker
		for(int i = 0; i < numbersOdd.length(); i++)
		{
			int checkNumeral = Character.getNumericValue(numbersOdd.charAt(i));
			if(!board.used[checkNumeral]){
				for (int r = 0; r < 3; r++) {
					for (int c = 0; c < 3; c++) {
						if (board.cells[r][c] == board.EMPTY && !board.used[checkNumeral]) {
							if (board.hasWon(checkNumeral, r, c)){
								System.out.println("Ai 2 found this " + checkNumeral + r + c);
								for(int j = 0; j < numbersEven.length(); j++)
								{
									int dragNumeral = Character.getNumericValue(numbersEven.charAt(j));
									if(!board.used[dragNumeral]){
										System.out.println("Ai 2 placing this " + dragNumeral + r + c);
										return ((r * 3 + c)*10) + dragNumeral;
									}
								}
							}
						}
					}
				}
			}
		}

		/*// Step 3 - imply standard RandomImpactSpacePlayer strategy
		dragNumeral =Character.getNumericValue((numbersEven.charAt(randomGen.nextInt(4))));
		if (board.cells[1][1] == board.EMPTY){
			System.out.println("Center");
			return ((1 * 3 + 1)*10) + dragNumeral;
		}*/

		/*// checking the corners of the board
		else if (board.cells[0][0] == board.EMPTY
				|| board.cells[0][2] == board.EMPTY
				|| board.cells[2][0] == board.EMPTY
				|| board.cells[2][2] == board.EMPTY) {
			while (true) {
				int p = randomGen.nextInt(9);
				if (p % 2 == 0 && p != 4) {
					dragNumeral =Character.getNumericValue((numbersEven.charAt(randomGen.nextInt(4))));
					if (board.cells[p / 3][p % 3] == board.EMPTY && !board.used[dragNumeral]){
						System.out.println("Corners");
						return p*10 + dragNumeral;
					}
				}
			}
		}*/

		/*// else generate a random number and complete the turn
		else {*/
			while (true) {
				int p = randomGen.nextInt(9);
				int dragNumeral =Character.getNumericValue((numbersEven.charAt(randomGen.nextInt(4))));
				if (board.cells[p / 3][p % 3] == board.EMPTY && !board.used[dragNumeral]){
					System.out.println("Random number");
					return p*10 + dragNumeral;
				}
			}
		}
	//}
}
