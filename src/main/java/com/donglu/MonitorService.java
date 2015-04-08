package com.donglu;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.eventbus.EventBus;

public class MonitorService {
	
	private Logger LOGGER = LoggerFactory.getLogger(MonitorService.class);
	
	private final String poll_sql = "select top 1 userIdentifier from CardUsage where DATEDIFF(SECOND,timestamp,GETDATE()) < 10 and deviceIdentifier in (%s) and eventType in (%s) order by timestamp desc";
	
	private ScheduledExecutorService scheduledExecutor;
	private String lastValue;
	
	private static MonitorService monitorService;
	
	private EventBus eventBus;
	
	public static MonitorService getInstanService(){
		if(monitorService == null){
			monitorService = new MonitorService();
		}
		return monitorService;
	}
	
	public void setEventBus(EventBus eventBus){
		this.eventBus = eventBus;
	}
	
	private MonitorService(){}
	
	public String getLastValue() {
		return lastValue ;
	}

	public void start(){
		if(scheduledExecutor != null){
			LOGGER.info("数据库轮询服务开始已经启动");
			return;
		}
		LOGGER.info("数据库轮询服务开始启动");
		scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
		
		final int poll_interval = Integer.valueOf(AppConfigrator.getProperties(AppConfigrator.key_poll_interval));
		final String deviceIdentifier = String.valueOf(AppConfigrator.getProperties(AppConfigrator.key_poll_device));
		final String deviceEventType = String.valueOf(AppConfigrator.getProperties(AppConfigrator.key_poll_eventType));
		
		LOGGER.info("数据库轮询服务查询sql:{}",poll_sql);
		LOGGER.info("数据库轮询服务间隔sql:{}",poll_interval);
		final String format = String.format(poll_sql, deviceIdentifier,deviceEventType);
		scheduledExecutor.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				try{
					String executeStringSQL = DatabaseConnector.executeStringSQL(format);
					if(executeStringSQL == null || executeStringSQL.trim().length() == 0){
						return;
					}
					if(lastValue == null || !executeStringSQL.equals(lastValue)){
						LOGGER.debug("数据库轮询服务当前查询状态:{},上一次状态:{}",executeStringSQL,lastValue);
						lastValue = executeStringSQL;
						Preconditions.checkNotNull(eventBus, "事件广播机制不可为null");
						eventBus.post(lastValue);
					}
				}catch(Exception e){
					LOGGER.error("数据库轮询任务执行时发生异常！",e);
				}
			}
		}, 3, poll_interval, TimeUnit.MILLISECONDS);
	}
	
}
