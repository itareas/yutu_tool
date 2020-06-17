package com.yutu.utils.database.jdbc;

import com.yutu.entity.ConfigConstants;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhaobc
 * @Date: 2020/3/26 13:15
 * @Description:
 */
public class SqlHelper {
    //定义要使用的变量
    private static Connection conn = null;
    private static PreparedStatement ps = null;
    private static ResultSet rs = null;
    private static CallableStatement cs = null;

    private static String driver = "";
    private static String url = "";
    private static String userName = "";
    private static String password = "";

    public static Connection getConn() {
        return conn;
    }

    public static PreparedStatement getPs() {
        return ps;
    }

    public static ResultSet getRs() {
        return rs;
    }

    public static CallableStatement getCs() {
        return cs;
    }

    /**
     * @Author: zhaobc
     * @Date: 2020/3/26 13:21
     * @Description: 加载驱动，只需要一次
     **/
    static {
        driver = ConfigConstants.MYSQL_DRIVER;
        url = ConfigConstants.MYSQL_URL;
        userName = ConfigConstants.MYSQL_USERNAME;
        password = ConfigConstants.MYSQL_PASSWORD;
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    /**
     * @Author: zhaobc
     * @Date: 2020/3/26 13:21
     * @Description: 得到连接
     **/
    private static Connection getConnection() {
        try {
            conn = DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * @Author: zhaobc
     * @Date: 2020/3/26 13:22
     * @Description: 普通select
     **/
    public static List<Map<String, Object>> executeQuery(String sql, String[] parameters) {
        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            if (parameters != null) {
                for (int i = 0; i < parameters.length; i++) {
                    ps.setString(i + 1, parameters[i]);
                }
            }
            rs = ps.executeQuery();
            //转换成listMap格式数据返回
            ResultSetMetaData md = rs.getMetaData(); //获得结果集结构信息,元数据
            int columnCount = md.getColumnCount();   //获得列数
            while (rs.next()) {
                Map<String, Object> rowData = new HashMap<String, Object>();
                for (int i = 1; i <= columnCount; i++) {
                    rowData.put(md.getColumnName(i), rs.getObject(i));
                }
                listMap.add(rowData);

            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            //关闭资源
            close(rs, cs, conn);
        }
        return listMap;
    }

    /**
     * @Author: zhaobc
     * @Date: 2020/3/26 13:21
     * @Description: update/delete/insert
     * sql格式:UPDATE tablename SET columnn = ? WHERE column = ?
     **/
    public static Integer executeUpdate(String sql, Object[] parameters) {
        int resultIndex = 0;
        try {
            //1.创建一个ps
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            //给？赋值
            if (parameters != null)
                for (int i = 0; i < parameters.length; i++) {
                    ps.setObject(i + 1, parameters[i]);
                }
            // 执行
            resultIndex = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();// 开发阶段
            throw new RuntimeException(e.getMessage());
        } finally {
            //关闭资源
            close(rs, ps, conn);
        }
        return resultIndex;
    }

    /**
     * @Author: zhaobc
     * @Date: 2020/3/26 13:21
     * @Description: 处理多个update/delete/insert
     **/
    public static void executeUpdateMultiParams(String[] sql, String[][] parameters) {
        try {
            //获得连接
            conn = getConnection();
            //可能传多条sql语句
            conn.setAutoCommit(false);
            for (int i = 0; i < sql.length; i++) {
                if (parameters[i] != null) {
                    ps = conn.prepareStatement(sql[i]);
                    for (int j = 0; j < parameters[i].length; j++)
                        ps.setString(j + 1, parameters[i][j]);
                }
                ps.executeUpdate();
            }
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new RuntimeException(e.getMessage());
        } finally {
            //关闭资源
            close(rs, ps, conn);
        }
    }


    /**
     * @Author: zhaobc
     * @Date: 2020/3/26 13:22
     * @Description: 执行存储过程
     **/
    public static void callProc(String sql, String[] parameters) {
        try {
            conn = getConnection();
            cs = conn.prepareCall(sql);
            //给？赋值
            if (parameters != null) {
                for (int i = 0; i < parameters.length; i++)
                    cs.setObject(i + 1, parameters[i]);
            }
            cs.execute();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            //关闭资源
            close(rs, cs, conn);
        }
    }

    /**
     * @Author: zhaobc
     * @Date: 2020/3/26 13:22
     * @Description: 调用带有输入参数且有返回值的存储过程
     **/
    public static CallableStatement callProcInput(String sql, String[] inparameters) {
        try {
            conn = getConnection();
            cs = conn.prepareCall(sql);
            if (inparameters != null)
                for (int i = 0; i < inparameters.length; i++)
                    cs.setObject(i + 1, inparameters[i]);
            cs.execute();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            //关闭资源
            close(rs, cs, conn);
        }
        return cs;
    }

    /**
     * @Author: zhaobc
     * @Date: 2020/3/26 13:22
     * @Description: 调用有返回值的存储过程
     **/
    public static CallableStatement callProcOutput(String sql, Integer[] outparameters) {
        try {
            conn = getConnection();
            cs = conn.prepareCall(sql);
            //给out参数赋值
            if (outparameters != null)
                for (int i = 0; i < outparameters.length; i++)
                    cs.registerOutParameter(i + 1, outparameters[i]);
            cs.execute();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            //关闭资源
            close(rs, cs, conn);
        }
        return cs;
    }

    public static void close(ResultSet rs, Statement ps, Connection conn) {
        if (rs != null)
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        rs = null;
        if (ps != null)
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        ps = null;
        if (conn != null)
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        conn = null;
    }


    public static void main(String[] args) {
        //mysql
//        driver = "com.mysql.cj.jdbc.Driver";
//        url = "jdbc:mysql://localhost:3306/yutu_model?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8&userSSL=false&autoReconnect=true";
//        userName = "root";
//        password = "root";

        //oracle
//        driver = "oracle.jdbc.OracleDriver";
//        url = "jdbc:oracle:thin:@192.168.4.25:1521:ORCL";
//        userName = "REPORTSXYD";
//        password = "REPORTSXYD";

        //sqlserver
//        driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
//        url = "jdbc:sqlserver://127.0.0.1:1433;DatabaseName=EnvBAR_DB";
//        userName = "sa";
//        password = "1234";

        executeQuery("select * from BUS_REVIEWERRO", null);
    }
}
