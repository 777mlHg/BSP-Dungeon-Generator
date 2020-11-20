package src.dungeonmap.entities;

public abstract class Entity
{
  protected int posX;
  protected int posY;

  public Entity(int positionX, int positionY)
  {
    this.posX = positionX;
    this.posY = positionY;
  }

  public int getPositionX()
  {
    return posX;
  }

  public int getPositionY()
  {
    return posY;
  }

  public void setPositionX(int positionX)
  {
    this.posX = positionX;
  }

  public void setPositionY(int positionY)
  {
    this.posY = positionY;
  }
}
