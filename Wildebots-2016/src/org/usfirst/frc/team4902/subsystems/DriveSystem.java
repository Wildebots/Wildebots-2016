package org.usfirst.frc.team4902.subsystems;

import org.usfirst.frc.team4902.robot.Input;
import org.usfirst.frc.team4902.robot.PortMap;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;

public class DriveSystem extends Subsystem {

	private static DriveSystem instance = new DriveSystem();

	// Attach motors to ports
	Talon LeftFront = new Talon(PortMap.LeftFrontMotor.getPort()),
			LeftBack = new Talon(PortMap.LeftBackMotor.getPort()),
			RightFront = new Talon(PortMap.RightFrontMotor.getPort()),
			RightBack = new Talon(PortMap.RightBackMotor.getPort());

	// Attach motors to drive train
	private RobotDrive drive = new RobotDrive(LeftFront, LeftBack, RightFront, RightBack);

	public static DriveSystem getInstance() {
		return instance;
	}

	@Override
	public void execute() {
//		System.out.println("Execute");
		double threshold = 0.18;
		double leftY = Input.getInstance().getLeftY(), rightY = Input.getInstance().getRightY();

		if (Math.abs(leftY) < threshold) {
			leftY = 0;
		}
		if (Math.abs(rightY) < threshold){
			rightY = 0;
		}

		tankDrive(leftY,rightY);
		
		//		if (leftY < -threshold || leftY > threshold) {
//			if (rightY < -threshold || rightY > threshold) {
//				this.tankDrive(leftY, rightY);
//			} else {
//				//				System.out.println("");
//			}
//		} else {
//			//			System.out.println("");
//		}
	}

	private synchronized void tankDrive(double left, double right) {
		drive.tankDrive(left, right);
		log();
	}
	

	public void log(){
		
	}

	/**
	 * Rotates the robot on the place
	 * @param degrees (negative for clockwise, positive for counterclockwise)
	 */
	public void rotate(int degrees) {
		Thread RotateThread = new Thread(() -> {
			double angle = Gyrometer.getInstance().getAngle();
			if (degrees<0)
				while (Gyrometer.getInstance().getAngle() < angle+degrees) {
					this.tankDrive(-0.5, 0.5);
				}
			else{
				while (Gyrometer.getInstance().getAngle()> angle+degrees){
					this.tankDrive(0.5,-0.5);
				}
			}
		});
		RotateThread.setDaemon(true);
		RotateThread.start();
	}
	
	public Talon getTalon(int index) {
		System.out.println(LeftFront == null);
		if (index == 0) {
			return LeftFront;
		} else {
			return null;
		}
	}

}