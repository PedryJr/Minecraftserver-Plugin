package spleef.ManagerMethods.GeneralMethods;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.MessageDecoration;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.UserStatus;
import spleef.Blueprints.SpleefBlueprint;

import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static spleef.spluff.spleefArenaList;
import static spleef.spluff.url;


public class DiscordBot {

    String token;

    DiscordApi api;

    public DiscordBot(){

        token = "-";

        api = new DiscordApiBuilder().setToken(token).setAllIntents().login().join();

    }


    // Discord bot Events
    public void DiscordEvents(){

        api.addMessageCreateListener(event -> {
            if (event.getMessageContent().equalsIgnoreCase("!ip")) {

                event.getChannel().sendMessage("For premium users: snowcentral.uk.to");
                event.getChannel().sendMessage("For cracked users: snowcentral.uk.to:25566");


            }else if (event.getMessageContent().equalsIgnoreCase("!list")) {

                if(event.getChannel().getId() == 1063069334928379944L){

                    if(Bukkit.getOnlinePlayers().size() == 0){

                        event.getChannel().sendMessage("***The are currently no online players!***");

                    }else{

                        event.getChannel().sendMessage("***Online players!***");

                        for(Player player : Bukkit.getOnlinePlayers()){

                            event.getChannel().sendMessage("*- " + player.getName() + "*");

                        }

                    }

                }

            }else if(event.getMessageContent().equalsIgnoreCase("!matches")) {

                if(event.getChannel().getId() == 1063069334928379944L){

                    int i = 0;

                    for(SpleefBlueprint arena : spleefArenaList){

                        if(!arena.active.isEmpty()){

                            i++;

                        }

                    }

                    if(i == 0){

                        event.getChannel().sendMessage("***There are currently no ongoing matches!***");

                    }else{

                        event.getChannel().sendMessage("***Active matches!***");

                        for(SpleefBlueprint arena : spleefArenaList){

                            if(!arena.active.isEmpty()){

                                String newSeconds;
                                String newMinutes;
                                String newHours;

                                if(arena.seconds < 10){

                                    newSeconds = "0" + arena.seconds;

                                }else{

                                    newSeconds = String.valueOf(arena.seconds);

                                }

                                if(arena.minutes < 10){

                                    newMinutes = "0" + arena.minutes;

                                }else{

                                    newMinutes = String.valueOf(arena.minutes);

                                }

                                if(arena.hours < 10){

                                    newHours = "0" + arena.hours;

                                }else{

                                    newHours = String.valueOf(arena.hours);

                                }

                                event.getChannel().sendMessage("*- " + arena.active.get(0).getName() + "* **VS** *" + arena.active.get(1).getName() + "* **(" + arena.points1 + "/" + arena.points2 + ") |** *Map: " + arena.name + "* **|** *Time: " + newHours + ":" + newMinutes + ":" + newSeconds + "* **|**");

                            }

                        }

                    }

                }

            }else{

                if(event.getChannel().getId() == 1063069334928379944L){

                    for(Player player : Bukkit.getOnlinePlayers()){

                        if(!event.getMessageAuthor().isBotUser()){

                            player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&9&l[DC] &f" + event.getMessageAuthor().getDisplayName() + "&9:&f " + event.getMessageContent()));

                        }

                    }

                }

            }
        });

    }

    public void MinecraftChatEvent(Player sender, String message) {


        api.getChannelById("1063069334928379944").flatMap(Channel::asTextChannel).get().sendMessage("**" + sender.getName() + "**: " + message).join();


    }

    public void playerJoin(String name){

        String string = "https://minotar.net/cube/" + name + "/100.png";
        URL url;

        try {
            url = new URL(string);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        new MessageBuilder()
                .append("A player has joined!", MessageDecoration.ITALICS)
                .append(" - ")
                .append(name, MessageDecoration.BOLD)
                .addAttachment(url)
                .send(api.getChannelById(1063069334928379944L).flatMap(Channel::asTextChannel).get());



    }
    public void playerLeave(String name){

        api.getChannelById(1063069334928379944L).flatMap(Channel::asTextChannel).get().sendMessage("***" + name + "*** *has logged off!*");

    }

    public void logOn(){

        api.updateStatus(UserStatus.ONLINE);

    }
    public void logOff(){

        api.updateStatus(UserStatus.OFFLINE);
        api.disconnect();
        api = null;

    }


}
