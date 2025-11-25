import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class LevelSelection extends JFrame {

    static class Level {
        int size;
        int[][] colours; // 4 corners: [corner][RGB]
        
        Level(int s, int[][] c) { 
            size = s; 
            colours = c; 
        }
    }

    private final JPanel gridPanel = new JPanel();
    
    // Dictionary of 10 base levels
    private final Map<Integer, Level> levels = new HashMap<>();
    
    // 50-color gradient cache
    private final Color[] colorGradient = new Color[50];

    public LevelSelection() {
        setTitle("Level Selection");
        setSize(640, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        getContentPane().setBackground(new Color(220, 225, 255));
        setLayout(new BorderLayout(12, 12));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JButton backBtn = new JButton("Back");
        backBtn.setBackground(new Color(200, 200, 255));
        backBtn.setFocusPainted(false);
        backBtn.setBorderPainted(false);
        backBtn.setOpaque(true);
        backBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backBtn.setBorder(new EmptyBorder(6,12,6,12));
        backBtn.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> new HomeScreen());
        });
        header.add(backBtn, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        // Initialize the 10 base levels
        initializeLevels();
        
        // Generate color gradient
        generateColorGradient();

        // Grid: 3 columns, many rows
        gridPanel.setLayout(new GridLayout(0, 3, 16, 16));
        gridPanel.setOpaque(false);
        JPanel container = new JPanel(new BorderLayout());
        container.setBorder(new EmptyBorder(12,12,12,12));
        container.setOpaque(false);
        container.add(gridPanel, BorderLayout.NORTH);

        JScrollPane scroll = new JScrollPane(container);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(new Color(220, 225, 255));
        add(scroll, BorderLayout.CENTER);

        // Generate levels
        generateLevels(50);

        setVisible(true);
    }
    
    // Levels!!
    private void initializeLevels() {

        levels.put(1, new Level(5, new int[][]{
            {255, 220, 240}, {200, 140, 255}, {255, 200, 80}, {255, 140, 160}
        }));
        
        levels.put(2, new Level(6, new int[][]{
            {255, 250, 240}, {180, 255, 220}, {255, 220, 100}, {220, 255, 140}
        }));
        
        levels.put(3, new Level(5, new int[][]{
            {240, 240, 255}, {160, 220, 255}, {200, 180, 255}, {140, 240, 240}
        }));
        
        levels.put(4, new Level(6, new int[][]{
            {255, 250, 245}, {255, 180, 160}, {255, 200, 140}, {255, 140, 120}
        }));

        levels.put(5, new Level(8, new int[][]{
            {255, 160, 200}, {180, 255, 220}, {200, 220, 255}, {255, 250, 220}
        }));
        
        levels.put(6, new Level(7, new int[][]{
            {240, 255, 245}, {140, 240, 220}, {200, 255, 220}, {120, 255, 240}
        }));
        
        levels.put(7, new Level(8, new int[][]{
            {255, 245, 220}, {255, 200, 120}, {255, 220, 140}, {255, 180, 100}
        }));
        
        levels.put(8, new Level(5, new int[][]{
            {255, 235, 245}, {255, 160, 200}, {255, 140, 180}, {255, 100, 160}
        }));
        
        levels.put(9, new Level(6, new int[][]{
            {240, 250, 255}, {180, 220, 255}, {220, 200, 255}, {200, 180, 255}
        }));
        
        levels.put(10, new Level(7, new int[][]{
            {255, 255, 240}, {255, 240, 140}, {255, 200, 100}, {255, 220, 120}
        }));
        
        levels.put(11, new Level(8, new int[][]{
            {245, 255, 250}, {180, 255, 220}, {140, 255, 180}, {160, 255, 160}
        }));
        
        levels.put(12, new Level(5, new int[][]{
            {255, 240, 245}, {255, 180, 160}, {255, 120, 140}, {255, 140, 120}
        }));
        
        levels.put(13, new Level(6, new int[][]{
            {230, 245, 255}, {180, 220, 255}, {140, 180, 255}, {120, 160, 255}
        }));
        
        levels.put(14, new Level(7, new int[][]{
            {255, 250, 235}, {255, 240, 180}, {255, 200, 100}, {255, 180, 80}
        }));
        
        levels.put(15, new Level(5, new int[][]{
            {240, 230, 255}, {220, 180, 255}, {180, 120, 255}, {160, 100, 255}
        }));
        
        levels.put(16, new Level(6, new int[][]{
            {220, 240, 255}, {160, 240, 240}, {100, 220, 220}, {120, 200, 200}
        }));
        
        levels.put(17, new Level(8, new int[][]{
            {255, 230, 200}, {255, 200, 160}, {255, 160, 100}, {255, 140, 80}
        }));
        
        levels.put(18, new Level(5, new int[][]{
            {220, 255, 240}, {200, 255, 180}, {180, 255, 120}, {200, 255, 100}
        }));
        
        levels.put(19, new Level(6, new int[][]{
            {255, 220, 230}, {255, 180, 210}, {255, 120, 200}, {255, 100, 180}
        }));
        
        levels.put(20, new Level(7, new int[][]{
            {200, 200, 255}, {160, 160, 255}, {120, 100, 255}, {100, 120, 255}
        }));
    }
    
    // Generate level display gradient
    private void generateColorGradient() {
        for (int i = 0; i < 50; i++) {
            float hue = (float)(i%30/ 30.0);
            colorGradient[i] = Color.getHSBColor(hue, 0.7f, 0.95f);
        }
    }

    private void generateLevels(int count) {
        gridPanel.removeAll();
        for (int i = 0; i < count; i++) {
            int levelNum = i + 1;
            
            int baseLevel = ((i % 20) + 1);
            Level level = levels.get(baseLevel);
            
            Color color = colorGradient[i % 50];
            
            gridPanel.add(createLevelTile(levelNum, level.size, level.colours, color));
        }
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private JPanel createLevelTile(int levelNum, int size, int[][] colours, Color borderColor) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setOpaque(false);

        // Color square 
        JPanel square = new JPanel();
        square.setBackground(borderColor);
        square.setPreferredSize(new Dimension(140, 140));
        square.setBorder(BorderFactory.createLineBorder(new Color(200,200,200), 2));
        square.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        square.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                new GameWindow(colours, size);
            }
        });

        // Center the square horizontally
        JPanel squareWrap = new JPanel(new GridBagLayout());
        squareWrap.setOpaque(false);
        squareWrap.add(square);

        card.add(squareWrap, BorderLayout.CENTER);

        // Label beneath: Level N — Size×Size
        JLabel lbl = new JLabel(String.format("Level %d — %dx%d", levelNum, size, size), SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setBorder(new EmptyBorder(8,0,0,0));
        card.add(lbl, BorderLayout.SOUTH);

        return card;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LevelSelection::new);
    }
}