package zerobase.wifi.service;

import zerobase.wifi.dto.PosHistoryDto;
import zerobase.wifi.dto.WifiInfoDto;
import zerobase.wifi.model.WifiInfoDetailModel;
import zerobase.wifi.model.WifiInfoModel;
import step.step1.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;


public class WifiService {

	/**
     * 저장해야할 와이파이 총 갯수
     */
    public static int getTotalWifi() {

    	//OpenApi test = new OpenApi();
    	String result = OpenApi.get(1, 1);

    	JsonParser Parser = new JsonParser();
    	JsonObject jsonObject1 = (JsonObject) Parser.parse(result);
    	JsonObject jsonObject2 = jsonObject1.get("TbPublicWifiInfo").getAsJsonObject();

    	return jsonObject2.get("list_total_count").getAsInt();
    }
	
	/**
     * DB에 저장된 와이파이 정보 전체 삭제
     */
	public static void dbAllDelete() {

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

            String sql = " TRUNCATE wifi ";

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
	
    /**
     * 오픈 api로 받아온 와이파이 정보를 저장
     */
	public static void addWifi() {
		
    	// 테이블 내용 전체 삭제
    	dbAllDelete();

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
                
                // 전체 와이파이 목록, db 추가 횟수
                int total = getTotalWifi();
                int repeat = total / 1000;
            	int remain = total - (repeat * 1000);
            	
            	int start = 1;
            	int end = 1000;
                
                String sql = " INSERT INTO wifi (X_SWIFI_MGR_NO, X_SWIFI_WRDOFC, X_SWIFI_MAIN_NM, "
                		+ "	X_SWIFI_ADRES1, X_SWIFI_ADRES2, X_SWIFI_INSTL_FLOOR, "
                		+ "	X_SWIFI_INSTL_TY, X_SWIFI_INSTL_MBY, X_SWIFI_SVC_SE, "
                		+ "	X_SWIFI_CMCWR, X_SWIFI_CNSTC_YEAR, X_SWIFI_INOUT_DOOR, "
                		+ "	X_SWIFI_REMARS3, LAT, LNT, WORK_DTTM) "
                		+ " VALUES (?, ?, ?, "
                		+ "	?, ?, ?, "
                		+ "	?, ?, ?, "
                		+ "	?, ?, ?, "
                		+ "	?, ?, ?, ?) ";
          
            	
            	for (int i = 1; i <= repeat + 1; i++) {
            		
            		if (end > repeat * 1000) {
                    	end = repeat * 1000 + remain;
                    }
            		
            		// 1000개씩 증가 > 조회하며 작업
            		String result = OpenApi.get(start, end);
                    
                    // json
                 	JsonParser Parser = new JsonParser();
                    JsonObject jsonObject1 = (JsonObject) Parser.parse(result);
                    JsonObject jsonObject2 = jsonObject1.get("TbPublicWifiInfo").getAsJsonObject();
                    JsonArray jsonArray = jsonObject2.getAsJsonArray("row");
                    
                    for(int j = 0; j < jsonArray.size(); j++) {
                    	JsonObject object = (JsonObject) jsonArray.get(j);
            			
            			String X_SWIFI_MGR_NO = object.get("X_SWIFI_MGR_NO").getAsString();
            			String X_SWIFI_WRDOFC = object.get("X_SWIFI_WRDOFC").getAsString();
            			String X_SWIFI_MAIN_NM = object.get("X_SWIFI_MAIN_NM").getAsString();
            			String X_SWIFI_ADRES1 = object.get("X_SWIFI_ADRES1").getAsString();
            			String X_SWIFI_ADRES2 = object.get("X_SWIFI_ADRES2").getAsString();
            			String X_SWIFI_INSTL_FLOOR = object.get("X_SWIFI_INSTL_FLOOR").getAsString();
            			String X_SWIFI_INSTL_TY = object.get("X_SWIFI_INSTL_TY").getAsString();
            			String X_SWIFI_INSTL_MBY = object.get("X_SWIFI_INSTL_MBY").getAsString();
            			String X_SWIFI_SVC_SE = object.get("X_SWIFI_SVC_SE").getAsString();
            			String X_SWIFI_CMCWR = object.get("X_SWIFI_CMCWR").getAsString();
            			String X_SWIFI_CNSTC_YEAR = object.get("X_SWIFI_CNSTC_YEAR").getAsString();
            			String X_SWIFI_INOUT_DOOR = object.get("X_SWIFI_INOUT_DOOR").getAsString();
            			String X_SWIFI_REMARS3 = object.get("X_SWIFI_REMARS3").getAsString();
            			double LAT = object.get("LAT").getAsDouble();
            			double LNT = object.get("LNT").getAsDouble();
            			String WORK_DTTM = object.get("WORK_DTTM").getAsString();
            			
            			preparedStatement = connection.prepareStatement(sql);
            		    
                        preparedStatement.setString(1, X_SWIFI_MGR_NO);
                        preparedStatement.setString(2, X_SWIFI_WRDOFC);
                        preparedStatement.setString(3, X_SWIFI_MAIN_NM);
                        preparedStatement.setString(4, X_SWIFI_ADRES1);
                        preparedStatement.setString(5, X_SWIFI_ADRES2);
                        preparedStatement.setString(6, X_SWIFI_INSTL_FLOOR);
                        preparedStatement.setString(7, X_SWIFI_INSTL_TY);
                        preparedStatement.setString(8, X_SWIFI_INSTL_MBY);
                        preparedStatement.setString(9, X_SWIFI_SVC_SE);
                        preparedStatement.setString(10, X_SWIFI_CMCWR);
                        preparedStatement.setString(11, X_SWIFI_CNSTC_YEAR);
                        preparedStatement.setString(12, X_SWIFI_INOUT_DOOR);
                        preparedStatement.setString(13, X_SWIFI_REMARS3);
                        preparedStatement.setDouble(14, LAT);
                        preparedStatement.setDouble(15, LNT);
                        preparedStatement.setString(16, WORK_DTTM);

                        int affected = preparedStatement.executeUpdate();
                        
                        if (affected > 0) {
                            //System.out.println("저장 성공");
                        } else {
                            //System.out.println("저장 실패");
                        }

                    }
                    
                    start += 1000;
                    end += 1000;
                    		
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
	
	/**
     * 와이파이 검색 기록(history) 저장
     */
	public static void addHistory(PosHistoryDto historyDto) {

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

            String sql = " INSERT INTO history (LAT, LNT, WORK_DTTM)\n"
            		+ " VALUES (?, ?, CURDATE()); ";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, historyDto.getLAT());
            preparedStatement.setDouble(2, historyDto.getLNT());

            int affected = preparedStatement.executeUpdate();

            if (affected > 0) {
                System.out.println("저장 성공");
            } else {
                System.out.println("저장 실패");
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

    
    /**
     * 위치 검색기록 목록을 리턴
     */
    public static List<PosHistoryDto> getHistoryList() {
    	
    	List<PosHistoryDto> list = new ArrayList<>();
    	
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

            String sql = " SELECT id, lat, lnt, work_dttm "
            		+ " FROM history "
            		+ "	ORDER BY id DESC; ";

            preparedStatement = connection.prepareStatement(sql);

            rs = preparedStatement.executeQuery();

            while (rs.next()) {

            	String ID = rs.getString("ID");    			
    			double LAT = rs.getDouble("LAT");
    			double LNT = rs.getDouble("LNT");
    			String WORK_DTTM = rs.getString("WORK_DTTM");
    			
    			PosHistoryDto historyDto = new PosHistoryDto();
    			historyDto.setID(ID);
    			historyDto.setLAT(LAT);
    			historyDto.setLNT(LNT);
    			historyDto.setWORK_DTTM(WORK_DTTM);
    			
    			list.add(historyDto);

                System.out.println(ID);

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

        return list;
    }
    
    /**
     * 위치 검색기록 목록을 리턴 (20개)
     */
    public static List<WifiInfoDto> getDisList(double lat, double lnt) {
    	
    	List<WifiInfoDto> list = new ArrayList<>();
    	
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

            String sql = " SELECT "
            		+ "	ROUND( "
            		+ "	(6371 * ACOS( "
            		+ "	COS(RADIANS(?)) * COS(RADIANS(km.LAT)) * COS(RADIANS(km.LNT) - RADIANS(?)) + "
            		+ "	SIN(RADIANS(?)) * SIN(RADIANS(km.LAT)) "
            		+ "	)), "
            		+ "	4 "
            		+ "	) AS distance, "
            		+ "	km.* "
            		+ "	FROM wifi km "
            		+ "	ORDER BY distance "
            		+ "	LIMIT 0, 20; ";

            preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setDouble(1, lat);
            preparedStatement.setDouble(2, lnt);
            preparedStatement.setDouble(3, lat);

            rs = preparedStatement.executeQuery();

            while (rs.next()) {

            	String distance = rs.getString("distance");
            	String X_SWIFI_MGR_NO = rs.getString("X_SWIFI_MGR_NO");
    			String X_SWIFI_WRDOFC = rs.getString("X_SWIFI_WRDOFC");
    			String X_SWIFI_MAIN_NM = rs.getString("X_SWIFI_MAIN_NM");
    			String X_SWIFI_ADRES1 = rs.getString("X_SWIFI_ADRES1");
    			String X_SWIFI_ADRES2 = rs.getString("X_SWIFI_ADRES2");
    			String X_SWIFI_INSTL_FLOOR = rs.getString("X_SWIFI_INSTL_FLOOR");
    			String X_SWIFI_INSTL_TY = rs.getString("X_SWIFI_INSTL_TY");
    			String X_SWIFI_INSTL_MBY = rs.getString("X_SWIFI_INSTL_MBY");
    			String X_SWIFI_SVC_SE = rs.getString("X_SWIFI_SVC_SE");
    			String X_SWIFI_CMCWR = rs.getString("X_SWIFI_CMCWR");
    			String X_SWIFI_CNSTC_YEAR = rs.getString("X_SWIFI_CNSTC_YEAR");
    			String X_SWIFI_INOUT_DOOR = rs.getString("X_SWIFI_INOUT_DOOR");
    			String X_SWIFI_REMARS3 = rs.getString("X_SWIFI_REMARS3");
    			double LAT = rs.getDouble("LAT");
    			double LNT = rs.getDouble("LNT");
    			String WORK_DTTM = rs.getString("WORK_DTTM");
    			
    			WifiInfoDto wifiDto = new WifiInfoDto();
    			wifiDto.setDistance(distance);
    			wifiDto.setX_SWIFI_MGR_NO(X_SWIFI_MGR_NO);
    			wifiDto.setX_SWIFI_WRDOFC(X_SWIFI_WRDOFC);
    			wifiDto.setX_SWIFI_MAIN_NM(X_SWIFI_MAIN_NM);
    			wifiDto.setX_SWIFI_ADRES1(X_SWIFI_ADRES1);
    			wifiDto.setX_SWIFI_ADRES2(X_SWIFI_ADRES2);
    			wifiDto.setX_SWIFI_INSTL_FLOOR(X_SWIFI_INSTL_FLOOR);
    			wifiDto.setX_SWIFI_INSTL_TY(X_SWIFI_INSTL_TY);
    			wifiDto.setX_SWIFI_INSTL_MBY(X_SWIFI_INSTL_MBY);
    			wifiDto.setX_SWIFI_SVC_SE(X_SWIFI_SVC_SE);
    			wifiDto.setX_SWIFI_CMCWR(X_SWIFI_CMCWR);
    			wifiDto.setX_SWIFI_CNSTC_YEAR(X_SWIFI_CNSTC_YEAR);
    			wifiDto.setX_SWIFI_INOUT_DOOR(X_SWIFI_INOUT_DOOR);
    			wifiDto.setX_SWIFI_REMARS3(X_SWIFI_REMARS3);
    			wifiDto.setLAT(LAT);
    			wifiDto.setLNT(LNT);
    			wifiDto.setWORK_DTTM(WORK_DTTM);
    			
    			list.add(wifiDto);

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

        return list;
    }
    
    
    public void delHistory(String id) {
        
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

            String sql = " DELETE FROM history "
            			+ "	WHERE id = ? ";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            
            System.out.println(sql);

            int affected = preparedStatement.executeUpdate();

            if (affected > 0) {
                System.out.println("삭제 성공");
            } else {
                System.out.println("삭제 실패");
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
    
    

    /**
     * 오픈API에서 와이파이 정보를 가져오고,
     * 그 내용을 데이터베이스에 저장하고,
     * 최종적으로 저장한 개수를 리턴
     */
    public static String saveWifiInfo() {
    	String url = "jdbc:mariadb://127.0.0.1:3306/mission1";
    	String dbUserId = "mission1";
    	String dbPassword = "zerobase";
    	
    	String count = "";
    	
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

            String sql = " SELECT COUNT(*) "
            		+ "	FROM wifi ";

            preparedStatement = connection.prepareStatement(sql);

            rs = preparedStatement.executeQuery();

            while (rs.next()) {

                count = rs.getString("count(*)");

                System.out.println(count);

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
        return count;
        
    }

    /**
     * 와이파이 상세 정보 리턴
     */
    public WifiInfoDto getDetail(WifiInfoDetailModel parameter) {

        throw new RuntimeException("구현해 주세요.");
    }
    
    
}
