package spleef;

import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import me.filoghost.holographicdisplays.api.hologram.line.TextHologramLine;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.javacord.api.DiscordApi;
import spleef.Blueprints.ParkourBlueprint;
import spleef.Blueprints.SpleefBlueprint;
import spleef.Blueprints.QueBlueprint;
import spleef.Events.Events;
import spleef.ManagerMethods.GeneralMethods.DiscordBot;
import spleef.ManualUpdate.ManualUpdate;
import spleef.commands.*;
import spleef.commands.Spleef.*;

import java.sql.*;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static spleef.ManagerMethods.GeneralMethods.StupidlyLargeMethods.renewArena;

public final class spluff extends JavaPlugin {

    public static ItemStack accept;
    public static ItemStack deny;
    public static Inventory challange;
    public static Inventory shopMenu;
    public static Inventory modeSelect;
    public static ArrayList<QueBlueprint> challangeList = new ArrayList<>();
    public static Block red;
    public static Block yellow;
    public static Block lime;

    public static String username;
    public static String password;
    public static String url;

    public static ArrayList<SpleefBlueprint> spleefArenaList;
    public static ArrayList<ParkourBlueprint> parkourArenaList;


    /** Spleef blueprints **/
    public static SpleefBlueprint Bliz;
    public static SpleefBlueprint Coliseum;
    public static SpleefBlueprint Void;
    public static SpleefBlueprint Nations;
    public static SpleefBlueprint Spooky;
    public static SpleefBlueprint Disco;
    public static SpleefBlueprint Mirage;
    public static SpleefBlueprint Forest;
    public static SpleefBlueprint Starwars;
    public static SpleefBlueprint Reef;
    public static SpleefBlueprint Ancient;
    public static SpleefBlueprint Timberland;
    public static SpleefBlueprint DuneTemple;
    public static SpleefBlueprint Oasis;
    public static SpleefBlueprint Skylands;
    public static SpleefBlueprint Holly;

    /** Parkour blueprints **/
    public static ParkourBlueprint LostSewers;
    public static ParkourBlueprint TempleRun;
    public static ParkourBlueprint Bibliotaph;
    public static ParkourBlueprint DreamsOfEscape;
    public static ParkourBlueprint FrostPunk;
    public static ParkourBlueprint Overturn;
    public static ParkourBlueprint Spiral;

    public static Inventory menu;
    public static Inventory spleefMenu;
    public static Inventory parkourMenu;
    public static Inventory dustShop;

    public static ItemStack buy50;
    public static ItemStack comingsoon;
    public static ItemStack buy200;
    public static ItemStack buy500;
    public static ItemStack buyDust;
    public static ItemStack playSpleef;
    public static ItemStack playParkour;
    public static ItemStack padding;
    public static ItemStack shovel;
    public static ItemStack compass;
    public static ItemStack exitQue;
    public static ItemStack restartPK;
    public static ItemStack guide;
    public static ItemStack checkpoint;
    public static ItemStack gadgets;
    public static ItemStack v1;
    public static ItemStack v2;
    public static ItemStack v3;
    public static ItemStack modePadding;
    public static Location spawn;
    public static Location limbo;
    public static Location c1;
    public static Chest chest;
    public static Inventory cinv;
    public static TextHologramLine head;
    public static TextHologramLine line;
    public static TextHologramLine head2;
    public static TextHologramLine line2;
    public static BukkitTask countdown;
    public static BukkitTask manualUpdate;

    public static HashMap<Player, BukkitTask> clocks = new HashMap<>();
    public static HashMap<Player, String> timeResult = new HashMap<>();
    public static HashMap<Player, Integer> timeResultInSeconds = new HashMap<>();
    private spluff plugin;
    public static boolean useHolographicDisplays;
    public static boolean gadgetdefined = false;
    HolographicDisplaysAPI api;
    public static Hologram lb;
    public static Hologram lb2;
    public static DiscordBot discordBot;
    public static Executor executor;

