import java.util.ArrayList;
import java.awt.Color;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
class Main extends JFrame{
  private Game game = new Game();
  private final String path = "Icons/";
  private String[] choices = {path+"GREENNEXT.png", path+"settings50.png", path+"TUTORIAL.png"};
  private Menu menu = new Menu(choices, this::choose, "Some random game", new Color(0,128,128), Color.DARK_GRAY);
  private Settings settings = new Settings(this);
  private Tutorial tut = new Tutorial(this::menu);

  public void restart(){
    Game game = new Game();
    game.setMain(this);
    start();
  }

  public void end(int win){
    this.setContentPane(new End(win, this));
  }
  
  public void start(){
    menu.setBackground(Color.DARK_GRAY);
    game.setMain(this);
    this.setContentPane(menu);
    this.setSize(game.dim);
    this.setVisible(true);
  }
  public void menu(){
    this.setContentPane(menu);
  }
  public void choose(int choice){
    if (choice==0){
      game.startGame();
      this.setContentPane(game);
    } else if (choice==1){
      this.setContentPane(settings);
    } else if (choice == 2){
      this.setContentPane(tut);
      revalidate();
      repaint();
    }
  }
  
  public void game(){
    this.setContentPane(game);
  }

  public void doFlip(){
    game.noDoFlip();
  }
  
  public static void main(String[] args){
    Main main = new Main();
    main.start();
  }
}