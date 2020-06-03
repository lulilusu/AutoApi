package com.tester.api.utlis;

import com.tester.api.beans.CaseBean;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBData {

    public static List<CaseBean> getData(String tableName) throws SQLException {
        String sql = " select * from " + tableName;
        Connection conn = JDBCUtil.getConnection();
        PreparedStatement stat = conn.prepareStatement(sql);
        ResultSet rs = stat.executeQuery();

        List<CaseBean> list = new ArrayList<>();
        while (rs.next()) {
            CaseBean cb = new CaseBean(
                    rs.getInt("id"),
                    rs.getString("desc"),
                    rs.getString("url"),
                    rs.getString("token"),
                    rs.getString("formData"),
                    rs.getString("method"),
                    rs.getString("params"),
                    rs.getString("expected"),
                    rs.getBoolean("isRun"));
            list.add(cb);
        }

        JDBCUtil.close(conn, stat,rs);
        return list;
    }
}