    @Override
    public void onEnable() {

        int numThreads = 8; // number of threads in the thread pool
        executor = Executors.newFixedThreadPool(numThreads);

        plugin = this;

        defineShit();
        discordBot.logOn();
        discordBot.DiscordEvents();

        getServer().getPluginManager().registerEvents(new Events(this), this);

        Objects.requireNonNull(getCommand("wins")).setExecutor(new winsCommand(plugin));
        Objects.requireNonNull(getCommand("money")).setExecutor(new moneyCommand(plugin));
        Objects.requireNonNull(getCommand("playto")).setExecutor(new playtoCommand());
        Objects.requireNonNull(getCommand("challange")).setExecutor(new challangeCommand());
        Objects.requireNonNull(getCommand("duel")).setExecutor(new duelCommand());
        Objects.requireNonNull(getCommand("spectate")).setExecutor(new spectateCommand(plugin));
        Objects.requireNonNull(getCommand("spec")).setExecutor(new specCommand(plugin));
        Objects.requireNonNull(getCommand("endgame")).setExecutor(new endgame(plugin));
        Objects.requireNonNull(getCommand("prefix")).setExecutor(new customRank());
        Objects.requireNonNull(getCommand("language")).setExecutor(new languagecommand());
        Objects.requireNonNull(getCommand("minispleef")).setExecutor(new minispleef());
        Objects.requireNonNull(getCommand("shop")).setExecutor(new shop());




    }

    @Override
    public void onDisable() {

        discordBot.logOff();

    }

    public void defineShit(){


        username = ".";
        password = ".";
        url = "jdbc:mysql://localhost:3306/minecraftdatabase?useSSL=false&allowPublicKeyRetrieval=true";
        useHolographicDisplays = Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays");

        defineArenas();
        defineItems();
        defineMenus();
        defineHolograms();

        discordBot = new DiscordBot();
        manualUpdate = new ManualUpdate(plugin).runTaskTimer(plugin, 0, 20);

    }

