package src.dungeonmap;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainWindow extends JFrame
{
  private static final long serialVersionUID = 1L; // remove warning

  private int mapPanelWidth = 40; // set default size for mapPanel
  private int mapPanelHeight = 40; // set default size for mapPanel

  private PaintGeneratedMap mapPanel;
  // private JFrame mapFrame = new JFrame("Map");

  private static JFrame app = new JFrame("Dungeon Map Generator GUI"); // Main window
  private static JPanel inputsContainerPanel = new JPanel();
  private static JPanel mapInputsPanel = new JPanel();
  private static JPanel generatePanel = new JPanel();
  private static JPanel containerPanel = new JPanel();

  private JLabel heightJLabel = new JLabel("Map Height:");
  private JLabel widthJLabel = new JLabel("Map Width:");

  private JButton wallColorButton = new JButton("Wall Color");
  private JButton floorColorButton = new JButton("Floor Color");

  private JTextField widthTextfield = new JTextField(3);
  private JTextField heightTextfield = new JTextField(3);

  private ButtonHandler buttonHandler;
  private JButton generateButton = new JButton("Generate");

  private Color wallColor = Color.gray;
  private Color floorColor = Color.white;

  public MainWindow()
  {
    inputsContainerPanel.setLayout(new BorderLayout());

    // mapInputsPanel.setLayout(new GridLayout());
    mapInputsPanel.add(heightJLabel);
    mapInputsPanel.add(heightTextfield);

    mapInputsPanel.add(widthJLabel);
    mapInputsPanel.add(widthTextfield);
    mapInputsPanel.add(wallColorButton);
    mapInputsPanel.add(floorColorButton);

    // generatePanel.setLayout(new GridLayout());
    generateButton.setPreferredSize(new Dimension(420, 30));
    generatePanel.add(generateButton);

    containerPanel.setLayout(new BorderLayout());
    containerPanel.add(mapInputsPanel, BorderLayout.NORTH);
    containerPanel.add(generatePanel, BorderLayout.LINE_START);
    widthTextfield.setText("" + mapPanelWidth);
    heightTextfield.setText("" + mapPanelHeight);

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
    app.setLocationRelativeTo(null);
    app.setVisible(true);
  }

  // https://stackoverflow.com/questions/38642034/how-do-i-provide-a-single-button-handler-object
  private class ButtonHandler implements ActionListener
  {

    public void actionPerformed(ActionEvent event)
    {
      // https://stackoverflow.com/questions/26565166/how-to-display-a-color-selector-when-clicking-a-button
      if (event.getSource() == wallColorButton)
      {
        wallColor = JColorChooser.showDialog(MainWindow.this, "Pick the Wall Color", wallColor);
        wallColorButton.setBackground(wallColor);
      }
      else if (event.getSource() == floorColorButton)
      {
        floorColor = JColorChooser.showDialog(MainWindow.this, "Pick the Floor Color", floorColor);
        floorColorButton.setBackground(floorColor);
      }
      else if (event.getSource() == generateButton) // make a new window with the map
      {
        mapPanelWidth = Integer.parseInt(widthTextfield.getText());
        mapPanelHeight = Integer.parseInt(heightTextfield.getText());

        // if mapwidth or mapheight is less than 15 popup message box
        try
        {
          app.setSize(new Dimension((mapPanelWidth + 7) * 20, (mapPanelHeight + 8) * 20));
          mapPanel = new PaintGeneratedMap(mapPanelWidth, mapPanelHeight, floorColor,
                  wallColor);

          // mapPanel.setLayout(new BoxLayout(mapPanel, BoxLayout.Y_AXIS));
          // https://stackoverflow.com/questions/9347076/how-to-remove-all-components-from-a-jframe-in-java

          // mapPanel.revalidate();
          // mapPanel.setVisible(true);
          // app.add(inputsContainerPanel, BorderLayout.NORTH);
          app.add(mapPanel);
          app.revalidate();

        }
        catch (Error e)
        {
          System.err.println(e);
          JOptionPane.showMessageDialog(app, "Map width and map height must be larger than 15!");
        }
      }
    }
  }
}
// TODO: change to gridbaglayout?
// https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html
