/**
 * @author Joseph Stout
 *
 *         this program use binary space partitioning to generate a random
 *         dungeon map
 */
public class Main
{
  public static void main(String[] args)
  {

    String wallColor;
    String floorColor;
    int mapWidth;
    int mapHeight;
    int[][] map;
    int sizeChoice;

    MapSizeMenu menu = new MapSizeMenu();

    System.out.println("Welcome to my dungeon generator!");// welcome message

    menu.initializeOptions();
    sizeChoice = menu.numberedChoice();

    floorColor = "\033[47m \033[0m";
    wallColor = "\033[40m \033[0m";

    if (sizeChoice == 1)
    {
      mapWidth = 30;
      mapHeight = 30;
    }
    else if (sizeChoice == 2)
    {
      mapWidth = 40;
      mapHeight = 40;
    }
    else if (sizeChoice == 3)
    {
      mapWidth = 50;
      mapHeight = 50;
    }

    else // custom menu selection
    {
      String[] ansiColors = {
              "\033[40m ", // BLACK
              "\033[41m ", // RED
              "\033[42m ", // GREEN
              "\033[43m ", // YELLOW
              "\033[44m ", // BLUE
              "\033[45m ", // MAGENTA
              "\033[46m ", // CYAN
              "\033[47m " // WHITE
      };
      CustomMenu custom = new CustomMenu();
      custom.initializeOptions();
      System.out.println("Enter integer choice for width: ");
      mapWidth = custom.intInput(20, 100);
      System.out.println("Enter integer choice for height: ");
      mapHeight = custom.intInput(20, 100);
      System.out.println("Enter choice for wall color: ");
      wallColor = ansiColors[custom.numberedChoice() - 1] + "\033[0m";
      System.out.println("Enter choice for floor color: ");
      floorColor = ansiColors[custom.numberedChoice() - 1] + "\033[0m";
    }

    map = new BSPTree().generateLeafs(mapWidth, mapHeight); // generate the map with 1s and 0s
    Menu.clearScreen(); // clear console
    Menu.printMap(map, wallColor, floorColor);// print the map
  }
}
