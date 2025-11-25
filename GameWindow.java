import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameWindow extends JFrame {

    private TileButton selectedButton = null;
    private TileButton[][] answerPlan;
    private JPanel gridPanel;
    private int size = 7; // 6x6 grid, for example

    // For shuffling
    private List<TileButton> tileList;   

    public int[] TLcolour = {252, 132, 3}; // Top Left
    public int[] TRcolour = {3, 194, 252}; // Top Right
    public int[] BRcolour = {148, 3, 252}; // Bottom Right
    public int[] BLcolour = {252, 3, 107}; // Bottom Left

    public GameWindow(int[][] colours, int size) {

        this.size = size;
        this.TLcolour = colours[0];
        this.TRcolour = colours[1];
        this.BLcolour = colours[2];
        this.BRcolour = colours[3];

        setTitle("I Love Hue - Java");
        setSize(600,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        answerPlan = new TileButton[size][size];
        gridPanel = new JPanel(new GridLayout(size, size));
        tileList = new ArrayList<>();

        // Call answer key method
        answerKey();

        Collections.shuffle(tileList); //shuffles list

        // adds shuffled tiles to JPanel, accounting for locked tiles
        int index = 0;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (answerPlan[row][col].getTile().isLocked()) {
                    answerPlan[row][col].setText("●");
                    gridPanel.add(answerPlan[row][col]);
                }
                else {
                    gridPanel.add(tileList.get(index));
                    tileList.get(index).setRow(row);
                    tileList.get(index).setCol(col);
                    index++;
                }                    
            }
        }
        add(gridPanel);
        setVisible(true);
    }
    
    public static void delay(int ms) {
        try {
            Thread.sleep(ms);
        }
        catch(InterruptedException e) {
        }
    }

    public Color colourChoser(int row, int col) {
        float rowIncrementor = (float) row/(size - 1); // index of row and col start at 0
        float colIncrementor = (float) col/(size - 1);

        // Find colour on leftmost cell of row
        int leftR = (int) ((1 - rowIncrementor) * TLcolour[0] + rowIncrementor * BLcolour[0]);
        int leftG = (int) ((1 - rowIncrementor) * TLcolour[1] + rowIncrementor * BLcolour[1]);
        int leftB = (int) ((1 - rowIncrementor) * TLcolour[2] + rowIncrementor * BLcolour[2]);

        // Find colour on rightmost cell of row
        int rightR = (int) ((1 - rowIncrementor) * TRcolour[0] + rowIncrementor * BRcolour[0]);
        int rightG = (int) ((1 - rowIncrementor) * TRcolour[1] + rowIncrementor * BRcolour[1]);
        int rightB = (int) ((1 - rowIncrementor) * TRcolour[2] + rowIncrementor * BRcolour[2]);

        // Find colour at specific cell
        int finalR = (int) ((1 - colIncrementor) * leftR + colIncrementor * rightR);
        int finalG = (int) ((1 - colIncrementor) * leftG + colIncrementor * rightG);
        int finalB = (int) ((1 - colIncrementor) * leftB + colIncrementor * rightB);

        return new Color(finalR, finalG, finalB);
    }

    public void answerKey() {
        // Establishes the answer key for the puzzle
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {

                boolean isLocked = (row == 0 && col == 0) ||
                                    (row == 0 && col == size - 1) ||
                                    (row == size - 1 && col == size - 1) ||
                                    (row == size - 1 && col == 0);

                
                Tile tile = new Tile(colourChoser(row, col), isLocked);   
        
                TileButton button = new TileButton(tile, row, col, row, col); // makes the tile into a button
                answerPlan[row][col] = button; // adds the button to the answer plan
                if (!button.getTile().isLocked()) { // adds the unlocked buttons to the list so they can be shuffled
                    tileList.add(button); 
                }
                button.addActionListener(e -> handleTileClick(e));
            }
        }
    }

    public boolean CheckIfComplete() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (!(answerPlan[row][col].getCol() == answerPlan[row][col].getRightCol()) ||
                    !(answerPlan[row][col].getRow() == answerPlan[row][col].getRightRow())) {
                    return false;
                }   
            }
        }
        return true;
    }

    // Clicked button happenings
    public void handleTileClick(ActionEvent e) {
        TileButton clickedButton = (TileButton) e.getSource();
        
        // switching
        if (selectedButton == null) {
            // Check if it's locked
            if (!clickedButton.getTile().isLocked()) {
                clickedButton.setBorderPainted(true);
                clickedButton.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
                delay(30);
                clickedButton.setBorderPainted(false);
            }
            selectedButton = clickedButton;
            clickedButton.setBorderPainted(true);
            clickedButton.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3)); // highlight
        } 
        else {
            if (!selectedButton.getTile().isLocked() & 
                !clickedButton.getTile().isLocked()) {

                // Swap the tiles visually and logically
                Tile placeHolderTile = selectedButton.getTile();
                int placeHolderRightRow = selectedButton.getRightRow();
                int placeHolderRightCol = selectedButton.getRightCol();
                
                // Not changing place, changing color and correct spot
                selectedButton.setTile(clickedButton.getTile());
                selectedButton.setRightRow(clickedButton.getRightRow());
                selectedButton.setRightCol(clickedButton.getRightCol());

                clickedButton.setTile(placeHolderTile);
                clickedButton.setRightRow(placeHolderRightRow);
                clickedButton.setRightCol(placeHolderRightCol);
    
                // Update button colors
                selectedButton.setBackground(selectedButton.getTile().getColor());
                clickedButton.setBackground(clickedButton.getTile().getColor());
                
                // Check if you've won
                if (CheckIfComplete()) {
                    JOptionPane.showMessageDialog(null, "YOU WIN!!!!", "Winna Winna", JOptionPane.PLAIN_MESSAGE);
                    //selectedButton.setBackground(new Color(109, 255, 82));
                    //selectedButton.setText("YOU WIN!!!");
                }
                // Reset highlight
                selectedButton.setBorder(null);
                selectedButton = null;

                
        }
            else 
                selectedButton.setBorder(null);
                selectedButton = null;
        }
    }
}
