
package src.dungeonmap.entities;

import src.dungeonmap.common.ImageLoader;
import src.dungeonmap.common.MyConstants;
import java.awt.image.BufferedImage;

/**
 * Healing
 */
public class Potion extends Entity
{
  BufferedImage potionImage;

  public Potion(int posX, int posY)
  {
    super(posX, posY);
    this.potionImage = ImageLoader.getImage("./images/potion.png");

  }

  public BufferedImage getPotionImage()
  {
    return this.potionImage;
  }

  public void heal(Player player)
  {
    player.heal(MyConstants.HEAL);
  }
}
