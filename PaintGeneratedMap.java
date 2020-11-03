
import java.awt.*;
import javax.swing.*;

public class PaintGeneratedMap extends JPanel
{
  private static final long serialVersionUID = 1841571143992758514L;
  private int[][] level;

  private Color wallColor;
  private Color floorColor;

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
    level = new BSPTree(mapWidth, mapHeight).generateLeafs();
    setBackground(wColor); // TODO: Stop gap. Use margins to offset?
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
        if (tileIntRepresentation == 0)
        {
          graphics.setColor(floorColor);
          graphics.fillRect((i + 1) * 10, (j + 1) * 10, 10, 10);// +1 to add margin
        }
        else
        {
          graphics.setColor(wallColor);
          graphics.fillRect((i + 1) * 10, (j + 1) * 10, 10, 10); // +1 to add margin
        }
      }
    }
  }
}