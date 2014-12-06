package com.paranoidalien.game.dules.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.paranoidalien.game.dules.DULES;

import java.awt.*;
import java.awt.event.KeyEvent;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.fullscreen = false;
        config.title = "DULES";
        config.resizable = false;
        config.useGL30 = false;
<<<<<<< HEAD
        config.width = 1920;
        config.height = 1080;
        config.vSyncEnabled = false;
        //config.foregroundFPS = 120;
=======
        config.width = 1280;
        config.height = 720;
        config.vSyncEnabled = true;
>>>>>>> 5ed3fa59d100e70d4de7567afb5c1d5518c9dada

		new LwjglApplication(new DULES(), config);
	}
}
