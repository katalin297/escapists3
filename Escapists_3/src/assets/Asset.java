package assets;

import java.awt.FontFormatException;
import java.io.IOException;

import util.Utils;

public class Asset {
	
	AssetType Type;
	
	public static Asset Load(String filepath) throws IOException, FontFormatException {
		
		String fileExtension = Utils.GetFileExtension(filepath);
		
		switch(fileExtension) {
			case "png":
			case "jpg":
				return new Texture(filepath);
			case "ttf":
				return new FontText(filepath);
			case ".wav":
				// TODO: Adds sound system
			default:
				System.out.println("Asset Type not available!");
		}
		
		return new Asset();
	}
	
	@SuppressWarnings("unchecked")
	public <T> T As() { return (T)this; }
}
