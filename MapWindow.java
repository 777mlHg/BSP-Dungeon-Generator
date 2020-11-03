import javax.swing.JFrame;
import javax.swing.BoxLayout;
import java.awt.*;

/**
 * MapWindow
 */
public class MapWindow extends JFrame
{
  private static final long serialVersionUID = 2L;// remove warning

  public static void paintMap(int mapWidth, int mapHeight, Color floorColor, Color wallColor, PaintGeneratedMap mapPanel, JFrame mapFrame)
  {
    mapPanel.setLayout(new BoxLayout(mapPanel, BoxLayout.Y_AXIS));
    mapFrame.setSize(new Dimension((mapWidth + 6) * 10, (mapHeight + 6) * 10));

    // https://stackoverflow.com/questions/9347076/how-to-remove-all-components-from-a-jframe-in-java
    mapFrame.getContentPane().removeAll();
    mapFrame.repaint();

    mapFrame.add(mapPanel);
    mapFrame.setVisible(true);
  }
}