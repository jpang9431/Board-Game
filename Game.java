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
  private String[] fileNames = { "BF.png", "BS.png", "BFS.png", "BS.png", "BF.png" },
      altFileNames = { "WF.png", "WS.png", "WFS.png",  "WS.png", "WF.png" };
  private Color[] pieceColors = { Color.RED, Color.CYAN, Color.GREEN }, colors = { Color.WHITE, Color.BLACK }, teamColors = {Color.YELLOW, Color.MAGENTA};
  private int teamTurn = 1, numPieces1 = 5, numPieces2 = 5;
  private static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
  public static final int width = dim.width, height = dim.height - 20, sqaure = height / 5, boardSize = 5,
      square = sqaure, smallSquare = height / 6, MOVE = 2, SHOOT = 0, FREEZE = 1, widthRemain = width - height;
  private JFrame frame = new JFrame();
  private boolean hasFreeze = false;
  private Coordinate seletecCord = null;
  private final Color freeze = Color.BLUE, noUse = Color.GRAY;
  private final Font font = new Font(Font.DIALOG, Font.PLAIN, sqaure/5);
  private JPanel textPanel = new JPanel();
  private JLabel turnLabel = new JLabel("", JLabel.CENTER), numTeam1 = new JLabel("", JLabel.CENTER), numTeam2  = new JLabel("", JLabel.CENTER), randomSeperator = new JLabel(" vs ", JLabel.CENTER);
  private JButton flip = new JButton("Flip");
  public void startGame() {
    this.setBackground(Color.DARK_GRAY);
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
        buttonBoard[row][col].setFocusPainted(false);
        System.out.println(col * sqaure+square);
      }
    }
    for (int i = 0; i < fileNames.length; i++) {
      setPiece(0, i, fileNames[i], -1);
      setPiece(buttonBoard.length - 1, i, altFileNames[i], 1);
    }
    textPanel.setLayout(new GridLayout(0,1));
    textPanel.add(turnLabel);
    turnLabel.setFont(font);
    JPanel tempPanel = new JPanel();
    tempPanel.add(numTeam1);
    numTeam1.setForeground(teamColors[0]);
    numTeam1.setFont(font);
    tempPanel.add(randomSeperator);
    randomSeperator.setFont(font);
    randomSeperator.setForeground(new Color(0, 128, 128));
    tempPanel.add(numTeam2);
    numTeam2.setForeground(teamColors[1]);
    numTeam2.setFont(font);
    tempPanel.setBackground(Color.DARK_GRAY);
    textPanel.add(tempPanel);
    this.add(textPanel);
    textPanel.setBounds(square*5, 0, square*5, height);
    textPanel.setBackground(Color.DARK_GRAY);
    textPanel.add(flip);
    flip.addActionListener(this);
    updateText();
    frame.setContentPane(this);
    frame.setSize(dim);
    frame.setVisible(true);
  }

  private void flip(){
    ArrayList<int[]> pieceCords = new ArrayList<int[]>();
    ArrayList<Piece> pieces = new ArrayList<Piece>();
    ArrayList<int[]> freezeCords = new ArrayList<int[]>();
    ArrayList<int[]> noUseCords = new ArrayList<int[]>();
      
    for (int row=0; row<board.length; row++){
      for (int col=0; col<board[0].length; col++){
        Piece piece =  (Piece) board[row][col].getPiece();
        int[] addCord = {4-row, col};
        Color color = buttonBoard[row][col].getBackground();
        if (piece!=null){
          pieceCords.add(addCord);
          pieces.add(piece);
          board[row][col].setPiece(null);
          buttonBoard[row][col].setIcon(null);
          buttonBoard[row][col].setBackground(null);
        }
        if (color.equals(freeze)){
          int[] addFreeze = new int[3];
          addFreeze[0] = addCord[0];
          addFreeze[1] = addCord[1];
          addFreeze[2] = board[row][col].getFreeze();
          freezeCords.add(addFreeze);
          board[row][col].setFreeze(0);
        } else if (color.equals(noUse)){
          noUseCords.add(addCord);
        }
      }
    }
    
    for (int i=0; i<pieceCords.size(); i++){
      board[pieceCords.get(i)[0]][pieceCords.get(i)[1]].setPiece((Object) pieces.get(i));
      buttonBoard[pieceCords.get(i)[0]][pieceCords.get(i)[1]].setIcon(new ImageIcon(pieces.get(i).getImage()));
    }

    for (int j=0; j<freezeCords.size(); j++){
      board[freezeCords.get(j)[0]][freezeCords.get(j)[1]].setFreeze(freezeCords.get(j)[2]);
    }
 
    reset(false);

    for (int k=0; k<noUseCords.size(); k++){
      buttonBoard[noUseCords.get(k)[0]][noUseCords.get(k)[1]].setBackground(noUse);
    }
  }
  
  private void updateText(){
    if (teamTurn==1){
      turnLabel.setText("<html>Team 1 turn<html>");
      turnLabel.setForeground(teamColors[0]);
    } else {
      turnLabel.setText("<html>Team 2 turn<html>");
      turnLabel.setForeground(teamColors[1]);
    }
    numTeam1.setText(String.valueOf(numPieces1));
    numTeam2.setText(String.valueOf(numPieces2));
  }
  
  private void setPiece(int row, int col, String fileName, int team) {
    Piece piece = getInstance(fileName, team);
    buttonBoard[row][col].setIcon(new ImageIcon(piece.getImage()));
    board[row][col].setPiece((Object) piece);
  }

  private void freeze(Coordinate cord){
    hasFreeze = true;
    cord.setFreeze();
    buttonBoard[seletecCord.getRow()][seletecCord.getCol()].setBackground(noUse);
    reset(false);
  }

  private void shoot(Coordinate cord){
    Piece piece = (Piece) cord.getPiece();
    if (piece!=null){
      if (piece.getTeam()==1){
        numPieces1--;
      } else if (piece.getTeam()==-1){
        numPieces2--;
      }
    }
    cord.setPiece(null);
    buttonBoard[cord.getRow()][cord.getCol()].setIcon(null);
    reset(true);
  }

  private void move(Coordinate cord){
    Piece piece = (Piece) board[seletecCord.getRow()][seletecCord.getCol()].getPiece();
    cord.setPiece(piece);
    buttonBoard[cord.getRow()][cord.getCol()].setIcon(new ImageIcon(piece.getImage()));
    seletecCord.setPiece(null);
    buttonBoard[seletecCord.getRow()][seletecCord.getCol()].setIcon(null);
    reset(true);
  }


  private void reset(boolean turn){
    if (turn){
      teamTurn = teamTurn*-1;
      updateText();
      hasFreeze = false;
    }
    for (int row = 0; row < board.length; row++) {
      for (int col = 0; col < board[0].length; col++) {
        if (board[row][col].isFrozen()){
          if (!turn||board[row][col].turn()){
            buttonBoard[row][col].setBackground(freeze);
          } else {
            buttonBoard[row][col].setBackground(board[row][col].getColor());
          }
        } else if (!buttonBoard[row][col].getBackground().equals(noUse)||turn){
          buttonBoard[row][col].setBackground(board[row][col].getColor());
        } else {
          buttonBoard[row][col].setBackground(noUse);
        }
      }
    }
    
  }

  private void setColors(ArrayList<int[]> rowCols, Color color, ArrayList<int[]> altRowCols, Color secColor) {
    reset(false);
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
    flip();
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
    
    if (row > board.length-1){
      row = board.length-1;
    } 
    Coordinate curCoordinate = board[row][col];
    Object objPiece = curCoordinate.getPiece();
    Piece curPiece = (Piece) curCoordinate.getPiece();
    JButton buttonClicked = buttonBoard[row][col];
    Color color = buttonClicked.getBackground();
    if (curPiece != null && (color.equals(colors[0])||color.equals(colors[1])) ) {
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
      seletecCord = curCoordinate;
    } else if (color.equals(pieceColors[1])&&!hasFreeze){
      freeze(curCoordinate);
    } else if (color.equals(pieceColors[0])){
      shoot(curCoordinate);
    } else if (color.equals(pieceColors[2])){
      move(curCoordinate);
    } else {
      reset(false);
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
    //testing();
    Game game = new Game();
    game.startGame();
  }
  public static void testing(){
    JFrame frame = new JFrame("Frame");
    
    Menu menu = new Menu(new String[]{"Icons/BF.png", "Icons/BFS.png", "Icons/BS.png"}, Game::randomMethod);

    frame.setContentPane(menu);
    frame.setSize(width, height);
    //frame.pack();
    frame.setVisible(true);
    
    
  }
  public static void randomMethod(int i){
    System.out.println(i);
  }
  public void menu(){
    Menu menu = new Menu(new String[0], this::clicked);
    Tutorial tut = new Tutorial(this::setMenu);
  }
  public void clicked(int item){
    //TODO
  }
  public void setMenu(){
    //TODO
  }
}