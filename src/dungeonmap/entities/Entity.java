package src.dungeonmap.entities;

public abstract class Entity
{
  protected int posX;
  protected int posY;

  public Entity(int posX, int posY)
  {
    this.posX = posX;
    this.posY = posY;
  }

  public int getPosX()
  {
    return posX;
  }

  public int getPosY()
  {
    return posY;
  }

  public void setPosX(int posX)
  {
    this.posX = posX;
  }

  public void setPosY(int posY)
  {
    this.posY = posY;
  }
}
