package com.thomas7520.saofrance.leveling.utils;

import com.thomas7520.saofrance.leveling.SaoFranceLeveling;
import com.thomas7520.saofrance.leveling.cmd.LevelingCMD;
import com.thomas7520.saofrance.leveling.event.LevelingEvent;
import com.thomas7520.saofrance.leveling.player.PlayerLeveling;
import com.thomas7520.saofrance.leveling.sql.SQLConnection;
import org.bukkit.configuration.Configuration;

import java.util.HashMap;

public class LevelingUtils {

    private final HashMap<String, PlayerLeveling> playersLeveling = new HashMap<>();
    private final SaoFranceLeveling main;
    private Configuration configuration;
    private SQLConnection sqlConnection;

    public LevelingUtils(SaoFranceLeveling saoFranceLeveling) {
        this.main = saoFranceLeveling;
        setConfiguration(main.getConfig());
        this.setupSQLConnexion();
        this.registerListeners();
        this.registerCommands();
    }

    private void setupSQLConnexion() {
        String host = getConfiguration().getString("host");
        String database = getConfiguration().getString("database");
        String user = getConfiguration().getString("user");
        String password = getConfiguration().getString("password");
        SQLConnection sqlConnection = new SQLConnection("jdbc:mysql://", host, database, user, password);
        sqlConnection.connect();
        setSqlConnection(sqlConnection);
        if(getSqlConnection().isConnected()) {
            System.out.println("The SQL connection has been successfully completed");
            getSqlConnection().tryCreateTable();
            return;
        }
        System.out.println("The SQL connection has failed, please review the configuration file.");
    }

    private void registerListeners() {
        main.getServer().getPluginManager().registerEvents(new LevelingEvent(), main);
    }

    private void registerCommands() {
        main.getCommand("saoxp").setExecutor(new LevelingCMD());
    }


    public HashMap<String, PlayerLeveling> getPlayersLeveling() {
        return playersLeveling;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public void setSqlConnection(SQLConnection sqlConnection) {
        this.sqlConnection = sqlConnection;
    }

    public SQLConnection getSqlConnection() {
        return sqlConnection;
    }

    public void savePlayer(PlayerLeveling playerLeveling) {
        int level = playerLeveling.getLevel();
        int experience = playerLeveling.getExperience();

        playerLeveling.getPlayerSQL().setLevel(level);
        playerLeveling.getPlayerSQL().setExperience(experience);
    }

    public void savePlayers() {
        playersLeveling.forEach((key, value) -> savePlayer(value));
    }



}
