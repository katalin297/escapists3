package renderer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import math.Vector2;
import math.Vector4;

class InterfaceRenderData {
	
	Vector2 Position = new Vector2();
	Vector2 Scale = new Vector2();
	Vector4 Color = new Vector4();
	int RoundedCorner = 0;
	
	int StrokeSize = 0;
	Vector4 StrokeColor = new Vector4();
	int StrokeRoundedCorner = 0;
	
	String ObjetType = "";
	String Data = ""; // For string
	
	
	public InterfaceRenderData(Vector2 position, Vector2 scale, Vector4 color, int roundedCorner) {
		this.ObjetType = "Rectangle";
		this.Position = position;
		this.Scale = scale;
		this.Color = color;
		this.RoundedCorner = roundedCorner;
	}
	
	public InterfaceRenderData(Vector2 position, Vector2 scale, Vector4 color, int roundedCorner, int strokeSize, Vector4 strokeColor, int strokeRoundedCorner) {
		this.ObjetType = "RectangleWithStroke";
		this.Position = position;
		this.Scale = scale;
		this.Color = color;
		this.RoundedCorner = roundedCorner;
		this.StrokeColor = strokeColor;
		this.StrokeSize = strokeSize;
		this.StrokeRoundedCorner = strokeRoundedCorner;
	}
	
	public InterfaceRenderData(Vector2 position, int fontSize, String dataText) {
		this.ObjetType = "Text";
		this.Data = dataText;
		this.Position = position;
		this.Scale = new Vector2(fontSize);
	}
	
}

public class UserInterface {
	
	static ArrayList<InterfaceRenderData> RenderData = new ArrayList<InterfaceRenderData>();  
	
	public static void Initalize() {
		return;
	}
	
	public static void DrawRectangle(Vector2 position, Vector2 scale, Vector4 color, int roundedCorner) {
		RenderData.add(new InterfaceRenderData(position, scale, color, roundedCorner));
	}
	
	public static void DrawRectangle(Vector2 position, Vector2 scale, Vector4 color, int roundedCorner, int strokeSize, Vector4 strokeColor, int strokeRoundedCorner) {
		RenderData.add(new InterfaceRenderData(position, scale, color, roundedCorner, strokeSize, strokeColor, strokeRoundedCorner));
	}
	
	public static void DrawText(Vector2 position, int scale, String textData) {
		RenderData.add(new InterfaceRenderData(position, scale, textData));
	}
	
	public static void OnDraw(Graphics2D graphicsAPI) {

		for(int i = 0; i < RenderData.size(); i++) {
			InterfaceRenderData renderData = RenderData.get(i);
			
			switch(renderData.ObjetType) {
				case "Rectangle": {
					Color c = new Color(renderData.Color.X, renderData.Color.Y, renderData.Color.Z, renderData.Color.W);
					graphicsAPI.setColor(c);
					graphicsAPI.fillRoundRect(
							renderData.Position.X, renderData.Position.Y,
							renderData.Scale.X, renderData.Scale.Y,
							renderData.RoundedCorner, renderData.RoundedCorner
					);
					break;
				}
				case "RectangleWithStroke": {
					Color c = new Color(renderData.Color.X, renderData.Color.Y, renderData.Color.Z, renderData.Color.W);
					graphicsAPI.setColor(c);
					graphicsAPI.fillRoundRect(
							renderData.Position.X, renderData.Position.Y,
							renderData.Scale.X, renderData.Scale.Y,
							renderData.RoundedCorner, renderData.RoundedCorner
					);
					
					c = new Color(renderData.StrokeColor.X, renderData.StrokeColor.Y, renderData.StrokeColor.Z, renderData.StrokeColor.W);
					graphicsAPI.setColor(c);
					graphicsAPI.setStroke(new BasicStroke(renderData.StrokeSize));
					graphicsAPI.drawRoundRect(
							renderData.Position.X + 5, renderData.Position.Y + 5,
							renderData.Scale.X - 10, renderData.Scale.Y - 10,
							renderData.StrokeRoundedCorner, renderData.StrokeRoundedCorner
					);
					break;
				}
				case "Text": {
					graphicsAPI.setFont(graphicsAPI.getFont().deriveFont(Font.PLAIN, renderData.Scale.X));
					graphicsAPI.drawString(renderData.Data, renderData.Position.X, renderData.Position.Y);
					break;
				}
				
			}
			
			
			
			
		}
		
		RenderData.clear();
		
		
	}
	
	
}
