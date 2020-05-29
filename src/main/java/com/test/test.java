package com.test;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class test {
    /**
     * 获取昨天日期字符串
     *
     * @param format 格式(例如:yyyy-MM-dd HH:mm:ss)
     * @return
     */
    public static String getYesterdayStr(String format) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return new SimpleDateFormat(format).format(cal.getTime());
    }
    private static String driverName = "org.apache.hive.jdbc.HiveDriver";

    public static void main(String[] args) throws SQLException {

        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // jdbc 地址, 使用手册: https://manual.sensorsdata.cn/sa/latest/jdbc-1573831.html
//        String url = "jdbc:hive2://106.52.216.152:21050/rawdata;auth=noSasl";
//        String url = "jdbc:hive2://sensorsdata.cantonfair.org.cn:21050/rawdata;auth=noSasl";
        String url = "jdbc:hive2://106.52.191.69:21050/rawdata;auth=noSasl";
//        String url = "jdbc:hive2://debugboxcreate1850x1.sa:21050/rawdata;auth=noSasl";
        Connection con = DriverManager.getConnection(url, "sa_cluster", null);
        Statement stmt = con.createStatement();
        String date = getYesterdayStr("yyyy-MM-dd");
        // 需要查询的项目名
        String project = "default";
        // 展品推荐曝光
//        String sql = String.format("select * from events where event = 'product_exposure' and date = '%s' /*SA(%s)*/;", date, project);
        String sql = String.format("select count(1) as num from events  /*SA(%s)*/;", date, project);

        // 点击及时沟通
        // String sql = String.format("select * from events where event = 'click_contact' and date = '%s' and is_recommend = 1 /*SA(%s)*/;", date, project);

        // 发送预约洽谈
        // String sql = String.format("select * from events where event = 'send_negotiation' and date = '%s' and is_recommend = 1 /*SA(%s)*/;", date, project);

        // 提交意向订单
        // String sql = String.format("select * from events where event = 'submit_order' and date = '%s' and is_recommend = 1 /*SA(%s)*/;", date, project);

        ResultSet res = stmt.executeQuery(sql);
        while (res.next()) {
            System.out.println(res.getString("num"));
//            System.out.println(res.getString("product_name"));
        }
    }
}
