package tile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import assets.Asset;
import assets.Texture;
import main.GamePanel;
import main.Renderer;
import math.Vector2;

public class TileManager {

   public Texture[] TileTextures;
   
   public int[][] MapTile;
   public boolean StencilMapTile;

   public TileManager() {
	  
	  // Initialize the memory first
      this.TileTextures = new Texture[10];
      this.MapTile = new int[Renderer.MAX_WORLD_SIZE.X][Renderer.MAX_WORLD_SIZE.Y];
      
      // Set up the textures and load the map
      GetTileTextures();
      LoadMap("/maps/world01.txt");
   }

   public void GetTileTextures() {
      try {
    	 TileTextures[0] = Asset.Load("/tiles/grass.png").<Texture>As();
    	 TileTextures[1] = Asset.Load("/tiles/wall.png").<Texture>As();
    	 TileTextures[2] = Asset.Load("/tiles/water.png").<Texture>As();
    	 TileTextures[3] = Asset.Load("/tiles/earth.png").<Texture>As();
    	 TileTextures[4] = Asset.Load("/tiles/tree.png").<Texture>As();
    	 TileTextures[5] = Asset.Load("/tiles/sand.png").<Texture>As();
         
      } catch (IOException e) {
         e.printStackTrace();
      }

   }

   public void LoadMap(String filePath) {
	   
      try {
         InputStream is = getClass().getResourceAsStream(filePath);
         BufferedReader br = new BufferedReader(new InputStreamReader(is));
         
         int col = 0;
         int row = 0;

         while(col < Renderer.MAX_WORLD_SIZE.X && row < Renderer.MAX_WORLD_SIZE.Y) {
        	 
        	 String line = br.readLine();

        	 while (col < Renderer.MAX_WORLD_SIZE.X) {
        		 
        		 String numbers[] = line.split(" ");
        	   
        		 int num = Integer.parseInt(numbers[col]);
        	   
        		 this.MapTile[col][row] = num;
        		 col++;
        		 
        	 }
        	 if(col == Renderer.MAX_WORLD_SIZE.X) {
        		 col = 0;
        		 row++;
        		 
        	 }    	 
         }
         
         br.close();
         
      } catch (Exception e) {
    	  
      }

   }

   public void OnDraw() { 
	   
	   int worldCol = 0;
	   int worldRow = 0;
	   
	   while(worldCol < Renderer.MAX_WORLD_SIZE.X && worldRow < Renderer.MAX_WORLD_SIZE.Y) {
		   
		   int tileNum = this.MapTile[worldCol][worldRow];
		   
		   Vector2 worldPosTile = new Vector2(worldCol * Renderer.TILE_SIZE, worldRow * Renderer.TILE_SIZE);		   
		   Renderer.Submit(worldPosTile, new Vector2(Renderer.TILE_SIZE), TileTextures[tileNum]);
		   
		   
		   worldCol++;
		   
		   if(worldCol == Renderer.MAX_WORLD_SIZE.Y) {
			   worldCol = 0;
			   worldRow++;
		   }	   
	   }   
   }   
}















