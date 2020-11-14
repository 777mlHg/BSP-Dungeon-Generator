package src.dungeonmap.common;

/**
 * Generate random number between min and max
 * 
 * @author Abel Callejo
 *         https://stackoverflow.com/questions/363681/how-do-i-generate-random-integers-within-a-specific-range-in-java
 * 
 * @param min
 * @param max
 * @return random int between min and max
 */
public class Randomizer // using random number
{
  public static int generate(int min, int max)
  {
    return min + (int)(Math.random() * ((max - min) + 1));
  }
}
