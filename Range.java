import java.util.ArrayList;

class Range {
  private boolean diag = false;
  private int[] maxRange;
  private int[] minRange;
  public static final int MOVE = 0, FREEZE = 1, SHOOT = 2, UP = 0, UR = 1, RI = 2, DR = 3, D = 4, DL = 5, L = 6,
      UL = 7;
  private final int[][] moves ={{-1, 0},
                                {-1, 1},
                                {0,1},
                                {1,1},
                                {1,0},
                                {1,-1},
                                {0,-1},
                                {-1,-1}
                               };
  private Game game;
  private boolean[] dir = new boolean[8];
  private boolean[] staticDir = new boolean[8];
  
  Range(int[] minRange, int[] maxRange, boolean diag, Game game) {
    this.minRange = minRange;
    this.maxRange = maxRange;
    this.diag = diag;
    this.game = game;
    for (int i = 0; i < staticDir.length; i++) {
      staticDir[i] = true;
    }
    if (!diag) {
      staticDir[UR] = false;
      staticDir[DR] = false;
      staticDir[DL] = false;
      staticDir[UL] = false;
    }
  }

  public ArrayList<int[]> getRange(int type, int curRow, int curCol) {
    dir = staticDir;
    Arraylist<int[]> reutrnCords = new ArrayList<int[]>();
    Coordinate[][] board = game.getBoard();
    int[] cords = new int[2];
    if (minRange[type] > 0) {
      for (int i = minRange[type]; i < maxRange[type]; i++) {
        for (int direction = 0; direction < 8; direction++) {
          if (dir[direction]) {
            cords[0] = curRow+moves[dir][0]*i;
            cords[1] = curCol+moves[dir][1]*i;
            dir[direction] = checkSquare(board[cords[0]][cords[1]]);
            returnCords.add(cords);
          }
        }
      }
    }
    return returnCords;
  }

  public boolean checkSquare(Coordinate cord) {
    if (cord.getFreeze()) {
      return false;
    } else if (cord.getPiece()) {
      return false;
    } else {
      return true;
    }
  }

  
  
}