package renderer;

import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Queue;

import main.Input;
import math.Vector2;
import math.Vector4;

class DialogueData {
	String Name;
	String TextData;
	
	DialogueData(String name, String textData) {
		this.Name = name;
		this.TextData = textData;
	}
}

public class DialogueSystem {
	
	static Queue<DialogueData> DialogueQueueData = new LinkedList<DialogueData>();
	static String ConstructedString = "";
	static boolean UsedOnce = false;
	static double TimeStepDelay = 0.0;
	
	public static void Initalize() {
	}
	
	public static void OnUpdate(double timeStep) {
		
		TimeStepDelay += timeStep;
		
		if(DialogueQueueData.size() > 0) {
			
			if(TimeStepDelay > 10.0) {
				
				DialogueData dialogueData = DialogueQueueData.peek(); // Access the front element
				
				if(ConstructedString.length() < dialogueData.TextData.length()) {
					ConstructedString = ConstructedString + dialogueData.TextData.charAt(ConstructedString.length());
				}
				
				TimeStepDelay = 0.0;
			}
		}
		
		if(Input.IsKeyPressed(KeyEvent.VK_ENTER)) {
			
			if(!UsedOnce) {
				// Remove the front element (FIFO)
				DialogueQueueData.poll();	
				ConstructedString = "";
			}
			UsedOnce = true;
		}
		else {
			UsedOnce = false;
		}
	}
	
	public static void DrawDialogue(String name, String textData) {
		DialogueQueueData.add(new DialogueData(name, textData));
	}
	
	public static void OnDraw() {
		if(DialogueQueueData.size() > 0) {
			DialogueData dialogueData = DialogueQueueData.peek(); // Access the front element
			
			UserInterface.DrawRectangle(
					new Vector2(60, 600), new Vector2(800, 240), new Vector4(0, 0, 0, 200), 32,
					5, new Vector4(255, 255, 255, 255), 22);
			
			UserInterface.DrawText(new Vector2(80, 650), 42, dialogueData.Name + ":");
			UserInterface.DrawText(new Vector2(80, 660 + 42 + 10), 36, ConstructedString);
			
			UserInterface.DrawText(new Vector2(580, 820), 26, "Press ENTER to close");
		}
	}
	
}
