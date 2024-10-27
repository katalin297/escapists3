package tile;

import java.awt.FontFormatException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import assets.Asset;
import assets.Texture;
import main.Application;
import main.GamePanel;
import math.BoundingBox;
import math.Vector2;
import physics.PhysicsSystem;
import renderer.Renderer;

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
	   
	   
	  /* 
		s - sand
		w - water
		g - grass
		
		t - tree
		b - bed
		d - doors (din jos in sus textura)
		
		f - prison floor
		
		= - horizontal door
		| - vertical doOr
		
		[ - bed part 1
		] - bed part 1
		
		
		
		// - for towers
		0 - vertical   |
		1 - orizontal  ==
		
		2 
		 |==
		 |
		
		3 
		 ==|
		   |
		
		4
		 |
		 |==
		
		5 
		   |
		 ==|
		 
		 
		 // - for prison itself
		 6 - vertical   |
		 7 - orizontal  ==
		 
		 8 
		  |==
		  |
		
		 9 
		  ==|
		    |
		
		 +
		  |
		  |==
		
		 -  
		    |
		  ==|
		 
		

	  */
	   
	   
	   try {
		   this.TileTextures.put("g", Asset.Load("/tiles/grass.png").<Texture>As());
		   this.TileTextures.put("w", Asset.Load("/tiles/water.png").<Texture>As());
		   this.TileTextures.put("s", Asset.Load("/tiles/sand.png").<Texture>As());
		   
		   
		   
		   // Tower
		   this.TileTextures.put("0", Asset.Load("/tiles/wall_tower/wall_tower_0.png").<Texture>As()); // change
		   this.TileTextures.put("1", Asset.Load("/tiles/wall_tower/wall_tower_1.png").<Texture>As()); // change
		   this.TileTextures.put("2", Asset.Load("/tiles/wall_tower/wall_tower_2.png").<Texture>As()); // change
		   this.TileTextures.put("3", Asset.Load("/tiles/wall_tower/wall_tower_3.png").<Texture>As()); // change
		   this.TileTextures.put("4", Asset.Load("/tiles/wall_tower/wall_tower_4.png").<Texture>As()); // change
		   this.TileTextures.put("5", Asset.Load("/tiles/wall_tower/wall_tower_5.png").<Texture>As()); // change
		   
		   // Prison
		   this.TileTextures.put("6", Asset.Load("/tiles/wall_prison/wall_prison_0.png").<Texture>As()); // change
		   this.TileTextures.put("7", Asset.Load("/tiles/wall_prison/wall_prison_1.png").<Texture>As()); // change
		   this.TileTextures.put("8", Asset.Load("/tiles/wall_prison/wall_prison_2.png").<Texture>As()); // change
		   this.TileTextures.put("9", Asset.Load("/tiles/wall_prison/wall_prison_3.png").<Texture>As()); // change
		   this.TileTextures.put("+", Asset.Load("/tiles/wall_prison/wall_prison_4.png").<Texture>As()); // change
		   this.TileTextures.put("-", Asset.Load("/tiles/wall_prison/wall_prison_5.png").<Texture>As()); // change
		   
		   // Doors
		   this.TileTextures.put("|", Asset.Load("/tiles/door_vertical.png").<Texture>As());
		   this.TileTextures.put("=", Asset.Load("/tiles/door_horizontal.png").<Texture>As());
		   
		   // Bed
		   this.TileTextures.put("[", Asset.Load("/tiles/bedTop.png").<Texture>As());
		   this.TileTextures.put("]", Asset.Load("/tiles/bedBottom.png").<Texture>As());
		   
		   
		   // Outside floor
		   this.TileTextures.put("_", Asset.Load("/tiles/floor_outside.png").<Texture>As());
		   
		   
		   this.TileTextures.put("f", Asset.Load("/tiles/prison_floor.png").<Texture>As());
		   this.TileTextures.put("m", Asset.Load("/tiles/stone.png").<Texture>As());
		   this.TileTextures.put("n", Asset.Load("/tiles/stone_dark.png").<Texture>As());
		   this.TileTextures.put("d", Asset.Load("/tiles/stone_diamond.png").<Texture>As());
		   this.TileTextures.put("a", Asset.Load("/tiles/stone_gold.png").<Texture>As());
		   
		   
		   
	   } catch (IOException e) {
		   e.printStackTrace();
	   } catch (FontFormatException e) {
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

        	 while (col < Renderer.MAX_WORLD_SIZE.X) {
        		 
        		 String numbers[] = mapInputLine.split(" ");
        		 String num = numbers[col];
        		 
        		 String numbersPhysics[] = physicsMapInputLine.split(" ");
        		 int numPhysics = Integer.parseInt(numbersPhysics[col]);

        		 
        		 
        		 
        		 this.MapTile[row][col] = num; 
        		 
        		 if(numPhysics == 1) {
//        			 BoundingBox finalBB = new BoundingBox(
//            				 new Vector2(col * Renderer.TILE_SIZE - Application.SCREEN_WIDTH / 2 + Renderer.TILE_SIZE / 2, row * Renderer.TILE_SIZE - Application.SCREEN_HEIGHT / 2 + Renderer.TILE_SIZE / 2),
//            				 new Vector2(Renderer.TILE_SIZE)
//            				 
//            		 );
            		 
//            		 System.out.print(finalBB.UpLeft.toString());
//            		 System.out.print(finalBB.UpRight.toString());
//            		 System.out.print(finalBB.DownLeft.toString());
//            		 System.out.println(finalBB.DownRight.toString());
            		 
            		 PhysicsSystem.AddStaticObject(
            				 new Vector2(col * Renderer.TILE_SIZE - Application.SCREEN_WIDTH / 2 + Renderer.TILE_SIZE / 2, row * Renderer.TILE_SIZE - Application.SCREEN_HEIGHT / 2 + Renderer.TILE_SIZE / 2),
            				 new Vector2(Renderer.TILE_SIZE)
            				 
            				 );
        		 }
        		 
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
		   
//		   if(TileTextures.get(tileType) == null) {
//			   return;
//		   }
		   
		   
		   Vector2 worldPosTile = new Vector2(worldCol * Renderer.TILE_SIZE, worldRow * Renderer.TILE_SIZE);		   
		   Renderer.SubmitDebug(worldPosTile, new Vector2(Renderer.TILE_SIZE), TileTextures.get(tileType), tileType);
		   
		   
		   worldCol++;
		   
		   if(worldCol == Renderer.MAX_WORLD_SIZE.X) {
			   worldCol = 0;
			   worldRow++;
		   }	   
	   }   
   }   
}