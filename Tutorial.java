import java.awt.*;
import javax.swing.*;
import java.lang.Runnable;
class Tutorial extends JPanel{
  private static final String abcd = "very fun game :)";
  public Tutorial(Runnable setMenu){
    JLabel label = new JLabel();
    label.setText(abcd);
    add(label);
    JButton button = new JButton();
    button.addActionListener(e -> {
      setMenu.run();
    });
  }
  
}