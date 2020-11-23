package src.dungeonmap.common;

/**
 * point data structure;
 * https://help.adobe.com/en_US/FlashPlatform/reference/actionscript/3/flash/geom/MyPoint.html
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

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + x;
    result = prime * result + y;
    return result;
  }

}