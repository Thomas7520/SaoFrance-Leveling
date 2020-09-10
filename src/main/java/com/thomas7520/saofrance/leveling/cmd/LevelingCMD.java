package com.thomas7520.saofrance.leveling.cmd;

import com.thomas7520.saofrance.leveling.player.IPlayerLeveling;
import com.thomas7520.saofrance.leveling.player.PlayerLeveling;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.thomas7520.saofrance.leveling.SaoFranceLeveling.getLevelingCMDUtils;
import static com.thomas7520.saofrance.leveling.SaoFranceLeveling.getUtils;
import static org.bukkit.Color.RED;

public class LevelingCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if(args.length == 0 && sender instanceof Player) {
            Player player = (Player) sender;
            PlayerLeveling playerLeveling = getUtils().getPlayersLeveling().get(player.getUniqueId().toString());

            getLevelingCMDUtils().sendInformation(sender, playerLeveling);

            return true;
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

            if(!NumberUtils.isNumber(args[3])) {
                sender.sendMessage(RED + "Vous devez spécifier un chiffre !");
                return true;
            }

            int number = Integer.parseInt(args[3]);

            if(number < 0) {
                sender.sendMessage(RED + "Vous devez spécifier un chiffre !");
                return true;
            }

            if(args[0].equalsIgnoreCase("give")) {

                if(args[2].equalsIgnoreCase("level")) {

                    getLevelingCMDUtils().giveLevel(sender, playerLeveling, number);

                    return true;

                } else if(args[2].equalsIgnoreCase("experience")) {

                    number = Integer.parseInt(args[3]) * 1000 / 100;
                    getLevelingCMDUtils().giveExperience(playerLeveling, number);

                    return true;
                }
            }

            if(args[0].equalsIgnoreCase("remove")) {

                if(args[2].equalsIgnoreCase("level")) {

                    getLevelingCMDUtils().removeLevel(sender, playerLeveling, number);

                    return true;

                } else if(args[1].equalsIgnoreCase("experience")) {

                    number = Integer.parseInt(args[3]) * 1000 / 100;
                    getLevelingCMDUtils().giveExperience(playerLeveling, number);

                    return true;
                }
            }
        }
        return false;
    }
}
