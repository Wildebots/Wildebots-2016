package org.usfirst.frc.team4902.subsystems;

public abstract class Subsystem {

	private int createCount = 0;

	// Prevents subsystems from being intialized more than once.
	public Subsystem() {
		super();
		createCount++;
		if (createCount >= 2) throw new RuntimeException("Cannot instantiate a subclass of Subsystem! Use getInstance() instead!");
	}

	/**
	 * Use this method to execute periodic functions
	 */
	public abstract void execute();
	
	/**
	 * Use this method to print values to console
	 */
	public abstract void log();
}
