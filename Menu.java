import java.util.HashMap;
import java.util.Scanner;

abstract class Menu
{
  int choice;
  int numberOfChoices;
  Scanner input;
  HashMap<Integer, String> options = new HashMap<Integer, String>();
  String message = "Enter the corresponding number to the map you want to generate";

  public abstract void initializeOptions();

  /**
   * print options initalized by initializeOptions()
   */
  public void printOptions()
  {
    for (int i = 1; i < options.size() + 1; i++)
    {
      System.out.println("" + i + " " + options.get(i));
    }
  }

  public int numberedChoice()
  {
    printOptions();
    input = new Scanner(System.in);

    System.out.println(message);
    do
    {
      System.out.print(String.format("Enter a number between 1 and %d: ", numberOfChoices));
      while (!input.hasNextInt())
      {
        System.out.print("Invalid input, try again: ");
        input.next();
      }
      choice = input.nextInt();
    }
    while (numberOfChoices < choice || choice < 0);
    return choice;
  }

  public static void printMap(int[][] mapArray, String wallColor, String floorColor)
  {
    for (int[] x : mapArray)
    {
      for (int y : x)
      {
        String out = (y == 0) ? floorColor : wallColor;
        System.out.print(out);

      }
      System.out.println("\033[0m");
    }
    System.out.println("done.");

  }

  public static void clearScreen()
  {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

}

class MapSizeMenu extends Menu
{
  @Override
  public void initializeOptions()
  {
    options.put(1, "Small(40x40)");
    options.put(2, "Medium(45x45)");
    options.put(3, "Large(50x50)");
    options.put(4, "Custom");
    numberOfChoices = options.size();
  }
}

class CustomMenu extends Menu
{

  @Override
  public void initializeOptions()
  {

    String[] colors = {
            "Black ",
            "Red ",
            "Green ",
            "Yellow ",
            "Blue ",
            "Magenta ",
            "Cyan ",
            "White "
    };
    for (int i = 1; i < colors.length + 1; i++)
    {
      options.put(i, colors[i - 1]);
    }
    numberOfChoices = options.size();
  }

  // used for inputing height and width
  public int intInput(int lowerLimit, int upperLimit)
  {
    input = new Scanner(System.in);
    do
    {
      System.out.print(String.format("Enter a number between %d and %d: ", lowerLimit, upperLimit));
      while (!input.hasNextInt())
      {
        System.out.print("Invalid input, try again: ");
        input.next();
      }
      choice = input.nextInt();
    }
    while (upperLimit < choice || choice < lowerLimit);
    return choice;
  }
}