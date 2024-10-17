package main;

import entity.Player;
import entity.Scene;
import math.Vector2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import tile.TileManager;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable {

   Thread GameThread;

   TileManager TileManager = new TileManager();
   Scene GameScene = new Scene();

   public GamePanel() {
    
      this.setPreferredSize(new Dimension(Application.SCREEN_WIDTH, Application.SCREEN_HEIGHT));
      this.setBackground(Color.black);
      this.setDoubleBuffered(true);
      this.setFocusable(true);
      this.addKeyListener(Input.GetInternalKeyHandler());
      
      Renderer.Initialize();
      
      
      // Set up the scene
      Player player = new Player();
      GameScene.AddEntity(player);
      
      GameScene.OnInitalize();
   }

   public void startGameThread() {
	  GameThread = new Thread(this);
      GameThread.start();
   }

   public void run() {
      double drawInterval = (double)(1000000000 / Renderer.FRAMES_PER_SECOND);
      double delta = 0.0D;
      long lastTime = System.nanoTime();
      long timer = 0L;
      while(this.GameThread != null) {
         long currentTime = System.nanoTime();
         delta += (double)(currentTime - lastTime) / drawInterval;
         timer += currentTime - lastTime;
         lastTime = currentTime;
         if (delta >= 1.0D) {
            this.Update(delta);
            this.repaint();
            --delta;
         }

         if (timer >= 1000000000L) {
            timer = 0L;
         }
      }

   }

   public void Update(double timeStep) {
	   
	   Renderer.BeginScene();
	   GameScene.OnUpdate(timeStep);
	   Renderer.EndScene();

   }

   public void paintComponent(Graphics graphicsAPI) {
	   
      super.paintComponent(graphicsAPI);
      
      // Draw the whole objects in the game scene
      TileManager.OnDraw();
      GameScene.OnDraw();
      
      
      
      
      Renderer.RenderFrame(this.GameScene.GetPrimaryCamera(), graphicsAPI);
      
      
      
      
//      Graphics2D g2 = (Graphics2D)g;
//      
//      tileM.draw(g2);
//
//
//      player.draw(g2);
//      
//      g2.dispose();
   }

}