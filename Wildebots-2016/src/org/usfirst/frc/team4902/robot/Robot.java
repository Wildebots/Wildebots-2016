
package org.usfirst.frc.team4902.robot;

import org.usfirst.frc.team4902.robot.EventSystem.HandlerType;
import org.usfirst.frc.team4902.subsystems.ShooterSystem;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	EventSystem.getInstance().addHandler(() -> {
    		ShooterSystem.getInstance().execute();
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
        
    }
    
    Encoder encoder = new Encoder(0, 1);
    
    @Override
    public void testInit() {
    	encoder.reset();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	System.out.println(encoder.get());
    }
    
}
