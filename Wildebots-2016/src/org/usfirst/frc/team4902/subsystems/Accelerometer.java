package org.usfirst.frc.team4902.subsystems;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;

public class Accelerometer {
	
	BuiltInAccelerometer accel = new BuiltInAccelerometer();
	
	private static Accelerometer instance = new Accelerometer();
	
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
