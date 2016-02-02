package org.usfirst.frc.team4902.subsystems;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.IMAQdxCameraControlMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;
import com.ni.vision.NIVision.ShapeMode;

import edu.wpi.first.wpilibj.CameraServer;

// This class handles the Camera subsystem and provides services related to the camera server
public class Camera {
	
	private static CameraServer camera;
	
	private static int session;
	
	// This function starts the camera server and can be seen in the FRC Driver Station
	public static void startCamera() {
		camera = CameraServer.getInstance();
		camera.setQuality(75);
		session = NIVision.IMAQdxOpenCamera("cam0", IMAQdxCameraControlMode.CameraControlModeController);
		NIVision.IMAQdxConfigureGrab(session);
		NIVision.IMAQdxStartAcquisition(session);
		System.out.println(session);
		NIVision.Rect rect = new NIVision.Rect(100,100,100,100);
		Image frame = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
		Thread CameraThread = new Thread(() -> {
			NIVision.IMAQdxGrab(session, frame, 0);
			NIVision.imaqDrawShapeOnImage(frame, frame, rect, DrawMode.DRAW_VALUE, ShapeMode.SHAPE_RECT, 0);
			camera.setImage(frame);
		});
        // The camera name (eg "cam0") can be found through the Roborio web interface
//		camera.startAutomaticCapture("cam0");
		CameraThread.setDaemon(true);
		CameraThread.start();
	}
}
