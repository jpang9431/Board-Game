import java.util.ArrayList;
import java.awt.Color;
class Piece{
  private ArrayList<int[]> tempArray = new ArrayList<int[]>();
  private String letter;
  private String icon;
  protected int team;
  protected boolean canAct = true;;
  private int[][] check  = {
    {-1,-1},
    {-1,1},
    {1,1},
    {-1,1},
    {-1,0},
    {0,1},
    {1,0},
    {0,-1}
  };
  
  Piece(String letter, String icon, int team){
    this.letter = letter;
    this.icon = icon;
    this.team = team;
  }

  public Color getColor(){
    return color;
  }
    
  public int getTeam(){
    return team;
  }

  public ArrayList<int[]> getSquares(int row, int col, boolean card, boolean diag, int min, int max){
    ArrayList<int[]> squares = new ArrayList<int[]>();
    int checkMax = 8;
    int checkMin = 0;
    if (!diag){
      checkMin = 4;
    }
    if (!card){
      checkMax = 4;
    }
    for (int i=min; i<max; i++){
      for (int square=checkMin; square<checkMax; square++){
        int[] cord = new int[2];
        cord[0] = row + check[square][0]*i;
        cord[1] = col + check[square][1]*i;
        squares.add(cord);
      }
    }
    return squares;
  }
  
  public ArrayList<int[]>move(Coordinate[][] board, int row, int col, boolean card, boolean diag, int min, int max){
    ArrayList<int[]> squares = getSquares(row, col, card, diag, min, max);
    for (int i=0; i<squares.size(); i++){
      int[] square = squares.get(i);
      if (board[square[0]][square[1]].getPiece()!=null){
        squares.remove(squares.get(i));
      }
    }
    return squares;
  }
}