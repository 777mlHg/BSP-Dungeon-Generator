package src.dungeonmap.common;

/**
 * point data structure;
 * https://help.adobe.com/en_US/FlashPlatform/reference/actionscript/3/flash/geom/Point.html
 */
public class MyPoint
{
  private int x;
  private int y;

  public MyPoint(int x, int y)
  {
    this.x = x;
    this.y = y;
  }

  public int getX()
  {
    return this.x;
  }

  public int getY()
  {
    return this.y;
  }
}