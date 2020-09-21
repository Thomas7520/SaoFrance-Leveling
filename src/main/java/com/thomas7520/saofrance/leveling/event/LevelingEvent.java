package com.thomas7520.saofrance.leveling.event;

import com.thomas7520.saofrance.leveling.player.PlayerLeveling;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

import static com.thomas7520.saofrance.leveling.SaoFranceLeveling.getLevelingSQLUtils;
import static com.thomas7520.saofrance.leveling.SaoFranceLeveling.getUtils;

public class LevelingEvent implements Listener {



    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if(!getLevelingSQLUtils().hasAccount(uuid)) getLevelingSQLUtils().createSQLAccount(uuid, player.getName());

        getUtils().getPlayersLeveling().put(uuid.toString(), new PlayerLeveling(uuid.toString()));

        PlayerLeveling playerLeveling = getUtils().getPlayersLeveling().get(uuid.toString());

        player.setLevel(playerLeveling.getLevel());
        player.setExp((float) (playerLeveling.getPercentageExperience() / 100));
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();

        PlayerLeveling playerLeveling = getUtils().getPlayersLeveling().remove(uuid);

        if(playerLeveling == null) return;

        getUtils().savePlayer(playerLeveling);

    }

}
