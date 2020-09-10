package com.thomas7520.saofrance.leveling.player;

import com.thomas7520.saofrance.leveling.SaoFranceLeveling;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SQLPlayerLeveling implements IPlayerLeveling{

    private String uuid;

    public SQLPlayerLeveling(String playerUUID) {
        this.uuid = playerUUID;
    }

    @Override
    public void setLevel(int newLevel) {
        try {
            final PreparedStatement ps = SaoFranceLeveling.getUtils().getSqlConnection().getConnection().prepareStatement("UPDATE players SET level = ? WHERE uuid = ?");
            ps.setInt(1, newLevel);
            ps.setString(2, uuid);
            ps.execute();
            ps.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getLevel() {
        int level = 0;
        try {
            PreparedStatement ps = SaoFranceLeveling.getUtils().getSqlConnection().getConnection().prepareStatement("SELECT level FROM players WHERE uuid = ?");
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                level = rs.getInt("level");
            }
            ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return level;
    }

    @Override
    public void setExperience(int newExperience) {
        try {
            final PreparedStatement ps = SaoFranceLeveling.getUtils().getSqlConnection().getConnection().prepareStatement("UPDATE players SET experience = ? WHERE uuid = ?");
            ps.setInt(1, newExperience);
            ps.setString(2, uuid);

            ps.execute();
            ps.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getExperience() {
        int experience = 0;
        try {
            PreparedStatement ps = SaoFranceLeveling.getUtils().getSqlConnection().getConnection().prepareStatement("SELECT experience FROM players WHERE uuid = ?");
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                experience = rs.getInt("experience");
            }
            ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return experience;
    }

    @Override
    public int getExperienceRequire() {
        return getExperience() - 1000;
    }

    @Override
    public int getPercentageExperience() {
        return getExperience() * 100 / 1000;
    }

    @Override
    public int getPercentageExperienceRequire() {
        return getExperienceRequire() * 100 / 1000;
    }

    @Override
    public boolean canLevelUp(int experienceAcquire) {
        return experienceAcquire + this.getExperience() >= 1000;
    }

    @Override
    public SQLPlayerLeveling getPlayerSQL() {
        throw new UnsupportedOperationException("You can't get this method in the object SQLPlayerLeveling !");
    }

    @Override
    public OfflinePlayer getPlayer() {
        return Bukkit.getOfflinePlayer(UUID.fromString(uuid));
    }
}
