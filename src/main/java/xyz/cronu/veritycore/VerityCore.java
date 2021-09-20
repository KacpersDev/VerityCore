package xyz.cronu.veritycore;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import xyz.cronu.veritycore.autosell.AutosellManager;
import xyz.cronu.veritycore.clubs.Club;
import xyz.cronu.veritycore.clubs.ClubManager;
import xyz.cronu.veritycore.commands.CommandBase;
import xyz.cronu.veritycore.commands.commandlist.admin.GetPickaxeCMD;
import xyz.cronu.veritycore.commands.commandlist.admin.NewPickaxeCMD;
import xyz.cronu.veritycore.commands.commandlist.admin.add.AdminCMD;
import xyz.cronu.veritycore.commands.commandlist.crates.CrateCMD;
import xyz.cronu.veritycore.commands.commandlist.crates.KeyCMD;
import xyz.cronu.veritycore.commands.commandlist.leaderboards.blocks.BlockCMD;
import xyz.cronu.veritycore.commands.commandlist.leaderboards.money.MoneyCMD;
import xyz.cronu.veritycore.commands.commandlist.leaderboards.tokens.TokenCMD;
import xyz.cronu.veritycore.commands.commandlist.misc.FeedCMD;
import xyz.cronu.veritycore.commands.commandlist.misc.FlyCMD;
import xyz.cronu.veritycore.commands.commandlist.pet.PetCMD;
import xyz.cronu.veritycore.commands.commandlist.leaderboards.prestige.PrestigeCMD;
import xyz.cronu.veritycore.commands.commandlist.privatemines.PrivateMineCMD;
import xyz.cronu.veritycore.crates.Crate;
import xyz.cronu.veritycore.events.consumable.ConsumableConsumeEvent;
import xyz.cronu.veritycore.events.crates.KeyOpenEvent;
import xyz.cronu.veritycore.events.joinleave.JoinQuitEvent;
import xyz.cronu.veritycore.events.mining.BlockBreak;
import xyz.cronu.veritycore.events.pet.PetActivate;
import xyz.cronu.veritycore.events.pet.PetBoxOpen;
import xyz.cronu.veritycore.events.pickaxe.EquipEvent;
import xyz.cronu.veritycore.events.pickaxe.PickaxeMenuEvent;
import xyz.cronu.veritycore.events.pickaxe.PreventItemMove;
import xyz.cronu.veritycore.leaderboards.LeaderboardManager;
import xyz.cronu.veritycore.menu.MenuListener;
import xyz.cronu.veritycore.mines.Mine;
import xyz.cronu.veritycore.mines.MineManager;
import xyz.cronu.veritycore.pickaxe.Enchant;
import xyz.cronu.veritycore.pickaxe.PickaxeManager;
import xyz.cronu.veritycore.pickaxe.enchants.*;
import xyz.cronu.veritycore.scoreboard.CScoreboard;
import xyz.cronu.veritycore.users.PrisonPlayerManager;
import xyz.cronu.veritycore.utilities.ConfigManager;
import xyz.cronu.veritycore.utilities.WorldUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VerityCore extends JavaPlugin {

	//TODO: Create an enchant that for every certain amount of blocks that you mine it'll increase the amount of blocks you receive overall.

	public static World MINE_WORLD;
	public static File MINE_SCHEMATIC;

	private static BukkitTask updaters;
	private static List<Enchant> enchantments = new ArrayList<>();
	private static VerityCore plugin;

	@Override
	public void onEnable() {
		plugin = this;
		ConfigManager.getInstance().setPlugin(this);
		saveDefaultConfig();

		MINE_WORLD = WorldUtil.create("mines");
		createSchematicFolder();

		registerEnchantments(new Fortune(), new KeyFinder(), new KeySmith(),
				new Tokenator(), new MagicFind(), new JackHammer(), new Speed(),
				new Stinger(), new ChaosFactor());
		registerListeners(new BlockBreak(), new JoinQuitEvent(), new MenuListener(), new PickaxeMenuEvent(),
				new ConsumableConsumeEvent(), new KeyOpenEvent(), new PetActivate(), new PetBoxOpen(),
				new PreventItemMove(), new EquipEvent());
		registerCommands(new AdminCMD(),
				new PrivateMineCMD(), new CrateCMD(), new KeyCMD(),
				new PetCMD(), new FeedCMD(), new FlyCMD(), new GetPickaxeCMD(), new NewPickaxeCMD(),
				new PrestigeCMD(), new MoneyCMD(), new BlockCMD(), new TokenCMD());

		Club.loadGangs();
		Mine.load();
		Crate.loadCrates();
		loadOnlinePlayers();
		LeaderboardManager.refreshLeaderboards();

		updaters = updaters();
	}

	@Override
	public void onDisable() {
		getUpdaters().cancel();
		ClubManager.saveClubs();
		MineManager.saveMines();
		unloadOnlinePlayers();
	}

	public BukkitTask updaters() {
		return Bukkit.getScheduler().runTaskTimer(this, () ->
						Bukkit.getOnlinePlayers().forEach(p -> {
							PickaxeManager.updatePickaxe(p);
							AutosellManager.broadCast(p);
							PrisonPlayerManager.unloadPrisonPlayer(p.getUniqueId());
						}),
				20, 20L * 10L);
	}

	public void unloadOnlinePlayers() {
		Bukkit.getOnlinePlayers().forEach(p -> PrisonPlayerManager.unloadPrisonPlayer(p.getUniqueId()));
	}

	public void loadOnlinePlayers() {
		Bukkit.getOnlinePlayers().forEach(p -> {
			PrisonPlayerManager.loadPrisonPlayer(p.getUniqueId());
			CScoreboard.scoreBoard(p);
		});
	}

	public void registerEnchantments(Enchant... enchants) {
		enchantments.addAll(Arrays.asList(enchants));
	}

	public void registerCommands(CommandBase... commands) {
		Arrays.stream(commands).forEach(cmd -> getCommand(cmd.getName()).setExecutor(cmd));
	}

	public void registerListeners(Listener... events) {
		Arrays.stream(events).forEach(e -> getServer().getPluginManager().registerEvents(e, this));
	}

	public void createSchematicFolder() {
		File file = new File(getDataFolder().getAbsolutePath() + "/schematic");
		if (!file.exists()) file.mkdirs();
		MINE_SCHEMATIC = new File(file.toString() + File.separatorChar + "mine.schematic");
	}


	public static BukkitTask getUpdaters() {
		return updaters;
	}

	public static VerityCore getPlugin() {
		return plugin;
	}

	public static List<Enchant> getEnchantments() {
		return enchantments;
	}

}
