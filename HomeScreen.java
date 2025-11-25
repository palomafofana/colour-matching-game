
import javax.swing.*;

import java.awt.*;
import java.awt.event.*; 

public class HomeScreen extends JFrame {

    public static void main(String[] args) {
        new HomeScreen();
    } 

    public HomeScreen() {
        setTitle("Hue Hopper 5000");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(220, 225, 255));
        panel.setOpaque(true);
        
        JLabel title = new JLabel("Hue Hopper 5000", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        
        // Create level button
        JButton createLevelBtn = new JButton("Create Your Own Level!!");
        createLevelBtn.setOpaque(true);
        createLevelBtn.setContentAreaFilled(true);
        createLevelBtn.setBorderPainted(false);
        createLevelBtn.setBackground(new Color(179, 155, 255));
        createLevelBtn.setFocusPainted(false); 

        // Play preset levels button
        JButton playLevelsBtn = new JButton("Play Predefined Levels");
        playLevelsBtn.setOpaque(true);
        playLevelsBtn.setContentAreaFilled(true);
        playLevelsBtn.setBorderPainted(false);
        playLevelsBtn.setBackground(new Color(142, 205, 253));
        panel.add(title);
        panel.add(createLevelBtn);
        panel.add(playLevelsBtn);
        
        add(panel);

        // Button actions
        createLevelBtn.addActionListener(e -> {
            dispose();
            new LevelCreator();
        });

        playLevelsBtn.addActionListener(e -> {
            dispose();
            new LevelSelection();
        });

        createLevelBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                createLevelBtn.setBackground(new Color(154, 128, 255));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                createLevelBtn.setBackground(new Color(179, 155, 255));
            }
        });

         playLevelsBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                playLevelsBtn.setBackground(new Color(115, 195, 253));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                playLevelsBtn.setBackground(new Color(142, 205, 253));
            }
        });
        
        setVisible(true);
    }
    
    
}

