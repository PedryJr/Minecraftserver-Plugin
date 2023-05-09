package spleef.ManagerMethods.GeneralMethods;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import spleef.Blueprints.ParkourBlueprint;
import spleef.Blueprints.SpleefBlueprint;
import spleef.Translator.Translator;
import spleef.spluff;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

import static spleef.spluff.*;

public class StupidlyLargeMethods {

    AsyncPlayerChatEvent event;
    spluff plugin;

    public StupidlyLargeMethods(spluff plugin) {

        this.plugin = plugin;

    }
    public static void renewArena(SpleefBlueprint arena) {

        arena.active.clear();
        arena.que.clear();
        arena.playtoQue.clear();
        arena.resetQue.clear();
        arena.spectators.clear();
        arena.endgame.clear();
        arena.ms = null;
        if (arena.beginDecay != null) {
            arena.beginDecay.cancel();
        }
        if (arena.beginMinispleef != null) {
            arena.beginMinispleef.cancel();
        }

        switch (arena.name) {

            case "Coliseum":

                spleefArenaList.remove(Coliseum);
                Coliseum = new SpleefBlueprint(0, 29, 3000, 22, 29, "Coliseum", new ItemStack(Material.CLAY_BALL));
                spleefArenaList.add(Coliseum);

                ItemMeta coliseumMeta = Coliseum.icon.getItemMeta();
                assert coliseumMeta != null;
                coliseumMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7&lColiseum"));
                Coliseum.icon.setItemMeta(coliseumMeta);

                break;

            case "Void":

                spleefArenaList.remove(Void);
                Void = new SpleefBlueprint(1000, 29, 3000, 22, 33, "Void", new ItemStack(Material.BEDROCK));
                spleefArenaList.add(Void);

                ItemMeta voidMeta = Void.icon.getItemMeta();
                assert voidMeta != null;
                voidMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8&lVoid"));
                Void.icon.setItemMeta(voidMeta);
                break;

            case "Nations":

                spleefArenaList.remove(Nations);
                Nations = new SpleefBlueprint(2000, 29, 0, 22, 33, "Nations", new ItemStack(Material.BANNER));
                spleefArenaList.add(Nations);

                ItemMeta nationsMeta = Nations.icon.getItemMeta();
                assert nationsMeta != null;
                nationsMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&1&lNa&2&lit&3&lio&4&lns"));
                Nations.icon.setItemMeta(nationsMeta);

                break;

            case "Spooky":

                spleefArenaList.remove(Spooky);
                Spooky = new SpleefBlueprint(0, 29, 2000, 22, 33, "Spooky", new ItemStack(Material.PUMPKIN));
                spleefArenaList.add(Spooky);

                ItemMeta spookyMeta = Spooky.icon.getItemMeta();
                assert spookyMeta != null;
                spookyMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6&lSpooky"));
                Spooky.icon.setItemMeta(spookyMeta);

                break;

            case "Disco":

                spleefArenaList.remove(Disco);
                Disco = new SpleefBlueprint(0, 29, 1000, 22, 29, "Disco", new ItemStack(Material.RECORD_10));
                spleefArenaList.add(Disco);

                ItemMeta discoMeta = Disco.icon.getItemMeta();
                assert discoMeta != null;
                discoMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&5&lDisco"));
                Disco.icon.setItemMeta(discoMeta);

                break;

            case "Forest":

                spleefArenaList.remove(Forest);
                Forest = new SpleefBlueprint(1000, 29, 1000, 22, 29, "Forest", new ItemStack(Material.SAPLING));
                spleefArenaList.add(Forest);

                ItemMeta forestMeta = Forest.icon.getItemMeta();
                assert forestMeta != null;
                forestMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&2&lForest"));
                Forest.icon.setItemMeta(forestMeta);

                break;

            case "Starwars":

                spleefArenaList.remove(Starwars);
                Starwars = new SpleefBlueprint(2000, 29, 2000, 22, 29, "Starwars", new ItemStack(Material.SEA_LANTERN));
                spleefArenaList.add(Starwars);

                ItemMeta starwarsMeta = Starwars.icon.getItemMeta();
                assert starwarsMeta != null;
                starwarsMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&5&lStarwars"));
                Starwars.icon.setItemMeta(starwarsMeta);

                break;

            case "Reef":

                spleefArenaList.remove(Reef);
                Reef = new SpleefBlueprint(1000, 29, 0, 22, 29, "Reef", new ItemStack(Material.RAW_FISH));
                spleefArenaList.add(Reef);

                ItemMeta reefMeta = Reef.icon.getItemMeta();
                assert reefMeta != null;
                reefMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e&lReef"));
                Reef.icon.setItemMeta(reefMeta);

                break;

            case "Ancient":

                spleefArenaList.remove(Ancient);
                Ancient = new SpleefBlueprint(1000, 29, 2000, 22, 29, "Ancient", new ItemStack(Material.BONE));
                spleefArenaList.add(Ancient);

                ItemMeta ancientMeta = Ancient.icon.getItemMeta();
                assert ancientMeta != null;
                ancientMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f&lAncient"));
                Ancient.icon.setItemMeta(ancientMeta);

                break;

            case "Timberland":

                spleefArenaList.remove(Timberland);
                Timberland = new SpleefBlueprint(2000, 29, 1000, 22, 29, "Timberland", new ItemStack(Material.LOG));
                spleefArenaList.add(Timberland);

                ItemMeta timberlandMeta = Timberland.icon.getItemMeta();
                assert timberlandMeta != null;
                timberlandMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&2&lTimberland"));
                Timberland.icon.setItemMeta(timberlandMeta);

                break;

            case "Dunetemple":

                spleefArenaList.remove(DuneTemple);
                DuneTemple = new SpleefBlueprint(1000, 29, -1000, 22, 29, "Dunetemple", new ItemStack(Material.SANDSTONE));
                spleefArenaList.add(DuneTemple);

                ItemMeta DuneTempleMeta = DuneTemple.icon.getItemMeta();
                assert DuneTempleMeta != null;
                DuneTempleMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6&lDunetemple"));
                DuneTemple.icon.setItemMeta(DuneTempleMeta);

                break;

            case "Oasis":

                spleefArenaList.remove(Oasis);
                Oasis = new SpleefBlueprint(998, 29, -2019, 22, 29, "Oasis", new ItemStack(Material.getMaterial(38)));
                spleefArenaList.add(Oasis);

                ItemMeta OasisMeta = Oasis.icon.getItemMeta();
                assert OasisMeta != null;
                OasisMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&lOasis"));
                Oasis.icon.setItemMeta(OasisMeta);

                break;

            case "Holly":

                spleefArenaList.remove(Holly);
                Holly = new SpleefBlueprint(3000, 30, 3000, 22, 29, "Holly", new ItemStack(Material.SUGAR));
                spleefArenaList.add(Holly);

                ItemMeta HollyMeta = Holly.icon.getItemMeta();
                assert HollyMeta != null;
                HollyMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&lHo&2&lll&4&ly"));
                Holly.icon.setItemMeta(HollyMeta);

                break;

            case "Skylands":

                spleefArenaList.remove(Skylands);
                Skylands = new SpleefBlueprint(4000, 30, 3000, 22, 29, "Skylands", new ItemStack(Material.IRON_NUGGET));
                spleefArenaList.add(Skylands);

                ItemMeta SkylandsMeta = Skylands.icon.getItemMeta();
                assert SkylandsMeta != null;
                SkylandsMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&3&lSkylands"));
                Skylands.icon.setItemMeta(SkylandsMeta);

                break;



        }

    }

