import java.util.ArrayList;
interface Freezable{
  ArrayList<Coordinate> getFrozen();
  boolean canFreeze(Coordinate cord);
}