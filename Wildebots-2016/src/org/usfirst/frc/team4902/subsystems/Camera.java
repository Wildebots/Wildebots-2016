package org.usfirst.frc.team4902.subsystems;

import org.usfirst.frc.team4902.robot.Calculations;

import edu.wpi.first.wpilibj.CameraServer;
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
	
	public void AutoShoot() {
		Thread centreCamThread = new Thread(() -> {
			double[] defaultValue = new double[0];
			double centreX = 0;
			double centreY = 0;
			while (true) {
				double[] centreXs = contours.getNumberArray("centerX", defaultValue);
				double[] centreYs = contours.getNumberArray("centerY", defaultValue);

				if (centreXs.length != 0 && centreYs.length != 0) { 
					centreX = centreXs[0];
					centreY = centreYs[0];
				} else { continue; }				
				System.out.println(centreX + " - " + centreY);
				
				if (centreY < frameMidY && frameMidY - centreY > threshold) {
					System.out.println("GO UP");
//					ShooterSystem.getInstance().setAngle(Encoders.getInstance().getShooterAngle() + 2);
				}
				else if (centreY > frameMidY && centreY - frameMidY > threshold) {
					System.out.println("GO DOWN");
//					ShooterSystem.getInstance().setAngle(Encoders.getInstance().getShooterAngle() - 2);
				}
				else {
					System.out.println("MIDDLE");
					break;
				}
			}
		});
		centreCamThread.setDaemon(true);
		centreCamThread.start();
		
		while (true) {
			if (centreCamThread.isAlive() != false) { break; }
		}
		
		double distance = Calculations.calcDist();
		System.out.println("Distance from goal: " + distance);
		
		double angle = Calculations.calcAngle(distance);
		System.out.println("Angle to shoot: " + angle);
//		ShooterSystem.getInstance().setAngle(angle);
	}
}