    public void defineArenas(){

        spleefArenaList = new ArrayList<>();
        parkourArenaList = new ArrayList<>();

        /*
        Bliz = new ArenaBlueprint(1902, 95, 1836, 22, 29, "Bliz", new ItemStack(Material.SNOWBALL));
        arenaList.add(Bliz);

        Mirage = new ArenaBlueprint(6935, 156, 7087, 22, 29, "Mirage", new ItemStack(Material.BRICK));
        arenaList.add(Mirage);

        name = new FfaBlueprint(-4005, 40, -2381, 65, 65, "name", new ItemStack(Material.PUMPKIN_SEEDS));
         */

        /** Define spleef arenas **/
        Coliseum = new SpleefBlueprint(0, 29, 3000, 22, 29, "Coliseum", new ItemStack(Material.CLAY_BALL));
        spleefArenaList.add(Coliseum);
        Void = new SpleefBlueprint(1000, 29, 3000, 22, 29, "Void", new ItemStack(Material.BEDROCK));
        spleefArenaList.add(Void);
        Nations = new SpleefBlueprint(2000, 29, 0, 22, 33, "Nations", new ItemStack(Material.BANNER));
        spleefArenaList.add(Nations);
        Spooky = new SpleefBlueprint(0, 29, 2000, 22, 33, "Spooky", new ItemStack(Material.PUMPKIN));
        spleefArenaList.add(Spooky);
        Disco = new SpleefBlueprint(0, 29, 1000, 22, 29, "Disco", new ItemStack(Material.RECORD_10));
        spleefArenaList.add(Disco);
        Forest = new SpleefBlueprint(1000, 29, 1000, 22, 29, "Forest", new ItemStack(Material.SAPLING));
        spleefArenaList.add(Forest);
        Starwars = new SpleefBlueprint(2000, 29, 2000, 22, 29, "Starwars", new ItemStack(Material.SEA_LANTERN));
        spleefArenaList.add(Starwars);
        Reef = new SpleefBlueprint(1000, 29, 0, 22, 29, "Reef", new ItemStack(Material.RAW_FISH));
        spleefArenaList.add(Reef);
        Ancient = new SpleefBlueprint(1000, 29, 2000, 22, 29, "Ancient", new ItemStack(Material.BONE));
        spleefArenaList.add(Ancient);
        Timberland = new SpleefBlueprint(2000, 29, 1000, 22, 29, "Timberland", new ItemStack(Material.LOG));
        spleefArenaList.add(Timberland);
        DuneTemple = new SpleefBlueprint(1000, 29, -1000, 22, 29, "Duntemple", new ItemStack(Material.SANDSTONE));
        spleefArenaList.add(DuneTemple);
        Oasis = new SpleefBlueprint(998, 29, -2019, 22, 29, "Oasis", new ItemStack(Material.getMaterial(38)));
        spleefArenaList.add(Oasis);
        Holly = new SpleefBlueprint(3000, 30, 3000, 22, 29, "Holly", new ItemStack(Material.SUGAR));
        spleefArenaList.add(Holly);
        Skylands = new SpleefBlueprint(4000, 30, 3000, 22, 29, "SkyLands", new ItemStack(Material.IRON_NUGGET));
        spleefArenaList.add(Skylands);

        /** Jungle Sapling Item **/
         Chest c = (Chest) new Location(Bukkit.getWorld("world"), 5932, 22, 5935).getBlock().getState();
         Inventory jsc = c.getBlockInventory();
         ItemStack junglesapling = jsc.getItem(0);

        /** Define parkour arenas **/
        LostSewers = new ParkourBlueprint(5000, 33, 3000, 5008, 63, 2890, "LostSewers", new ItemStack(Material.BRICK), 180, 0.5, -0.5, 4996.5, 36.2, 3001.5);
        parkourArenaList.add(LostSewers);
        TempleRun = new ParkourBlueprint(4992, 34, 2006, 5014, 27, 1852, "TempleRun", new ItemStack(Material.DEAD_BUSH), -90, 0.5, 0.5, 4992.5, 37.2, 2002.5);
        parkourArenaList.add(TempleRun);
        Bibliotaph = new ParkourBlueprint(988, 49, -3003, 1010, 67, -2964, "Bibliotaph", new ItemStack(Material.KNOWLEDGE_BOOK), -90, 0.5, 0.5, 988.5, 52.2, -3005.5);
        parkourArenaList.add(Bibliotaph);
        DreamsOfEscape = new ParkourBlueprint(3001, 17, 5003, 2999, 91, 5000, "DreamsOfEscape", new ItemStack(Material.LEAVES), -90, 0, 0, 3012.5, 20, 5004);
        parkourArenaList.add(DreamsOfEscape);
        FrostPunk = new ParkourBlueprint(4999, 30, 3997, 4860, 58, 3919, "FrostPunk", new ItemStack(Material.PACKED_ICE), 90, 0.5, 0.5, 4999.5, 34.2, 4001.5);
        parkourArenaList.add(FrostPunk);
        Overturn = new ParkourBlueprint(1522, 128, -1512, 1484, 89, -1277, "Overturn", new ItemStack(Material.QUARTZ_BLOCK), 90, 0.5, 0.5, 1522, 130, -1508);
        parkourArenaList.add(Overturn);

        Spiral = new ParkourBlueprint(5940, 5, 6113, 6000, 250, 6134, "Spiral", junglesapling, -90, 0.5, 0.5, 5942.5, 6.2, 6108.5);
        parkourArenaList.add(Spiral);

    }

