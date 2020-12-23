package src.dungeonmap.entities;

import java.awt.image.BufferedImage;
import src.dungeonmap.common.ImageLoader;
import src.dungeonmap.common.Randomizer;

public class Monster extends Character
{

  BufferedImage monsterImage;
  private static final String[] monsterImagesDirectory = new String[] { "./src/images/snake.png", "./src/images/cat.png", "./src/images/ooze.png" };

  public Monster(String name, int maxHealth, int attackDamage, int positionX, int positionY)
  {
    super(name, maxHealth, attackDamage, positionX, positionY);
    String imageDirectory = monsterImagesDirectory[Randomizer.generate(0, monsterImagesDirectory.length - 1)];

    this.monsterImage = ImageLoader.getImage(imageDirectory);
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