    public void chatLevelDisplay(AsyncPlayerChatEvent event) throws Exception {

        this.event = event;

        Connection connection = DriverManager.getConnection(url, username, password);

        Statement statement = connection.createStatement();

        ResultSet data = statement.executeQuery("select * from players");

        while (data.next()) {

            if (data.getString("UUID").equals(event.getPlayer().getUniqueId().toString())) {

                int xp = data.getInt("xp");
                int lvl = 1;

                if (xp > 200) {

                    lvl++;
                }
                if (xp > 260) {

                    lvl++;
                }
                if (xp > 338) {

                    lvl++;
                }
                if (xp > 440) {

                    lvl++;
                }
                if (xp > 571) {

                    lvl++;
                }
                if (xp > 742) {

                    lvl++;
                }
                if (xp > 965) {

                    lvl++;
                }
                if (xp > 1254) {

                    lvl++;
                }
                if (xp > 1631) {

                    lvl++;
                }
                if (xp > 2120) {

                    lvl++;
                }
                if (xp > 2757) {

                    lvl++;
                }
                if (xp > 3584) {

                    lvl++;
                }
                if (xp > 4659) {

                    lvl++;
                }
                if (xp > 6057) {

                    lvl++;
                }
                if (xp > 7874) {

                    lvl++;
                }
                if (xp > 10236) {

                    lvl++;
                }
                if (xp > 13307) {

                    lvl++;
                }
                if (xp > 17299) {

                    lvl++;
                }
                if (xp > 22488) {

                    lvl++;
                }
                if (xp > 29235) {

                    lvl++;
                }
                if (xp > 38006) {

                    lvl++;
                }
                if (xp > 49408) {

                    lvl++;
                }
                if (xp > 64230) {

                    lvl++;
                }
                if (xp > 83499) {

                    lvl++;
                }
                if (xp > 104374) {

                    lvl++;
                }
                if (xp > 125249) {

                    lvl++;
                }
                if (xp > 150299) {

                    lvl++;
                }
                if (xp > 180359) {

                    lvl++;
                }
                if (xp > 216431) {

                    lvl++;
                }
                if (xp > 259717) {

                    lvl++;
                }
                if (xp > 311661) {

                    lvl++;
                }
                if (xp > 373993) {

                    lvl++;
                }
                if (xp > 448792) {

                    lvl++;
                }
                if (xp > 538550) {

                    lvl++;
                }
                if (xp > 646261) {

                    lvl++;
                }
                if (xp > 775513) {

                    lvl++;
                }
                if (xp > 930616) {

                    lvl++;
                }
                if (xp > 1116739) {

                    lvl++;
                }
                if (xp > 1340087) {

                    lvl++;
                }
                if (xp > 1608104) {

                    lvl++;
                }
                if (xp > 1929725) {

                    lvl++;
                }
                if (xp > 2315670) {

                    lvl++;
                }
                if (xp > 2778804) {

                    lvl++;
                }
                if (xp > 3334565) {

                    lvl++;
                }
                if (xp > 4001478) {

                    lvl++;
                }
                if (xp > 4801774) {

                    lvl++;
                }
                if (xp > 5762129) {

                    lvl++;
                }
                if (xp > 6914555) {

                    lvl++;
                }
                if (xp > 8297466) {

                    lvl = 50;
                }

                sendMessage(event.getPlayer(), lvl, event.getRecipients(), data);

            }

        }

    }

