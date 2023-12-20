class Game{
  private Piece[][] board = new Piece[5][5];
  private int turn = -1;
  Game(int rows, int cols){
    board = new Piece[rows][cols];
  }
  Game(){
    board = new Piece[5][5];
  }
  
}