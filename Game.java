import java.util.ArrayList;
import java.awt.Color;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Game extends JPanel implements ActionListener {
  private Coordinate[][] board = new Coordinate[5][5];
  private JButton[][] buttonBoard = new JButton[5][5];
  private ArrayList<Object> team1 = new ArrayList<Object>();
  private ArrayList<Object> team2 = new ArrayList<Object>();
  private String[] instaceNames = { "S", "F", "FS", "F", "S" };
  private Color[] pieceColors = {Color.RED, Color.CYAN, Color.MAGENTA, Color.CYAN, Color.RED};
  private int teamTurn = -1;
  private Color[] colors = {Color.WHITE, Color.BLACK};
  public void startGame(){
    int row = 0;
    for (int i=0; i<2; i++){
      for (int name=0; name<instaceNames.length; name++){
        if (i==0){
          team1.add(getInstance(instaceNames[name], -1));
          row = 0;
          board[row][name] = new Coordinate(team1.get(name));
        } else if (i==1){
          team2.add(getInace(instaceNames[name], -1));
          row = board.length-1;
          board[row][name] = new Coordinate(team2.get(name));
        }
        buttonBoard[row][name] = new JButton(instanceNames[name]);
        buttonBoard[row][name].setBackGround(pieceColors[name]);
        buttonBoard[row][name].setForeground(colors[i]);
        buttonBoard[row][name].addActionListener(this);
        buttonBoard[row][name].setActionCommand(row+","+col);
      }
    }    
    for (int curRow=0; curRow<buttonBoard.length; curRow++){
      for (int curCol=0; curCol<buttonBoard[0].length; curCol++){
        if (buttonBoard[curRow][curCol]==null){
          teamTurn = teamTurn*-1;
          buttonBoard[curRow][curCol] = new JButton("");
          if (teamTurn==1){
            buttonBoard[curRow][curCol].setBackground(Color.WHITE);
          } else {
            buttonBoard[curRow][curCol].setBackground(Color.BLACK);
          }
        }
      }
    }
  }

  public Object getInstance(String name, int team) {
    if (name.equals("F")) {
      return (Object) new Freeze(team);
    } else if (name.equals("S")) {
      return (Object) new Shoot(team);
    } else {
      return (Object) new FreezeShoot(team);
    }
  }

  public void setSquares(int row, int col){
    int curColor = -1;
    for (int curRow=0; curRow<buttonBoard.length; curRow++){
      for (int curCol=0; curCol<buttonBoard[0].length; curCol++){
        Piece piece = (Piece) board[curRow][curCol].getPiece();
        curColor = curColor*-1;
        if (piece==null){
          if (curColor==1){
            buttonBoard[curRow][curCol].setBackground(Color.WHITE);
          } else {
            buttonBoard[curRow][curCol].setBackground(Color.BLACK);
          }
        }
      }
    }
    ArrayList<int[]> freezeCords = new ArrayList<int[]>();
    ArrayList<int[]> shootCords = new ArrayList<int[]>();
    ArrayList<int[]> moves = new ArrayList<int[]>();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String command = e.getActionCommand();
    System.out.println(command);
  }

  public static void main(String[] args) {
    System.out.println("Run");
  }
}