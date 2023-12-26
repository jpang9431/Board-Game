import java.awt.Color;
import java.util.ArrayList;

class Freeze extends Piece implements Freezable {
  Freeze(String icon, int team) {
    super(icon, team);
  }

  public ArrayList<int[]> move(Coordinate[][] board, int row, int col) {
    return move(board, row, col, true, false, 1, 2);
  }

  public ArrayList<int[]> getFreezeable(Coordinate[][] board, int row, int col) {
    ArrayList<int[]> squares = getSquares(row, col, false, true, 1, 2);
    canAct = false;
    return squares;
  }
}