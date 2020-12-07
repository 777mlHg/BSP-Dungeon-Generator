package src.dungeonmap;

import src.dungeonmap.common.MyConstants;
import src.dungeonmap.common.MyPoint;
import src.dungeonmap.entities.Potion;
import src.dungeonmap.entities.Monster;
import src.dungeonmap.entities.Player;

import java.awt.*;

import javax.swing.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class gamePanel extends JPanel
{
  private static final long serialVersionUID = 1841571143992758514L;

  private int mapWidth;
  private int mapHeight;
  private Player player;

  private Color wallColor;
  private Color floorColor;

  private BSPTree mapBSP; // initialize the map
  private int[][] tiles; // map in array representation

  int[][] listOfCenters;
  private ArrayList<Monster> monsterList;
  private ArrayList<Potion> potionList;

  /**
   * @param mapWidth
   * @param mapHeight
   * @param fColor
   * @param wColor
   */
  public gamePanel(int mapWidth, int mapHeight, Color fColor, Color wColor)
  {
    this.wallColor = wColor;
    this.floorColor = fColor;
    this.mapWidth = mapWidth;
    this.mapHeight = mapHeight;

    this.mapBSP = new BSPTree(mapWidth, mapHeight); // create a new map
    this.tiles = mapBSP.generateLeafs(); // create new level

    // generate the centers of all the room
    this.listOfCenters = CenterPoints();

    // initialize player and initial location
    this.player = new Player("Player", 15, 5);
    this.player.setPosX(listOfCenters[0][0]);
    this.player.setPosY(listOfCenters[0][1]);

    // create monsters
    this.monsterList = new ArrayList<>();
    generateMonster();
    // create heals
    this.potionList = new ArrayList<>();
    generatePotions();

    setBackground(wallColor);
  }

  protected void paintComponent(Graphics graphics)
  {
    super.paintComponent(graphics);
    // draw level
    drawLevel(graphics);
    // draw HUD
    drawHUD(graphics);

    // draw heal
    for (Potion potions : potionList)
    {
      graphics.drawImage(potions.getPotionImage(), potions.getPosX() * 20, potions.getPosY() * 20, null);
    }
    // draw monster
    for (Monster monster : monsterList)
    {
      graphics.drawImage(monster.getMonsterImage(), monster.getPosX() * 20, monster.getPosY() * 20, null);
    }
    // draw the player
    graphics.drawImage(player.getPlayerImage(), player.getPosX() * 20, player.getPosY() * 20, null);
    graphics.dispose();
  }

  public boolean isFloor(int x, int y)
  {
    return tiles[x][y] == MyConstants.FLOOR;
  }

  /**
   * 
   * @return unique center points
   */
  public int[][] CenterPoints()
  {
    ArrayList<Leaf> mLeafs = mapBSP.getLeafs();
    MyPoint center;
    Map<Integer, MyPoint> pointSet = new HashMap<>();

    for (int i = 0; i < mLeafs.size(); i++)
    {
      center = mLeafs.get(i).getRoom().getCenter();
      pointSet.put(center.hashCode(), center);
    }
    int[][] uniquePoints = new int[pointSet.size()][2];
    int counter = 0;
    for (Map.Entry<Integer, MyPoint> entry : pointSet.entrySet())
    {
      uniquePoints[counter][0] = entry.getValue().getX();
      uniquePoints[counter][1] = entry.getValue().getY();
      counter++;
    }
    return uniquePoints;
  }

  private void drawLevel(Graphics graphics)
  {

    int j;
    int i;
    int tileRepresentation;
    // https://stackoverflow.com/questions/27463951/java-awt-graphics-for-loop-grid
    // iterate through the 2D array level.
    // draw the floor and walls
    for (i = 0; i < mapWidth; i++)
    {
      for (j = 0; j < mapHeight; j++)
      {
        tileRepresentation = tiles[i][j];

        if (tileRepresentation == MyConstants.FLOOR)
        {
          graphics.setColor(floorColor);
          graphics.fillRect((i) * 20, (j) * 20, 20, 20);
        }
        else if (tileRepresentation == MyConstants.WALL)
        {
          graphics.setColor(wallColor);
          graphics.fillRect((i) * 20, (j) * 20, 20, 20);
        }
        // TODO trapped tiles
      }
    }
  }

  public void drawHUD(Graphics graphics)
  {
    graphics.setColor(Color.RED);
    graphics.drawString("Health: " + player.getHealth(), 20, 20);
    graphics.drawString("Attack Damage: " + player.getAttackDamage(), 20, 40);

  }

  public void handlePlayer(int playerMX, int playerMY)
  {
    if (player.isAlive())// Check if player is alive
    {
      playerMX += player.getPosX(); // get move position
      playerMY += player.getPosY(); // get move position
      try
      {
        if (isFloor(playerMX, playerMY)) // check if move location is floor
        {
          Monster monsterAtPlace = getMonster(playerMX, playerMY);
          Potion potionAtPlace = getPotionAtPlace(playerMX, playerMY);
          if (monsterAtPlace != null)
          {
            monsterAtPlace.interact(player);
            if (monsterAtPlace.shouldRemove())
            {
              monsterList.remove(monsterAtPlace);
            }
          }
          else if (potionAtPlace != null)
          {
            potionAtPlace.heal(player);
            potionList.remove(potionAtPlace);
          }
          else
          {
            player.move(playerMX - player.getPosX(), playerMY - player.getPosY());
          }
        }
      }
      catch (Exception e)
      {
        System.out.println(e.getMessage() + " out of bounds");
      }
    }
    else // if dead
    {
      JOptionPane.showMessageDialog(this, "You Died! Press Generate to Restart.");
    }
    repaint();
  }

  private void generatePotions()
  {
    for (int i = 1; i < listOfCenters.length; i++)
    {
      potionList.add(new Potion(listOfCenters[i][0], listOfCenters[i][1]));
    }
  }

  private Potion getPotionAtPlace(int x, int y)
  {
    for (Potion potion : potionList)
    {
      if (potion.getPosX() == x && potion.getPosY() == y)
      {
        return potion;
      }
    }
    return null;
  }

  /**
   * each tile have a chance for monster to spawn
   */
  private void generateMonster()
  {
    int tileRepresentation;
    double randomNum;
    for (int i = 0; i < mapWidth; i++)
    {
      for (int j = 0; j < mapHeight; j++)
      {
        tileRepresentation = tiles[i][j];
        randomNum = Math.random();
        if (tileRepresentation == MyConstants.FLOOR)
        {
          if (player.getPosX() != i && player.getPosY() != j)
          {
            if (randomNum < MyConstants.MONSTER_SPAWN_CHANCE)
            {
              monsterList.add(new Monster("Monster " + monsterList.size(), 10, 2, i, j));
            }
          }
        }

      }
    }
  }

  private Monster getMonster(int x, int y)
  {
    for (Monster monster : monsterList)
    {
      if (monster.getPosX() == x && monster.getPosY() == y)
      {
        return monster;
      }
    }
    return null;
  }

  private void handleMonsters(Monster monster, int monsterMX, int monsterMY)
  {
    monsterMX += monster.getPosX();
    monsterMY += monster.getPosY();
    try
    {
      if (isFloor(monsterMX, monsterMY)) // check check location is a floor tile
      {
        if (getMonster(monsterMX, monsterMY) == null)
        {
          if (player.getPosX() == monsterMX && player.getPosY() == monsterMY) // if monster move on to player location attack the player
          {
            monster.attack(player);
          }
          else
          {
            monster.move(monsterMX - monster.getPosX(), monsterMY - monster.getPosY());
          }
        }
      }
    }
    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }

  }

  public void moveMonsters()
  {

    for (Monster monster : monsterList)
    {

      int monsterX = monster.getPosX();
      int monsterY = monster.getPosY();
      int playerX = player.getPosX();
      int playerY = player.getPosY();

      if (Math.abs(playerX - monsterX) < MyConstants.TRIGGER_DIST &&
              Math.abs(playerY - monsterY) < MyConstants.TRIGGER_DIST)
      {
        AStar as = new AStar(tiles, monsterX, monsterY, false);
        List<AStar.Node> path = as.findPathTo(playerX, playerY);
        int movementX = path.get(1).x - monsterX;
        int movementY = path.get(1).y - monsterY;

        handleMonsters(monster, movementX, movementY);
      }
    }
  }
}