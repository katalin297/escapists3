package main;

import entity.BasketBallField;
import entity.Chest;
import entity.CraftingTable;
import entity.Hole;
import entity.Player;
import entity.PoliceNPC;
import entity.Scene;
import entity.Stone;
import entity.Tree;
import math.Vector2;
import math.Vector4;
import physics.PhysicsSystem;
import renderer.DialogueSystem;
import renderer.Renderer;
import renderer.UserInterface;
import renderer.UserInterfaceOpening;

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

	   UserInterfaceOpening.OnInitialize(); 
	   
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
	   
	   Hole hole = new Hole();
	   GameScene.AddEntity(hole);
	   
	   Player player = new Player();
	   GameScene.AddEntity(player);
	   
	   PoliceNPC policeNPC = new PoliceNPC();
	   GameScene.AddEntity(policeNPC);
	   
	   CraftingTable craftingTable = new CraftingTable();
	   GameScene.AddEntity(craftingTable);
	   
	   Chest chest = new Chest();
	   GameScene.AddEntity(chest);
	   
	   
	   GenerateStones();
	   GenerateTrees();
	   
	   
	   
	   GameScene.OnInitalize();
	   TileManager.Initialize();
	   
	   DialogueSystem.DrawDialogue("", "");
	   DialogueSystem.DrawDialogue("Player", "Welcome to the game!");
	   DialogueSystem.DrawDialogue("Player", "Our main aim is to escape this prison!");
	   DialogueSystem.DrawDialogue("Player", "We have to craft a shovel to escape!");
	   DialogueSystem.DrawDialogue("Player", "Lets explore the world firstly.");
   }
   
   
   
   public void Update(double timeStep) {
	   
	   if(!UserInterfaceOpening.StartGame) {
		   UserInterfaceOpening.OnUpdate();
		   return;
	   }
	   
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
      
      if(!UserInterfaceOpening.StartGame || UserInterfaceOpening.FinishedGame) {
    	  UserInterfaceOpening.OnDraw((Graphics2D)graphicsAPI);   
      } else {
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
          
      }
      
      
      
      // Release the graphics handler from memory
      graphicsAPI.dispose();
   }
   
   public void GenerateStones() {
	   Stone stone1 = new Stone(new Vector2(42 * Renderer.TILE_SIZE, 16 * Renderer.TILE_SIZE), Renderer.TILE_SIZE * 4); 
	   GameScene.AddEntity(stone1);
	   
	   Stone stone2 = new Stone(new Vector2(50 * Renderer.TILE_SIZE+ Renderer.TILE_SIZE / 2, 15 * Renderer.TILE_SIZE + Renderer.TILE_SIZE / 2), Renderer.TILE_SIZE * 3); 
	   GameScene.AddEntity(stone2);
	   
	   Stone stone3 = new Stone(new Vector2(40 * Renderer.TILE_SIZE, 26 * Renderer.TILE_SIZE), Renderer.TILE_SIZE * 3 + Renderer.TILE_SIZE / 2); 
	   GameScene.AddEntity(stone3);
	   
	   Stone stone4 = new Stone(new Vector2(48 * Renderer.TILE_SIZE + Renderer.TILE_SIZE / 2, 27 * Renderer.TILE_SIZE + Renderer.TILE_SIZE / 2), Renderer.TILE_SIZE * 4); 
	   GameScene.AddEntity(stone4);
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