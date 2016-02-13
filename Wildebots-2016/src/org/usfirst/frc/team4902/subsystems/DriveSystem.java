package org.usfirst.frc.team4902.subsystems;

import org.usfirst.frc.team4902.robot.Input;
import org.usfirst.frc.team4902.robot.PortMap;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;

public class DriveSystem extends Subsystem {
	
	//variables for arcade drive
	private boolean rotated;
	private double savedAngle = 0;
	private double savedLeftX = 0;
	private double savedLeftY = 0;
	

	private static DriveSystem instance = new DriveSystem();

	// Attach motors to ports
	Talon LeftFront = new Talon(PortMap.LeftFrontMotor.getPort()),
			LeftBack = new Talon(PortMap.LeftBackMotor.getPort()),
			RightFront = new Talon(PortMap.RightFrontMotor.getPort()),
			RightBack = new Talon(PortMap.RightBackMotor.getPort());

	// Attach motors to drive train
	private RobotDrive drive = new RobotDrive(LeftFront, LeftBack, RightFront, RightBack);

	/**
	 * A constant to tweak how quickly the robot will adjust back to straight.
	 */
	private final double ADJUSTMENT_SPEED_CONSTANT = 1.5;
	private final double ADJUSTMENT_SPEED_CONSTANT_TWO = 0.01;;
	private double lastAngle = 0;
	private double currentAngle = 0;
	private double correction = 0;
	
	
	public static DriveSystem getInstance() {
		return instance;
	}

	@Override
	public void execute() {
		double leftY = Input.getInstance().getLeftYThreshold(), rightY = Input.getInstance().getRightYThreshold();
		drive.tankDrive(-leftY, -rightY);
	}

	public synchronized void tankDrive(double left, double right) {
		drive.tankDrive(left, right);
	}
	
	public void arcadeDrive(){
		double leftX = Input.getInstance().getLeftX();
		double leftY = Input.getInstance().getLeftY();
		double robotAngle = Gyrometer.getInstance().getAngle() % 360.0;
		
		if (leftX>0.5 && leftY>0.5){
			savedAngle = Math.toDegrees(Math.atan(leftY/leftX));
			savedLeftX = leftX;
			savedLeftY = leftY;
		}
		
		
		if ((savedLeftX<0 && savedLeftY<0) || (savedLeftX>0 && savedLeftY<0)){
			savedAngle+=180;
		}
		
		
		double difference = savedAngle- robotAngle;
		
		if (difference> 180){
			difference = difference-360;
		}
		
		if (Math.abs(difference)>3){
			
			if (difference>0){
				drive.tankDrive(0.4,-0.4);
			}
			
			if (difference<0){
				drive.tankDrive(-0.4,0.4);
			}
			rotated = false;
		}else{
			rotated = true;
		}
		
		if (rotated){
			drive.tankDrive(Input.getInstance().getLeftTrigger()-Input.getInstance().getRightTrigger(),Input.getInstance().getLeftTrigger()-Input.getInstance().getRightTrigger());
		}
	
		
		
	}
	
	public void setLeft(double speed) {
		LeftFront.set(speed);
		LeftBack.set(speed);
	}
	
	public void setBack(double speed) {
		RightFront.set(speed);
		RightBack.set(speed);
	}
	
	public void setSpeed(double speed) {
		drive.tankDrive(speed, speed);
	}

	public void log(){
		
	}

	/**
	 * Rotates the robot on the place
	 * @param degrees (negative for clockwise, positive for counterclockwise)
	 */
	public void rotate(double degrees) {
		Thread RotateThread = new Thread(() -> {
			double angle = Gyrometer.getInstance().getAngle();
			if (degrees<0)
				while (Gyrometer.getInstance().getAngle() < angle+degrees) {
					this.tankDrive(-0.2, 0.2);
				}
			else{
				while (Gyrometer.getInstance().getAngle()> angle+degrees){
					this.tankDrive(0.2,-0.2);
				}
			}
		});
		RotateThread.setDaemon(true);
		RotateThread.start();
	}
	
	public void driveStraight(double speed){
		
		double angle = Math.toRadians(Gyrometer.getInstance().getAngle());
		
		drive.tankDrive(speed + Math.sin(angle) * ADJUSTMENT_SPEED_CONSTANT, speed - Math.sin(angle) * ADJUSTMENT_SPEED_CONSTANT);
	}
	
	public void driveStraightTwo(double speed){
		
		lastAngle = currentAngle;
		currentAngle = Math.toRadians(Gyrometer.getInstance().getAngle());
		correction+= (currentAngle - lastAngle)*ADJUSTMENT_SPEED_CONSTANT_TWO;
		if (currentAngle<0){
			drive.tankDrive(speed-correction,speed);
		}
		if (currentAngle>0){
			drive.tankDrive(speed,speed-correction);
		}
	}
	
	

}
