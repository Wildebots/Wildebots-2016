package org.usfirst.frc.team4902.robot;

import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;

public class MasterTimer {

	private static MasterTimer instance = new MasterTimer();

	private Timer T = new Timer(true);

	public static MasterTimer getInstance() {
		return instance;
	}

	public void schedule(Runnable r, Duration d) {
		T.schedule(MasterTimer.getTask(r), d.toMillis());
	}

	public void scheduleAtFixedRate(Runnable r, Duration delay , Duration rate) {
		long millisDelay;
		if (delay == null) {
			millisDelay = 0;
		} else {
			millisDelay = delay.toMillis();
		}
		T.scheduleAtFixedRate(MasterTimer.getTask(r), millisDelay , rate.toMillis());
	}

	public void schedule(TimerTask t, Duration d) {
		T.schedule(t, d.toMillis());
	}

	public static TimerTask getTask(Runnable r) {
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				r.run();
			}
		};
		return task;
	}

	public void praise() {
		System.out.println("All praise the MasterTimer...");
	}

}
