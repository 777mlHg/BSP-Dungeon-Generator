package src.dungeonmap.common;

/**
 * point data structure;
 * https://help.adobe.com/en_US/FlashPlatform/reference/actionscript/3/flash/geom/Point.html
 */
public class Point
{
  private int x;
  private int y;

  public Point(int x, int y)
  {
    this.x = x;
    this.y = y;
  }

  public int getX()
  {
    return x;
  }

  public int getY()
  {
    return y;
  }
}