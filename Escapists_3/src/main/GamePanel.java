package main;

import entity.BasketBallField;
import entity.Player;
import entity.PoliceNPC;
import entity.Scene;
import entity.Tree;
import math.Vector2;
import math.Vector4;
import physics.PhysicsSystem;
import renderer.DialogueSystem;
import renderer.Renderer;
import renderer.UserInterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

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
	   
	   UserInterface.Initalize();
	   DialogueSystem.Initalize();
	   PhysicsSystem.Initialize();	   

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
	   BasketBallField basketBallField = new BasketBallField();
	   GameScene.AddEntity(basketBallField);
	   
	   Player player = new Player();
	   GameScene.AddEntity(player);
	   
	   PoliceNPC policeNPC = new PoliceNPC();
	   GameScene.AddEntity(policeNPC);
	   
	   GenerateTrees();
	   
	   
	   
	   GameScene.OnInitalize();
	   TileManager.Initialize();
	   
	   DialogueSystem.DrawDialogue("Player", "Welcome to the game!");
	   DialogueSystem.DrawDialogue("Player", "Denis cel mai pizdos");
	   DialogueSystem.DrawDialogue("Player", "Catalin gay");
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
	   
	   DialogueSystem.OnUpdate(timeStep);
	   
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
		   if (delta >= 1.0) {
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
      
      // Before rendering the UI, we should submit the draw calls to the UserInterface System
      DialogueSystem.OnDraw();
      
      // Finally, draw the user interface (after everything was rendered)
      UserInterface.OnDraw((Graphics2D)graphicsAPI);
      
      // Release the graphics handler from memory
      graphicsAPI.dispose();
   }
   
   public void GenerateTrees() {
	   Random random = new Random();
	   for (int i = 0; i < 4; i++) {   
	       int randomNumber = random.nextInt(20) - 10;
		   Tree tree = new Tree(
				   new Vector2(16 * Renderer.TILE_SIZE + i * 8 * Renderer.TILE_SIZE, 52 * Renderer.TILE_SIZE + 10 - randomNumber)
		   );
		   GameScene.AddEntity(tree);		   
	   }
	   
	   for (int i = 0; i < 2; i++) {   
	       int randomNumber = random.nextInt(20) - 10;
		   Tree tree = new Tree(
				   new Vector2(50 * Renderer.TILE_SIZE + i * 5 * Renderer.TILE_SIZE, 52 * Renderer.TILE_SIZE + 10 - randomNumber)
		   );
		   GameScene.AddEntity(tree);		   
	   }
	   for (int i = 0; i < 2; i++) {   
	       int randomNumber = random.nextInt(20) - 10;
		   Tree tree = new Tree(
				   new Vector2(75 * Renderer.TILE_SIZE + i * 5 * Renderer.TILE_SIZE, 52 * Renderer.TILE_SIZE + 10 - randomNumber)
		   );
		   GameScene.AddEntity(tree);		   
	   }
	   
	   for (int i = 0; i < 2; i++) {   
	       int randomNumber = random.nextInt(20) - 10;
		   Tree tree = new Tree(
				   new Vector2(50 * Renderer.TILE_SIZE + i * 6 * Renderer.TILE_SIZE, 61 * Renderer.TILE_SIZE + 10 - randomNumber)
		   );
		   GameScene.AddEntity(tree);		   
	   }
	   for (int i = 0; i < 1; i++) {   
	       int randomNumber = random.nextInt(20) - 10;
		   Tree tree = new Tree(
				   new Vector2(78 * Renderer.TILE_SIZE + i * 6 * Renderer.TILE_SIZE, 61 * Renderer.TILE_SIZE + 10 - randomNumber)
		   );
		   GameScene.AddEntity(tree);		   
	   }
	   
	   
	   for (int i = 0; i < 2; i++) {
	       int randomNumber = random.nextInt(20) - 10;
		   Tree tree = new Tree(
				   new Vector2(33 * Renderer.TILE_SIZE + i * 7 * Renderer.TILE_SIZE, 59 * Renderer.TILE_SIZE + 10 - randomNumber)
		   );
		   GameScene.AddEntity(tree);		   
	   }
	   
	   for (int i = 0; i < 4; i++) {
		   
	       int randomNumber = random.nextInt(20) - 10;
		   Tree tree = new Tree(
				   new Vector2(20 * Renderer.TILE_SIZE + i * 7 * Renderer.TILE_SIZE, 66 * Renderer.TILE_SIZE + 10 - randomNumber)
		   );
		   GameScene.AddEntity(tree);		   
	   }
	   for (int i = 0; i < 4; i++) {
		   
	       int randomNumber = random.nextInt(20) - 10;
		   Tree tree = new Tree(
				   new Vector2(18 * Renderer.TILE_SIZE + i * 9 * Renderer.TILE_SIZE, 76 * Renderer.TILE_SIZE + 10 - randomNumber)
		   );
		   GameScene.AddEntity(tree);		   
	   }
   }

}