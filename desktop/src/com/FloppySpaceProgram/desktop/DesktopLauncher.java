package com.FloppySpaceProgram.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import FloppySpaceProgram.FSPGame;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Floppy Space Program";
		config.width = 960;
		config.height = 640;
		new LwjglApplication(new FSPGame(), config);
	}
}
