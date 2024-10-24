package assets;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

public class FontText extends Asset {
	
	public Font InternalFont;
	public boolean Loaded = false;
	
	FontText(String path) throws IOException, FontFormatException {
		
		Type = AssetType.FONT;
		
		try {
			Loaded = true;
			InputStream is = getClass().getResourceAsStream(path);
			InternalFont = Font.createFont(Font.TRUETYPE_FONT, is);			
		} catch(FontFormatException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		
	}
}
