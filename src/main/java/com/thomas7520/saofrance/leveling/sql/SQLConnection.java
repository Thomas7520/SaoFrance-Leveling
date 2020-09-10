package com.thomas7520.saofrance.leveling.sql;

import java.sql.*;

public class SQLConnection {

    private Connection connection;
    private final String urlBase;
    private final String host;
    private final String database;
    private final String user;
    private final String password;

    public SQLConnection(String urlbase, String host, String database, String user, String password) {
        this.urlBase = urlbase;
        this.host = host;
        this.database = database;
        this.user = user;
        this.password = password;
    }

    public void connect() {
        if (!this.isConnected()) {
            try {
                this.connection = DriverManager.getConnection(this.urlBase + this.host + "/" + this.database, this.user, this.password);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void disconnect() {
        if (this.isConnected()) {
            try {
                this.connection.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isConnected() {
        return this.connection != null;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void tryCreateTable() {
        try {
            final PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS players(uuid varchar(255), name varchar(255), level int(10), experience int(10))");
            ps.execute();
            ps.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
