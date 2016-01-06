/**
 * @file        WorldController.java
 * @author      Abhiroop Tandon (20061667)
 * @assignment  NumericalXandO     
 */
package wit.cgd.numxando.game;

import wit.cgd.numxando.game.ai.CheckAndImpactPlayer;
import wit.cgd.numxando.game.ai.MinimaxPlayer;
import wit.cgd.numxando.game.util.GameStats;

import com.badlogic.gdx.Game;

import wit.cgd.numxando.screens.MenuScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

public class WorldController extends InputAdapter {

	@SuppressWarnings("unused")
	private static final String	TAG	= WorldController.class.getName();
	public float				viewportWidth;
	public int					width, height;
	float						timeLeftGameOverDelay;

	public Board				board;
	
	private Game                game;
	
	boolean                     dragging = false;
    int                         dragX, dragY;
    int                         dragNumeral;

	private void backToMenu() {
	    // switch to menu screen
	    game.setScreen(new MenuScreen(game));
	}

	public WorldController(Game game) {
	    this.game = game;
	    init();
	}

	private void init() {
		Gdx.input.setInputProcessor(this);
		board = new Board();
		board.firstPlayer = new HumanPlayer(board);
		//board.secondPlayer = new CheckAndImpactPlayer(board);
		board.secondPlayer = new MinimaxPlayer(board);
		board.start();

		timeLeftGameOverDelay = 2;
	}

	@SuppressWarnings("static-access")
	public void update(float deltaTime) {
	    if (board.gameState == Board.GameState.PLAYING) {
	        board.move();
	    }

	    if (board.gameState != Board.GameState.PLAYING) {
	        timeLeftGameOverDelay -= deltaTime;
	        if (timeLeftGameOverDelay < 0) {
	        	if (board.gameState== board.gameState.ODD_WON) {
		    	    GameStats.instance.win();
		    	} else if (board.gameState== board.gameState.EVEN_WON) {
		    	    GameStats.instance.lose();
		    	} else {
		    	    GameStats.instance.draw();
		    	}
	            backToMenu();
	        }
	    }
	}
	
	@Override
	public boolean keyUp(int keycode) {
	    if (keycode == Keys.ESCAPE || keycode == Keys.BACK) {
	        backToMenu();
	    }
	    return false;
	}

	@Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (board.gameState == Board.GameState.PLAYING && board.currentPlayer.human) {

            // convert to cell position
            int row = 4 * (height - screenY) / height;
            int col = (int) (viewportWidth * (screenX - 0.5 * width) / width) + 1;

            // board move - just place piece and return
            if (row >= 0 && row < 3 && col >= 0 && col < 3) {
                board.move(row, col, dragNumeral);
                return true;
            }

            dragX = screenX;
            dragY = screenY;

            // check if valid start of a drag for first player
            if (row == 2 && col == -2 && board.currentPlayer==board.firstPlayer) {
                dragging = true;
                dragNumeral = 1;
                return true;
            }
            
            if (row == 2 && col == -1 && board.currentPlayer==board.firstPlayer) {
                dragging = true;
                dragNumeral = 3;
                return true;
            }
            
            if (row == 1 && col == -1 && board.currentPlayer==board.firstPlayer) {
                dragging = true;
                dragNumeral = 5;
                return true;
            }
            
            if (row == 0 && col == -2 && board.currentPlayer==board.firstPlayer) {
                dragging = true;
                dragNumeral = 7;
                return true;
            }
            
            if (row == 0 && col == -1 && board.currentPlayer==board.firstPlayer) {
                dragging = true;
                dragNumeral = 9;
                return true;
            }
            
            
            // check if valid start of a drag for second player
            if (row == 2 && col == 3 && board.currentPlayer==board.secondPlayer) {
                dragging = true;
                dragNumeral = 2;
                return true;
            }
            
            if (row == 2 && col == 4 && board.currentPlayer==board.secondPlayer) {
                dragging = true;
                dragNumeral = 4;
                return true;
            }
            
            if (row == 0 && col == 3 && board.currentPlayer==board.secondPlayer) {
                dragging = true;
                dragNumeral = 6;
                return true;
            }
            
            if (row == 0 && col == 4 && board.currentPlayer==board.secondPlayer) {
                dragging = true;
                dragNumeral = 8;
                return true;
            }

        }

        return true;
    }
	
	@Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        dragX = screenX;
        dragY = screenY;        
        return true;
    }
	
	@Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

		System.out.println(dragNumeral);
		
        if (!dragging) dragNumeral = 0;
        
        dragging = false;

        // convert to cell position
        int row = 4 * (height - screenY) / height;
        int col = (int) (viewportWidth * (screenX - 0.5 * width) / width) + 1;

        // if we were not dragging then no move to place
        if (dragNumeral==0) return true;

        // if a valid board cell then place piece
        
        if (row >= 0 && row < 3 && col >= 0 && col < 3) {
            board.move(row, col, dragNumeral);
            return true;
        }

        return true;
    }
}
