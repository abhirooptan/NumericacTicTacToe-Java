/**
 * @file        Board.java
 * @author      Abhiroop Tandon (20061667)
 * @assignment  NumericalXandO     
 */

package wit.cgd.numxando.game;

import wit.cgd.numxando.game.util.AudioManager;
import wit.cgd.numxando.game.util.Constants;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Board {

	public static final String	TAG	= Board.class.getName();

	public static enum GameState {
		PLAYING, DRAW, ODD_WON, EVEN_WON
	}

	public GameState		gameState;

	public final int	EMPTY	= 0;
	public int[][]	cells	= new int[3][3];
	public boolean[] used	= new boolean[10];

	public BasePlayer firstPlayer, secondPlayer;
	public BasePlayer currentPlayer;


	public Board() {
		init();
	}

	private void init() {
		for(int i = 0; i < 9; i++)
			used[i] = false;
		start();
	}

	public void render(SpriteBatch batch) {
		TextureRegion region = Assets.instance.board.region;
		batch.draw(region.getTexture(), -2, -Constants.VIEWPORT_HEIGHT / 2 + 0.1f, 0, 0, 4, 4, 1, 1, 0,
				region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(), false,
				false);

        
		for (int row = 0; row < 3; row++)
			for (int col = 0; col < 3; col++) {
				if (cells[row][col] == EMPTY) continue;
				//region = cells[row][col] == X ? Assets.instance.x.region : Assets.instance.o.region;
				region = Assets.instance.numbers.get(cells[row][col]-1).region;
				batch.draw(region.getTexture(), col * 1.4f - 1.9f, row * 1.4f - 2.3f, 0, 0, 1, 1, 1, 1, 0,
						region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(),
						false, false);
			}
		// draw drag and drop pieces
		
		region =  Assets.instance.numbers.get(0).region;
		batch.draw(region.getTexture(), (-1.5f) * 1.4f - 1.9f, 2 * 1.4f - 2.3f, 0, 0, 1, 1, 1, 1, 0,
                region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(),
                false, false);
		
		region =  Assets.instance.numbers.get(2).region;
		batch.draw(region.getTexture(), (-0.8f) * 1.4f - 1.9f, 2 * 1.4f - 2.3f, 0, 0, 1, 1, 1, 1, 0,
                region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(),
                false, false);
		
		region =  Assets.instance.numbers.get(4).region;
		batch.draw(region.getTexture(), (-1) * 1.4f - 1.9f, 1 * 1.4f - 2.3f, 0, 0, 1, 1, 1, 1, 0,
                region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(),
                false, false);
		
		region =  Assets.instance.numbers.get(6).region;
		batch.draw(region.getTexture(), (-1.5f) * 1.4f - 1.9f, 0 * 1.4f - 2.3f, 0, 0, 1, 1, 1, 1, 0,
                region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(),
                false, false);
		
		region =  Assets.instance.numbers.get(8).region;
		batch.draw(region.getTexture(), (-0.8f) * 1.4f - 1.9f, 0 * 1.4f - 2.3f, 0, 0, 1, 1, 1, 1, 0,
                region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(),
                false, false);
		
		region =  Assets.instance.numbers.get(1).region;
		batch.draw(region.getTexture(), (2.8f) * 1.4f - 1.9f, 2 * 1.4f - 2.3f, 0, 0, 1, 1, 1, 1, 0,
                region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(),
                false, false);
		
		region =  Assets.instance.numbers.get(3).region;
		batch.draw(region.getTexture(), (3.5f) * 1.4f - 1.9f, 2 * 1.4f - 2.3f, 0, 0, 1, 1, 1, 1, 0,
                region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(),
                false, false);
		
		region =  Assets.instance.numbers.get(5).region;
		batch.draw(region.getTexture(), (2.8f) * 1.4f - 1.9f, 0 * 1.4f - 2.3f, 0, 0, 1, 1, 1, 1, 0,
                region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(),
                false, false);
		
		region =  Assets.instance.numbers.get(7).region;
		batch.draw(region.getTexture(), (3.5f) * 1.4f - 1.9f, 0 * 1.4f - 2.3f, 0, 0, 1, 1, 1, 1, 0,
                region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(),
                false, false);
	}

	public void start() {
		for (int r = 0; r < 3; ++r)
			for (int c = 0; c < 3; ++c)
				cells[r][c] = EMPTY;
		gameState = GameState.PLAYING;
		currentPlayer = firstPlayer;
	}

	public boolean move() {
		return move(-1, -1, 0);
	}

	public boolean move(int row, int col, int dragNumeral) {
		
		
		
		//System.out.println("Moving " + currentPlayer.human);
		if (currentPlayer.human) {
			if (dragNumeral == 0 || used[dragNumeral]) return false;
			//AudioManager.instance.play(Assets.instance.sounds.first);
			if (row<0 || col<0 || cells[row][col]!=EMPTY) return false; 
		} else {								// computer player
			//System.out.println("Turn");
			int pos = currentPlayer.move();
			AudioManager.instance.play(Assets.instance.sounds.second);
			dragNumeral = pos%10;
			col = (pos/10)%3;
			row = (pos/10)/3;
		}
		
		// store move
		cells[row][col] = dragNumeral;
		used[dragNumeral] = true;
		for (int r = 0; r < 3; ++r) {
			for (int c = 0; c < 3; ++c) {
				System.out.print(cells[r][c]);
				}
			System.out.println();
			}
		
				
		if (hasWon(row, col)) {
			AudioManager.instance.play(Assets.instance.sounds.win);
			gameState = currentPlayer == firstPlayer ? GameState.ODD_WON : GameState.EVEN_WON; 
			
		} else if (isDraw()) {
			AudioManager.instance.play(Assets.instance.sounds.draw);
			gameState = GameState.DRAW;
		}

		// switch player
		if (gameState == GameState.PLAYING) {
			currentPlayer = (currentPlayer==firstPlayer ? secondPlayer : firstPlayer);
			//System.out.println("Players turn " + currentPlayer.toString());
		}
		
		return true;
	}
	
	public boolean isDraw() {
		for (int r = 0; r < 3; ++r) {
			for (int c = 0; c < 3; ++c) {
				if (cells[r][c] == EMPTY) {
					return false; // an empty seed found, not a draw, exit
				}
			}
		}
		return true; // no empty cell, it's a draw
	}
	
	// Return true if the player with has won after placing at (row, col)
	public boolean hasWon(int row, int col) {
		return ((cells[row][0] +   // 3-in-the-row
                cells[row][1] +
                cells[row][2] == 15
                && cells[row][0] != EMPTY
                && cells[row][1] != EMPTY
                && cells[row][2] != EMPTY)
                
          || (cells[0][col] + // 3-in-the-column
             cells[1][col] +
             cells[2][col] == 15
             && cells[0][col] != EMPTY
             && cells[1][col] != EMPTY
             && cells[2][col] != EMPTY)
             
          || (cells[0][0] + // 3-in-the-diagonal
             cells[1][1] +
             cells[2][2] == 15
             && cells[0][0] != EMPTY
             && cells[1][1] != EMPTY
             && cells[2][2] != EMPTY)
             
          || (cells[0][2] + // 3-in-the-opposite-diagonal
             cells[1][1] +
             cells[2][0] == 15
             && cells[0][2] != EMPTY
             && cells[1][1] != EMPTY
             && cells[2][0] != EMPTY));
	}
	
	// Return true if the player with has won after placing at (row, col)
		public boolean hasWon(int dragNumeral, int row, int col) {
			return ((cells[row][0] +   // 3-in-the-row
	                cells[row][1] +
	                cells[row][2] + dragNumeral == 15)
	                
	          || (cells[0][col] + // 3-in-the-column
	             cells[1][col] +
	             cells[2][col] + dragNumeral == 15)
	             
	          || (cells[0][0] + // 3-in-the-diagonal
	             cells[1][1] +
	             cells[2][2] + dragNumeral == 15)
	             
	          || (cells[0][2] + // 3-in-the-opposite-diagonal
	             cells[1][1] +
	             cells[2][0] + dragNumeral == 15)
	             );
		}
}
