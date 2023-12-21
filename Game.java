class Game{
  private Coordinate[][] board = new Piece[5][5];
  private int turn = -1;
  Game(int rows, int cols){
    board = new Coordinate[rows][cols];
    for (int row = 0; row<rows; row++){
      for (int col = 0; col<cols; col++){
        board[row][col] = new Coordinate(row,col);
      }
    }
  }
  Game(){
    board = new Piece[5][5];
  }
  public Coordinate[][] getBoard(){
    return board;
  }
}