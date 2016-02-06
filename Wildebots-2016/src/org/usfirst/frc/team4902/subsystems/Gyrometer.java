package org.usfirst.frc.team4902.subsystems;

import org.usfirst.frc.team4902.robot.PortMap;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;


public class Gyrometer {

	private static Gyrometer instance = new Gyrometer();

	private Gyro gyro = new AnalogGyro(PortMap.Gyro.getPort());
	
<<<<<<< HEAD
	public static Gyrometer getInstance() {
		return instance;
=======
	private ADXRS450_Gyro gyro;
	
	public Gyrometer() {
		gyro = new ADXRS450_Gyro();
		gyro.reset();
>>>>>>> refs/remotes/origin/Gyro
	}

	// Returns angle between 0 and 360 degrees relative to calibrated angle
	public double getAngle() {
		return -gyro.getAngle();
	}
<<<<<<< HEAD

	public void reset() {
=======
	
	public void calibrate() {
>>>>>>> refs/remotes/origin/Gyro
		gyro.reset();
	}

}
