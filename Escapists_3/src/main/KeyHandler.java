package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.HashSet;
import java.util.Set;

public class KeyHandler implements KeyListener {

	public Set<Integer> PressedKeys = new HashSet<>();

	public void keyTyped(KeyEvent e) {
		return;
	}

	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		PressedKeys.add(Integer.valueOf(keyCode));
	}

	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		PressedKeys.remove(Integer.valueOf(keyCode));
	}
}