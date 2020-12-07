package src.dungeonmap;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * 
 * key listener is added to the app(J frame)
 */
public class KeyEventListener implements KeyListener
{
  private gamePanel map;

  public KeyEventListener(MainWindow runner)
  {
    this.map = runner.getMapPanel();

  }

  @Override
  public void keyPressed(KeyEvent e)
  {
    switch (e.getKeyCode())
    {
      case KeyEvent.VK_W:
        updateMap(map, 0, -1);
        break;
      case KeyEvent.VK_A:
        updateMap(map, -1, 0);
        break;
      case KeyEvent.VK_S:
        updateMap(map, 0, 1);
        break;
      case KeyEvent.VK_D:
        updateMap(map, 1, 0);
    }

  }

  // update the map when a movement key is pressed
  private void updateMap(gamePanel map, int mX, int mY)
  {
    map.handlePlayer(mX, mY);
    map.moveMonsters();
  }

  @Override
  public void keyReleased(KeyEvent e)
  {

  }

  @Override
  public void keyTyped(KeyEvent e)
  {
  }
}
