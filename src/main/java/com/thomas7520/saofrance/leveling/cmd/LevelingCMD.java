package com.thomas7520.saofrance.leveling.cmd;

import com.thomas7520.saofrance.leveling.player.IPlayerLeveling;
import com.thomas7520.saofrance.leveling.player.PlayerLeveling;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.thomas7520.saofrance.leveling.SaoFranceLeveling.getLevelingCMDUtils;
import static com.thomas7520.saofrance.leveling.SaoFranceLeveling.getUtils;
import static org.bukkit.ChatColor.RED;

public class LevelingCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(args.length == 0) {
                PlayerLeveling playerLeveling = getUtils().getPlayersLeveling().get(player.getUniqueId().toString());

                getLevelingCMDUtils().sendInformation(sender, playerLeveling);

                return true;
            }

            if(!sender.hasPermission("saoxp.exp")) {
                sender.sendMessage(RED + "Vous n'avez pas la permission d'executer cette commande.");
                return true;
            }
        }


        if(args.length == 2 && args[0].equalsIgnoreCase("info")) {
            IPlayerLeveling playerLeveling = getLevelingCMDUtils().getIPlayerLeveling(sender, args[1]);

            if(playerLeveling == null) {
                sender.sendMessage(RED + "Le joueur est introuvable !");
                return true;
            }

            getLevelingCMDUtils().sendInformation(sender, playerLeveling);

        }


        if(args.length == 4){
            IPlayerLeveling playerLeveling = getLevelingCMDUtils().getIPlayerLeveling(sender, args[1]);

            if(playerLeveling == null) {
                sender.sendMessage(RED + "Le joueur est introuvable !");
                return true;
            }

            if(!StringUtils.isNumeric(args[3])) {
                sender.sendMessage(RED + "Vous devez spécifier un chiffre entier !");
                return true;
            }

            double number = Double.parseDouble(args[3]);

            if(number < 0) {
                sender.sendMessage(RED + "Vous devez spécifier un chiffre supérieur ou égal à 0 !");
                return true;
            }

            if(args[0].equalsIgnoreCase("give")) {

                if(args[2].equalsIgnoreCase("level")) {

                    getLevelingCMDUtils().giveLevel(sender, playerLeveling, number);

                    return true;

                } else if(args[2].equalsIgnoreCase("p")) {

                    getLevelingCMDUtils().giveExperience(playerLeveling, (int) number, true);

                    return true;
                } else if(args[2].equalsIgnoreCase("exp")) {

                    getLevelingCMDUtils().giveExperience(playerLeveling, (int) number, false);

                    return true;
                }
            }

            if(args[0].equalsIgnoreCase("take")) {

                if(args[2].equalsIgnoreCase("level")) {

                    getLevelingCMDUtils().removeLevel(sender, playerLeveling, (int) number);

                    return true;

                } else if(args[2].equalsIgnoreCase("p")) {

                    getLevelingCMDUtils().removeExperience(sender, playerLeveling, number, true);

                    return true;
                } else if(args[2].equalsIgnoreCase("exp")) {

                    getLevelingCMDUtils().removeExperience(sender, playerLeveling, number, false);

                    return true;
                }
            }
        }
        return false;
    }
}
