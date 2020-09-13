package com.thomas7520.saofrance.leveling.utils;

import org.bukkit.scheduler.BukkitRunnable;

import static com.thomas7520.saofrance.leveling.SaoFranceLeveling.getUtils;

public class LevelingTaskSaving extends BukkitRunnable {

    @Override
    public void run() {
        System.out.println("[LEVELING-SAVE] Sauvegarde des joueurs...");
        getUtils().savePlayers();
    }
}
