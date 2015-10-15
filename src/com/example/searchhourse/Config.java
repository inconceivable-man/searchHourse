package com.example.searchhourse;

public class Config {

	// 详情信息id
	public static final String NEWS_ID = "news_id";

	// 详情信息评论id
	public static final String COMMENT_ID = "conmment_id";

	// 城市列表
	public static final String CHOICE_CITY = "http://ikft.house.qq.com/index.php?guid=866500021200250&devua=appkft_1080_1920_XiaomiMI4LTE_1.8.3_Android19&act=kftcitylistnew&channel=71&devid=866500021200250&appname=QQHouse&mod=appkft";

	// 定义字母数组
	public static final String[] CITY_TYPE = new String[] { "A", "B", "C", "D",
			"E", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q", "R", "S",
			"T", "W", "X", "Y", "Z" };

	/**
	 * 首页WebView内容
	 */
	public static final String FIRST_PAGE_WEBVIEW = "http://ikft.house.qq.com/index.php?guid=866500021200250&devua=appkft_1080_1920_XiaomiMI4LTE_1.8.3_Android19&devid=866500021200250&appname=QQHouse&mod=appkft&act=homepage&channel=71&cityid=%s";
	/**
	 * 首页 ListView内容
	 * 
	 * 1)进入时：reqnum=10,pageflag=0,buttonmore=0;
	 * 2)点击查看更多时：reqnum=20,pageflag=0,buttonmore=1;
	 * 3)刷新时：reqnum=20,pageflag=1,buttonmore=1;
	 */
	public static final String FIRST_PAGE_LISTVIEW = "http://ikft.house.qq.com/index.php?guid=866500021200250&devua=appkft_1080_1920_XiaomiMI4LTE_1.8.3_Android19&devid=866500021200250&appname=QQHouse&mod=appkft&reqnum=%d&pageflag=%d&act=newslist&channel=71&buttonmore=%d&cityid=%s";
	/**
	 * 资讯详情
	 */
	public static final String NEWS_DETAIL = "http://ikft.house.qq.com/index.php?guid=866500021200250&devua=appkft_1080_1920_XiaomiMI4LTE_1.8.3_Android19&devid=866500021200250&appname=QQHouse&mod=appkft&act=newsdetail&channel=71&newsid=%s";
	/**
	 * 资讯评论
	 */
	public static final String NEWS_COMMENT = "http://ikft.house.qq.com/index.php?guid=866500021200250&devua=appkft_1080_1920_XiaomiMI4LTE_1.8.3_Android19&devid=866500021200250&appname=QQHouse&mod=appkft&reqnum=20&pageflag=0&act=newscomment&channel=71&targetid=%s";
	/**
	 * 找新房
	 */
	public static final String LOOKING_NEWHOUSE = "http://ikft.house.qq.com/index.php?guid=866500021200250&devua=appkft_1080_1920_XiaomiMI4LTE_1.8.3_Android19&rn=10&order=0&searchtype=normal&devid=866500021200250&page=%d&appname=QQHouse&mod=appkft&act=searchhouse&channel=71&cityid=%s";
	/**
	 * 找新房 楼盘信息
	 */
	public static final String NEW_HOUSE_INFO = "http://ikft.house.qq.com/index.php?guid=866500021200250&devua=appkft_1080_1920_XiaomiMI4LTE_1.8.3_Android19&hid=%d&devid=866500021200250&appname=QQHouse&mod=appkft&act=houseinfo&channel=71";
	/**
	 * 置业顾问
	 */
	public static final String AGENT = "http://ikft.house.qq.com/index.php?guid=000000000000000&devua=appkft_1080_1776_GenymotionSamsungGalaxyS5-4.4.4-API19-1080x1920_1.8.3_Android19&rn=10&hid=%d&devid=000000000000000&page=1&appname=QQHouse&mod=appkft&act=agentlist&channel=65";
	/**
	 * 找新房 评论
	 */
	public static final String NEW_HOUSE_COMMENT = "http://ikft.house.qq.com/index.php?guid=866500021200250&devua=appkft_1080_1920_XiaomiMI4LTE_1.8.3_Android19&rn=0&hid=%d&devid=866500021200250&page=1&appname=QQHouse&mod=appkft&type=all&act=housecomment&channel=71";
	/**
	 * 最新开盘
	 */
	public static final String NEWEST_HOUSE = "http://ikft.house.qq.com/index.php?guid=866500021200250&devua=appkft_1080_1920_XiaomiMI4LTE_1.8.3_Android19&rn=10&order=0&searchtype=normal&devid=866500021200250&page=%d&appname=QQHouse&mod=appkft&feature=996&act=searchhouse&channel=71&cityid=%s";
	/**
	 * 最新开盘 楼市信息
	 */
	public static final String NEWEST_HOUSE_INFO = "http://ikft.house.qq.com/index.php?guid=866500021200250&devua=appkft_1080_1920_XiaomiMI4LTE_1.8.3_Android19&hid=%d&devid=866500021200250&appname=QQHouse&mod=appkft&act=houseinfo&channel=71";
	/**
	 * 最新开盘 楼市评论
	 */
	public static final String NEWEST_HOUSE_COMMENT = "http://ikft.house.qq.com/index.php?guid=866500021200250&devua=appkft_1080_1920_XiaomiMI4LTE_1.8.3_Android19&rn=0&hid=%d&devid=866500021200250&page=1&appname=QQHouse&mod=appkft&type=all&act=housecomment&channel=71";
	/**
	 * 打折优惠
	 */
	public static final String DISCOUNT_SEAL = "http://ikft.house.qq.com/index.php?&page=1&appname=QQHouse&mod=appkft&act=discountslist&channel=71&cityid=1";
	/**
	 * 打折优惠 楼盘信息
	 */
	public static final String DISCOUNT_HOUSE_INFO = "http://ikft.house.qq.com/index.php?&hid=%d&appname=QQHouse&mod=appkft&act=houseinfo&channel=71";
	/**
	 * 打折优惠 楼盘评论
	 */
	public static final String DISCOUNT_HOUSE_COMMENT = "http://ikft.house.qq.com/index.php?&rn=0&hid=157017&page=1&appname=QQHouse&mod=appkft&type=all&act=housecomment&channel=71";
	/**
	 * 看房团
	 */
	public static final String TEAM_HOUSE = "http://ikft.house.qq.com/index.php?guid=000000000000000&devua=appkft_1080_1776_GenymotionSamsungGalaxyS5-4.4.4-API19-1080x1920_1.8.3_Android19&devid=000000000000000&appname=QQHouse&mod=appkft&act=kftlist&channel=65&cityid=%d";
	public static final String TEAM_HOUSE_DETAIL = "http://ikft.house.qq.com/index.php?&rid=7243&appname=QQHouse&mod=appkft&act=aroundhouseandequips&channel=65";
	/**
	 * 参团 地图图片
	 */
	public static final String TEAM_HOUSE_MAP = "http://st.map.soso.com/api?size=350*200&center=%s,%s&markers=%s,%s,1";

