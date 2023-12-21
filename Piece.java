abstract class Piece extends Range{
  private String letter = null;
  private int team = -1;
  private boolean isAlive = true;
  Piece(int[] minRange, int[] maxRange, boolean diag, Game game, String letter, int team){
    super(minRange, maxRange, diag, game);
    this.letter;
    this.team;
  }
  public String getLetter(){
    return letter;
  }
  public int getTeam(){
    return team;
  }
  public boolean getAlive(){
    return alive;
  }
}