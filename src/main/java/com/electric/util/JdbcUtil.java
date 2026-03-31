package com.electric.util;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

import com.electric.exception.BizException;
import com.electric.model.response.JdbcResult;

import lombok.extern.slf4j.Slf4j;

/**
 * jdbc工具类
 *
 * @author sunk
 * @date 2026/3/31
 */
@Slf4j
public class JdbcUtil {

    /**已经存在错误编码*/
    public static final String ALREADY_CODE = "42S02";
    public static final String VALUE        = "value";

    /**
     * 获取链接
     *
     * @param url 数据库地址
     * @param user 用户名
     * @param password 密码
     * @return
     * @create  2021年9月14日 上午11:24:06 luochao
     * @history
     */
    public static Connection getConnection(String url, String user, String password) {
        //1、注册JDBC驱动
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //2、获取数据库连接
            Connection connection = DriverManager.getConnection(url, user, password);
            return connection;
        } catch (Exception e) {
            log.error("获取数据库Connection失败,url:{};user{}", url, user, e);
            return null;
        }

    }

    /**
     * 执行查询
     *
     * @param url
     * @param user
     * @param password
     * @param sql
     * @return
     * @create  2021年9月14日 下午1:09:23 luochao
     * @history
     */
    public static <T> List<T> executeQuery(String url, String user, String password, String sql, Class<T> clazz) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection(url, user, password);
            try {
                ps = conn.prepareStatement(sql);
                ps.setQueryTimeout(60);
            } catch (SQLException e) {
                throw new RuntimeException("解析sql异常:" + e.getMessage());
            }
            try {
                rs = ps.executeQuery();
                log.info("执行sql:" + sql + "成功");
            } catch (SQLException e) {
                throw new RuntimeException("executeQuery失败:" + e.getMessage());
            }
            try {
                return convert(rs, clazz);
            } catch (Exception e) {
                log.error("获取ResultSet中数据失败", e);
                throw new RuntimeException("获取ResultSet中数据失败");
            }
        } finally {
            close(rs, ps, conn);
        }
    }

    /**
     * 执行查询,调用方须自己关闭Connection
     *
     * @param <T>
     * @param conn
     * @param sql
     * @param clazz
     * @return
     * @create  2022年7月1日 上午9:25:00 luochao
     * @history
     */
    public static <T> List<T> executeQuery(Connection conn, String sql, Class<T> clazz) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(sql);
            ps.setQueryTimeout(60);
        } catch (SQLException e) {
            throw new RuntimeException("解析sql异常");
        }
        try {
            rs = ps.executeQuery();
            log.info("执行sql:" + sql + "成功");
        } catch (SQLException e) {
            throw new RuntimeException("executeQuery失败" + e.getMessage());
        }
        try {
            return convert(rs, clazz);
        } catch (Exception e) {
            log.error("获取ResultSet中数据失败", e);
            throw new RuntimeException("获取ResultSet中数据失败");
        }

    }

    /**
     * 执行查询,返回List<Map<String, Object>>
     *
     * @param url 地址
     * @param user 用户名
     * @param password 密码
     * @param sql 执行sql
     * @return
     * @create  2022年7月1日 上午9:22:03 luochao
     * @history
     */
    public static List<Map<String, Object>> executeQuery(String url, String user, String password, String sql) {
        return executeQuery(url, user, password, sql, 60);
    }

    /**
     * 执行查询,返回List<Map<String, Object>>
     *
     * @param url 地址
     * @param user 用户名
     * @param password 密码
     * @param sql 执行sql
     * @param  queryTimeout 查询超时时间
     * @return
     * @create  2022年7月1日 上午9:22:03 luochao
     * @history
     */
    public static List<Map<String, Object>> executeQuery(String url, String user, String password, String sql, Integer queryTimeout) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection(url, user, password);
            ps = conn.prepareStatement(sql);
            ps.setQueryTimeout(queryTimeout);
            rs = ps.executeQuery();
            log.info("执行sql:" + sql + "成功");
            return convertList(rs);
        } catch (SQLSyntaxErrorException e1) {
            String sqlState = e1.getSQLState();
            if (ALREADY_CODE.equals(sqlState)) {
                throw new BizException("表不存在");
            }
            log.error("执行sql查询失败,sql:" + sql, e1);
            throw new BizException("请确认sql是否正确");
        } catch (Exception e) {
            log.error("执行sql查询失败,sql:" + sql, e);
            throw new RuntimeException("执行sql查询失败", e);
        } finally {
            close(rs, ps, conn);
        }
    }

    /**
     * 执行修改
     *
     * @param url
     * @param user
     * @param password
     * @param sql
     * @return
     * @create  2021年9月14日 下午1:07:46 luochao
     * @history
     */
    public static JdbcResult executeUpdate(String url, String user, String password, String sql) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection(url, user, password);
            ps = conn.prepareStatement(sql);
            ps.setQueryTimeout(60);
            int executeUpdate = ps.executeUpdate();
            log.info("执行sql:" + sql + "成功");
            if (executeUpdate >= 0) {
                return JdbcResult.success();
            } else {
                return JdbcResult.fail(Integer.valueOf(executeUpdate).toString());
            }
        } catch (SQLSyntaxErrorException e1) {
            String sqlState = e1.getSQLState();
            if (ALREADY_CODE.equals(sqlState)) {
                return JdbcResult.fail("表不存在");
            }
            log.error("执行sql修改失败,sql:" + sql, e1);
            throw new BizException("请确认sql是否正确");
        } catch (Exception e) {
            log.error("执行sql修改失败,sql:" + sql, e);
            throw new RuntimeException("执行sql修改失败", e);
        } finally {
            close(ps, conn);
        }
    }

    /**
     * sql修改，Connection为传入参数，必须要求传入者实现关闭
     *
     * @param conn
     * @param sql
     * @return
     * @create  2022年6月27日 上午10:50:27 luochao
     * @history
     */
    public static JdbcResult executeUpdate(Connection conn, String sql) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setQueryTimeout(60);
            int executeUpdate = ps.executeUpdate();
            log.info("执行sql:" + sql + "成功");
            if (executeUpdate >= 0) {
                return JdbcResult.success(executeUpdate);
            } else {
                return JdbcResult.fail(Integer.valueOf(executeUpdate).toString());
            }
        } catch (SQLSyntaxErrorException e1) {
            String sqlState = e1.getSQLState();
            if (ALREADY_CODE.equals(sqlState)) {
                return JdbcResult.fail("表不存在");
            }
            log.error("执行sql修改失败,sql:" + sql, e1);
            throw new BizException("请确认sql是否正确");
        } catch (Exception e) {
            log.error("执行sql修改失败,sql:" + sql, e);
            throw new RuntimeException("执行sql修改失败", e);
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    log.error("关闭Statement失败", e);
                }
            }
        }

    }

    /**
     * sql执行，Connection为传入参数，必须要求传入者实现关闭
     *
     * @param conn 连接
     * @param sql 要执行的sql
     * @return
     * @create  2022年6月27日 上午10:49:03 luochao
     * @history
     */
    public static JdbcResult execute(Connection conn, String sql) {
        Statement statement = null;
        try {
            statement = conn.createStatement();
            statement.setQueryTimeout(60);
            statement.execute(sql);
            log.info("执行sql:" + sql + "成功");
            return JdbcResult.success();
        } catch (SQLSyntaxErrorException e1) {
            String sqlState = e1.getSQLState();
            if (ALREADY_CODE.equals(sqlState)) {
                return JdbcResult.fail("表不存在");
            }
            log.error("执行sql查询失败,sql:" + sql, e1);
            throw new BizException("请确认sql是否正确");
        } catch (Exception e) {
            log.error("执行sql查询失败sql:" + sql, e);
            throw new RuntimeException("执行sql修改失败", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    log.error("关闭Statement失败", e);
                }
            }
        }
    }

    /**
     * 释放资源
     *
     * @param st
     * @param conn
     * @create  2021年9月14日 上午11:28:48 luochao
     * @history
     */
    public static void close(Statement st, Connection conn) {
        close(null, st, conn);
    }

    /**
     * 释放资源
     *
     * @param rs
     * @param st
     * @param conn
     * @create  2021年9月14日 上午11:28:04 luochao
     * @history
     */
    public static void close(ResultSet rs, Statement st, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                log.error("关闭ResultSet失败", e);
            }
        }
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                log.error("关闭Statement失败", e);
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                log.error("关闭Connection失败", e);
            }
        }
    }

    /**
     *
     *
     * @param <T>
     * @param rs
     * @param clazz
     * @return
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @create  2021年9月14日 下午2:26:03 luochao
     * @history
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> convert(ResultSet rs, Class<T> clazz) throws SQLException, InstantiationException, IllegalAccessException {
        //结果集的元素对象
        ResultSetMetaData rsmd = rs.getMetaData();
        //获取结果集的元素个数
        int colCount = rsmd.getColumnCount();
        //返回结果的列表集合
        List<T> list = new ArrayList<T>();
        //业务对象的属性数组
        Field[] fields = clazz.getDeclaredFields();
        while (rs.next()) {
            //对每一条记录进行操作
            T obj = clazz.newInstance();
            //将每一个字段取出进行赋值
            for (int i = 1; i <= colCount; i++) {
                Object value = rs.getObject(i);
                //寻找该列对应的对象属性
                for (int j = 0; j < fields.length; j++) {
                    Field f = fields[j];
                    //如果匹配进行赋值
                    if (f.getName().equalsIgnoreCase(rsmd.getColumnName(i))) {
                        boolean flag = f.isAccessible();
                        f.setAccessible(true);
                        f.set(obj, value);
                        f.setAccessible(flag);

                    }
                    if (VALUE.equals(f.getName())) {
                        obj = (T) value;
                    }
                }
            }
            list.add(obj);
        }
        return list;
    }

    /**
     * 转List<Map<String, Object>>
     *
     * @param rs
     * @return
     * @create  2022年7月1日 上午9:26:37 luochao
     * @history
     */
    public static List<Map<String, Object>> convertList(ResultSet rs) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            ResultSetMetaData md = rs.getMetaData();
            int columnCount = md.getColumnCount();
            while (rs.next()) {
                Map<String, Object> rowData = new LinkedHashMap<String, Object>();
                for (int i = 1; i <= columnCount; i++) {
                    rowData.put(md.getColumnLabel(i), rs.getString(i));
                }
                list.add(rowData);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    /**
     * 转Map<String, Object>
     *
     * @param rs
     * @return
     * @create  2022年7月1日 上午9:32:14 luochao
     * @history
     */
    public static Map<String, Object> convertMap(ResultSet rs) {
        Map<String, Object> map = new TreeMap<String, Object>();
        try {
            ResultSetMetaData md = rs.getMetaData();
            int columnCount = md.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    map.put(md.getColumnName(i), rs.getObject(i));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return map;
    }
}