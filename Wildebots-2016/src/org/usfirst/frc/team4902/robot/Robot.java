
package org.usfirst.frc.team4902.robot;

import org.usfirst.frc.team4902.robot.EventSystem.HandlerType;
import org.usfirst.frc.team4902.subsystems.DriveSystem;
import org.usfirst.frc.team4902.subsystems.Gyrometer;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SPI.Port;

public class Robot extends IterativeRobot {
	
//	SPI gyro = new SPI(Port.kOnboardCS0);
		
	ADXRS450_Gyro gyro = new ADXRS450_Gyro();
	
	Encoder encoder = new Encoder(0,1);

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		EventSystem.getInstance().addHandler(() -> {
			gyro.calibrate();
			gyro.reset();
		}, Input.getInstance().getButtonA(), HandlerType.OnPress);
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString line to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the switch structure below with additional strings.
	 * If using the SendableChooser make sure to add them to the chooser code above as well.
	 */
	public void autonomousInit() {

	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {

	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		DriveSystem.getInstance().execute();
	}



	@Override
	public void testInit() {
		gyro.reset();
	}

	@Override
	public void disabledInit() {

	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
//		System.out.println("Encoder: "+encoder.getRaw());
//		System.out.println("Angle: "+Gyrometer.getInstance().getAngle());
//		DriveSystem.getInstance().execute();
		System.out.println(gyro.getAngle());
	}

}
