package org.usfirst.frc.team4902.subsystems;

public abstract class Subsystem {

	int createCount = 0;

	public Subsystem() {
		super();
		createCount++;
		if (createCount >= 2) throw new RuntimeException("Cannot instantiate a subclass of Subsystem! Use getInstance() instead!");
	}

	public abstract void execute();

}
