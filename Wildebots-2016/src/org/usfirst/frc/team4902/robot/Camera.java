package org.usfirst.frc.team4902.robot;

import edu.wpi.first.wpilibj.CameraServer;

// This class handles the Camera subsystem and provides services related to the camera server
public class Camera {
	
	private static CameraServer camera;
	
	// This function starts the camera server and can be seen in the FRC Driver Station
	public static void startCamera() {
		camera = CameraServer.getInstance();
		camera.setQuality(75);
        // The camera name (eg "cam0") can be found through the Roborio web interface
		camera.startAutomaticCapture("cam0");
	}
}
