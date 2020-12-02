package com.test;

import com.sensorsdata.analytics.javasdk.SensorsAnalytics;

import java.sql.*;
import java.util.HashMap;

public class SdkTest {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://debugboxreset3485x1.sa:3305/metadata?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";


    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "work";
    static final String PASS = "b_Jn/pr1Nw";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        SensorsAnalytics sa = null;
        try {
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);
            // 注册神策 SDK
            // 文件落盘的方式发送数据, 文档: https://manual.sensorsdata.cn/sa/latest/java-sdk-1573928.html#id-.JavaSDKv1.13-ConcurrentLoggingConsumer
            // sa = new SensorsAnalytics(new SensorsAnalytics.ConcurrentLoggingConsumer("./access.log"));

            // 网络请求方式发送数据, 文档: https://manual.sensorsdata.cn/sa/latest/java-sdk-1573928.html#id-.JavaSDKv1.13-BatchConsumer
            sa = new SensorsAnalytics(new SensorsAnalytics.BatchConsumer("http://debugboxreset3485x1.sa:8106/sa?project=default", 50, 0, true));
            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // 执行查询
            System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT username, password FROM user";
            ResultSet rs = stmt.executeQuery(sql);

            // 展开结果集数据库
            while (rs.next()) {
                // 通过字段检索
                String username = rs.getString("username");
                String password = rs.getString("password");
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("username", username);
                hashMap.put("password", password);
                // 输出数据
                sa.itemSet("itemTypeTest", "itemIdTest", hashMap);
            }
            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        } catch (Exception e) {
            // 处理 Class.forName 错误
            e.printStackTrace();
        } finally {
            // 关闭神策分析 SDK 所有服务并销毁实例
            assert sa != null;
            sa.shutdown();
            // 关闭资源
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
            }// 什么都不做
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        System.out.println("Goodbye!");
    }
}
