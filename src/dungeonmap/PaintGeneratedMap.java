package src.dungeonmap;

import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PaintGeneratedMap extends JPanel
{
  private static final long serialVersionUID = 1841571143992758514L;

  private Color wallColor;
  private Color floorColor;

  private BufferedImage playerImage; // image of the player

  BSPTree mapRaw; // initalize the map
  private int[][] level; // map in array representaion

  int posX; // player postition x
  int posY; // player postition y

  /**
   * 
   * @param mapWidth
   * @param mapHeight
   * @param fColor
   * @param wColor
   */
  public PaintGeneratedMap(int mapWidth, int mapHeight, Color fColor, Color wColor)
  {
    this.wallColor = wColor;
    this.floorColor = fColor;
    loadPlayerImage(); // load the image so paintComponet can draw it
    this.mapRaw = new BSPTree(mapWidth, mapHeight); // create a new map
    this.level = mapRaw.generateLeafs(); // create a new level

    // initalize player inital location
    posX = mapRaw.getRandomRoomCenter().getRoomCenter().getX();
    posY = mapRaw.getRandomRoomCenter().getRoomCenter().getY();
    level[posX][posY] = 3;// 3

    System.out.println(posX + " " + posY);

    // TODO: Stop gap. Use margins to offset?
    setBackground(wallColor);
  }

  public void paintComponent(Graphics graphics)
  {

    super.paintComponent(graphics);
    // iterate throught the 2D array BSP level.
    int tileIntRepresentation;
    int j;
    int i;

    for (i = 0; i < level.length; i++)
    {
      for (j = 0; j < level[i].length; j++)
      {

        // https://stackoverflow.com/questions/27463951/java-awt-graphics-for-loop-grid
        tileIntRepresentation = level[i][j];
        if (tileIntRepresentation == 3)

        {
          graphics.setColor(floorColor);
          graphics.fillRect((i + 1) * 20, (j + 1) * 20, 20, 20);
          graphics.drawImage(playerImage, (i + 1) * 20, (j + 1) * 20, null);
        }
        else if (tileIntRepresentation == 0)
        {
          graphics.setColor(floorColor);
          graphics.fillRect((i + 1) * 20, (j + 1) * 20, 20, 20);// +1 to add margin
        }
        else
        {
          graphics.setColor(wallColor);
          graphics.fillRect((i + 1) * 20, (j + 1) * 20, 20, 20); // +1 to add margin
        }
      }
    }
    System.out.println("end");

  }

  private BufferedImage loadPlayerImage()
  {
    try
    {
      playerImage = ImageIO.read(new File("./images/apic.png"));

    }
    catch (IOException e)
    {
      System.err.println(e);
    }
    return playerImage;
  }

}