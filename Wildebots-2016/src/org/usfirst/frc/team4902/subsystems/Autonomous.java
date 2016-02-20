package org.usfirst.frc.team4902.subsystems;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TimerTask;
import java.util.concurrent.Future;

import javax.naming.InitialContext;

import org.usfirst.frc.team4902.robot.MasterTimer;

import edu.wpi.first.wpilibj.interfaces.Gyro;

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

	public static boolean inRange(double value, double target, double variance) {
		if (value+variance > target && value-variance < target) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void log() {
		// TODO Auto-generated method stub

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

				rotate(-90, () -> {

					double distConst = 0.58;

					return inRange(Ultrasonics.getInstance().getSideDistance(), 0.58, 0.05);
					
				}),

				new AutoAction() {

					volatile boolean finished = false;

					volatile boolean threeSecondsPassed = false;

					TimerTask task = MasterTimer.getTask(() -> {
						finished = true;
					});

					TimerTask t = MasterTimer.getTask(() -> {
						threeSecondsPassed = true;
					});

					@Override
					public boolean isFinished() {

						return finished && threeSecondsPassed;

					}

					public void init() {
						MasterTimer.getInstance().schedule(task, Duration.ofMillis(1500));
						MasterTimer.getInstance().schedule(t,Duration.ofMillis(3000));
					};

					@Override
					public void execute() {

						if (!inRange(Accelerometer.getInstance().getZ(), 0, 0.05)) {
							task.cancel();
							MasterTimer.getInstance().schedule(task, Duration.ofMillis(1500));
						}

					}

					@Override
					public void exit() {
						DriveSystem.getInstance().setSpeed(0);

					}

				}

				));
	}

}