    public void defineMenus(){

        /* Define Main Menu

         */

        menu.setItem (12, playSpleef);
        menu.setItem (14, playParkour);

        for(int i = 0; i < 27; i++){

            if(i < 9 || i > 17){

                menu.setItem(i, padding);

            }

        }

        /* Define Game Menu

         */

        /*
       gameMenu.setItem(31, name.icon);
       gameMenu.setItem(13, Mirage.icon);
       gameMenu.setItem(10, Bliz.icon);
         */

        /** Set spleef menu icons **/
        spleefMenu.setItem(10, Coliseum.icon);
        spleefMenu.setItem(19, Nations.icon);
        spleefMenu.setItem(11, Void.icon);
        spleefMenu.setItem(20, Spooky.icon);
        spleefMenu.setItem(12, Disco.icon);
        spleefMenu.setItem(21, Forest.icon);
        spleefMenu.setItem(13, Starwars.icon);
        spleefMenu.setItem(22, Reef.icon);
        spleefMenu.setItem(14, Ancient.icon);
        spleefMenu.setItem(23, Timberland.icon);
        spleefMenu.setItem(15, DuneTemple.icon);
        spleefMenu.setItem(24, Oasis.icon);
        spleefMenu.setItem(16, Holly.icon);
        spleefMenu.setItem(25, Skylands.icon);

        /** Set parkour menu icons **/
        parkourMenu.setItem(10, LostSewers.icon);
        parkourMenu.setItem(19, TempleRun.icon);
        parkourMenu.setItem(11, Bibliotaph.icon);
        parkourMenu.setItem(20, DreamsOfEscape.icon);
        parkourMenu.setItem(12, FrostPunk.icon);
        parkourMenu.setItem(21, Overturn.icon);

        challange = Bukkit.createInventory(null, 36, ChatColor.translateAlternateColorCodes('&', "   &f&lSnow&b&lCentral &8- &cChallange!"));

        shopMenu.setItem(13, buyDust);
        dustShop.setItem(11, buy50);
        dustShop.setItem(13, buy200);
        dustShop.setItem(15, buy500);

        modeSelect = Bukkit.createInventory(null, 27, ChatColor.translateAlternateColorCodes('&', "&c&lSelect mode!"));

        modeSelect.setItem(11, v1);
        modeSelect.setItem(13, v2);
        modeSelect.setItem(15, v3);

        modeSelect.setItem(10, modePadding);
        modeSelect.setItem(4, modePadding);
        modeSelect.setItem(22, modePadding);
        modeSelect.setItem(6, modePadding);
        modeSelect.setItem(24, modePadding);
        modeSelect.setItem(16, modePadding);


    }

