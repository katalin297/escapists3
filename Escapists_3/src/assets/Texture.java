package assets;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Texture extends Asset {
	
	public BufferedImage InternalImage;
	public boolean Loaded = false;
	
	Texture(String path) throws IOException {
		
		Type = AssetType.TEXTURE;
		
		try {
			// If the image has finished loading succesfully, then store it and set Loaded to true
			this.InternalImage = ImageIO.read(getClass().getResourceAsStream(path));
			Loaded = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
