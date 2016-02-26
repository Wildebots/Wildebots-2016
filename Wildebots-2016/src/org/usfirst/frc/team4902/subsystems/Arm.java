package org.usfirst.frc.team4902.subsystems;

import org.usfirst.frc.team4902.robot.Calculations;
import org.usfirst.frc.team4902.robot.Input;
import org.usfirst.frc.team4902.robot.PortMap;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.PIDCommand;

public class Arm extends Subsystem{
	
	private final double baseSegmentLength = 54.9; // cm
	private final double secondSegmentLength = 41.9; // cm
	
	private final double baseStartingAngle = 20.25; // Degrees
	private final double secondStartingAngle = 69.75;

	private final double BASE_SPEED_ADJUSTMENT = 0.4;
	private final double SECOND_SPEED_ADJUSTMENT = 0.5;

	private final double offset = 6.2; // cm
	
	final double MAX_EXTENSION = 38.1; // cm (15 inches max)
	
	private static Arm instance = new Arm();
	
	private Talon baseSegmentMotor = new Talon(PortMap.ArmBaseSegmentMotor.getPort());
	private Talon secondSegmentMotor = new Talon(PortMap.ArmSecondSegmentMotor.getPort());
		
	public static Arm getInstance(){
		return instance;
	}

	@Override
	public void execute() {
		double baseSegmentSpeed = -(Input.getSecondaryInstance().getLeftYThreshold() * BASE_SPEED_ADJUSTMENT);
		double secondSegmentSpeed = Input.getSecondaryInstance().getRightYThreshold() * SECOND_SPEED_ADJUSTMENT;
		
		double secondAngle = Encoders.getInstance().getSecondSegmentAngle();
		
		if (this.isInLegalPosition()) {
			baseSegmentMotor.set(baseSegmentSpeed);
			secondSegmentMotor.set(secondSegmentSpeed);
		}
		
		else {
			
			System.out.println("REACHED LIMIT!");
			
			if (baseSegmentSpeed >= 0) {
				baseSegmentMotor.set(baseSegmentSpeed);
			}
			
			if (secondAngle <= 0 && secondSegmentSpeed >= 0) {
				secondSegmentMotor.set(secondSegmentSpeed);
			}
			
			else if (secondAngle >= 0 && secondSegmentSpeed <= 0) {
				secondSegmentMotor.set(secondSegmentSpeed);
			}
			
			else {
				baseSegmentMotor.set(0);
				secondSegmentMotor.set(0);
			}
		}
	}
	
	public boolean isInLegalPosition() {
		double baseSegmentAngle = 180 - (Encoders.getInstance().getBaseSegmentAngle() + baseStartingAngle);
		double secondSegmentAngle = Encoders.getInstance().getSecondSegmentAngle() + secondStartingAngle;
		
		System.out.print("Base Angle: " + baseSegmentAngle);
		System.out.print(" : Second Angle: " + secondSegmentAngle);
		
		double ext =  Calculations.getArmExtension(baseSegmentLength, secondSegmentLength, baseSegmentAngle, secondSegmentAngle, offset);
		
		System.out.println(" - Extension: " + ext);
		
		if (ext > MAX_EXTENSION-2){
			return false;
		} 
		else {
			return true;
		}
	}

	@Override
	public void log() {
		// TODO Auto-generated method stub
	}
	
	public Object manipulate(Object o) {
		System.out.println(o.toString() + " was Manipulated!");
		return instance.hashCode()*43223523;
	}
}
