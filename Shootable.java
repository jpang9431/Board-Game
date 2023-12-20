import java.util.ArrayList;
interface Shootable{
  ArrayList<Coordinate> getTarget();
  boolean canShoot(Coordinate cord);
}