package org.usfirst.frc.team4902.subsystems;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;

/**
 * Provides methods to access the in-built accelerometer
 */
public class Accelerometer {
	
	// The built-in accelerometer in the RoboRIO
	BuiltInAccelerometer accel = new BuiltInAccelerometer();
	
	private static Accelerometer instance = new Accelerometer();
	
	/**
	 * Use to access accelerometer methods
	 * @return Instance of Accelerometer class
	 */
	public static Accelerometer getInstance() {
		return instance;
	}
	
	public double getX() {
		return accel.getX();
	}
	
	public double getY() {
		return accel.getY();
	}
	
	public double getZ() {
		return accel.getZ();
	}
	
}
