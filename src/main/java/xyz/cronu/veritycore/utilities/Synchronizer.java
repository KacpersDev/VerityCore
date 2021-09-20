package xyz.cronu.veritycore.utilities;

import org.bukkit.Bukkit;
import xyz.cronu.veritycore.VerityCore;

import java.util.ArrayList;
import java.util.List;

public class Synchronizer {
	private static List<Runnable> taskQueue = new ArrayList<>();

	private static boolean running = false;

	private static boolean runningTask = false;

	public static int synchronize(Runnable run) {
		return Bukkit.getScheduler().runTask(VerityCore.getPlugin(), run).getTaskId();
	}

	public static int desynchronize(Runnable run) {
		return Bukkit.getScheduler().runTaskAsynchronously(VerityCore.getPlugin(), run).getTaskId();
	}

}
