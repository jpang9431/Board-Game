import java.awt.Color;
class Coordinate{
  private Object piece = null;
  private int isFreeze = 0, row = 0, col = 0;
  private Color color = null;

  Coordinate(Color color, int row, int col){
    this.color = color;
    this.row = row;
    this.col = col;
  }

  public int getRow(){
    return row;
  }

  public int getCol(){
    return col;
  }
  
  public Color getColor(){
    return color;
  }
  
  public boolean isFrozen(){
    return isFreeze>0;
  }

  public void setFreeze(){
    isFreeze = 2;
  }
  
  public boolean turn(){
    if (isFreeze>0){
      isFreeze--;
    }
    return isFrozen();
  }
  
  public Object getPiece(){
    return piece;
  }

  
  public void setPiece(Object piece){
    this.piece = piece;
  }

  @Override
  public boolean equals(Object o) {
    if (o==this){
      return true;
    } else if (!(o instanceof Coordinate)){
      return true;
    } else {
      Coordinate cord = (Coordinate) o;
      return cord.getRow()==this.getRow()&&cord.getCol()==this.getCol();
    }
  }
}