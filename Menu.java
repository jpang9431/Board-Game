import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.io.File;
import javax.imageio.*;
import java.awt.Image;
import java.util.function.Consumer;

class Menu extends JPanel implements MouseListener {
  private int targetFPS = 60;
  private double itteration = 0;
  private double scaleXY = 0;
  private double delta = 0.05;
  private int bobing = 0;
  private static final int width = Game.width, height = Game.height;
  private BufferedImage[] imageOG = new BufferedImage[5];
  private BufferedImage[] rotImage = new BufferedImage[5];
  private File alive = new File("images/Image.jpg");
  private int imageNum;
  private Consumer<Integer> listener;
  private String label;
  private Color txtColor = new Color(0,128,128);
  private Color backgroundColor = Color.DARK_GRAY;
  private int imgW = 100, imgH = 100;

  /**
   * @param paths takes in paths of pictures and addes them to imageOG, 5 Max
   * @param listener takes in a method that takes in an int and returns void
   **/
  public Menu(String[] paths, Consumer<Integer> listener) {
    setPreferredSize(new Dimension(width, height));
    addMouseListener(this);
    if (paths != null) {
      imageOG = new BufferedImage[paths.length];
      rotImage = new BufferedImage[paths.length];
      for (int i = 0; i < paths.length; i++) {
        try {
          imageOG[i] = resize(ImageIO.read(new File(paths[i])), imgW, imgH);
        } catch (Exception e) {
          imageOG[i] = createImageFromString(paths[i], width, height);
          e.printStackTrace();
        }finally{
          rotImage[i] = imageOG[i];
        }
      }
    } else {
      for (int i = 0; i < imageOG.length; i++) {
        try {
          imageOG[i] = (ImageIO.read(alive));
        } catch (Exception e) {
          imageOG[i] = createImageFromString("Placeholder", width, height);
          e.printStackTrace();
        }finally{
          rotImage[i] = imageOG[i];
        }
      }
    }
    this.listener = listener;

    Timer timer = new Timer(1000 / targetFPS, e -> {
      // System.out.println("Reboot");
      Point mousePoint = MouseInfo.getPointerInfo().getLocation();
      SwingUtilities.convertPointFromScreen(mousePoint, this);

      imageNum = findComponent(mousePoint);
      // System.out.println(imageNum);

      itteration += delta;
      bobing = (int) (3.5 * (Math.sin(itteration) + Math.sin(itteration / 9) - Math.sin(itteration / 5)));
      scaleXY = 1 - (((Math.cos(itteration) - Math.cos(itteration / 4) + Math.cos((itteration / 3) + 2)) + 2.712) / 14);
      for (int i = 0; i < imageOG.length; i++) {
        rotImage[i] = rotateImageByDegrees((BufferedImage) imageOG[i], 4
            * (Math.sin(itteration) - Math.sin(itteration / 2.0) + Math.sin(itteration / 3.0) + Math.cos(itteration)),
            scaleXY);
      }
      repaint();
    });
    timer.setRepeats(true);
    timer.start();
  }

  public Menu(String[] paths, Consumer<Integer> listener, String label) {
    this(paths, listener);
    this.label = label;
  }
  
  public Menu(String[] paths, Consumer<Integer> listener, String label, Color txtColor, Color backgroundColor){
    this(paths, listener, label);
    this.txtColor = txtColor;
    this.backgroundColor = backgroundColor;
  }
  /**
   * @param paths array of strings for file paths or strings to be made into images (if not a valid path)
   * @param listener method to be called back to once item is clicked
   * @param label text to be displayed at the top of the screen
   * @param txtColor text color for creation of images, unused if all paths are valid
   * @param backgroundColor background color for creation of images, unused if all paths are valid
   * @param width default width for the images (autoresizes)
   * @param height default height of images (autoresizes)
  **/
  public Menu(String[] paths, Consumer<Integer> listener, String label, Color txtColor, Color backgroundColor, int width, int height){
    this(paths, listener, label, txtColor, backgroundColor);
    imgW = width;
    imgH = height;
  }
  public Menu(String[] paths, Consumer<Integer> listener, int width, int height){
    this(paths, listener);
    imgW = width;
    imgH = height;
  }
  public Menu(String[] paths, Consumer<Integer> listener, String label, int width, int height){
    this(paths, listener, width, height);
    this.label = label;
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    if (label != null) {
      Font oldFont = g.getFont();
      Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 30);
      g.setFont(font);
      g.setColor(new Color(0,128,128));
      FontMetrics fontMetric = g2d.getFontMetrics(font);
      int x = (width - fontMetric.stringWidth(label)) / 2;
      int y = ((height - fontMetric.getHeight()) / 2) + fontMetric.getAscent() - 200;
      g2d.drawString(label, x, y);
      g.setFont(oldFont);
    }

