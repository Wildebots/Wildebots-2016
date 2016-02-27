package org.usfirst.frc.team4902.subsystems;

import org.usfirst.frc.team4902.robot.Calculations;
import org.usfirst.frc.team4902.robot.Input;
import org.usfirst.frc.team4902.robot.PortMap;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;

public class Arm extends Subsystem {
	
	private final double baseSegmentLength = 41.9; // cm
	private final double secondSegmentLength = 54.9; // cm
	
	private final double baseStartingAngle = 14.8; //144mm and 37 mm
	private final double secondStartingAngle = 75.2;

	private final double BASE_SPEED_ADJUSTMENT = 0.4;
	private final double SECOND_SPEED_ADJUSTMENT = 0.5;

	private final double offset = 6.5; // cm
	
	private final double firstSlow  = 22.0; // cm
	
	final double MAX_EXTENSION = 38.1; // cm (15 inches max)
	
	double ext;
	
	private static Arm instance = new Arm();
	
	private Talon baseSegmentMotor = new Talon(PortMap.ArmBaseSegmentMotor.getPort());
	private Talon secondSegmentMotor = new Talon(PortMap.ArmSecondSegmentMotor.getPort());
		
	public static Arm getInstance(){
		return instance;
	}
	
	public void singleArmExecute() {
		double baseSegmentSpeed = -(Input.getSecondaryInstance().getLeftYThreshold() * BASE_SPEED_ADJUSTMENT);
		double baseAngle = Encoders.getInstance().getBaseSegmentAngle();
		
		if (baseAngle > 0 && baseAngle < 85) {
			baseSegmentMotor.set(baseSegmentSpeed);
		}
	}

	@Override
	public void execute() {
		
		if (this.isDisabled()) return;
		
		double baseSegmentSpeed = -(Input.getSecondaryInstance().getLeftYThreshold() * BASE_SPEED_ADJUSTMENT);
		double secondSegmentSpeed = Input.getSecondaryInstance().getRightYThreshold() * SECOND_SPEED_ADJUSTMENT;
		
		if (Math.abs(secondSegmentSpeed) < 0.08) {
			if (Encoders.getInstance().getBaseSegmentAngle() > 90) {
				secondSegmentMotor.set(0.05);
				return;
			}
			else if (Encoders.getInstance().getBaseSegmentAngle() < 90) {
				secondSegmentMotor.set(-0.05);
				return;
			}
		}
		
		double secondAngle = Encoders.getInstance().getSecondSegmentAngle() + secondStartingAngle;
		
		if (this.isInLegalPosition()) {			
			if (ext > firstSlow) {
				System.out.println("SLOWING DOWN");
				secondSegmentMotor.set(secondSegmentSpeed / 2.0);
				baseSegmentMotor.set(baseSegmentSpeed / 2.0);
			}
			else {
				secondSegmentMotor.set(secondSegmentSpeed);
				baseSegmentMotor.set(baseSegmentSpeed);
			}
		}
		else {
			
			System.out.println("REACHED LIMIT!");
			
			if (baseSegmentSpeed <= 0) {
				baseSegmentMotor.set(baseSegmentSpeed);
			} else {
				baseSegmentMotor.set(-0.1);
			}
			
			if (secondAngle >= 180 && secondSegmentSpeed >= 0) {
				secondSegmentMotor.set(secondSegmentSpeed);
				return;
			} else if (secondAngle <= 180 && secondSegmentSpeed <= 0) {
				secondSegmentMotor.set(secondSegmentSpeed);
				return;
			} else {
				secondSegmentMotor.set(-0.1);
			}
		}
	}
	
	public boolean isInLegalPosition() {
		double baseSegmentAngle = 180 - (Encoders.getInstance().getBaseSegmentAngle() + baseStartingAngle);
		double secondSegmentAngle = Encoders.getInstance().getSecondSegmentAngle() + secondStartingAngle;
			
		ext =  Calculations.getArmExtension(baseSegmentLength, secondSegmentLength, baseSegmentAngle, secondSegmentAngle, offset);
		
		System.out.print("Base:" + baseSegmentAngle);
		System.out.print(" - Second: " + secondSegmentAngle);
		System.out.println(" - Extension: " + ext);
		
		if (ext > (MAX_EXTENSION-10)) {
			return false;
		} 
		else {
			return true;
		}
	}

	@Override
	public void log() {
		
	}
	
	public Object manipulate(Object o) {
		System.out.println(o.toString() + " was Manipulated!");
		return instance.hashCode()*43223523;
	}
}
