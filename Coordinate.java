class Coordinate{
  private Object piece = null;
  private int isFreeze = 0;

  Coordinate(Object piece){
    this.piece = piece;
  }

  Coordinate(){}
  
  public boolean isFrozen(){
    return isFreeze<=0;
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
}