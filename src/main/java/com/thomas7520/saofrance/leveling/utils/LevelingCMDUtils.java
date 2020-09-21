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
import static org.bukkit.ChatColor.RED;

public class LevelingCMDUtils {

    public void sendInformation(CommandSender sender, IPlayerLeveling playerLeveling){
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                String.format("&2=====&6Sao-Leveling&2=====" +
                        "\nPseudo : %s" +
                        "\nNiveau : %s" +
                        "\nExpérience : %s/1000 ( %s )" +
                        "\n&2=====&6Sao-Leveling&2=====", playerLeveling.getPlayer().getName(), playerLeveling.getLevel(), playerLeveling.getExperience(), LevelingUtils.round(playerLeveling.getPercentageExperience(), 1) + "%")));

    }


    public IPlayerLeveling getIPlayerLeveling(CommandSender sender, String playerName) {

        IPlayerLeveling playerLeveling = null;

        Player player = Bukkit.getPlayer(playerName);

        if(player != null) playerLeveling = getUtils().getPlayersLeveling().get(player.getUniqueId().toString());

        if(playerLeveling == null && !getLevelingSQLUtils().hasAccount(playerName)) return null;

        if(playerLeveling == null) playerLeveling = new SQLPlayerLeveling(getLevelingSQLUtils().getUUIDPlayerByName(playerName));

        return playerLeveling;
    }

    public void giveLevel(CommandSender sender, IPlayerLeveling playerLeveling, double newLevel) {
        int oldLevel = playerLeveling.getLevel();

        if(newLevel < 0) {
            sender.sendMessage(RED + "Impossible d'attribuer le nouveau niveau ( < 0 )");
            return;
        }

        playerLeveling.setLevel((int) (playerLeveling.getLevel() + newLevel));

        if(!playerLeveling.getPlayer().isOnline()) return;

        Player player = ((PlayerLeveling) playerLeveling).getPlayer();
        TitleUtils title = new TitleUtils();
        title.send(((PlayerLeveling) playerLeveling).getPlayer(), ChatColor.GREEN + "LEVEL UP!", ChatColor.AQUA + String.valueOf(oldLevel) + " \u2794 " + playerLeveling.getLevel(), 1, 2, 1);
        Firework firework = (Firework) player.getWorld().spawnEntity(new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY() + 5.0, player.getLocation().getZ()), EntityType.FIREWORK);
        FireworkMeta fireworkMeta = firework.getFireworkMeta();
        fireworkMeta.setPower(1);
        Color color = Color.fromRGB(50, 50, 255);
        fireworkMeta.addEffect(FireworkEffect.builder().withColor(color).build());
        firework.setFireworkMeta(fireworkMeta);
        firework.detonate();

        for (int i = 0; i < 5; ++i) {
            final Firework fw2 = (Firework)player.getWorld().spawnEntity(new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY() + 5.0, player.getLocation().getZ()), EntityType.FIREWORK);
            fw2.setFireworkMeta(fireworkMeta);
            firework.detonate();
        }

        player.setLevel(playerLeveling.getLevel());
        player.setExp((float) (playerLeveling.getPercentageExperience() / 100));

    }

    public void removeLevel(CommandSender sender, IPlayerLeveling playerLeveling, int level) {
        int newLevel = playerLeveling.getLevel() - level;
        int oldLevel = playerLeveling.getLevel();

        if(newLevel < 0) {
            sender.sendMessage(RED + "Impossible d'attribuer le nouveau niveau ( < 0 )");
            return;
        }

        playerLeveling.setLevel(playerLeveling.getLevel() - level);

        if(!playerLeveling.getPlayer().isOnline()) return;

        Player player = ((PlayerLeveling) playerLeveling).getPlayer();
        TitleUtils title = new TitleUtils();
        title.send(((PlayerLeveling) playerLeveling).getPlayer(), RED + "LEVEL DOWN!", ChatColor.AQUA + String.valueOf(oldLevel) + " \u2794 " + (playerLeveling.getLevel()), 1, 2, 1);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDERDRAGON_GROWL, 5.0f, 5.0f);

        player.setLevel(playerLeveling.getLevel());
        player.setExp((float) (playerLeveling.getPercentageExperience() / 100));


    }


    public void giveExperience(IPlayerLeveling playerLeveling, double experience, boolean needConvertPercentage) {

        if(needConvertPercentage) experience = experience / 100 * 1000;

        int experienceRemaining = (int) (experience - playerLeveling.getExperienceRequire());

        if (playerLeveling.canLevelUp((int) experience)) {
            playerLeveling.setExperience(Math.max(experienceRemaining, 0));
            giveLevel(null, playerLeveling, 1);
            return;
        }
        playerLeveling.setExperience((int) (playerLeveling.getExperience() + experience));

        if(!playerLeveling.getPlayer().isOnline()) return;

        Player player = ((PlayerLeveling) playerLeveling).getPlayer();

        player.setLevel(playerLeveling.getLevel());
        player.setExp((float) (playerLeveling.getPercentageExperience() / 100));

    }

    public void removeExperience(CommandSender sender, IPlayerLeveling playerLeveling, double newExperience, boolean needConvertPercentage) {

        if(needConvertPercentage) newExperience = newExperience / 100 * 1000;
        if(newExperience > playerLeveling.getExperience() && playerLeveling.getLevel() != 0) {
            removeLevel(sender, playerLeveling, 1);
            playerLeveling.setExperience(0);
            return;
        }

        if(playerLeveling.getLevel() == 0 && playerLeveling.getExperience() - newExperience < 0) {
            sender.sendMessage(RED + "L'expérience ne peut pas être inférieur à 0 lorsqu'il est au niveau 0 !");
            return;
        }

        playerLeveling.setExperience((int) (playerLeveling.getExperience() - newExperience));

        if(!playerLeveling.getPlayer().isOnline()) return;

        Player player = ((PlayerLeveling) playerLeveling).getPlayer();

        player.setLevel(playerLeveling.getLevel());
        player.setExp((float) (playerLeveling.getPercentageExperience() / 100));

    }

}
