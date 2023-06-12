package zerobase.wifi.service;

import zerobase.wifi.dto.WifiInfoDto;

public class DatabaseTest {


    /**
     * TODO: 2. 데이터베이스 연결 테스트
     */
    public static void main(String[] args) {

        //WifiService.dbAllDelete();
        int total = WifiService.getTotalWifi();
        System.out.println(total);
        
        WifiService.addWifi();
        String count = WifiService.saveWifiInfo();
        System.out.println(count);
    	

    }




}
