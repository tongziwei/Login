package service;

import java.sql.ResultSet;
import java.sql.SQLException;

import db.DBManager;

public class Service {
	
	public Boolean login(String username, String password) {

        // ��ȡSql��ѯ���
        String logSql = "select * from user where username ='" + username
                + "' and password ='" + password + "'";

        // ��ȡDB����
        DBManager sql = DBManager.createInstance();
        sql.connectDB();

        // ����DB����
        try {
            ResultSet rs = sql.executeQuery(logSql);
            if (rs.next()) {
                sql.closeDB();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sql.closeDB();
        return false;
    }

    public Boolean register(String username, String password) {
    	// ��ȡSql��ѯ���
        String checkSql = "select * from user where username ='" + username + "'";
        // ��ȡSql�������
        String regSql = "insert into user(username,password) values('"+ username+ "','"+ password+ "') ";

        // ��ȡDB����
        DBManager sql = DBManager.createInstance();
        sql.connectDB();
        // ����DB����
        try {
            ResultSet rs = sql.executeQuery(checkSql);
            if (rs.next()) {
                sql.closeDB();
                return false;
            }else{
            	int ret = sql.executeUpdate(regSql);
		        if (ret != 0) {
		            sql.closeDB();
		            return true;
		        }
		        sql.closeDB();
		        return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        sql.closeDB();
        return false;
    }
    
    public String findPassword(String username) {
    	String password = null;
    	String error = "username is invalid";
    	// ��ȡSql��ѯ���
        String checkSql = "select * from user where username ='" + username + "'";
     
        // ��ȡDB����
        DBManager sql = DBManager.createInstance();
        sql.connectDB();
        // ����DB����
        try {
            ResultSet rs = sql.executeQuery(checkSql);
            if (rs.next()) {
                password = rs.getString("password");
                sql.closeDB();
                return password;
            }else{
            	return error;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        sql.closeDB();
        return password;

    }
}
