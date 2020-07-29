package com.tester.api.utlis;

import com.tester.api.beans.CaseBean;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBData {

    public static Connection conn = JDBCUtil.getConnection();
    public static List<CaseBean> getData(String tableName) throws SQLException {
        String sql = " select * from " + tableName;
        PreparedStatement stat = conn.prepareStatement(sql);
        ResultSet rs = stat.executeQuery();

        List<CaseBean> list = new ArrayList<>();
        while (rs.next()) {
            CaseBean cb = new CaseBean(
                    rs.getInt("id"),
                    rs.getString("desc"),
                    rs.getString("url"),
                    rs.getString("token"),
                    rs.getString("parameterType"),
                    rs.getString("method"),
                    rs.getString("params"),
                    rs.getString("expected"),
                    rs.getString("sql"),
                    rs.getBoolean("isRun"));
            list.add(cb);
        }
        JDBCUtil.close(conn, stat,rs);
        return list;
    }

    public static ResultSet query(String sql) throws SQLException {
        PreparedStatement stat = conn.prepareStatement(sql);
        ResultSet rs = stat.executeQuery();

        return rs;
    }

}
