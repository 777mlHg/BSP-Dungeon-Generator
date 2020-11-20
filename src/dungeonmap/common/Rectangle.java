package src.dungeonmap.common;

/**
 * Rectangle structure; represents Rectangle in actionScript
 * https://help.adobe.com/en_US/FlashPlatform/reference/actionscript/3/flash/geom/Rectangle.html
 */
public class Rectangle
{
  private int x1;
  private int y1;
  private int x2;
  private int y2;

  public Rectangle(int x, int y, int w, int h)
  {
    this.x1 = x;
    this.y1 = y;
    this.x2 = x + w;
    this.y2 = y + h;
  }

  public int getX1()
  {
    return this.x1;
  }

  public int getX2()
  {
    return this.x2;
  }

  public int getY1()
  {
    return this.y1;
  }

  public int getY2()
  {
    return this.y2;
  }

  /**
   * @author https://eskerda.com/bsp-dungeon-generation/
   * @return center of the rectangle
   */
  public MyPoint getCenter()
  {
    int centerX = (int)Math.floor((this.x1 + this.x2) / 2);
    int centerY = (int)Math.floor((this.y1 + this.y2) / 2);
    MyPoint centerPointObject = new MyPoint(centerX, centerY);
    return centerPointObject;
  }

}