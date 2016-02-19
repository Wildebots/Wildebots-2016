package org.usfirst.frc.team4902.subsystems;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TimerTask;

import org.usfirst.frc.team4902.robot.MasterTimer;

public class Autonomous extends Subsystem {
	
	

	public static abstract class AutoAction {
		
		public void init() {};

		public abstract boolean isFinished();

		public abstract void execute();

		public abstract void exit();

	}

	private static Autonomous instance = new Autonomous();

	private boolean isDone = false;

	public static final ArrayList<AutoAction> ActionList = new ArrayList<>();
	

	private AutoAction current = (ActionList.isEmpty()) ? null : ActionList.get(0);

	private Autonomous() {
		super();
	}

	public static Autonomous getInstance() {
		return instance;
	}

	@Override
	public void execute() {
		if (isDone) return;
		else if (current.isFinished()) {
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

	static {
		ActionList.addAll(Arrays.asList(
				
			new AutoAction() {
	
				final double distConst = 0.58;
	
				@Override
				public boolean isFinished() {
					return (inRange(Ultrasonics.getInstance().getSideDistance(), distConst, 0.05));
				}
	
				@Override
				public void execute() {
	//				double dist = Ultrasonics.getInstance().getSideDistance();
	//				if (inRange(Gyrometer.getInstance().getAngle(), 90, 0.5)) {
	//					if (inRange(Ultrasonics.getInstance().getForwardDistance(), target, variance))
	//				} else {
	//					DriveSystem.getInstance().rotate(90);
	//				}
	
				}
	
				@Override
				public void exit() {
					DriveSystem.getInstance().setSpeed(0);
				}
				
			},
			
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
