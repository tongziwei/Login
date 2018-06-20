package db;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class DBManager {
	// ���ݿ����ӳ���
    public static final String DRIVER = "com.mysql.jdbc.Driver";
    public static final String USER = "root";
    public static final String PASS = "root";
    public static final String URL = "jdbc:mysql://localhost:3306/userdb";
    
   /* private static final String sDropDB = "drop database if exists userdb";
    private static final String sCreateDB = "create database userdb";
    private static final String sUseDB = "use userdb";
    private static final String sDropTb = "drop table if exists user";
    private static final String sCreateTb = "create table user(id int(11) auto_increment not null primary key,username char(20) not null,password char(20) not null)";*/

    // ��̬��Ա��֧�ֵ�̬ģʽ
    private static DBManager per = null;
    private Connection conn = null;
    private Statement stmt = null;

    // ��̬ģʽ-����ģʽ
    private DBManager() {
    }

    public static DBManager createInstance() {
        if (per == null) {
            per = new DBManager();
            per.initDB();
        }
        return per;
    }

    // ��������
    public void initDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // �������ݿ⣬��ȡ���+����
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

    // �ر����ݿ� �رն����ͷž��
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

    // ��ѯ
    public ResultSet executeQuery(String sql) {
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    // ����/ɾ��/�޸�
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
