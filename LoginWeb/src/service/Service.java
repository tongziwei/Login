package service;

import java.sql.ResultSet;
import java.sql.SQLException;

import db.DBManager;

public class Service {
	
	public Boolean login(String username, String password) {

        // 获取Sql查询语句
        String logSql = "select * from user where username ='" + username
                + "' and password ='" + password + "'";

        // 获取DB对象
        DBManager sql = DBManager.createInstance();
        sql.connectDB();

        // 操作DB对象
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
    	// 获取Sql查询语句
        String checkSql = "select * from user where username ='" + username + "'";
        // 获取Sql插入语句
        String regSql = "insert into user(username,password) values('"+ username+ "','"+ password+ "') ";

        // 获取DB对象
        DBManager sql = DBManager.createInstance();
        sql.connectDB();
        // 操作DB对象
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
    	// 获取Sql查询语句
        String checkSql = "select * from user where username ='" + username + "'";
     
        // 获取DB对象
        DBManager sql = DBManager.createInstance();
        sql.connectDB();
        // 操作DB对象
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
