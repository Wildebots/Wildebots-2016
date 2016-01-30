package org.usfirst.frc.team4902.robot;

import java.util.ArrayList;
import java.util.HashMap;

import edu.wpi.first.wpilibj.buttons.JoystickButton;

public final class EventSystem extends Thread {
	
	public enum HandlerType {
		OnPress, OnRelease, WhilePressed;
	}
	
	public class Handler {
		
		private Runnable r;
		
		private HandlerType type;
		
		public Handler(Runnable r, HandlerType type) {
			this.r = r;
			this.type = type;
		}
		
		public Runnable getRunnable() {
			return r;
		}
		
		public HandlerType getType() {
			return type;
		}
		
	}
	
	private HashMap<JoystickButton, ArrayList<Handler>> eventMap = new HashMap<>();
	
	private HashMap<JoystickButton, Boolean> pressedMap = new HashMap<>();
	
	private final static EventSystem instance = new EventSystem();
	
	public static EventSystem getInstance() {
		return instance;
	}
	
	public EventSystem() {
		Input.getInstance().getButtons().forEach(button -> {
			eventMap.put(button, new ArrayList<Handler>());
			pressedMap.put(button, false);
		});
		this.start();
	}
	
	@Override
	public void run() {
		while (true) {
			Input.getInstance().getButtons().forEach(button -> {
				if (button.get()) {
					eventMap.get(button).forEach(handler -> {
						if (handler.getType().equals(HandlerType.OnPress)) {
							if (!pressedMap.get(button)) {
								handler.getRunnable().run();
							}
						} else if (handler.getType().equals(HandlerType.WhilePressed)) {
							handler.getRunnable().run();
						} else if (!handler.getType().equals(HandlerType.OnRelease)) {
							handler.getRunnable().run();
						}
					});
					pressedMap.put(button, true);
				} else if (!button.get()) {
					eventMap.get(button).forEach(handler -> {
						if (handler.getType().equals(HandlerType.OnRelease)) {
							if (pressedMap.get(button)) {
								handler.getRunnable().run();
							}
						}
					});
					pressedMap.put(button, false);
				}
			});
		}
	}
	
	public void addHandler(Runnable r, JoystickButton button, HandlerType type) {
		if (!eventMap.keySet().contains(button)) {
			throw new RuntimeException("Attempted to map handler to unregistered button!");
		} else {
			eventMap.get(button).add(new Handler(r,type));
		}
	}
	
}
