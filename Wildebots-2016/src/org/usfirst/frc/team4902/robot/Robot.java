package org.usfirst.frc.team4902.robot;

import java.time.Duration;

import org.usfirst.frc.team4902.robot.EventSystem.HandlerType;
import org.usfirst.frc.team4902.subsystems.Arm;
import org.usfirst.frc.team4902.subsystems.Camera;
import org.usfirst.frc.team4902.subsystems.DriveSystem;
import org.usfirst.frc.team4902.subsystems.Encoders;
import org.usfirst.frc.team4902.subsystems.ShooterSystem;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.NamedSendable;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.ITable;

/**
 * Main robot class
 * Starting point of program
 */
public class Robot extends IterativeRobot {

	private static Robot instance;

	public static Robot getInstance() {
		return instance;
	}

	public volatile boolean noShooterLimit = false;

	public void robotInit() {

		instance = this;

		Camera.startCamera();

		// Disable subsystems here
		//    		DriveSystem.getInstance().disable();
		//    		Arm.getinstance().disable();
		//

		SmartDashboard.putBoolean("DB/Button 0", true);

		EventSystem.getInstance().addHandler(() -> {
			noShooterLimit = true;
		}, Input.getSecondaryInstance().getButtonB(), HandlerType.OnPress);

		EventSystem.getInstance().addHandler(() -> {
			noShooterLimit = false;
			Encoders.getInstance().resetShooterEncoder();
		}, Input.getSecondaryInstance().getButtonB(), HandlerType.OnRelease);

		EventSystem.getInstance().addHandler(() -> {
			ShooterSystem.getInstance().shoot();
		}, Input.getSecondaryInstance().getRightBumper(), HandlerType.OnPress);

		EventSystem.getInstance().addHandler(() -> {
			ShooterSystem.getInstance().pickup();
		}, Input.getSecondaryInstance().getLeftBumper(), HandlerType.OnPress);

		EventSystem.getInstance().addHandler(() -> {
			ShooterSystem.getInstance().stopShooterMotors();
		}, Input.getSecondaryInstance().getLeftBumper(), HandlerType.OnRelease);

		EventSystem.getInstance().addHandler(() -> {
			ShooterSystem.getInstance().shoot(0.4);
		}, Input.getSecondaryInstance().getButtonA(), HandlerType.OnPress);

		//    	~~ DO NOT ENABLE! ~~

		//		EventSystem.getInstance().addHandler(() -> {
		//		ShooterSystem.getInstance().setAngle(ShooterSystem.LOWER_LIMIT);
		//	}, Input.getSecondaryInstance().getButtonX(), HandlerType.OnPress);

		//    	EventSystem.getInstance().addHandler(() -> {
		//    		ShooterSystem.getInstance().setAngleGUNVIR(-45);
		//    	}, Input.getSecondaryInstance().getButtonX(), HandlerType.OnPress);

		//    	EventSystem.getInstance().addHandler(() -> {
		//    		ShooterSystem.getInstance().setAngleGUNVIR(0);
		//    	}, Input.getSecondaryInstance().getButtonY(), HandlerType.OnPress);

		//    	EventSystem.getInstance().addHandler(() -> {
		//    		DriveSystem.getInstance().rotate(45);
		//    	}, Input.getPrimaryInstance().getButtonX(), HandlerType.OnPress);

		//    	EventSystem.getInstance().addHandler(() -> {
		//    		ShooterSystem.getInstance().setAngle(-60);
		//    	}, Input.getPrimaryInstance().getButtonA(), HandlerType.OnPress);

		//    	EventSystem.getInstance().addHandler(() -> {
		//    		ShooterSystem.getInstance().setAngle(45);
		//    	}, Input.getSecondaryInstance().getButtonB(), HandlerType.OnPress);

		//    	EventSystem.getInstance().addHandler(() -> {
		//    		ShooterSystem.getInstance().setAngle(-80);
		//    	}, Input.getSecondaryInstance().getButtonX(), HandlerType.OnPress);

		//    	EventSystem.getInstance().addHandler(() -> {
		//    		double angle = Encoders.getInstance().getShooterAngle();
		//    		ShooterSystem.getInstance().setAngle(-80);
		//    		DriveSystem.getInstance().driveStraight(0.7);
		//    		ShooterSystem.getInstance().pickup();
		//    		MasterTimer.getInstance().schedule(() -> {
		//    			ShooterSystem.getInstance().setAngle(angle);
		//    			DriveSystem.getInstance().stopRotate();
		//    			ShooterSystem.getInstance().stopShooterMotors();
		//    		}, Duration.ofSeconds(4));
		//    	}, Input.getSecondaryInstance().getButtonB(), HandlerType.OnPress);

	}

	volatile boolean stop = false;

	volatile double speed = 0.8;

	public boolean enableAutonomous, GoBack;

	public void autonomousPeriodic() {
		if (enableAutonomous) {
			DriveSystem.getInstance().setSpeed(speed);
		}
	}

	public void teleopPeriodic() {
		ShooterSystem.getInstance().execute();
		DriveSystem.getInstance().execute();
		Arm.getInstance().singleArmExecute(); // Use for single base arm
	}

	@Override
	public void testInit() {

	}

	public void autonomousInit() {
		enableAutonomous = SmartDashboard.getBoolean("DB/Button 0");
		GoBack = SmartDashboard.getBoolean("DB/Button 1");
		if (enableAutonomous) {
			MasterTimer.getInstance().schedule(() -> {
				if (GoBack) {
					speed = -0.8;
					MasterTimer.getInstance().schedule(() -> speed = 0, Duration.ofMillis(1400));
				} else {
					speed = 0;
				}
			}, Duration.ofSeconds(2));
		}
	}

	@Override
	public void testPeriodic() {

	}

	@Override
	public void disabledInit() {
		Encoders.getInstance().resetArmEncoders();
		Encoders.getInstance().resetShooterEncoder();
	}

}
