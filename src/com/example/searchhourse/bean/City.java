package com.example.searchhourse.bean;

public class City {

	public static final int TYPE_LABEL=0;
	public static final int TYPE_NAME=1;
	
	public static int getTypeCount(){ //返回数据类型的个数
		return 2;
	}
	
	public int getType(){
		if(comparename.equals("label")){
			return TYPE_LABEL;
		}else
			return TYPE_NAME;
	}
	
	private String cityid;
	private String cityalias;
	private String cityname;
	private String comparename;
	private String center_x;
	private String center_y;
	private String lng;
	private String lat;
	private String mobiletype;
	private String citypinyin;
	private String esfalias;

	public String getCityid() {
		return cityid;
	}

	public void setCityid(String cityid) {
		this.cityid = cityid;
	}

	public String getCityalias() {
		return cityalias;
	}

	public void setCityalias(String cityalias) {
		this.cityalias = cityalias;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public String getComparename() {
		return comparename;
	}

	public void setComparename(String comparename) {
		this.comparename = comparename;
	}

	public String getCenter_x() {
		return center_x;
	}

	public void setCenter_x(String center_x) {
		this.center_x = center_x;
	}

	public String getCenter_y() {
		return center_y;
	}

	public void setCenter_y(String center_y) {
		this.center_y = center_y;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getMobiletype() {
		return mobiletype;
	}

	public void setMobiletype(String mobiletype) {
		this.mobiletype = mobiletype;
	}

	public String getCitypinyin() {
		return citypinyin;
	}

	public void setCitypinyin(String citypinyin) {
		this.citypinyin = citypinyin;
	}

	public String getEsfalias() {
		return esfalias;
	}

	public void setEsfalias(String esfalias) {
		this.esfalias = esfalias;
	}

	public City() {
		super();
	}

	@Override
	public String toString() {
		return "City [cityname=" + cityname + "]";
	}
	
	
}
