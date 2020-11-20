package src.dungeonmap.entities;

import java.awt.image.BufferedImage;
import src.dungeonmap.common.ImageLoader;

/**
 * Represents the player. The player has an inventory of items they can use.
 */

public class Player extends Character
{

  private BufferedImage playerImage;

  public Player(String name, int maxHealth, int attackDamage)
  {
    super(name, maxHealth, attackDamage, 0, 0);
    this.playerImage = ImageLoader.getImage("./images/pika.png");

  }

  public BufferedImage getPlayerImage()
  {
    return playerImage;
  }

  @Override
  public void attack(Character other)
  {
    int damageWithAdditional = attackDamage;
    other.takeDamage(damageWithAdditional);
  }

}
