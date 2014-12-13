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
        config.resizable = true;
        config.useGL30 = false;
        config.width = 1280;
        config.height = 720;
        //config.foregroundFPS = 120;
        config.width = 1280;
        config.height = 720;
        config.vSyncEnabled = true;

		new LwjglApplication(new DULES(), config);
	}
}
