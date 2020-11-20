package src.dungeonmap;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainWindow extends JFrame
{
  private static final long serialVersionUID = 1L; // remove warning

  private int mapPanelWidth = 30; // set default size for mapPanel
  private int mapPanelHeight = 30; // set default size for mapPanel

  private MapPanel mapPanel;

  private static JFrame app = new JFrame("Dungeon Map Generator GUI"); // Main window
  private static JPanel inputsContainerPanel = new JPanel();
  private static JPanel mapInputsPanel = new JPanel();
  private static JPanel generatePanel = new JPanel();
  private static JPanel containerPanel = new JPanel();

  private JLabel heightJLabel = new JLabel("Map Height:");
  private JLabel widthJLabel = new JLabel("Map Width:");

  private JButton wallColorButton = new JButton("Wall Color");
  private JButton floorColorButton = new JButton("Floor Color");

  private JTextField widthTextField = new JTextField(3);
  private JTextField heightTextField = new JTextField(3);

  private ButtonHandler buttonHandler;
  private JButton generateButton = new JButton("Generate");

  private Color wallColor = Color.gray; // set the default wall color
  private Color floorColor = Color.white;// set the default floor color

  KeyEventListener keyEventListener;

  public MainWindow()
  {
    inputsContainerPanel.setLayout(new BorderLayout());

    mapInputsPanel.add(heightJLabel);
    mapInputsPanel.add(heightTextField);

    mapInputsPanel.add(widthJLabel);
    mapInputsPanel.add(widthTextField);
    mapInputsPanel.add(wallColorButton);
    mapInputsPanel.add(floorColorButton);

    generateButton.setPreferredSize(new Dimension(420, 30));
    generatePanel.add(generateButton);

    containerPanel.setLayout(new BorderLayout());
    containerPanel.add(mapInputsPanel, BorderLayout.NORTH);
    containerPanel.add(generatePanel, BorderLayout.LINE_START);
    widthTextField.setText("" + mapPanelWidth);
    heightTextField.setText("" + mapPanelHeight);

    buttonHandler = new ButtonHandler();
    wallColorButton.addActionListener(buttonHandler);
    wallColorButton.setBackground(wallColor);
    floorColorButton.addActionListener(buttonHandler);
    floorColorButton.setBackground(floorColor);
    generateButton.addActionListener(buttonHandler);

    inputsContainerPanel.add(containerPanel, BorderLayout.LINE_START);

    app.add(inputsContainerPanel, BorderLayout.NORTH);
    app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    app.pack();
    // app.setLocationRelativeTo(null);
    app.setVisible(true);

    app.setFocusable(true);
    app.requestFocusInWindow();
    generate();

  }

  /**
   * 
   * https://stackoverflow.com/questions/38642034/how-do-i-provide-a-single-button-handler-object
   * map the app's button to their intended function
   */
  private class ButtonHandler implements ActionListener
  {
    public void actionPerformed(ActionEvent event)
    {

      // wall color button
      if (event.getSource() == wallColorButton)
      {
        // https://stackoverflow.com/questions/26565166/how-to-display-a-color-selector-when-clicking-a-button
        wallColor = JColorChooser.showDialog(MainWindow.this, "Pick the Wall Color", wallColor);
        wallColorButton.setBackground(wallColor);
      }
      // floor color button
      else if (event.getSource() == floorColorButton)
      {
        floorColor = JColorChooser.showDialog(MainWindow.this, "Pick the Floor Color", floorColor);
        floorColorButton.setBackground(floorColor);
      }
      // generate map button; if button is pressed generate a new map panel
      else if (event.getSource() == generateButton)
      {
        mapPanelWidth = Integer.parseInt(widthTextField.getText());
        mapPanelHeight = Integer.parseInt(heightTextField.getText());
        generate();
      }
    }
  }

  public void generate()
  {
    try
    {
      this.mapPanel = new MapPanel(mapPanelWidth, mapPanelHeight, floorColor, wallColor);
      // set app size to conform to the map panel size
      app.setSize(new Dimension(((mapPanelWidth < 15 ? 20 : mapPanelWidth) + 7) * 20, (mapPanelHeight + 7) * 20));
    }
    catch (Error e)
    {
      System.err.println(e);
      // if mapWidth or mapHeight is less than 15 popup message box
      JOptionPane.showMessageDialog(app, "Map width and map height must be larger than 15!");
    }
    // remove everything to repaint the map panel
    // TODO: remove only map panel to repaint map panel
    app.getContentPane().removeAll();
    app.add(inputsContainerPanel, BorderLayout.NORTH);

    app.removeKeyListener(keyEventListener);
    // add key listener to the app J frame
    // TODO: add key listener to map panel
    keyEventListener = new KeyEventListener(this);
    app.addKeyListener(keyEventListener);

    app.add(mapPanel);
    app.revalidate();
  }

  public MapPanel getMapPanel()
  {
    return mapPanel;
  }
}