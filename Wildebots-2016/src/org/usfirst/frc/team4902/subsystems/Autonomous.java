package org.usfirst.frc.team4902.subsystems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Future;

import javax.management.RuntimeErrorException;

public class Autonomous extends Subsystem {

	public static abstract class AutoAction {

		public void init() {};

		public abstract boolean isFinished();

		public abstract void execute();

		public abstract void exit();

	}

	public static interface condition {

		public boolean eval();

	}

	private static Autonomous instance = new Autonomous();

	private boolean isDone = false;

	public static final ArrayList<AutoAction> ActionList = new ArrayList<>();


	private AutoAction current;

	private Autonomous() {
		super();
	}

	public static Autonomous getInstance() {
		return instance;
	}

	@Override
	public void execute() {
		if (isDone) return;
		if (current == null) {
			current = ActionList.get(0);
			current.init();
		} else if (current.isFinished()) {
			current.exit();
			if (ActionList.indexOf(current) == ActionList.size()-1) {
				isDone = true;
				return;
			} else {
				current = ActionList.get(ActionList.indexOf(current)+1);
				current.init();
			}
		} else {
			current.execute();
		}
	}

	public static boolean inRangeVariance(double value, double target, double variance) {
		if (value+variance > target && value-variance < target) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean inRange(double value, double lower, double upper) {
		if (value > lower && value < upper) {
			return true;
		} else {
			return false;
		}
		
	}

	@Override
	public void log() {
		
	}

	private static AutoAction rotate(int deg) {
		return rotate(deg, () -> {return false;});
	}

	private static AutoAction rotate(int deg, condition initCond) {
		if (initCond == null) throw new RuntimeException("Null argument is Autonomous.rotate");
		return new AutoAction() {

			Future<Boolean> future;

			boolean exit = false;

			@Override
			public void init() {
				if (initCond.eval()) {
					exit = true;
					return;
				}
				future = DriveSystem.getInstance().rotate(deg);
			}

			@Override
			public boolean isFinished() {
				return future.isDone() || exit;
			}

			@Override
			public void exit() {}

			@Override
			public void execute() {}

		};
	}

	static {
		ActionList.addAll(Arrays.asList(

				rotate(-90)

				));
	}

}
