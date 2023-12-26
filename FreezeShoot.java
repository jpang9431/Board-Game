import java.awt.Color;
import java.util.ArrayList;
class FreezeShoot extends Piece implements Shootable, Freezable{
  FreezeShoot(String icon, int team){
    super(icon, team);
  }
  
  public ArrayList<int[]> move(Coordinate[][] board, int row, int col){
    return move(board, row, col, true, true, 1,2);
  }
  
  public ArrayList<int[]> getFreezeable(Coordinate[][] board, int row, int col){
    ArrayList<int[]> squares = getSquares(row, col, true, true, 1, 2);
    canAct = false;
    return squares;
  }
  
  public ArrayList<int[]> getShootable(Coordinate[][] board, int row, int col){
    ArrayList<int[]> squares = getSquares(row, col, true, true, 2, 3);
    for (int i=0; i<squares.size(); i++){
      int[] square = squares.get(i);
      Piece piece = (Piece) board[square[0]][square[1]].getPiece();
      if (piece==null||piece.getTeam()==team){
        squares.remove(square);
      }
    }
    return squares;
  }
}