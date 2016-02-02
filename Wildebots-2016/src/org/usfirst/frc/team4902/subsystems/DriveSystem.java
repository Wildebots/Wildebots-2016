package org.usfirst.frc.team4902.subsystems;

import org.usfirst.frc.team4902.robot.Input;
import org.usfirst.frc.team4902.robot.PortMap;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;

public class DriveSystem extends Subsystem {
	
	private static DriveSystem instance = new DriveSystem();
	
	Talon LeftFront = new Talon(PortMap.LeftFrontMotor.getPort()),
	LeftBack = new Talon(PortMap.LeftBackMotor.getPort()),
	RightFront = new Talon(PortMap.RightFrontMotor.getPort()),
	RightBack = new Talon(PortMap.RightBackMotor.getPort());
	
	private RobotDrive drive = new RobotDrive(LeftFront, LeftBack, RightFront, RightBack);

	public static DriveSystem getInstance() {
		return instance;
	}
	
	@Override
	public void execute() {
		double threshold = 0.18;
		double leftY = Input.getInstance().getLeftY(), rightY = Input.getInstance().getRightY();
		if (leftY < -threshold || leftY > threshold) {
			if (rightY < -threshold || rightY > threshold) {
				this.tankDrive(leftY, rightY);
			} else {
//				System.out.println("");
			}
		} else {
//			System.out.println("");
		}
	}
	
	private synchronized void tankDrive(double left, double right) {
		drive.tankDrive(left, right);
	}
	
	public void rotate(int degrees) {
		Thread RotateThread = new Thread(() -> {
			double angle = Gyrometer.getInstance().getAngle();
			while (Gyrometer.getInstance().getAngle() < angle+degrees) {
				this.tankDrive(1, -1);
			}
		});
		RotateThread.setDaemon(true);
		RotateThread.start();
	}

}
