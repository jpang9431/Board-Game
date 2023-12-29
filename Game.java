import java.util.ArrayList;
import java.awt.Color;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

class Game extends JPanel implements ActionListener, MouseListener {
  private Coordinate[][] board = new Coordinate[5][5];
  private JButton[][] buttonBoard = new JButton[5][5];
  private ArrayList<Object> team1 = new ArrayList<Object>(), team2 = new ArrayList<Object>();
  private String[] fileNames = { "BS.png", "BF.png", "BFS.png", "BF.png", "BS.png" },
      altFileNames = { "WS.png", "WF.png", "WFS.png", "WF.png", "WS.png" };
  private Color[] pieceColors = { Color.RED, Color.CYAN, Color.GREEN }, colors = { Color.WHITE, Color.BLACK };
  private int teamTurn = -1, size;
  private static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
  public static final int width = dim.width, height = dim.height - 20, sqaure = height / 5, boardSize = 5,
      square = sqaure, smallSquare = height / 6, MOVE = 2, SHOOT = 0, FREEZE = 1;
  private JFrame frame = new JFrame();
  private boolean isLeft = false;
  private ArrayList<Coordinate> changedSquares = new ArrayList<Coordinate>();

  public void startGame() {
    this.setSize(dim);
    this.setLayout(null);
    int team = -1;
    for (int row = 0; row < board.length; row++) {
      for (int col = 0; col < board.length; col++) {
        team = team * -1;
        Color color = null;
        if (team == 1) {
          color = colors[0];
        } else {
          color = colors[1];
        }
        board[row][col] = new Coordinate(color, row, col);
        buttonBoard[row][col] = new JButton("");
        buttonBoard[row][col].addMouseListener(this);
        buttonBoard[row][col].setBackground(color);
        this.add(buttonBoard[row][col]);
        buttonBoard[row][col].setBounds(col * sqaure, row * square, square, square);
      }
    }
    for (int i = 0; i < fileNames.length; i++) {
      setPiece(0, i, fileNames[i], -1);
      setPiece(buttonBoard.length - 1, i, altFileNames[i], 1);
    }
    frame.setContentPane(this);
    frame.setSize(dim);
    frame.setVisible(true);
  }

  private void setPiece(int row, int col, String fileName, int team) {
    Piece piece = getInstance(fileName, team);
    buttonBoard[row][col].setIcon(new ImageIcon(piece.getImage()));
    board[row][col].setPiece((Object) piece);
  }

  // Note first cord is the target/the affected cord second is only for move where
  // you move the piece from
  private void updateSquare(Coordinate[] cords, int type) {
    int aftRow = cords[0].getRow();
    int aftCol = cords[0].getCol();
    if (type == 1) {
      buttonBoard[aftRow][aftCol].setBackground(Color.BLUE);
      cords[0].setFreeze();
    } else {
      buttonBoard[aftRow][aftCol].setIcon(null);
      if (type == 2) {
        Piece piece = (Piece) cords[1].getPiece();
        cords[1].setPiece(piece);
        buttonBoard[cords[1].getRow()][cords[1].getCol()].setIcon(new ImageIcon(piece.getIcon()));
      }
      cords[0].setPiece(null);
    }
    for (int i = 0; i < changedSquares.size(); i++) {
      Coordinate curCord = changedSquares.get(i);
      if (!curCord.turn()) {
        buttonBoard[curCord.getRow()][curCord.getCol()].setBackground(curCord.getColor());
        changedSquares.remove(curCord);
        i--;
      }
    }
  }

  private void setColors(ArrayList<int[]> rowCols, Color color, ArrayList<int[]> altRowCols, Color secColor) {
    for (int row = 0; row < board.length; row++) {
      for (int col = 0; col < board[0].length; col++) {
        if (board[row][col].isFrozen()){
          buttonBoard[row][col].setBackground(Color.BLUE);
        } else {
          buttonBoard[row][col].setBackground(board[row][col].getColor());
        }
      }
    }
    colorSquares(rowCols, color);
    colorSquares(altRowCols, secColor);
  }

  private void colorSquares(ArrayList<int[]> rowCols, Color color){
      for (int i=0; i<rowCols.size(); i++){
        int[] rowCol = rowCols.get(i);
        buttonBoard[rowCol[0]][rowCol[1]].setBackground(color);
      }
  }

  public Piece getInstance(String name, int team) {
    String fileName = name;
    name = name.substring(1);
    if (name.equals("F.png")) {
      return (Piece) new Freeze(fileName, team);
    } else if (name.equals("S.png")) {
      return (Piece) new Shoot(fileName, team);
    } else {
      return (Piece) new FreezeShoot(fileName, team);
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {

  }

  public ArrayList<int[]> getFreeze(Freezable piece, int row, int col) {
    return piece.getFreezeable(board, row, col);
  }

  public ArrayList<int[]> getShoot(Shootable piece, int row, int col) {
    return piece.getShootable(board, row, col);
  }

  public void mousePressed(MouseEvent e) {
  }

  public void mouseClicked(MouseEvent e) {
    int button = e.getButton();
    int row = e.getYOnScreen() / sqaure;
    int col = e.getXOnScreen() / sqaure;
    Coordinate curCoordinate = board[row][col];
    Object objPiece = curCoordinate.getPiece();
    Piece curPiece = (Piece) curCoordinate.getPiece();
    if (curPiece != null) {
      if (button == MouseEvent.BUTTON1) {
        ArrayList<int[]> squares = curPiece.move(board, row, col);
        setColors(squares, pieceColors[2], new ArrayList<int[]>(), null);
      } else if (button == MouseEvent.BUTTON3) {
        String pieceName = curPiece.getIcon();
        if (pieceName.equals("F")) {
          ArrayList<int[]> squares = getFreeze((Freeze) objPiece, row, col);
          setColors(squares, pieceColors[1], new ArrayList<int[]>(), null);
        } else if (pieceName.equals("S")) {
          ArrayList<int[]> squares = getShoot((Shoot) objPiece, row, col);
          setColors(squares, pieceColors[0], new ArrayList<int[]>(), null);
        } else if (pieceName.equals("FS")) {
          ArrayList<int[]> squares = getFreeze((FreezeShoot) objPiece, row, col);
          ArrayList<int[]> altSquares = getShoot((FreezeShoot) objPiece, row, col);
          setColors(squares, pieceColors[1], altSquares, pieceColors[0]);
        }
      }
    }

  }

  public void mouseEntered(MouseEvent e) {
  }

  public void mouseReleased(MouseEvent e) {
  }

  public void mouseExited(MouseEvent e) {
  }

  public static void main(String[] args) {
    System.out.println("Run");
    Game game = new Game();
    game.startGame();

  }
}