    for (int i = 0; i < imageOG.length; i++) {
      int curX = (width / (imageOG.length + 1)) * (i + 1);
      if (imageNum == i) {
        g2d.drawImage(rotImage[i], (int) (curX - (rotImage[i].getWidth() * scaleXY) / 2),
            (int) ((height / 2) - (rotImage[i].getHeight() * scaleXY) / 2 - bobing), null);
      } else {
        g2d.drawImage(imageOG[i], (int) (curX - imageOG[i].getWidth() / 2),
            (int) ((height / 2) - (imageOG[i].getHeight()) / 2), null);
      }

    }
  }

  public BufferedImage createImageFromString(String str, int width, int height) {
    Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 30);
    Canvas c = new Canvas();
    FontMetrics fontMetric = c.getFontMetrics(font);

    BufferedImage newImg;
    int x;
    int fill;
    if(fontMetric.stringWidth(str)<width){
      newImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      x = (width - fontMetric.stringWidth(str))/2;
      fill = width;
    }else{
      newImg = new BufferedImage((int)(fontMetric.stringWidth(str)*1.1), height, BufferedImage.TYPE_INT_ARGB);
      x = 0;
      fill = fontMetric.stringWidth(str);
    }

    Graphics2D g2d =  newImg.createGraphics();
    Font oldFont = g2d.getFont();

    g2d.setColor(backgroundColor);
    g2d.fillRect(0,0, fill, height);

    g2d.setFont(font);
    g2d.setColor(txtColor);

    int y = ((height - fontMetric.getHeight()) / 2) + fontMetric.getAscent();
    g2d.drawString(str, x, y);

    g2d.setFont(oldFont);

    return resize(newImg, width, height);
  }
  //https://stackoverflow.com/questions/9417356/bufferedimage-resize
  public BufferedImage resize(BufferedImage img, int newW, int newH) {
    int w = img.getWidth();
    int h = img.getHeight();
    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = dimg.createGraphics();
    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
    g.dispose();
    return dimg;
  }

  public BufferedImage rotateImageByDegrees(BufferedImage img, double angle, double scale) {

    double rads = Math.toRadians(angle);
    double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
    int w = img.getWidth();
    int h = img.getHeight();
    int newWidth = (int) Math.floor(w * cos + h * sin);
    int newHeight = (int) Math.floor(h * cos + w * sin);

    BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = rotated.createGraphics();
    AffineTransform at = new AffineTransform();
    at.translate((newWidth - w) / 2, (newHeight - h) / 2);

    int x = w / 2;
    int y = h / 2;

    at.rotate(rads, x, y);
    at.scale(scale, scale);
    g2d.setTransform(at);
    g2d.drawImage(img, 0, 0, this);
    g2d.dispose();

    return rotated;
  }

  public void setText(String string) {
    label = string;
  }

  private int findComponent(Point point) {
    int pointX = (int) point.getX(), pointY = (int) point.getY();
    // System.out.println(pointX + ", " + pointY);
    for (int i = 0; i < imageOG.length; i++) {
      int curX = (width / (imageOG.length + 1)) * (i + 1);
      int startX = (int) (curX - (imageOG[i].getWidth()) / 2),
          startY = (int) ((height / 2) - (imageOG[i].getHeight()) / 2);
      if ((pointX > startX && pointX < startX + imageOG[i].getWidth())
          && (pointY > startY && pointY < startY + imageOG[i].getHeight())) {
        return i;
      }
    }
    return -1;
  }

  @Override
  public void mousePressed(MouseEvent e) {

  }

  @Override
  public void mouseReleased(MouseEvent e) {
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    // System.out.println("Hello world");
    int pic = findComponent(e.getPoint());
    // System.out.println(pic);
    if (pic != -1) {
      listener.accept(pic);
    }
  }

  /*
  -----------------------------------------------------
  Start of getters/setters
  -----------------------------------------------------
  */
  public BufferedImage getOGBufferedImage(int i){
    return imageOG[i];
  }
  public BufferedImage getRotatedBufferedImage(int i){
    return rotImage[i];
  }
  public Consumer<Integer> getListener(){
    return listener;
  }
  public void setListener(Consumer<Integer> lstn){
    listener = lstn;
  }
  public String getLabel(){
    return label;
  }
  public void setLabel(String label){
    this.label = label;
  }
  public Color txtColor(){
    return txtColor;
  }
  public void setTxtColor(Color c){
    txtColor = c;
  }
  public Color bgrndColor(){
    return backgroundColor;
  }
  public void setBgrndColor(Color c){
    backgroundColor = c;
  }
  public int targetWidth(){
    return imgW;
  }
  public int targetH(){
    return imgH;
  }
  public void setWidth(int width){
    imgW = width;
  }
  public void setHeight(int height){
    imgH = height;
  }
}