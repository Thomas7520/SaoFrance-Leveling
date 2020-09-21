package com.thomas7520.saofrance.leveling.player;

import org.bukkit.OfflinePlayer;

public interface IPlayerLeveling {


    void setLevel(int level);

    int getLevel();

    void setExperience(int experience);

    int getExperienceRequire();

    int getExperience();

    double getPercentageExperience();

    double getPercentageExperienceRequire();

    boolean canLevelUp(int experienceAcquire);

    SQLPlayerLeveling getPlayerSQL();

    OfflinePlayer getPlayer();

}
