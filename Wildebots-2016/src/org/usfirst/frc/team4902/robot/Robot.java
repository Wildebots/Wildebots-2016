package org.usfirst.frc.team4902.robot;

import java.text.DecimalFormat;

import org.usfirst.frc.team4902.robot.EventSystem.HandlerType;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		EventSystem.getInstance().addHandler(() -> {
			System.out.println("A released!");
		}, Input.getInstance().getButtonA(), HandlerType.OnRelease);
		EventSystem.getInstance().addHandler(() -> {
			System.out.println("A pressed!");
		}, Input.getInstance().getButtonA(), HandlerType.OnPress);
		EventSystem.getInstance().addHandler(() -> {
			System.out.println("A is held!");
		}, Input.getInstance().getButtonA(), HandlerType.WhilePressed);
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

	}

	@Override
	public void teleopInit() {

	}

	@Override
	public void testInit() {
//		EventSystem.getInstance().addHandler(() -> {
//			System.out.println("Button A released!");
//		}, Input.getInstance().getButtonA(), HandlerType.OnRelease);
	}
	
	DecimalFormat format = new DecimalFormat("#.###");

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
//		System.out.print("Left Y: " + format.format(Input.getInstance().getLeftY()));
//		System.out.print(" Right Y: " + format.format(Input.getInstance().getRightY()));
//		System.out.print(" Left X: " + format.format(Input.getInstance().getLeftX()));
//		System.out.println(" Right X: " + format.format(Input.getInstance().getRightX()));
//		System.out.print("Left trigger: " + Input.getInstance().getLeftTrigger());
//		System.out.println(" Right trigger: " + Input.getInstance().getRightTrigger());
	}

}
