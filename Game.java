import java.util.ArrayList;
import java.awt.Color;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.*;
class Game extends JPanel implements ActionListener {
  private Coordinate[][] board = new Coordinate[5][5];
  private JButton[][] buttonBoard = new JButton[5][5];
  private ArrayList<Object> team1 = new ArrayList<Object>();
  private ArrayList<Object> team2 = new ArrayList<Object>();
  private String[] fileNames = {"S.png", "F.png", "FS.png", "F.png", "S.png"};
  private Color[] pieceColors = {Color.RED, Color.CYAN, Color.MAGENTA, Color.CYAN, Color.RED};
  private int teamTurn = -1;
  private Color[] colors = {Color.WHITE, Color.BLACK};
  private int size;
  private static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
  public static final int width = dim.width, height = dim.height, sqaure = height/5;
  private JFrame frame = new JFrame();
  public void startGame(){
    this.setSize(dim);
    this.setLayout(null);
    System.out.println(sqaure);
    int row = 0;
    for (int i=0; i<2; i++){
      for (int name=0; name<fileNames.length; name++){
        if (i==0){
          team1.add(getInstance(fileNames[name], -1));
          row = 0;
          board[row][name] = new Coordinate(team1.get(name));
        } else if (i==1){
          team2.add(getInstance(fileNames[name], -1));
          row = board.length-1;
          board[row][name] = new Coordinate(team2.get(name));
        }
        Icon icon = new ImageIcon()
        buttonBoard[row][name] = new JButton();
        if (teamTurn==-1){
          buttonBoard[row][name].setBackground(colors[0]);
        } else {
          buttonBoard[row][name].setBackground(colors[1]);
        }
        //buttonBoard[row][name]
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
        this.add(buttonBoard[curRow][curCol]);
        buttonBoard[curRow][curCol].setBounds(curCol*sqaure, curRow*sqaure, sqaure, sqaure);
        buttonBoard[curRow][curCol].addActionListener(this);
        buttonBoard[curRow][curCol].setActionCommand(curRow+","+curCol);
      }
    }
    frame.setContentPane(this);
    frame.setSize(dim);
    frame.setVisible(true);
  }


  public Object getInstance(String name, int team) {
    if (name.equals("F.png")) {
      return (Object) new Freeze(name, team);
    } else if (name.equals("S.png")) {
      return (Object) new Shoot(name, team);
    } else {
      return (Object) new FreezeShoot(name, team);
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
    Game game = new Game();
    game.startGame();
  }
}