    public void defineItems(){

        yellow = Bukkit.getWorld("world").getBlockAt(-1, 142, 1);
        red = Bukkit.getWorld("world").getBlockAt(1, 142, 1);
        lime = Bukkit.getWorld("world").getBlockAt(-1, 142, -1);

        c1 = new Location(Bukkit.getWorld("world"), 979, 37, 979);
        chest = (Chest) c1.getBlock().getState();
        cinv = chest.getBlockInventory();

        deny = cinv.getItem(0);
        accept = cinv.getItem(1);

        menu = Bukkit.createInventory(null, 27, ChatColor.translateAlternateColorCodes('&', "     &f&lSnow&b&lCentral &8&l- &c&lMenu!"));
        shopMenu = Bukkit.createInventory(null, 27, ChatColor.translateAlternateColorCodes('&', "     &f&lSnow&b&lCentral &e&lShop"));
        dustShop = Bukkit.createInventory(null, 27, ChatColor.translateAlternateColorCodes('&', "     &f&lSnow&b&lCentral &e&lBuy Dust"));
        spleefMenu = Bukkit.createInventory(null, 45, ChatColor.translateAlternateColorCodes('&', "&f&lSnow&b&lCental &8&l- &c&lSelect a map!"));
        parkourMenu = Bukkit.createInventory(null, 45, ChatColor.translateAlternateColorCodes('&', "&f&lSnow&b&lCental &8&l- &c&lSelect a map!"));
        shovel = new ItemStack(Material.DIAMOND_SPADE);
        compass = new ItemStack(Material.COMPASS);
        exitQue = new ItemStack(Material.REDSTONE_TORCH_ON);
        restartPK = new ItemStack(Material.ARROW);
        checkpoint = new ItemStack(Material.FEATHER);
        spawn = new Location(Bukkit.getWorld("world"), 0.5, 125, 0.5, 0, 0);
        comingsoon = new ItemStack(Material.STICK);

        ItemMeta comingsoonMeta = comingsoon.getItemMeta();
        comingsoonMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8&oComing soon..."));
        comingsoon.setItemMeta(comingsoonMeta);

        buyDust = new ItemStack(Material.SUGAR);
        ItemMeta buyDustMeta = buyDust.getItemMeta();
        buyDustMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&lBuy Mystery Dust!"));

        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9Buy mystery dust that"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9you can use on &fSnow&bCentral"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9cosmetics!"));
        buyDustMeta.setLore(lore);
        buyDust.setItemMeta(buyDustMeta);

        buy50 = new ItemStack(Material.SULPHUR);
        buy200 = new ItemStack(Material.GLOWSTONE_DUST);
        buy500 = new ItemStack(Material.SUGAR);

        v1 = new ItemStack(Material.WOOD_SPADE);
        v2 = new ItemStack(Material.STONE_SPADE);
        v3 = new ItemStack(Material.IRON_SPADE);
        modePadding = new ItemStack(Material.SNOW_BALL);

        ItemMeta buy50Meta = buy50.getItemMeta();
        buy50Meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "   &7&l-&b&lBuy &l50&7&l-"));
        lore.clear();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9This will cost &f&l10&c$"));
        buy50Meta.setLore(lore);
        buy50.setItemMeta(buy50Meta);

        ItemMeta buy200Meta = buy200.getItemMeta();
        buy200Meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "   &7&l-&b&lBuy &l200&7&l-"));
        lore.clear();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9This will cost &f&l35&c$"));
        buy200Meta.setLore(lore);
        buy200.setItemMeta(buy200Meta);

        ItemMeta buy500Meta = buy500.getItemMeta();
        buy500Meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "   &7&l-&b&lBuy &l500&7&l-"));
        lore.clear();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9This will cost &f&l60&c$"));
        buy500Meta.setLore(lore);
        buy500.setItemMeta(buy500Meta);




        playSpleef = new ItemStack(Material.SNOW_BLOCK);
        playParkour = new ItemStack(Material.FEATHER);
        padding = new ItemStack(Material.THIN_GLASS);

        ItemMeta checkpointMeta = checkpoint.getItemMeta();
        assert  checkpointMeta != null;
        checkpointMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f&lCheckpoint"));
        checkpointMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        checkpoint.setItemMeta(checkpointMeta);

        ItemMeta shovelMeta = shovel.getItemMeta();
        assert shovelMeta != null;
        shovelMeta.addEnchant(Enchantment.DIG_SPEED, 5, false);
        shovelMeta.setUnbreakable(true);
        shovelMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&lShovel"));
        shovelMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        shovelMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        shovelMeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
        shovelMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        shovel.setItemMeta(shovelMeta);

        ItemMeta menuMeta = compass.getItemMeta();
        assert menuMeta != null;
        menuMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&lMenu"));
        compass.setItemMeta(menuMeta);

        /*
        ItemMeta blizMeta = Bliz.icon.getItemMeta();
        assert blizMeta != null;
        blizMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&lBliz"));
        Bliz.icon.setItemMeta(blizMeta);

        ItemMeta mirageMeta = Mirage.icon.getItemMeta();
        assert mirageMeta != null;
        mirageMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&lMirage"));
        Mirage.icon.setItemMeta(mirageMeta);

        ItemMeta ffaMeta = name.icon.getItemMeta();
        assert ffaMeta != null;
        ffaMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f&lFFA"));
        name.icon.setItemMeta(ffaMeta);
         */

        /** Design menu and hotbar icons **/
        ItemMeta playSpleefMeta = playSpleef.getItemMeta();
        assert playSpleefMeta != null;
        playSpleefMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8- &fSpleef &8-"));
        playSpleef.setItemMeta(playSpleefMeta);

        ItemMeta playParkourMeta = playParkour.getItemMeta();
        assert playParkourMeta != null;
        playParkourMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8- &6Parkour &8-"));
        playParkour.setItemMeta(playParkourMeta);

        ItemMeta exitQueItemMeta = exitQue.getItemMeta();
        assert exitQueItemMeta != null;
        exitQueItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&cLeave queue"));
        exitQue.setItemMeta(exitQueItemMeta);

        ItemMeta restartPKMeta = restartPK.getItemMeta();
        assert restartPKMeta != null;
        restartPKMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e&lRestart"));
        restartPK.setItemMeta(restartPKMeta);

        ItemMeta paddingMeta = padding.getItemMeta();
        assert paddingMeta != null;
        paddingMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8&l-"));
        padding.setItemMeta(paddingMeta);

        /** Design spleef icons **/
        ItemMeta coliseumMeta = Coliseum.icon.getItemMeta();
        assert coliseumMeta != null;
        coliseumMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7&lColiseum"));
        Coliseum.icon.setItemMeta(coliseumMeta);

        ItemMeta nationsMeta = Nations.icon.getItemMeta();
        assert nationsMeta != null;
        nationsMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&1&lNa&2&lit&3&lio&4&lns"));
        Nations.icon.setItemMeta(nationsMeta);

        ItemMeta spookyMeta = Spooky.icon.getItemMeta();
        assert spookyMeta != null;
        spookyMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6&lSpooky"));
        Spooky.icon.setItemMeta(spookyMeta);

        ItemMeta voidMeta = Void.icon.getItemMeta();
        assert voidMeta != null;
        voidMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8&lVoid"));
        Void.icon.setItemMeta(voidMeta);

        ItemMeta discoMeta = Disco.icon.getItemMeta();
        assert discoMeta != null;
        discoMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&5&lDisco"));
        Disco.icon.setItemMeta(discoMeta);

        ItemMeta forestMeta = Forest.icon.getItemMeta();
        assert forestMeta != null;
        forestMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&2&lForest"));
        Forest.icon.setItemMeta(forestMeta);

        ItemMeta starwarsMeta = Starwars.icon.getItemMeta();
        assert starwarsMeta != null;
        starwarsMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&5&lStarwars"));
        Starwars.icon.setItemMeta(starwarsMeta);

        ItemMeta reefMeta = Reef.icon.getItemMeta();
        assert reefMeta != null;
        reefMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e&lReef"));
        Reef.icon.setItemMeta(reefMeta);

        ItemMeta ancientMeta = Ancient.icon.getItemMeta();
        assert ancientMeta != null;
        ancientMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f&lAncient"));
        Ancient.icon.setItemMeta(ancientMeta);

        ItemMeta timberlandMeta = Timberland.icon.getItemMeta();
        assert timberlandMeta != null;
        timberlandMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&2&lTimberland"));
        Timberland.icon.setItemMeta(timberlandMeta);

        ItemMeta DuneTempleMeta = DuneTemple.icon.getItemMeta();
        assert DuneTempleMeta != null;
        DuneTempleMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6&lDunetemple"));
        DuneTemple.icon.setItemMeta(DuneTempleMeta);

        ItemMeta OasisMeta = Oasis.icon.getItemMeta();
        assert OasisMeta != null;
        OasisMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&lOasis"));
        Oasis.icon.setItemMeta(OasisMeta);

        ItemMeta HollyMeta = Holly.icon.getItemMeta();
        assert HollyMeta != null;
        HollyMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&lHo&2&lll&4&ly"));
        Holly.icon.setItemMeta(HollyMeta);

        ItemMeta SkylandsMeta = Skylands.icon.getItemMeta();
        assert SkylandsMeta != null;
        SkylandsMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&3&lSkylands"));
        Skylands.icon.setItemMeta(SkylandsMeta);

        /** Design parkour icons **/
        ItemMeta LostSewersMeta = LostSewers.icon.getItemMeta();
        assert LostSewersMeta != null;
        LostSewersMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7&lLost Sewers"));
        LostSewers.icon.setItemMeta(LostSewersMeta);

        ItemMeta TempleRunMeta = TempleRun.icon.getItemMeta();
        assert TempleRunMeta != null;
        TempleRunMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6&lTemple Run"));
        TempleRun.icon.setItemMeta(TempleRunMeta);

        ItemMeta BibliotaphMeta = Bibliotaph.icon.getItemMeta();
        assert BibliotaphMeta != null;
        BibliotaphMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&9&lBibliotaph"));
        Bibliotaph.icon.setItemMeta(BibliotaphMeta);

        ItemMeta DreamsOfEscapeMeta = DreamsOfEscape.icon.getItemMeta();
        assert DreamsOfEscapeMeta != null;
        DreamsOfEscapeMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&lDreams Of Escape"));
        DreamsOfEscape.icon.setItemMeta(DreamsOfEscapeMeta);

        ItemMeta FrostPunkMeta = FrostPunk.icon.getItemMeta();
        assert FrostPunkMeta != null;
        FrostPunkMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f&lFrostPunk"));
        FrostPunk.icon.setItemMeta(FrostPunkMeta);

        ItemMeta OverturnMeta = Overturn.icon.getItemMeta();
        assert OverturnMeta != null;
        OverturnMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&lOverturn"));
        Overturn.icon.setItemMeta(OverturnMeta);

        ItemMeta SpiralMeta = Spiral.icon.getItemMeta();
        assert SpiralMeta != null;

        SpiralMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8- &b&lThe &2&lSpiral &8-"));

        ArrayList<String> eventLore = new ArrayList<>();
        eventLore.add(ChatColor.translateAlternateColorCodes('&', "&f&l* &6Event"));
        SpiralMeta.setLore(eventLore);

        Spiral.icon.setItemMeta(SpiralMeta);

    }

    public void defineHolograms(){

        if(useHolographicDisplays){

            try{

                Class.forName("com.mysql.cj.jdbc.Driver");

            }catch (ClassNotFoundException e){

                System.out.println(e.getMessage());

            }

            try{

                Connection connection = DriverManager.getConnection(url, username, password);

                Statement statement = connection.createStatement();

                ResultSet result = statement.executeQuery("select * from players order by money DESC");

                api = HolographicDisplaysAPI.get(plugin);

                lb = api.createHologram(new Location(Bukkit.getWorld("world"), 10.5, 128.3, 0.5));

                head = lb.getLines().appendText(ChatColor.translateAlternateColorCodes('&', "&f&lLeadeboard (Money)!"));

                int i = 1;

                while(result.next()){

                    line = lb.getLines().appendText(ChatColor.translateAlternateColorCodes('&', "&b" + i + "&c. &f&l" + result.getString(2) + " &c| &f&l" + result.getString(4) + "&c$"));

                    i++;

                    if(i >= 10){

                        break;

                    }

                }

                ResultSet result1 = statement.executeQuery("select * from players order by xp DESC");

                lb2 = api.createHologram(new Location(Bukkit.getWorld("world"), -9.5, 128.3, 0.5));

                head2 = lb2.getLines().appendText(ChatColor.translateAlternateColorCodes('&', "&f&lLeadeboard (Xp)!"));

                int j = 1;

                while(result1.next()){

                    line2 = lb2.getLines().appendText(ChatColor.translateAlternateColorCodes('&', "&b" + j + "&c. &f&l" + result1.getString(2) + " &c| &f&l" + result1.getString(5) + "&cxp"));

                    j++;

                    if(j >= 10){

                        break;

                    }

                }

            }catch (SQLException e) {

                Bukkit.getLogger().fine(e.getMessage());

                throw new RuntimeException(e);

            }

        }

    }

}