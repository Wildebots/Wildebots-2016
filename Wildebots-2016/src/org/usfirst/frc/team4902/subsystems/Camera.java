package org.usfirst.frc.team4902.subsystems;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * This class handles the Camera subsystem and provides services related to the camera server
 */
public class Camera {
	
	private CameraServer camera;
	private NetworkTable contours = NetworkTable.getTable("GRIP/myContoursReport");
	
	private int threshold = 20;
//	private int frameMidX = 640 / 2;
	private int frameMidY = 480 / 2;
	
	private static Camera instance = new Camera();
	
	/**
	 * Use to access Camera methods
	 * @return Instance of Camera class
	 */
	public static Camera getInstance() {
		return instance;
	}
	
	// This function starts the camera server and can be seen in the FRC Driver Station
	public void startCamera() {
		camera = CameraServer.getInstance();
		camera.setQuality(75);
        // The camera name (eg "cam0") can be found through the Roborio web interface
		camera.startAutomaticCapture("cam0");
	}
	
	public void centerCamera() {
		Thread centreCamThread = new Thread(() -> {
			double[] defaultValue = new double[0];
			while (true) {
//				double centreX = contours.getNumberArray("centerX", defaultValue)[0];
				double centreY = contours.getNumberArray("centerY", defaultValue)[0];
				
				System.out.println(centreX + " - " + centreY);
				
				if (Math.abs(frameMidY - centreY) < threshold) {
					break;
				} else {
					ShooterSystem.getInstance().setAngle();
				}
			}
		});
	}
}
