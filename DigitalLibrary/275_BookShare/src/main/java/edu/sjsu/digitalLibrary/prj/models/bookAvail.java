package edu.sjsu.digitalLibrary.prj.models;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import org.json.JSONArray;

public class bookAvail implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int subId;
	private String start_date;
	private String single_start_date;
	
	private String end_date;
	private int regionId;
	private double region_long;
	private double region_lat;
	private String region_name;
	
	
	
	public String getRegion_name() {
		return region_name;
	}

	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}

	public bookAvail(int subId, String start_date,String end_date, int regionId, double region_long, double region_lat, String region_name)
	{
		this.subId=subId;
		this.start_date=start_date;
		this.end_date=end_date;
		this.regionId=regionId;
		this.region_lat=region_lat;
		this.region_long=region_long;
		this.region_name=region_name;
	}
	
	public int getRegionId() {
		return regionId;
	}
	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}
	public double getRegion_long() {
		return region_long;
	}
	public void setRegion_long(long region_long) {
		this.region_long = region_long;
	}
	public double getRegion_lat() {
		return region_lat;
	}
	public void setRegion_lat(long region_lat) {
		this.region_lat = region_lat;
	}
	public int getSubId() {
		return subId;
	}
	public void setSubId(int subId) {
		this.subId = subId;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getSingle_start_date() {
		return single_start_date;
	}
	public void setSingle_start_date(String single_start_date) {
		this.single_start_date = single_start_date;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
