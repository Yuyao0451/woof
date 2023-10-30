package com.woof.util;

import java.sql.*;
import java.util.ResourceBundle;

public class Util {

    private static ResourceBundle bundle = ResourceBundle.getBundle("jdbc");

    private static final String DRIVER = bundle.getString("driver");

    private static final String URL = bundle.getString("url");

    private static final String USER = bundle.getString("user");

    private static final String PASSWORD = bundle.getString("password");


    static {
        try {
            Class.forName(DRIVER);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

    }

    public static Connection getConnection() throws SQLException{
        Connection con = DriverManager.getConnection(URL,USER,PASSWORD);
        return con;
    }

    public static void closeResources(Connection con , PreparedStatement ps , ResultSet rs){
        
        
        if (rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null){
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (con != null){
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}