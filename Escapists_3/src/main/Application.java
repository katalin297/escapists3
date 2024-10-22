package main;

import java.awt.Component;
import javax.swing.JFrame;

public class Application {
	
   public static final int SCREEN_WIDTH = 1600;
   public static final int SCREEN_HEIGHT = 900;
	
   public static void main(String[] args) {
	   
      JFrame window = new JFrame();
      window.setDefaultCloseOperation(3);
      window.setResizable(false);
      window.setTitle("2D Adventure");
      
      GamePanel gamePanel = new GamePanel();
      window.add(gamePanel);
      window.pack();
      window.setLocationRelativeTo((Component)null);
      window.setVisible(true);
      
      gamePanel.startGameThread();
   }
}