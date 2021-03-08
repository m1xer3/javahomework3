package ru.danilsibgatullin.homeworklesson2.BroadCastChat.serverside.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public final class ConnectDB {

    static final String DRIVER = "com.mysql.jdbc.Driver";
    static final String DB = "jdbc:mysql://127.0.0.1:3306/lesson";
    static final String USER = "root";
    static final String PASSWORD = "root";

    private static ConnectDB connectDB;
    private static Connection conn = null;


    public static Connection getConnectDB () throws SQLException, ClassNotFoundException {
        if(conn == null){
            conn = createConnect();
        }
        return conn;
    }

    private static Connection createConnect() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        conn = DriverManager.getConnection(DB,USER,PASSWORD);
        return conn;
    }

    private ConnectDB(){
    };
}
