package com.goertek.login.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by clara.tong on 2018/5/28.
 */

public class WebService {
    private static String IP = "172.31.115.26:8080"; //服务器IP

    // 通过Get方式获取HTTP服务器数据
    public static String executeHttpGet(String username, String password) {

        HttpURLConnection conn = null;
        InputStream in = null;

        try {
            // 用户名 密码
            // URL 地址
            String path = "http://" + IP + "/LoginWeb/Login";
            path = path + "?username=" + username + "&password=" + password;
            try{
                conn = (HttpURLConnection) new URL(path).openConnection();
                conn.setConnectTimeout(3000); // 设置超时时间
                conn.setReadTimeout(3000);
                conn.setDoInput(true);
                conn.setRequestMethod("GET"); // 设置获取信息方式
                conn.setRequestProperty("Charset", "UTF-8"); // 设置接收数据编码格式
                in = conn.getInputStream();
                return parseInfo(in);
            }catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 意外退出时进行连接关闭保护
            if (conn != null) {
                conn.disconnect();
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    // 将输入流转化为 String 型
    private static String parseInfo(InputStream inStream) throws Exception {
        byte[] data = read(inStream);
        // 转化为字符串
        return new String(data, "UTF-8");
    }

    // 将输入流转化为byte型
    public static byte[] read(InputStream inStream) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }
        inStream.close();
        return outputStream.toByteArray();
    }

    // 通过Get方式获取HTTP服务器数据
    public static String executeHttpPost(String username,String password){
        HttpURLConnection connection = null;
        InputStream in = null;

        try{
            String Url = "http://" + IP + "/LoginWeb/Register";
            try {
                URL url = new URL(Url);
                connection = (HttpURLConnection)url.openConnection();

                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                connection.setReadTimeout(8000);//传递数据超时

                connection.setUseCaches(false);
                connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

                connection.connect();

                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                String data = "username=" + URLEncoder.encode(username,"UTF-8") + "&password=" + URLEncoder.encode(password,"UTF-8");
                out.writeBytes(data);
                out.flush();
                out.close();

                int resultCode = connection.getResponseCode();
                if(HttpURLConnection.HTTP_OK == resultCode) {
                    in = connection.getInputStream();
                    return parseInfo(in);
                }
                return null;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //意外退出时，连接关闭保护
            if(connection != null){
                connection.disconnect();
            }
            if(in != null){
                try{
                    in.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    // 通过Get方式获取HTTP服务器数据
    public static String executeHttpGet(String username) {

        HttpURLConnection conn = null;
        InputStream in = null;

        try {
            // 用户名 密码
            // URL 地址
            String path = "http://" + IP + "/LoginWeb/FindPassword";
            path = path + "?username=" + username;
            try{
                conn = (HttpURLConnection) new URL(path).openConnection();
                conn.setConnectTimeout(3000); // 设置超时时间
                conn.setReadTimeout(3000);
                conn.setDoInput(true);
                conn.setRequestMethod("GET"); // 设置获取信息方式
                conn.setRequestProperty("Charset", "UTF-8"); // 设置接收数据编码格式
                in = conn.getInputStream();
                return parseInfo(in);
            }catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 意外退出时进行连接关闭保护
            if (conn != null) {
                conn.disconnect();
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }
}
