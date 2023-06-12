package step.step2;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import zerobase.wifi.service.DbConnection;

public class DataBaseConnection {

    public static void test() {
    	/**
         * 데이터 베이스 연결 테스트
         * 시간 조회
         */
    	
    	String url = "jdbc:mariadb://127.0.0.1:3306/mission1";
    	String dbUserId = "mission1";
    	String dbPassword = "zerobase";
    	
    	try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);

            String sql = " SELECT NOW() ";

            preparedStatement = connection.prepareStatement(sql);

            rs = preparedStatement.executeQuery();

            while (rs.next()) {

                String time = rs.getString(1);

                System.out.println(time);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

    }
}
