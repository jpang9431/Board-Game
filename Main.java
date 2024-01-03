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
  private Menu menu = new Menu(choices, this::choose);
  private Settings settings = new Settings(this);
  public void start(){
    this.setContentPane(menu);
    this.setSize(game.dim);
    this.setVisible(true);
  }
  
  public void choose(int choice){
    System.out.println(choice);
    if (choice==0){
      game.startGame();
      this.setContentPane(game);
    } else if (choice==1){
      this.setContentPane(settings);
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