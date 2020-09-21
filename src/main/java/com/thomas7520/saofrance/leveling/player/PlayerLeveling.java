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
        return 1000 - this.experience;
    }

    @Override
    public double getPercentageExperience() {
        return (double) this.experience / 1000 * 100;
    }

    @Override
    public double getPercentageExperienceRequire() {
        return (double) getExperienceRequire() / 1000 * 100;
    }

    @Override
    public boolean canLevelUp(int experienceAcquire) {
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

    @Override
    public String toString() {
        return "PlayerLeveling{" +
                "uuid='" + uuid + '\'' +
                ", level=" + level +
                ", experience=" + experience +
                ", experiencePercentage=" + getPercentageExperience() +
                ", experienceRequire=" + getExperienceRequire() +
                ", experienceRequirePercentage=" + getPercentageExperienceRequire() +
                '}';
    }
}
