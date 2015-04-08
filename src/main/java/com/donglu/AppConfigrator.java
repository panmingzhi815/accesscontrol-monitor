package com.donglu;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author panmingzhi
 * @createTime 2015年4月3日
 * @content 配置文件
 */
public class AppConfigrator {

	private static final Logger LOGGER = LoggerFactory.getLogger(AppConfigrator.class);
	
	public static final String default_charset = "UTF-8";
	
	public static final String key_sqlserver_ip = "sqlserver_ip";
	public static final String key_sqlserver_port = "sqlserver_port";
	public static final String key_sqlserver_databasename = "sqlserver_database_name";
	public static final String key_username = "sqlserver_database_username";
	public static final String key_password = "sqlserver_database_password";
	
	public static final String key_poll_interval = "poll_interval";
	public static final String key_poll_device = "poll_device_identifier";
	public static final String key_poll_eventType = "poll_device_eventType";
	public static final String key_clean_delay = "clean_delay";
	
	public static final String key_header_x = "header_x";
	public static final String key_header_y = "header_y";
	public static final String key_header_width = "header_width";
	public static final String key_header_height = "header_height";
	public static final String key_header_sql = "header_sql";
	
	public static final String key_exist_label = "exist_label";
	
	private static final String propertiesFilePath = "AppConfigurator.properties";
	private static Properties properties;
	
	private AppConfigrator(){}

	private static void checkProperties() {
		if (properties != null) {
			return;
		}
		properties = new Properties();
		
		Path path = Paths.get(propertiesFilePath);
		if(!Files.exists(path)){	
			try {
				Files.createFile(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
			init();
		}
		
		loadProperties();
	}
	
	public static void loadProperties(){
		try (
			FileInputStream fis = new FileInputStream(propertiesFilePath);
			InputStreamReader inputStreamReader = new InputStreamReader(fis,Charset.forName(default_charset))){
			properties.load(inputStreamReader);
			
			Set<Entry<Object,Object>> entrySet = properties.entrySet();
			for (Entry<Object, Object> entry : entrySet) {
				LOGGER.info("己加载key {}={}",entry.getKey(),entry.getValue());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void init() {
		try (
			FileOutputStream fos = new FileOutputStream(propertiesFilePath, false);
			PrintWriter out = new PrintWriter(fos);
			) {
			out.println("#背景图片为当前目录下的backgroup.jpg,只需同名替换即可");
			
			out.println("#sqlserver数据库ip地址");
			out.println(String.format("%s=%s", key_sqlserver_ip,"127.0.0.1"));
			out.println("#sqlserver数据库端口");
			out.println(String.format("%s=%s", key_sqlserver_port,"1433"));
			out.println("#sqlserver数据库名称");
			out.println(String.format("%s=%s", key_sqlserver_databasename,"onecard"));
			out.println("#sqlserver数据库登录名");
			out.println(String.format("%s=%s", key_username,"sa"));
			out.println("#sqlserver数据库登录密码");
			out.println(String.format("%s=%s", key_password,"1"));
			
			out.println("#sqlserver数据库轮询设备编码");
			out.println(String.format("%s=%s", key_poll_device,""));
			out.println("#sqlserver数据库轮询事件类型");
			out.println(String.format("%s=%s", key_poll_eventType,""));
			out.println("#sqlserver数据库轮询间隔");
			out.println(String.format("%s=%s", key_poll_interval,"100"));
			
			out.println("#界面数据清空间隔");
			out.println(String.format("%s=%s", key_clean_delay,"99999"));
			
			out.println("#大图贴x轴坐标");
			out.println(String.format("%s=%s", key_header_x,"10"));
			out.println("#大图贴y轴坐标");
			out.println(String.format("%s=%s", key_header_y,"150"));
			out.println("#大图贴宽度");
			out.println(String.format("%s=%s", key_header_width,"300"));
			out.println("#大图贴高度");
			out.println(String.format("%s=%s", key_header_height,"500"));
			out.println("#大图贴sql数据来源");
			out.println(String.format("%s=%s", key_header_sql,"select headImage from CardUser where identifier = ?"));
			
			out.println("#已经存在的标签，必须列出所以需显示的标签");
			out.println(String.format("%s=%s", key_exist_label,"title"));
			
			out.println("#title标签x轴");
			out.println(String.format("%s=%s", "title_x","30"));
			out.println("#title标签y轴");
			out.println(String.format("%s=%s", "title_y","10"));
			out.println("#title标签宽度");
			out.println(String.format("%s=%s", "title_width","600"));
			out.println("#title标签高度");
			out.println(String.format("%s=%s", "title_height","100"));
			out.println("#title标签字号大小");
			out.println(String.format("%s=%s", "title_size","30"));
			out.println("#title标签字体名称");
			out.println(String.format("%s=%s", "title_family","宋体"));
			out.println("#title标签字体颜色");
			out.println(String.format("%s=%s", "title_color","0 0 0"));
			out.println("#title标签sql数据来源");
			out.println(String.format("%s=%s", "title_sql","select '深圳市东陆高新实业有限公司' where 'xxxx' != ?"));
			
			out.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		loadProperties();
	}
	
	public static String getProperties(String key){
		checkProperties();
		return properties.getProperty(key);
	}

}
