package com.thomas7520.saofrance.leveling.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerLeveling implements IPlayerLeveling {

    private String uuid;
    private int level;
    private int experience;

    public PlayerLeveling(String playerUUID) {
        this.uuid = playerUUID;
        this.level = getPlayerSQL().getLevel();
        this.experience = getPlayerSQL().getExperience();
    }

    @Override
    public void setLevel(int newLevel) {
        this.level = newLevel;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public void setExperience(int newExperience) {
        this.experience = newExperience;
    }

    @Override
    public int getExperience() {
        return this.experience;
    }

    @Override
    public int getExperienceRequire() {
        return this.experience - 1000;
    }

    @Override
    public int getPercentageExperience() {
        return this.experience * 100 / 1000;
    }

    @Override
    public int getPercentageExperienceRequire() {
        return getExperienceRequire() * 100 / 1000;
    }

    @Override
    public boolean canLevelUp(int experienceAcquire) {
        System.out.println(experienceAcquire + this.experience);
        return experienceAcquire + this.experience >= 1000;
    }

    @Override
    public Player getPlayer() {
        return Bukkit.getPlayer(UUID.fromString(uuid));
    }

    @Override
    public SQLPlayerLeveling getPlayerSQL() {
        return new SQLPlayerLeveling(getPlayer().getUniqueId().toString());
    }
}
