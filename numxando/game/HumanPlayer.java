/**
 * @file        HumanPlayer.java
 * @author      Abhiroop Tandon (20061667)
 * @assignment  NumericalXandO     
 */

package wit.cgd.numxando.game;

public class HumanPlayer extends BasePlayer {

	
	public HumanPlayer(Board board) {
		super(board);
		human = true;
	}

	@Override
	public int move() {
		// human move handled in worldController
		return 0;
	}
}
