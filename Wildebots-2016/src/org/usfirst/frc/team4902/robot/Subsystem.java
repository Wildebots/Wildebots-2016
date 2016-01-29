package org.usfirst.frc.team4902.robot;

public abstract class Subsystem {
	
	public final Subsystem getInstance() {
		return this;
	}
	
	public abstract void execute();
}
