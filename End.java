import java.util.ArrayList;
import java.awt.Color;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

class End extends JPanel implements ActionListener{
  private Main main = null;
  private Font font = new Font(Font.DIALOG, Font.PLAIN, 30);
  private Color txtColor = new Color(0,128,128);
  End(int winTeam, Main main){
    this.main = main;
    this.setLayout(new GridLayout(0,1));
    JLabel label = new JLabel("Team "+winTeam+" wins", JLabel.CENTER);
    label.setFont(font);
    label.setForeground(txtColor);
    JButton button = new JButton("Play again");
    button.addActionListener(e -> {
      main.restart();
    });
    button.setFont(font);
    button.setForeground(txtColor);
    this.setBackground(Color.DARK_GRAY);
    this.add(label);
    this.add(button);
  }
  public void actionPerformed(ActionEvent e) {}
}