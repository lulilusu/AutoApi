package com.tester.api.utlis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class JDBCUtil {
    private static String driverName = null;
    private static  String url = null;
    private static  String user = null;
    private static  String pwd = null;

    static {
        try {
            Properties prop = new Properties();
            String path = ".\\src\\main\\resources\\db.properties";

            FileInputStream in = new FileInputStream(new File(path));
            prop.load(in);
            System.out.println(path);
            System.out.println(prop);

            driverName = prop.getProperty("DRIVERNAME");
            url = prop.getProperty("URL");
            user = prop.getProperty("USER");
            pwd = prop.getProperty("PWD");

            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            System.err.println("加载驱动失败");
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.err.println("文件加载失败");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("配置文件读取失败");
            e.printStackTrace();
        }
    }
    public static Connection getConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, pwd);
        } catch (SQLException e) {
            System.err.println("获取连接失败");
            e.printStackTrace();
        }
        return connection;
    }

    public static void close(Connection con, Statement stat, ResultSet rs){
        if(con!=null){
            try{
                con.close();
            }catch(SQLException ex){}
        }

        if(stat!=null){
            try{
                stat.close();
            }catch(SQLException ex){}
        }

        if(rs!=null){
            try{
                rs.close();
            }catch(SQLException ex){}
        }
    }
}
