package main;

import entity.Player;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import tile.TileManager;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable {

	// SCREEN SETTINGS
   final int originalTileSize = 16;
   final int scale = 3;
   
   public final int tileSize = originalTileSize * 3;
   public final int maxScreenCol = 16;
   public final int maxScreenRow = 12;
   public final int screenWidth = tileSize * maxScreenCol;
   public final int screenHeight = tileSize * maxScreenRow;
   
   public final int maxWorldCol = 50;
   public final int maxWorldRow = 50;
   public final int worldWidth = tileSize * maxWorldCol;
   public final int worldHeight = tileSize * maxWorldRow;
   
   // FPS
   int FPS = 60;
   
   TileManager tileM = new TileManager(this);
   KeyHandler keyH = new KeyHandler();
   Thread gameThread;
   public Player player = new Player(this,keyH);

   public GamePanel() {
    
      this.setPreferredSize(new Dimension(screenWidth, screenHeight));
      this.setBackground(Color.black);
      this.setDoubleBuffered(true);
      this.addKeyListener(keyH);
      this.setFocusable(true);
   }

   public void startGameThread() {
      gameThread = new Thread(this);
      gameThread.start();
   }

   public void run() {
      double drawInterval = (double)(1000000000 / this.FPS);
      double delta = 0.0D;
      long lastTime = System.nanoTime();
      long timer = 0L;
      while(this.gameThread != null) {
         long currentTime = System.nanoTime();
         delta += (double)(currentTime - lastTime) / drawInterval;
         timer += currentTime - lastTime;
         lastTime = currentTime;
         if (delta >= 1.0D) {
            this.update();
            this.repaint();
            --delta;
         }

         if (timer >= 1000000000L) {
            timer = 0L;
         }
      }

   }

   public void update() {
	   
      player.update();
   }

   public void paintComponent(Graphics g) {
	   
      super.paintComponent(g);
      
      Graphics2D g2 = (Graphics2D)g;
      
      tileM.draw(g2);


      player.draw(g2);
      
      g2.dispose();
   }

}