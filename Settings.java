import java.util.ArrayList;
import java.awt.Color;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
class Settings extends JPanel implements ActionListener{
  private boolean[] isOn = {true};
  private String[] choiceString = {"Do automatic flip"};
  private ArrayList<String> choices = new ArrayList<String>();
  private JButton[] button = new JButton[isOn.length];
  private Main game = null;
  private Color teal = new Color(0,128,128);
  private Font font = new Font(Font.DIALOG, Font.PLAIN, 30);
  Settings(Main game){
    this.game = game;
    this.setSize(Game.dim);
    for (int i=0; i<choiceString.length; i++){
      choices.add(choiceString[i]);
    }
    this.setBackground(Color.DARK_GRAY);
    this.setLayout(new GridLayout(0,1));
    
    for (int i=0; i<isOn.length; i++){
      button[i] = new JButton(choiceString[i]+": "+isOn[i]);
      button[i].addActionListener(this);
      button[i].setActionCommand(choices.get(i));
      button[i].setForeground(teal);
      button[i].setBackground(Color.gray);
      button[i].setFocusPainted(false);
      button[i].setFont(font);
      this.add(button[i]);
    }
    JButton back = new JButton("Back");
    back.setForeground(teal);
    back.setBackground(Color.DARK_GRAY);
    back.addActionListener(this);
    back.setActionCommand("Back");
    back.setFont(font);
    this.add(back);
  }
  public void actionPerformed(ActionEvent e) {
    String command = e.getActionCommand();
    int index = choices.indexOf(command);
    if (index==0){
      game.doFlip();
      isOn[index] = !isOn[index];
    }
    if (index!=-1){
      button[index].setText(choiceString[index]+": "+isOn[index]);
    } else {
      game.start();
    }
    
  }


  
  
}