package db;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class DBManager {
	// 数据库连接常量
    public static final String DRIVER = "com.mysql.jdbc.Driver";
    public static final String USER = "root";
    public static final String PASS = "root";
    public static final String URL = "jdbc:mysql://localhost:3306/userdb";
    
   /* private static final String sDropDB = "drop database if exists userdb";
    private static final String sCreateDB = "create database userdb";
    private static final String sUseDB = "use userdb";
    private static final String sDropTb = "drop table if exists user";
    private static final String sCreateTb = "create table user(id int(11) auto_increment not null primary key,username char(20) not null,password char(20) not null)";*/

    // 静态成员，支持单态模式
    private static DBManager per = null;
    private Connection conn = null;
    private Statement stmt = null;

    // 单态模式-懒汉模式
    private DBManager() {
    }

    public static DBManager createInstance() {
        if (per == null) {
            per = new DBManager();
            per.initDB();
        }
        return per;
    }

    // 加载驱动
    public void initDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 连接数据库，获取句柄+对象
    public void connectDB() {
        System.out.println("Connecting to database...");
        try {
            conn = (Connection) DriverManager.getConnection(URL, USER, PASS);
            stmt = (Statement) conn.createStatement();
       /*     stmt.execute(sDropDB);
            stmt.execute(sCreateDB);
            stmt.execute(sUseDB);
            stmt.execute(sDropTb);
            stmt.execute(sCreateTb);*/
       
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("SqlManager:Connect to database successful.");
    }

    // 关闭数据库 关闭对象，释放句柄
    public void closeDB() {
        System.out.println("Close connection to database..");
        try {
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Close connection successful");
    }

    // 查询
    public ResultSet executeQuery(String sql) {
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    // 增添/删除/修改
    public int executeUpdate(String sql) {
        int ret = 0;
        try {
            ret = stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

}
