package org.usfirst.frc.team4902.robot;

import org.usfirst.frc.team4902.robot.EventSystem.HandlerType;
import org.usfirst.frc.team4902.robot.Input.ActionMethod;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotDrive;

public class Robot extends IterativeRobot {

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		EventSystem.getInstance().addHandler(() -> {
			System.out.println("Pressed!");
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
			RobotDrive drive = new RobotDrive(0, 2, 15, 14);
			EventSystem e = EventSystem.getInstance();
			Input.getInstance().getButtons().forEach(button -> {
				EventSystem.getInstance().addHandler(() -> {
					//    			System.out.println(Input.getInstance().getButtonName(button) + " pressed!");
				}, button, HandlerType.WhilePressed);
			});
			//    	EventSystem.getInstance().addHandler(() -> {
			//    		System.out.println("Held!");
			//    	}, Input.getInstance().getButtonA(), HandlerType.WhilePressed);
		}

		/**
		 * This function is called periodically during test mode
		 */
		public void testPeriodic() {
			//    	System.out.println("Left Y: "+Input.getInstance().getLeftY());
			//    	System.out.println("Right Y: "+Input.getInstance().getRightY());
		}

	}