    public void sendMessage(Player sender, int lvl, Set<Player> recipients, ResultSet data) throws Exception {

        String fromLang = data.getString("lang");

        Connection connection1 = DriverManager.getConnection(url, username, password);

        Statement statement1 = connection1.createStatement();

        ResultSet data1 = statement1.executeQuery("select * from players");

        String originalMessage = event.getMessage();

        if (sender.hasPermission("group.creator")) {
            event.setFormat(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&4: &f" + originalMessage));

            spreadMessage(sender, lvl, recipients, data1, "group.creator", originalMessage, fromLang);

        } else if (sender.hasPermission("group.admins")) {

            event.setFormat(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&c: &f" + originalMessage));

            spreadMessage(sender, lvl, recipients, data1, "group.admins", originalMessage, fromLang);

        } else if (sender.hasPermission("group.meneger")) {

            event.setFormat(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&5: &f" + originalMessage));

            spreadMessage(sender, lvl, recipients, data1, "group.meneger", originalMessage, fromLang);

        } else if (sender.hasPermission("group.headmoder")) {

            event.setFormat(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&d: &f" + originalMessage));

            spreadMessage(sender, lvl, recipients, data1, "group.headmoder", originalMessage, fromLang);

        } else if (sender.hasPermission("group.moder")) {

            event.setFormat(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&2: &f" + originalMessage));

            spreadMessage(sender, lvl, recipients, data1, "group.moder", originalMessage, fromLang);

        } else if (sender.hasPermission("group.newmoder")) {

            event.setFormat(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&a: &f" + originalMessage));

            spreadMessage(sender, lvl, recipients, data1, "group.newmoder", originalMessage, fromLang);

        } else if (sender.hasPermission("group.builder")) {

            event.setFormat(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&e: &f" + originalMessage));

            spreadMessage(sender, lvl, recipients, data1, "group.builder", originalMessage, fromLang);

        } else if (sender.hasPermission("group.youtube")) {

            event.setFormat(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&f: &f" + originalMessage));

            spreadMessage(sender, lvl, recipients, data1, "group.youtube", originalMessage, fromLang);

        } else if (sender.hasPermission("group.twitch")) {

            event.setFormat(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&5: &f" + originalMessage));

            spreadMessage(sender, lvl, recipients, data1, "group.twitch", originalMessage, fromLang);

        } else if (sender.hasPermission("plugin.yeti")) {

            event.setFormat(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&f: &f" + originalMessage));

            spreadMessage(sender, lvl, recipients, data1, "plugin.yeti", originalMessage, fromLang);

        } else if(sender.hasPermission("plugin.customrank")){

            event.setFormat(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&8: &f" + originalMessage));

            spreadMessage(sender, lvl, recipients, data1, "plugin.customrank", originalMessage, fromLang);

        }else if (sender.hasPermission("plugin.krampus")) {

            event.setFormat(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&b: &f" + originalMessage));

            spreadMessage(sender, lvl, recipients, data1, "plugin.krampus", originalMessage, fromLang);

        } else if (sender.hasPermission("plugin.amorok")) {

            event.setFormat(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&1: &f" + originalMessage));

            spreadMessage(sender, lvl, recipients, data1, "plugin.amorok", originalMessage, fromLang);

        } else if (sender.hasPermission("group.beta1")) {

            event.setFormat(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&8: &f" + originalMessage));

            spreadMessage(sender, lvl, recipients, data1, "group.beta1", originalMessage, fromLang);

        }else if (sender.hasPermission("group.default")) {

            event.setFormat(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&8: &7" + originalMessage));

            spreadMessage(sender, lvl, recipients, data1, "group.default", originalMessage, fromLang);

        }

    }

    public void spreadMessage(Player sender, int lvl, Set<Player> recipients, ResultSet data1, String group, String originalMessage, String fromLang) throws Exception {


        Translator translator = new Translator();
        Set<Player> toRemove = new HashSet<>();

        discordBot.MinecraftChatEvent(sender, originalMessage);

        while (data1.next()) {

            for (Player player : recipients) {

                if (data1.getString("UUID").equals(player.getUniqueId().toString())) {

                    String newMessage;
                    String toLang = data1.getString("lang");

                    if (fromLang.equals(toLang)) {

                        newMessage = originalMessage;

                    } else {

                        newMessage = translator.translate(fromLang, toLang, originalMessage);

                    }

                    if (sender != player) {

                        switch (group) {

                            case "group.creator":
                                toRemove.add(player);
                                player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&4: &f" + newMessage));
                                break;
                            case "group.admins":
                                toRemove.add(player);
                                player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&c: &f" + newMessage));
                                break;
                            case "group.meneger":
                            case "group.twitch":
                                toRemove.add(player);
                                player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&5: &f" + newMessage));
                                break;
                            case "group.headmoder":
                                toRemove.add(player);
                                player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&d: &f" + newMessage));
                                break;
                            case "group.moder":
                                toRemove.add(player);
                                player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&2: &f" + newMessage));
                                break;
                            case "group.newmoder":
                                toRemove.add(player);
                                player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&a: &f" + newMessage));
                                break;
                            case "group.builder":
                                toRemove.add(player);
                                player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&e: &f" + newMessage));
                                break;
                            case "group.youtube":
                            case "plugin.yeti":
                                toRemove.add(player);
                                player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&f: &f" + newMessage));
                                break;
                            case "plugin.customrank":
                            case "group.beta1":
                                toRemove.add(player);
                                player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&8: &f" + newMessage));
                                break;
                            case "plugin.krampus":
                                toRemove.add(player);
                                player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&b: &f" + newMessage));
                                break;
                            case "plugin.amorok":
                                toRemove.add(player);
                                player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&1: &f" + newMessage));
                                break;
                            case "group.default":
                                toRemove.add(player);
                                player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&8: &7" + newMessage));

                        }
                    }
                }
            }
        }

        for(Player player :toRemove){

            event.getRecipients().remove(player);

        }
    }
}
