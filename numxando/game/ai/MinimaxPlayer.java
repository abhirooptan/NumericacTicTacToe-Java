/**
 * @file        MinimaxPlayer.java
 * @author      Abhiroop Tandon (20061667)
 * @assignment  NumericalXandO  
 * @brief		doesnt works as expected
 * 				Moves at the same place all the time
 * 				but does finds a correct piece to move
 * 				and sometimes wins as well   
 */
package wit.cgd.numxando.game.ai;

import java.util.Random;

import com.badlogic.gdx.Gdx;

import wit.cgd.numxando.game.BasePlayer;
import wit.cgd.numxando.game.Board;

public class MinimaxPlayer extends BasePlayer {

	int skill;
	
    private Random randomGenerator;

    public MinimaxPlayer(Board board) {
        super(board);
        name = "MinimaxPlayer";

        skill = 5;  // skill is measure of search depth

        randomGenerator = new Random();
    }

    @Override
    public int move () {
        return (int) minimax("2468", "13579", 0);
    }

    private float minimax(String mySymbol, String opponentSymbol, int depth) {

        final float WIN_SCORE = 100;        
        final float DRAW_SCORE = 0;

        float score;
        float maxScore = -10000;
        int maxPos = -1;

        // for each board position
        for(int i = 0; i < mySymbol.length(); i++)
		{
			int dragNumeral = Character.getNumericValue(mySymbol.charAt(i));
			if(!board.used[dragNumeral]){
		        for (int r=0; r<3; ++r) { 
		            for (int c=0; c<3; ++c) {
		
		                // skip over used positions
		                if (board.cells[r][c]!=board.EMPTY) continue;
		                
		                
		
		                String indent = new String(new char[depth]).replace("\0", "  ");
		                //Gdx.app.log(indent, "search ("+r+","+c+")");
		
		                // place move 
		                board.cells[r][c] = Character.getNumericValue(dragNumeral);
		                
		
		                // evaluate board (recursively)
		                if (board.hasWon(dragNumeral, r, c)) {
		                    score = WIN_SCORE;
		                    //board.used[dragNumeral] = true;
		                } else if (board.isDraw()) {
		                    score = DRAW_SCORE;
		                } else {
		                    if (depth<skill) {
		                        score = -minimax(opponentSymbol, mySymbol, depth+1);
		                    } else {
		                        score = 0;
		                    }
		                }
		
		                // update ranking
		
		                if (Math.abs(score-maxScore)<1.0E-5 && randomGenerator.nextDouble()<0.1) {
		                    maxScore = score;
		                    maxPos = 3*r+c;
		
		                } else if (score>maxScore) {    // clear 
		                    maxScore = score;
		                    maxPos = 3*r+c;
		                } 
		
		                //Gdx.app.log(indent, "Score "+score);
		
		                // undo move 
		                board.cells[r][c] = board.EMPTY;
		
		            }
		        }
			}
		}
		
		        // on uppermost call return move not score
		        return (depth==0? maxPos : maxScore);
		
		    };

}
