
package org.usfirst.frc.team4902.robot;

import org.usfirst.frc.team4902.subsystems.DriveSystem;
import org.usfirst.frc.team4902.subsystems.Gyrometer;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		
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
		Gyrometer.getInstance().reset();
		Gyrometer.getInstance().calibrate();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		DriveSystem.getInstance().driveStraight(0.6);
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		DriveSystem.getInstance().execute();
	}

	@Override
	public void testInit() {
		
	}

	@Override
	public void disabledInit() {

	}

	double newTarget = 0;

	double target = 0;

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		
	}

}
