class Coordinate{
  private int row, col;
  private Piece piece = null;
  private boolean isFroze = false;
  Coordinate(int row, int col){
    this.row = row;
    this.col = col;
  }
  public int[] getCord(){
    int[] returnInt = new int[2];
    returnInt[0] = row;
    returnInt[1] = col;
    return returnInt;
  }
  public boolean getFreeze(){
    return isFroze;
  }
  public Piece getPiece(){
    return piece;
  }
}