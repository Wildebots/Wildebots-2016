
package org.usfirst.frc.team4902.robot;

import org.usfirst.frc.team4902.robot.EventSystem.HandlerType;
import org.usfirst.frc.team4902.subsystems.DriveSystem;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Talon;

public class Robot extends IterativeRobot {
	
	Talon motor = new Talon(0);
	
	Encoder encoder = new Encoder(0,1);

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		EventSystem.getInstance().addHandler(() -> {
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
		EventSystem.getInstance().addHandler(() -> {
			System.out.println("Set target!");
			target = newTarget;
		}, Input.getInstance().getButtonA(), HandlerType.OnPress);
		EventSystem.getInstance().addHandler(() -> {
			System.out.println("Encoder reset!");
			encoder.reset();
		}, Input.getInstance().getButtonB(), HandlerType.OnPress);
		encoder.reset();
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
		motor.set(Input.getInstance().getRightY());
		System.out.println(encoder.get());
//		double change = Input.getInstance().getRightY()*0.05;
//		if (change > 0) {
//			System.out.println(change);
//			newTarget += change;
//			System.out.println("NewTarget: "+newTarget);
//		}
//		this.execute();
	}
	
	public void reset() {
		
	}
	
	public void execute() {
		double remain = target-getRotations(encoder.get());
		motor.set(remain/target);
	}
	
	public double getRotations(int count) {
		return count/360.0;
	}

}
