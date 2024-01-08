import java.awt.*;
import javax.swing.*;
import java.lang.Runnable;
import java.awt.Color;
class Tutorial extends JPanel{
  private static final String abcd = "<html>Hello and welcome to chess -2.0, the worse and more unbalanced version! In this game, you can left click a piece to see the possible move spots, right click a piece to see its possible utility. You can win the game if you take all your opponent's pieces. Good luck and have fun!!!!!!!!!!!!!!!!<html>";
  public Tutorial(Runnable setMenu){
    this.setLayout(null);
    JLabel label = new JLabel();
    label.setBounds(Game.width/4,0,Game.width/2,Game.height-20);
    label.setText(abcd);
    label.setBackground(Color.DARK_GRAY);
    label.setForeground(new Color(0,128,128));
    this.setBackground(Color.DARK_GRAY);
    add(label);
    JButton button = new JButton("BACK");
    button.setBounds(Game.width/4,(int) (Game.height/1.5), Game.width/2, Game.height/10);
    button.addActionListener(e -> {
      setMenu.run();
    });
    button.setBackground(new Color(170, 189, 43));
    add(button);
  }
  
}