/**
 * @file        RandomSpacePlayer.java
 * @author      Abhiroop Tandon (20061667)
 * @assignment  NumericalXandO
 * @brief		Generates a valid random number at a valid random
 * 				place. Works fine (i guess)      
 */

package wit.cgd.numxando.game.ai;

import wit.cgd.numxando.game.BasePlayer;
import wit.cgd.numxando.game.Board;

import java.util.Random;

public class RandomSpacePlayer extends BasePlayer {
	
	private Random randomGen;
	public String numbers = "2468";
	
	public RandomSpacePlayer(Board board) {
		super(board);
		name = "Random Generator";
		
		randomGen = new Random();
	}

	@Override
	public int move() {
		while(true){
			int pos = randomGen.nextInt(9);
			int dragNumeral =Character.getNumericValue((numbers.charAt(randomGen.nextInt(4))));
			System.out.println("value " + dragNumeral + " pos " + pos);
			if(board.cells[(pos/3)][(pos%3)] == board.EMPTY && !board.used[dragNumeral])
				return (pos*10)+dragNumeral;
		}
	}	
}
