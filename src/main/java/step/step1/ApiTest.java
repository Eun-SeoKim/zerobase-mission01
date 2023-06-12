package step.step1;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import zerobase.wifi.service.WifiService;

public class ApiTest {


    /**
     * TODO: 1. Open API 테스트
     */
    public static void main(String[] args) {

    	int total = WifiService.getTotalWifi();
    	int repeat = total / 1000;
    	int remain = total - (repeat * 1000);
    	
    	int start = 1;
    	int end = 1000;
    	
    	for (int i = 1; i <= repeat + 1; i++) {
    		
    		if (end > repeat * 1000) {
            	end = repeat * 1000 + remain;
            }
    		
    		// 1000개씩 증가 > 조회하며 작업
    		String result = OpenApi.get(start, end);
            //System.out.println(result);
            
            // json
         	JsonParser Parser = new JsonParser();
            JsonObject jsonObject1 = (JsonObject) Parser.parse(result);
            JsonObject jsonObject2 = jsonObject1.get("TbPublicWifiInfo").getAsJsonObject();
            JsonArray jsonArray = jsonObject2.getAsJsonArray("row");
            
            //System.out.println(jsonObject1);
            //System.out.println(jsonObject2);
            
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
    			String LAT = object.get("LAT").getAsString();
    			String LNT = object.get("LNT").getAsString();
    			String WORK_DTTM = object.get("WORK_DTTM").getAsString();

//    			System.out.println(X_SWIFI_MGR_NO);
//    			System.out.println(X_SWIFI_WRDOFC);
            }
            
            start += 1000;
            end += 1000;
            		
    	}
    	
    	
        

    }




}
