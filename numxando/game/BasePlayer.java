/**
 * @file        BasePlayer.java
 * @author      Abhiroop Tandon (20061667)
 * @assignment  NumericalXandO     
 */

package wit.cgd.numxando.game;

public abstract class BasePlayer {

	public boolean	human;
	//public int		mySymbol, opponentSymbol;
	public String	name;
	public Board	board;

	public BasePlayer(Board board) {
		this.board = board;
		//setSymbol(symbol);
		human = false;
	}

	/*// Set/change the symbol used by player
	public void setSymbol(int symbol) {
		mySymbol = symbol;
		opponentSymbol = (symbol == board.X) ? board.O : board.X;
	}*/

	public abstract int move ();
}
