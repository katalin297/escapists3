package main;

import entity.Player;
import entity.Scene;
import math.Vector2;
import math.Vector4;
import physics.PhysicsSystem;
import renderer.Renderer;
import renderer.UserInterface;

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
    
	   // Initalize the window and the renderer
	   ConfigureWindow();
	   Renderer.Initialize();
	   
	   SetUpScene(); 
   }
   
   void ConfigureWindow() {
	   this.setPreferredSize(new Dimension(Application.SCREEN_WIDTH, Application.SCREEN_HEIGHT));
	   this.setBackground(Color.black);
	   this.setDoubleBuffered(true);
	   this.setFocusable(true);
	   this.addKeyListener(Input.GetInternalKeyHandler());
   }
   
   void SetUpScene() {
	   // Set up the scene
	   Player player = new Player();
	   GameScene.AddEntity(player);
	   
	   PhysicsSystem.Initialize();
	   GameScene.OnInitalize();
	   TileManager.Initialize();
	   UserInterface.Initalize();
   }
   
   public void Update(double timeStep) {
	   // Begin preparing the scene
	   Renderer.BeginScene();
	   
	   // Before updating the actual scene, we have to firstly
	   // to prepare the physics scene in order for the entities
	   // to be able to know if they collided
	   GameScene.OnPhysicsUpdate(timeStep);
	   
	   // Update the game scene with all the entities
	   GameScene.OnUpdate(timeStep);
	   
	   // End recording the scene
	   Renderer.EndScene();	    
	   
	   UserInterface.DrawRectangle(
			   new Vector2(40, 620), new Vector2(600, 240), new Vector4(0, 0, 0, 200), 32,
			   5, new Vector4(255, 255, 255, 255), 22);
	   
	   UserInterface.DrawText(new Vector2(70, 670), 32, "Catalin gay");
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
		   
		   // If delta is more than 1, it means that at this time we should render a frame
		   if (delta >= 1.0D) {
			   // Update the scene firstly (player, NPCs, etc.)
			   this.Update(delta);
            
			   // After that, draw the frame
			   this.repaint();
            
			   --delta;
		   }

		   if (timer >= 1000000000L) {
			   timer = 0L;
		   }
	   }

   }

   public void startGameThread() {
	   GameThread = new Thread(this);
	   GameThread.start();
   }


   public void paintComponent(Graphics graphicsAPI) {
      super.paintComponent(graphicsAPI);
      
      // (Firstly draw the map, and after draw the characters over it)
      // Draw the Map
      TileManager.OnDraw();

      // Draw the objects in the whole game scene
      GameScene.OnDraw();      
      
      // Render the submited objects into the screen
      Renderer.RenderFrame(this.GameScene.GetPrimaryCamera(), graphicsAPI);
      
      // Finally, draw the user interface (after everything was rendered)
      UserInterface.OnDraw((Graphics2D)graphicsAPI);
      
      // Release the graphics handler from memory
      graphicsAPI.dispose();
   }

}