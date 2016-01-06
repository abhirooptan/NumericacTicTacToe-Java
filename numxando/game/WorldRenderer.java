/**
 * @file        WorldRenderer.java
 * @author      Abhiroop Tandon (20061667)
 * @assignment  NumericalXandO     
 */

package wit.cgd.numxando.game;

import wit.cgd.numxando.game.util.Constants;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public class WorldRenderer implements Disposable {

	@SuppressWarnings("unused")
	private static final String	TAG	= WorldRenderer.class.getName();

	public OrthographicCamera	camera;
	public OrthographicCamera	cameraGUI;
	
	private SpriteBatch			batch;
	private WorldController		worldController;

	public WorldRenderer(WorldController worldController) {
		this.worldController = worldController;
		init();
	}

	private void init() {
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		camera.position.set(0, 0, 0);
		camera.update();
		cameraGUI = new OrthographicCamera(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
		cameraGUI.position.set(0, 0, 0);
		cameraGUI.setToOrtho(true); // flip y-axis
		cameraGUI.update();
	}

	public void resize(int width, int height) {
		camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / (float) height) * (float) width;
		camera.update();
		worldController.viewportWidth = camera.viewportWidth;
		worldController.width = width;
		worldController.height = height;
		cameraGUI.viewportHeight = Constants.VIEWPORT_GUI_HEIGHT;
		cameraGUI.viewportWidth = (Constants.VIEWPORT_GUI_HEIGHT / (float) height) * (float) width;
		cameraGUI.position.set(cameraGUI.viewportWidth / 2, cameraGUI.viewportHeight / 2, 0);
		cameraGUI.update();
	}

	@SuppressWarnings("static-access")
	public void render() {
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		worldController.board.render(batch);
		/*batch.end();
		
		batch.setProjectionMatrix(camera.combined);
        batch.begin();
        worldController.board.render(batch);*/

        if (worldController.dragging) {

            float x = worldController.dragX;
            x = (float) (worldController.viewportWidth * (x - 0.55 * worldController.width) / worldController.width);

            float y = worldController.dragY;
            y = (float) (4.0 * (worldController.height - y) / worldController.height-2.6);
            
            //System.out.printf("Draging %d and in cell (%f,%f)\n", 
            		//worldController.dragNumeral, x, y);

            TextureRegion dragRegion = Assets.instance.numbers.get(worldController.dragNumeral-1).region;
            		batch.draw(dragRegion.getTexture(),
                    x, y, 0, 0, 1, 1, 1, 1, 0,
                    dragRegion.getRegionX(), dragRegion.getRegionY(), 
                    dragRegion.getRegionWidth(), dragRegion.getRegionHeight(),
                    false, false);
        
            		//System.out.println(dragRegion);
        }

        batch.end();
		
		// GUI rendering
		
		batch.setProjectionMatrix(cameraGUI.combined);
		batch.begin();
		if (worldController.board.gameState != Board.GameState.PLAYING) {
			float x = cameraGUI.viewportWidth / 2;
			float y = cameraGUI.viewportHeight / 2;
			BitmapFont fontGameOver = Assets.instance.fonts.defaultBig;
			fontGameOver.setColor(1, 0.75f, 0.25f, 1);
			String message = "Odd Won";
			if (worldController.board.gameState== worldController.board.gameState.EVEN_WON) 
				message = "Even Won";
			else if (worldController.board.gameState== worldController.board.gameState.DRAW)
				message = "Draw";
			fontGameOver.drawMultiLine(batch, message, x, y, 0, BitmapFont.HAlignment.CENTER);
			fontGameOver.setColor(1, 1, 1, 1);
		}
//			if (board.gameState != Board.GameState.PLAYING) {
//			timeLeftGameOverDelay -= deltaTime;
//			if (timeLeftGameOverDelay < 0) {
//				backToMenu();
//			}
//		}
		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
	}
}
