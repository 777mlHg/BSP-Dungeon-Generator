package src.dungeonmap.common;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageLoader
{
  private static BufferedImage image;

  public static BufferedImage getImage(String fileLocation)
  {
    try
    {
      image = ImageIO.read(new File(fileLocation));
    }
    catch (IOException e)
    {
      System.err.println(e);
    }
    return image;
  }

}
