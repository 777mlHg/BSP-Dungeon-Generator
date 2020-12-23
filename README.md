# BSP Dungeon Generator 🗺️

Java dungeon generator using binary space partitioning

https://en.wikipedia.org/wiki/Binary_space_partitioning

## Installation

- Clone this repo
- `javac Main.java` in the directory of Main.java to compile
- `java Main` to execute the program

## File Structure

📦src  
┣ 📂dungeonmap  
┃ ┣ 📂common  
┃ ┃ ┣ 📜ImageLoader.java  
┃ ┃ ┣ 📜MyConstants.java  
┃ ┃ ┣ 📜MyPoint.java  
┃ ┃ ┣ 📜Randomizer.java  
┃ ┃ ┗ 📜Rectangle.java  
┃ ┣ 📂entities  
┃ ┃ ┣ 📜Character.java  
┃ ┃ ┣ 📜Entity.java  
┃ ┃ ┣ 📜Monster.java  
┃ ┃ ┣ 📜Player.java  
┃ ┃ ┗ 📜Potion.java  
┃ ┣ 📜Astar.java  
┃ ┣ 📜BSPTree.java  
┃ ┣ 📜gamePanel.java  
┃ ┣ 📜KeyEventListener.java  
┃ ┣ 📜Main.java  
┃ ┗ 📜MainWindow.java  
┗ 📂images  
┃ ┣ 📜cat.png  
┃ ┣ 📜monster.png  
┃ ┣ 📜ooze.png  
┃ ┣ 📜player.png  
┃ ┣ 📜potion.png  
┃ ┗ 📜snake.png

## Usage

- Open terminal in directory of Main.java
- Compile and run

- Click generate to generate a map
- Change width, height, wall and floor color to modify map.

- Use WASD keys to move character

## Works Cited

Map Generation

- [Timothy Hely](https://gamedevelopment.tutsplus.com/tutorials/how-to-use-bsp-trees-to-generate-game-maps--gamedev-12268)

Path finding

- [rosettacodeorg](https://rosettacode.org/wiki/A*_search_algorithm#Java)

Player, Monster and Items

- [Trystan](http://trystans.blogspot.com/2016/01/roguelike-tutorial-00-table-of-contents.html)

## License

[MIT](https://choosealicense.com/licenses/mit/)
