package com.thomas7520.saofrance.leveling;

import com.thomas7520.saofrance.leveling.utils.LevelingCMDUtils;
import com.thomas7520.saofrance.leveling.utils.LevelingSQLUtils;
import com.thomas7520.saofrance.leveling.utils.LevelingUtils;
import org.bukkit.plugin.java.JavaPlugin;

public final class SaoFranceLeveling extends JavaPlugin {


    private static LevelingUtils levelingUtils;
    private static LevelingSQLUtils levelingSQLUtils;
    private static LevelingCMDUtils levelingCMDUtils;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        levelingUtils = new LevelingUtils(this);
        levelingSQLUtils = new LevelingSQLUtils();
        levelingCMDUtils = new LevelingCMDUtils();
    }

    @Override
    public void onDisable() {
        getUtils().savePlayers();
        getUtils().getSqlConnection().disconnect();
    }

    public static LevelingUtils getUtils() {
        return levelingUtils;
    }

    public static LevelingSQLUtils getLevelingSQLUtils() {
        return levelingSQLUtils;
    }

    public static LevelingCMDUtils getLevelingCMDUtils() {
        return levelingCMDUtils;
    }
}
