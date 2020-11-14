package src.dungeonmap;

import src.dungeonmap.common.MyConstants;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class PaintGeneratedMap extends JPanel
{
  private static final long serialVersionUID = 1841571143992758514L;

  private Color wallColor;
  private Color floorColor;

  private BufferedImage playerImage; // image of the player

  private BSPTree mapRaw; // initalize the map
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
    System.out.println(Arrays.deepToString(level));

    // initalize player inital location
    posX = mapRaw.getRandomRoomCenter().getX();
    posY = mapRaw.getRandomRoomCenter().getY();
    level[posX][posY] = MyConstants.PLAYER;// 3

    // TODO: Stop gap. Use margins to offset?
    setBackground(wallColor);

    // https://stackoverflow.com/questions/31442009/how-to-move-an-object-in-a-jpanel-using-the-arrow-keys
    Action leftAction = new AbstractAction()
    {
      @Override
      public void actionPerformed(ActionEvent event)// left
      {
        try
        {
          System.out.println("asd");

          posX -= 1;
          if (level[posX][posY] == MyConstants.WALL)
          {
            posX += 1;
          }
        }
        catch (Exception e)
        {
          // TODO: handle exception
          System.out.println(e);

        }
        System.out.println(posX);
        repaint();
      }
    };

    Action rightAction = new AbstractAction()
    {
      @Override
      public void actionPerformed(ActionEvent event) // right
      {
        try
        {
          posX += 1;
          if (level[posX][posY] == MyConstants.WALL)
          {
            posX -= 1;
          }
          System.out.println(posX);
        }
        catch (Exception e)
        {
          // TODO: handle exception
          System.out.println(e);
        }

        repaint();
      }
    };
    Action upAction = new AbstractAction()
    {
      @Override
      public void actionPerformed(ActionEvent event) // up
      {
        try
        {
          posY -= 1;
          if (level[posX][posY] == MyConstants.WALL)
          {
            posY += 1;
          }
        }
        catch (Exception e)
        {
          // TODO: handle exception
          System.out.println(e);

        }
        System.out.println(posY);

        repaint();
      }
    };
    Action downAction = new AbstractAction()
    {
      @Override
      public void actionPerformed(ActionEvent event) // down
      {
        try
        {
          posY += 1;
          if (level[posX][posY] == MyConstants.WALL)
          {
            posY -= 1;
          }
        }
        catch (Exception e)
        {
          // TODO: handle exception
          System.out.println(e);

        }
        System.out.println(posY);

        repaint();
      }
    };

    bindKeyStroke(WHEN_IN_FOCUSED_WINDOW, "move.left", KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), leftAction);
    bindKeyStroke(WHEN_IN_FOCUSED_WINDOW, "move.left", KeyStroke.getKeyStroke(KeyEvent.VK_KP_LEFT, 0), leftAction);
    bindKeyStroke(WHEN_IN_FOCUSED_WINDOW, "move.left", KeyStroke.getKeyStroke(KeyEvent.VK_4, 0), leftAction);
    bindKeyStroke(WHEN_IN_FOCUSED_WINDOW, "move.left", KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), leftAction);

    bindKeyStroke(WHEN_IN_FOCUSED_WINDOW, "move.right", KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), rightAction);
    bindKeyStroke(WHEN_IN_FOCUSED_WINDOW, "move.right", KeyStroke.getKeyStroke(KeyEvent.VK_KP_RIGHT, 0), rightAction);
    bindKeyStroke(WHEN_IN_FOCUSED_WINDOW, "move.right", KeyStroke.getKeyStroke(KeyEvent.VK_6, 0), rightAction);
    bindKeyStroke(WHEN_IN_FOCUSED_WINDOW, "move.right", KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), rightAction);
    // up
    bindKeyStroke(WHEN_IN_FOCUSED_WINDOW, "move.up", KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), upAction);
    bindKeyStroke(WHEN_IN_FOCUSED_WINDOW, "move.up", KeyStroke.getKeyStroke(KeyEvent.VK_KP_UP, 0), upAction);
    bindKeyStroke(WHEN_IN_FOCUSED_WINDOW, "move.up", KeyStroke.getKeyStroke(KeyEvent.VK_6, 0), upAction);
    bindKeyStroke(WHEN_IN_FOCUSED_WINDOW, "move.up", KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), upAction);
    // down
    bindKeyStroke(WHEN_IN_FOCUSED_WINDOW, "move.down", KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), downAction);
    bindKeyStroke(WHEN_IN_FOCUSED_WINDOW, "move.down", KeyStroke.getKeyStroke(KeyEvent.VK_KP_DOWN, 0), downAction);
    bindKeyStroke(WHEN_IN_FOCUSED_WINDOW, "move.down", KeyStroke.getKeyStroke(KeyEvent.VK_6, 0), downAction);
    bindKeyStroke(WHEN_IN_FOCUSED_WINDOW, "move.down", KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), downAction);

  }

  protected void paintComponent(Graphics graphics)
  {

    super.paintComponent(graphics);
    // iterate throught the 2D array BSP level.
    int tileIntRepresentation;
    int j;
    int i;
    Graphics2D g2d = (Graphics2D)graphics.create();

    // https://stackoverflow.com/questions/27463951/java-awt-graphics-for-loop-grid
    for (i = 0; i < level.length; i++)
    {
      for (j = 0; j < level[i].length; j++)
      {
        tileIntRepresentation = level[i][j];

        if (tileIntRepresentation == MyConstants.FLOOR)
        {
          g2d.setColor(floorColor);
          g2d.fillRect((i + 1) * 20, (j + 1) * 20, 20, 20);// +1 to add margin
        }
        else if (tileIntRepresentation == MyConstants.WALL)
        {
          g2d.setColor(wallColor);
          g2d.fillRect((i + 1) * 20, (j + 1) * 20, 20, 20); // +1 to add margin
        }
        else
        {
          g2d.setColor(floorColor);
          g2d.fillRect((i + 1) * 20, (j + 1) * 20, 20, 20);
        }
      }
    }

    g2d.drawImage(playerImage, (posX + 1) * 20, (posY + 1) * 20, null);

    g2d.dispose();
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

  protected void bindKeyStroke(int condition, String name, KeyStroke keyStroke, Action action)
  {
    InputMap im = getInputMap(condition);
    ActionMap am = getActionMap();

    im.put(keyStroke, name);
    am.put(name, action);
  }
}