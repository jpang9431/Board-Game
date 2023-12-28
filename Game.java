import java.util.ArrayList;
import java.awt.Color;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
class Game extends JPanel implements ActionListener , MouseListener{
  private Coordinate[][] board = new Coordinate[5][5];
  private JButton[][] buttonBoard = new JButton[5][5];
  private ArrayList<Object> team1 = new ArrayList<Object>(), team2 = new ArrayList<Object>();
  private String[] fileNames = {"BS.png", "BF.png", "BFS.png", "BF.png", "BS.png"}, altFileNames = {"WS.png", "WF.png", "WFS.png", "WF.png", "WS.png"};
  private Color[] pieceColors = {Color.RED, Color.CYAN, Color.MAGENTA, Color.CYAN, Color.RED}, colors = {Color.WHITE, Color.BLACK};
  private int teamTurn = -1, size;
  private static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
  public static final int width = dim.width, height = dim.height, sqaure = height/5, boardSize = 5, square = sqaure;
  private JFrame frame = new JFrame();
  private boolean isLeft = false;
  public void startGame(){
    this.setSize(dim);
    this.setLayout(null);
    int row = 0;
    this.addMouseListener(this);
    for (int i=0; i<2; i++){
      for (int name=0; name<fileNames.length; name++){
        if (i==0){
          team1.add(getInstance(fileNames[name], -1));
          row = 0;
          board[row][name] = new Coordinate(team1.get(name));
        } else if (i==1){
          team2.add(getInstance(altFileNames[name], 1));
          row = board.length-1;
          board[row][name] = new Coordinate(team2.get(name));
        }
        Piece curPiece = (Piece) board[row][name].getPiece();
        buttonBoard[row][name] = new JButton(new ImageIcon(curPiece.getImage()));
        if (teamTurn==-1){
          buttonBoard[row][name].setBackground(colors[0]);
        } else {
          buttonBoard[row][name].setBackground(colors[1]);
        }
      }
    }    
    for (int curRow=0; curRow<buttonBoard.length; curRow++){
      for (int curCol=0; curCol<buttonBoard[0].length; curCol++){
        if (buttonBoard[curRow][curCol]==null){
          teamTurn = teamTurn*-1;
          board[curRow][curCol] = new Coordinate();
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
        buttonBoard[curRow][curCol].addMouseListener(this);
        buttonBoard[curRow][curCol].setActionCommand(curRow+","+curCol);
      }
    }
    frame.setContentPane(this);
    frame.setSize(dim);
    frame.setVisible(true);
  }

  public void rebuild(){
    for (int row=0; row<buttonBoard.length; row++){
      for (int col=0; col<buttonBoard[0].length; col++){
        Piece curPiece = (Piece) board[row][col].getPiece();
      }
    }
  }


  public Object getInstance(String name, int team) {
    String fileName = name;
    name = name.substring(1);
    
    if (name.equals("F.png")) {
      return (Object) new Freeze(fileName, team);
    } else if (name.equals("S.png")) {
      return (Object) new Shoot(fileName, team);
    } else {
      return (Object) new FreezeShoot(fileName, team);
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
    /*String command = e.getActionCommand();
    String[] splitCommand = command.split(",",2);
    int[] rowCol = {Integer.valueOf(splitCommand[0]), Integer.valueOf(splitCommand[1])};
    Coordinate curCoordinate = board[rowCol[0]][rowCol[1]];
    Object objPiece = curCoordinate.getPiece();
    Piece curPiece = (Piece) curCoordinate.getPiece();
    ArrayList<int[]> move = new ArrayList<int[]>();
    ArrayList<int[]> freeze = new ArrayList<int[]>();
    ArrayList<int[]> shoot = new ArrayList<int[]>();
    if (curPiece!=null){
      String pieceName = curPiece.getIcon();
      if(isLeft){
        move = curPiece.move(board, rowCol[0], rowCol[1]);
      } else {
        if (pieceName.equals("F")){
          freeze = getFreeze((Freeze) objPiece, rowCol[0], rowCol[1]);
        } else if (pieceName.equals("S")){
          shoot = getShoot((Shoot) objPiece, rowCol[0], rowCol[1]);
        } else if (pieceName.equals("FS")){
          freeze = getFreeze((FreezeShoot) objPiece, rowCol[0], rowCol[1]);
          shoot = getShoot((FreezeShoot) objPiece, rowCol[0], rowCol[1]);
        }
      }
    }*/
    /*System.out.println("Piece at "+rowCol[0]+","+rowCol[1]);
    for(int i=0; i<freeze.size(); i++){
      System.out.println("Freeze: "+freeze.get(i)[0]+","+freeze.get(i)[1]);
    }
    for (int j=0; j<shoot.size(); j++){
      System.out.println("Shoot: "+shoot.get(j)[0]+","+shoot.get(j)[1]);
    }*/
  }

  public ArrayList<int[]> getFreeze(Freezable piece, int row, int col){
    return piece.getFreezeable(board, row, col);
  }

  public ArrayList<int[]> getShoot(Shootable piece, int row, int col){
    return piece.getShootable(board, row, col);
  }

  public void mousePressed(MouseEvent e){}
  
  public void mouseClicked(MouseEvent e){
    int button = e.	getButton();
    int row = e.getYOnScreen()/sqaure;
    int col = e.getXOnScreen()/sqaure;
    Coordinate curCoordinate = board[row][col];
    Object objPiece = curCoordinate.getPiece();
    Piece curPiece = (Piece) curCoordinate.getPiece();
    ArrayList<int[]> move = new ArrayList<int[]>();
    ArrayList<int[]> freeze = new ArrayList<int[]>();
    ArrayList<int[]> shoot = new ArrayList<int[]>();
    if (curPiece!=null){
      if (button==MouseEvent.BUTTON1){
        move = curPiece.move(board, row, col);
      } else if (button==MouseEvent.BUTTON3){
        String pieceName = curPiece.getIcon();
        if (pieceName.equals("F")){
          freeze = getFreeze((Freeze) objPiece, row, col);
        } else if (pieceName.equals("S")){
          shoot = getShoot((Shoot) objPiece, row, col);
        } else if (pieceName.equals("FS")){
          freeze = getFreeze((FreezeShoot) objPiece, row, col);
          shoot = getShoot((FreezeShoot) objPiece, row, col);
        }
      }
    }
    System.out.println("Piece at "+row+","+col);
    for(int i=0; i<freeze.size(); i++){
      System.out.println("Freeze: "+freeze.get(i)[0]+","+freeze.get(i)[1]);
    }
    for (int j=0; j<shoot.size(); j++){
      System.out.println("Shoot: "+shoot.get(j)[0]+","+shoot.get(j)[1]);
    }
  }
  
  public void mouseEntered(MouseEvent e){}
  public void mouseReleased(MouseEvent e){}
  public void mouseExited(MouseEvent e){}
  
  public static void main(String[] args) {
    System.out.println("Run");
    Game game = new Game();
    game.startGame();
  }
}