	/**
	 * 搜索界面
	 */
	public static final String SEARCH_HOUSE = "http://ikft.house.qq.com/index.php?guid=000000000000000&devua=appkft_1080_1776_GenymotionSamsungGalaxyS5-4.4.4-API19-1080x1920_1.8.3_Android19&devid=000000000000000&appname=QQHouse&mod=appkft&act=searchsetting&channel=65&cityid=%s";

	/**
	 * 搜索列表：（输入之后自动显示）
	 */
	public static final String SEARCH_HOUSE_INPUT = "http://ikft.house.qq.com/index.php?query=%s&appname=QQHouse&mod=appkft&act=smartbox&channel=65&city=%s";

	/**
	 * 搜索结果列表（点击搜索之后获得）
	 */
	public static final String SEARCH_HOUSE_RESULT = "http://ikft.house.qq.com/index.php?guid=000000000000000&devua=appkft_1080_1776_GenymotionSamsungGalaxyS5-4.4.4-API19-1080x1920_1.8.3_Android19&rn=10&order=0&searchtype=normal&devid=000000000000000&page=1&appname=QQHouse&keyword=%s&mod=appkft&act=searchhouse&channel=65&cityid=1";
	/**
	 * 获取短信验证码
	 */
	public static final String GET_SMSCODE = "http://ikft.house.qq.com/index.php?guid=000000000000000&devua=appkft_1080_1776_GenymotionSamsungGalaxyS5-4.4.4-API19-1080x1920_1.8.3_Android19&devid=000000000000000&appname=QQHouse&mod=appkft&act=sendsms&channel=65&mobile=%s";
	/**
	 * 提交验证码
	 */
	public static final String POST_SMSCODE = "http://ikft.house.qq.com/index.php?guid=000000000000000&devua=appkft_1080_1776_GenymotionSamsungGalaxyS5-4.4.4-API19-1080x1920_1.8.3_Android19&smscode=%s&devid=000000000000000&appname=QQHouse&mod=appkft&act=checksms&channel=65&mobile=%s";

}
