package it.traininground.badluck.util.statistics;

import java.util.HashMap;

public final class TimeStats {
	
	private static HashMap<String, Long> timeStat = new HashMap<String, Long>();
	private static HashMap<String, Long> startTime = new HashMap<String, Long>();
	
	private TimeStats() {}
	
	public static void start(String name) {
		startTime.put(name, System.currentTimeMillis());
	}
	
	public static boolean started(String name) {
		return startTime.containsKey(name);
	}
	
	public static void reset(String name) {
		timeStat.remove(name);
		startTime.remove(name);
	}
	
	public static void stop(String name) {
		long time = System.currentTimeMillis();
		timeStat.compute(name, (String k, Long v) -> (v != null ? v : 0) + time - (startTime.get(name)));
		startTime.put(name, time);
	}
	
	public static long stopAndGet(String name) {
		stop(name);
		return get(name);
	}
	
	public static long stopGetAndReset(String name) {
		long result = stopAndGet(name);
		reset(name);
		return result;
	}
	
	public static long get(String name) {
		return timeStat.getOrDefault(name, 0l);
	}
}
