package src.dungeonmap.entities;

import java.awt.image.BufferedImage;
import src.dungeonmap.common.ImageLoader;

public class Monster extends Character
{

  BufferedImage monsterImage;

  public Monster(String name, int maxHealth, int attackDamage, int positionX, int positionY)
  {
    super(name, maxHealth, attackDamage, positionX, positionY);
    this.monsterImage = ImageLoader.getImage("./images/monster.png");
  }

  public BufferedImage getMonsterImage()
  {
    return this.monsterImage;
  }

  public void interact(Player player)
  {
    player.attack(this);
  }

  public boolean shouldRemove()
  {
    return !isAlive();
  }

}
