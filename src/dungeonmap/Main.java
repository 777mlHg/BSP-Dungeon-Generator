package src.dungeonmap;

import javax.swing.SwingUtilities;

/**
 * 
 * Rogue like game
 * 
 * @author Joseph Stout
 * 
 */

public class Main
{
  public static void main(String[] args)
  {
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        new MainWindow();
      }
    });
  }
}
