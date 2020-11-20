package src.dungeonmap;

import src.dungeonmap.common.MyConstants;
import src.dungeonmap.common.MyPoint;

import src.dungeonmap.entities.Monster;
import src.dungeonmap.entities.Player;

import java.awt.*;

import javax.swing.*;

import java.util.ArrayList;
import java.util.List;

public class MapPanel extends JPanel
{
  private static final long serialVersionUID = 1841571143992758514L;

  private int mapWidth;
  private int mapHeight;
  private Player player;

  private Color wallColor;
  private Color floorColor;

  private BSPTree mapBSP; // initialize the map
  private int[][] level; // map in array representation

  int[][] listOfCenters;
  private ArrayList<Monster> monsterList;

  /**
   * @param mapWidth
   * @param mapHeight
   * @param fColor
   * @param wColor
   */
  public MapPanel(int mapWidth, int mapHeight, Color fColor, Color wColor)
  {
    this.wallColor = wColor;
    this.floorColor = fColor;
    this.mapWidth = mapWidth;
    this.mapHeight = mapHeight;

    this.mapBSP = new BSPTree(mapWidth, mapHeight); // create a new map
    this.level = mapBSP.generateLeafs(); // create new level

    // generate the centers of all the room
    // TODO: items in the center of the room
    this.listOfCenters = CenterPoints();

    // initialize player and initial location
    this.player = new Player("Player", 10, 10);
    this.player.setPositionX(listOfCenters[0][0]);
    this.player.setPositionY(listOfCenters[0][1]);

    // create monsters
    this.monsterList = generateEnemies();

    setBackground(wallColor);
  }

  protected void paintComponent(Graphics graphics)
  {
    super.paintComponent(graphics);
    int tileRepresentation;
    int j;
    int i;
    Graphics2D g2d = (Graphics2D)graphics.create();

    // https://stackoverflow.com/questions/27463951/java-awt-graphics-for-loop-grid
    // iterate through the 2D array level.
    for (i = 0; i < mapWidth; i++)
    {
      for (j = 0; j < mapHeight; j++)
      {
        tileRepresentation = level[i][j];

        if (tileRepresentation == MyConstants.FLOOR)
        {
          g2d.setColor(floorColor);
          g2d.fillRect((i) * 20, (j) * 20, 20, 20);
        }
        else if (tileRepresentation == MyConstants.WALL)
        {
          g2d.setColor(wallColor);
          g2d.fillRect((i) * 20, (j) * 20, 20, 20);
        }
        // TODO trapped tiles
      }
    }

    // draw monster
    for (Monster monster : monsterList)
    {
      g2d.drawImage(monster.getMonsterImage(), monster.getPositionX() * 20, monster.getPositionY() * 20, null);
    }
    // draw the player
    g2d.drawImage(player.getPlayerImage(), player.getPositionX() * 20, player.getPositionY() * 20, null);
    g2d.dispose();
  }

  public int[][] CenterPoints()
  {
    ArrayList<Leaf> mLeafs = mapBSP.getLeafs();
    int[][] possiblePoints = new int[mLeafs.size()][2];
    for (int i = 0; i < mLeafs.size(); i++)
    {
      MyPoint centers = mLeafs.get(i).getRoom().getCenter();
      possiblePoints[i][0] = centers.getX();
      possiblePoints[i][1] = centers.getY();
    }
    return possiblePoints;
  }

  private ArrayList<Monster> generateEnemies()
  {
    int tileRepresentation;
    ArrayList<Monster> enemies = new ArrayList<>();
    for (int i = 0; i < mapWidth; i++)
    {
      for (int j = 0; j < mapHeight; j++)
      {
        tileRepresentation = level[i][j];
        if (tileRepresentation == MyConstants.FLOOR && Math.random() < MyConstants.MONSTER_SPAWN_CHANCE)
        {
          if (player.getPositionX() != i && player.getPositionY() != j)
          {
            enemies.add(new Monster("Monster" + i + j, 10, 1, i, j));
          }
        }
      }
    }
    return enemies;
  }

  public boolean isValidPosition(int x, int y)
  {
    return level[x][y] == MyConstants.FLOOR;
  }

  public void handlePlayer(int playerMX, int playerMY)
  {

    playerMX += player.getPositionX();
    playerMY += player.getPositionY();
    if (isValidPosition(playerMX, playerMY))
    {
      Monster monsterAtPlace = getMonster(playerMX, playerMY);
      if (monsterAtPlace != null)
      {
        monsterAtPlace.interact(player);
        if (monsterAtPlace.shouldRemove())
        {
          monsterList.remove(monsterAtPlace);
        }
      }
      else
      {
        player.move(playerMX - player.getPositionX(), playerMY - player.getPositionY());
      }
    }
    repaint();
  }

  private Monster getMonster(int x, int y)
  {
    for (Monster monster : monsterList)
    {
      if (monster.getPositionX() == x && monster.getPositionY() == y)
      {
        return monster;
      }
    }
    return null;
  }

  // TODO: Refactoring
  private void handleMonsters(Monster monster, int monsterMX, int monsterMY)
  {
    monsterMX += monster.getPositionX();
    monsterMY += monster.getPositionY();

    if (isValidPosition(monsterMX, monsterMY))
    {
      if (getMonster(monsterMX, monsterMY) == null)
      {
        if (player.getPositionX() == monsterMX && player.getPositionY() == monsterMY)
        {
          monster.attack(player);
        }
        else
        {
          monster.move(monsterMX - monster.getPositionX(), monsterMY - monster.getPositionY());
        }
      }
    }
  }

  public void moveMonsters()
  {

    for (Monster monster : monsterList)
    {

      int monsterX = monster.getPositionX();
      int monsterY = monster.getPositionY();
      int playerX = player.getPositionX();
      int playerY = player.getPositionY();

      if (Math.abs(playerX - monsterX) < MyConstants.TRIGGER_DIST &&
              Math.abs(playerY - monsterY) < MyConstants.TRIGGER_DIST)
      {
        AStar as = new AStar(level, monsterX, monsterY, true);
        List<AStar.Node> path = as.findPathTo(playerX, playerY);
        int movementX = path.get(1).x - monsterX;
        int movementY = path.get(1).y - monsterY;
        handleMonsters(monster, movementX, movementY);
      }
    }
  }
}
