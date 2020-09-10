package com.thomas7520.saofrance.leveling.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import static com.thomas7520.saofrance.leveling.SaoFranceLeveling.getLevelingSQLUtils;
import static com.thomas7520.saofrance.leveling.SaoFranceLeveling.getUtils;

public class LevelingSQLUtils {

    public boolean hasAccount(UUID uuid) {
        PreparedStatement ps = null;
        boolean exist = false;
        try {
            ps = getUtils().getSqlConnection().getConnection().prepareStatement("SELECT uuid FROM players WHERE uuid = ?");
            ps.setString(1, uuid.toString());
            exist = ps.executeQuery().next();
            ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return exist;
    }

    public boolean hasAccount(String name) {
        PreparedStatement ps = null;
        boolean exist = false;
        try {
            ps = getUtils().getSqlConnection().getConnection().prepareStatement("SELECT name FROM players WHERE name = ?");
            ps.setString(1, name);
            exist = ps.executeQuery().next();
            ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return exist;
    }

    public void createSQLAccount(UUID uuid, String playerName) {
        if(getLevelingSQLUtils().hasAccount(uuid)) return;
        try {
            final PreparedStatement ps = getUtils().getSqlConnection().getConnection().prepareStatement("INSERT INTO players(uuid,name,level,experience) VALUES(?,?,?,?)");
            ps.setString(1, uuid.toString());
            ps.setString(2, playerName);
            ps.setInt(3, 0);
            ps.setInt(4, 0);
            ps.execute();
            ps.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String getUUIDPlayerByName(String name) {
        String uuid = null;

        try {
            PreparedStatement ps = getUtils().getSqlConnection().getConnection().prepareStatement("SELECT uuid FROM players WHERE name = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                uuid = rs.getString(1);
            }
            ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return uuid;
    }
}
