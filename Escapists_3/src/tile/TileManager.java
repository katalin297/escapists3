package tile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import assets.Asset;
import assets.Texture;
import main.Application;
import main.GamePanel;
import main.Renderer;
import math.BoundingBox;
import math.Vector2;
import physics.PhysicsSystem;

public class TileManager {

   public HashMap<String, Texture> TileTextures;
   
   public String[][] MapTile;
   public boolean StencilMapTile;

   public TileManager() { }
   
   public void Initialize() {
	   // Initialize the memory first
	   this.TileTextures = new HashMap<String, Texture>();	
	   this.MapTile = new String[Renderer.MAX_WORLD_SIZE.X][Renderer.MAX_WORLD_SIZE.Y];
	      
	   // Set up the textures and load the map
	   GetTileTextures();
	   LoadMap("/maps/physics01.txt", "/maps/physics02.txt");
   }

   public void GetTileTextures() {
      try {
    	 this.TileTextures.put("0", Asset.Load("/tiles/grass.png").<Texture>As());
    	 this.TileTextures.put("1", Asset.Load("/tiles/wall.png").<Texture>As());
    	 this.TileTextures.put("2", Asset.Load("/tiles/water.png").<Texture>As());
    	 this.TileTextures.put("3", Asset.Load("/tiles/earth.png").<Texture>As());
    	 this.TileTextures.put("4", Asset.Load("/tiles/tree.png").<Texture>As());
    	 this.TileTextures.put("5", Asset.Load("/tiles/sand.png").<Texture>As());
    	 
         
      } catch (IOException e) {
         e.printStackTrace();
      }

   }

   public void LoadMap(String filePathMap, String filePathPhysicsMap) {
	   
      try {
         InputStream mapInput = getClass().getResourceAsStream(filePathMap);
         InputStream physicsMapInput = getClass().getResourceAsStream(filePathPhysicsMap);
         
         BufferedReader mapInputReader = new BufferedReader(new InputStreamReader(mapInput));
         BufferedReader physicsMapInputReader = new BufferedReader(new InputStreamReader(physicsMapInput));
         
         int col = 0;
         int row = 0;

         while(col < Renderer.MAX_WORLD_SIZE.X && row < Renderer.MAX_WORLD_SIZE.Y) {
        	 
        	 String mapInputLine = mapInputReader.readLine();
        	 String physicsMapInputLine = physicsMapInputReader.readLine();
        	 
        	 //System.out.println(mapInputLine);
        	 
        	 
        	 //System.out.print("New:\n");

        	 while (col < Renderer.MAX_WORLD_SIZE.X) {
        		 
        		 String numbers[] = mapInputLine.split(" ");
        		 String num = numbers[col];
        		 
        		 //String numbersPhysics[] = physicsMapInputLine.split(" ");
        		 //int numPhysics = Integer.parseInt(numbersPhysics[col]);

        		 
        		 
        		 
        		 this.MapTile[row][col] = num;
        		 
        		 
        		// System.out.print(num + " ");
        		 
        		 
        		 
//        		 if(numPhysics == 1) {
//        			 BoundingBox finalBB = new BoundingBox(
//            				 new Vector2(col * Renderer.TILE_SIZE - Application.SCREEN_WIDTH / 2 + Renderer.TILE_SIZE / 2, row * Renderer.TILE_SIZE - Application.SCREEN_HEIGHT / 2 + Renderer.TILE_SIZE / 2),
//            				 new Vector2(Renderer.TILE_SIZE)
//            				 
//            		 );
//            		 
////            		 System.out.print(finalBB.UpLeft.toString());
////            		 System.out.print(finalBB.UpRight.toString());
////            		 System.out.print(finalBB.DownLeft.toString());
////            		 System.out.println(finalBB.DownRight.toString());
//            		 
//            		 PhysicsSystem.AddStaticObject(
//            				 new Vector2(col * Renderer.TILE_SIZE - Application.SCREEN_WIDTH / 2 + Renderer.TILE_SIZE / 2, row * Renderer.TILE_SIZE - Application.SCREEN_HEIGHT / 2 + Renderer.TILE_SIZE / 2),
//            				 new Vector2(Renderer.TILE_SIZE)
//            				 
//            				 );
//        		 }
//        		
        		 
        		 
        		 
        		 
        		 
        		 
        		 col++;
        		 
        		 
        		 
        	 }
        	 if(col == Renderer.MAX_WORLD_SIZE.X) {
        		 col = 0;
        		 row++;
        		 //System.out.println();
        	 }    	 
         }
         
         mapInputReader.close();
         physicsMapInputReader.close();
         
      } catch (Exception e) {
    	  
    	  System.out.println(e.getMessage());
    	  
      }

   }

   public void OnDraw() { 
	   
	   int worldCol = 0;
	   int worldRow = 0;
	   
	   while(worldCol < Renderer.MAX_WORLD_SIZE.X && worldRow < Renderer.MAX_WORLD_SIZE.Y) {
		   
		   String tileType = this.MapTile[worldRow][worldCol];
		   
		   Vector2 worldPosTile = new Vector2(worldCol * Renderer.TILE_SIZE, worldRow * Renderer.TILE_SIZE);		   
		   Renderer.Submit(worldPosTile, new Vector2(Renderer.TILE_SIZE), TileTextures.get(tileType));
		   
		   
		   worldCol++;
		   
		   if(worldCol == Renderer.MAX_WORLD_SIZE.X) {
			   worldCol = 0;
			   worldRow++;
		   }	   
	   }   
   }   
}















