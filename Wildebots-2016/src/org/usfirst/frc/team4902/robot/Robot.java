
package org.usfirst.frc.team4902.robot;

import org.usfirst.frc.team4902.robot.EventSystem.HandlerType;
import org.usfirst.frc.team4902.subsystems.ShooterSystem;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {

	private static Robot instance;

	public static Robot getInstance() {
		return instance;
	}

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		instance = this;
		EventSystem.getInstance().addHandler(() -> {
			ShooterSystem.getInstance().execute();
		}, Input.getInstance().getButtonA(), HandlerType.OnPress);
	}

	public void autonomousInit() {

	}

	public void autonomousPeriodic() {

	}

	public void teleopPeriodic() {

	}


	@Override
	public void testInit() {

	}

	public void testPeriodic() {

	}
}
