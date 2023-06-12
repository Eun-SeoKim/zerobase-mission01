package zerobase.wifi.dto;


public class PosHistoryDto {
	private String ID;
	private double LAT;
	private double LNT;
	private String WORK_DTTM;
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public double getLAT() {
		return LAT;
	}
	public void setLAT(double lAT) {
		LAT = lAT;
	}
	public double getLNT() {
		return LNT;
	}
	public void setLNT(double lNT) {
		LNT = lNT;
	}
	public String getWORK_DTTM() {
		return WORK_DTTM;
	}
	public void setWORK_DTTM(String wORK_DTTM) {
		WORK_DTTM = wORK_DTTM;
	}

}


