package com.thomas7520.saofrance.leveling.utils;

import com.thomas7520.saofrance.leveling.player.IPlayerLeveling;
import com.thomas7520.saofrance.leveling.player.PlayerLeveling;
import com.thomas7520.saofrance.leveling.player.SQLPlayerLeveling;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import static com.thomas7520.saofrance.leveling.SaoFranceLeveling.getLevelingSQLUtils;
import static com.thomas7520.saofrance.leveling.SaoFranceLeveling.getUtils;
import static org.bukkit.Color.RED;

public class LevelingCMDUtils {

    public void sendInformation(CommandSender sender, IPlayerLeveling playerLeveling){
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                String.format("&2=====&6Sao-Leveling&2=====" +
                        "\nPseudo : %s" +
                        "\nNiveau : %s" +
                        "\nExpérience : %s/1000 ( %s )" +
                        "\n&2=====&6Sao-Leveling&2=====", playerLeveling.getPlayer().getName(), playerLeveling.getLevel(), playerLeveling.getExperience(), playerLeveling.getPercentageExperience() + "%")));
    }


    public IPlayerLeveling getIPlayerLeveling(CommandSender sender, String playerName) {

        IPlayerLeveling playerLeveling = null;

        Player player = Bukkit.getPlayer(playerName);

        if(player != null) playerLeveling = getUtils().getPlayersLeveling().get(player.getUniqueId().toString());

        if(playerLeveling == null && !getLevelingSQLUtils().hasAccount(playerName)) return null;

        if(playerLeveling == null) playerLeveling = new SQLPlayerLeveling(getLevelingSQLUtils().getUUIDPlayerByName(playerName));

        return playerLeveling;
    }

    public void giveLevel(CommandSender sender, IPlayerLeveling playerLeveling, int newLevel) {

        if(newLevel < 0) {
            sender.sendMessage(RED + "Impossible d'attribuer le nouveau niveau ( < 0 )");
            return;
        }

        playerLeveling.setLevel(playerLeveling.getLevel() + newLevel);

    }

    public void removeLevel(CommandSender sender, IPlayerLeveling playerLeveling, int level) {
        int newLevel = playerLeveling.getLevel() - level;

        if(newLevel < 0) {
            sender.sendMessage(RED + "Impossible d'attribuer le nouveau niveau ( < 0 )");
            return;
        }

        playerLeveling.setLevel(playerLeveling.getLevel() - level);
    }


    public void giveExperience(IPlayerLeveling playerLeveling, int newExperience) {


        if(playerLeveling.canLevelUp(newExperience)) {

            playerLeveling.setExperience(playerLeveling.getExperience() + newExperience);



            if (!playerLeveling.getPlayer().isOnline())  return;
                Player player = ((PlayerLeveling) playerLeveling).getPlayer();
                // send method for levelup with animation
                // SaoFranceLeveling.getUtils().sendLevelUP(player, newLevel)
                TitleUtils title = new TitleUtils();
                title.send(((PlayerLeveling) playerLeveling).getPlayer(), ChatColor.GREEN + "LEVEL UP!", ChatColor.AQUA + String.valueOf(playerLeveling.getLevel() - 1) + " \u2794 " + playerLeveling.getLevel(), 1, 2, 1);
                Firework firework = (Firework) player.getWorld().spawnEntity(new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY() + 5.0, player.getLocation().getZ()), EntityType.FIREWORK);
                FireworkMeta fireworkMeta = firework.getFireworkMeta();
                fireworkMeta.setPower(1);
                Color color = Color.fromRGB(50, 50, 255);
                fireworkMeta.addEffect(FireworkEffect.builder().withColor(color).build());
                firework.setFireworkMeta(fireworkMeta);
                firework.detonate();

        }
    }

}
