package com.donglu;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class MonitorUI{
	private Logger LOGGER = LoggerFactory.getLogger(MonitorUI.class);
	
	protected Shell shell;
	private Display display;
	private Label lblNewLabel;
	private Integer key_header_x;
	private Integer key_header_y;
	private Integer key_header_width;
	private Integer key_header_height;
	private String key_header_sql;
	
	private EventBus eventBus;
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MonitorUI window = new MonitorUI();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		display.addFilter(SWT.KeyDown, new Listener() {
			public void handleEvent(Event event) {
				if (event.stateMask == (SWT.SHIFT) && event.keyCode == SWT.ESC) {
					System.exit(1);
				}
			}
		});
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell(SWT.NO_TRIM);
		shell.setSize(1024, 768);
		Rectangle bounds = Display.getDefault().getPrimaryMonitor().getBounds();
		shell.setBounds(bounds);
		
		try(FileInputStream fileInputStream = new FileInputStream("backgroup.jpg")){
			Image image = new Image(display, fileInputStream);
			ImageData scaledTo = image.getImageData().scaledTo(bounds.width, bounds.height);
			shell.setBackgroundImage(new Image(display, scaledTo));
			shell.setBackgroundMode(SWT.INHERIT_FORCE); 
			image.dispose();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		key_header_x = Integer.valueOf(AppConfigrator.getProperties(AppConfigrator.key_header_x));
		key_header_y = Integer.valueOf(AppConfigrator.getProperties(AppConfigrator.key_header_y));
		key_header_width = Integer.valueOf(AppConfigrator.getProperties(AppConfigrator.key_header_width));
		key_header_height = Integer.valueOf(AppConfigrator.getProperties(AppConfigrator.key_header_height));
		key_header_sql = String.valueOf(AppConfigrator.getProperties(AppConfigrator.key_header_sql));
		
		lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBounds(key_header_x, key_header_y, key_header_width, key_header_height);
		
		String exist_label = AppConfigrator.getProperties(AppConfigrator.key_exist_label);
		String[] split = exist_label.split(",");
		for (String key : split) {
			int key_x = Integer.valueOf(AppConfigrator.getProperties(key+"_x"));
			int key_y = Integer.valueOf(AppConfigrator.getProperties(key+"_y"));
			int key_width = Integer.valueOf(AppConfigrator.getProperties(key+"_width"));
			int key_height = Integer.valueOf(AppConfigrator.getProperties(key+"_height"));
			
			Integer key_size = Integer.valueOf(AppConfigrator.getProperties(key+"_size"));
			String key_family = String.valueOf(AppConfigrator.getProperties(key+"_family"));
			String key_color = String.valueOf(AppConfigrator.getProperties(key+"_color"));
			String key_sql = String.valueOf(AppConfigrator.getProperties(key+"_sql"));
			String rgb[] = key_color.split(" ");
			
			Label lbl = new Label(shell,SWT.NONE);
			lbl.setBounds(key_x, key_y, key_width, key_height);
			lbl.setFont(new Font(display,key_family,key_size,SWT.NORMAL));
			lbl.setForeground(SWTResourceManager.getColor(Integer.valueOf(rgb[0]), Integer.valueOf(rgb[1]), Integer.valueOf(rgb[2])));
			lbl.setData("sql", key_sql);
			String executeSQL = DatabaseConnector.executeStringSQL(key_sql,"0");
			lbl.setText(executeSQL);
		}
		
		eventBus = new EventBus("refreshView");
		eventBus.register(this);
		
		MonitorService instanService = MonitorService.getInstanService();
		instanService.setEventBus(eventBus);
		instanService.start();
	}
	
	@Subscribe
	public void refreshView(final String lastValue){
		Display.getDefault().syncExec(new Runnable() {
			
			@Override
			public void run() {
					LOGGER.info("开始刷新界面数据：用户编号{}",lastValue);
					try{
						byte[] executeSQL = DatabaseConnector.executeImageSQL(key_header_sql,lastValue);
						if(executeSQL != null && executeSQL.length != 0){
							Image image = new Image(display, new ByteArrayInputStream(executeSQL));
							ImageData scaledTo = image.getImageData().scaledTo(key_header_width, key_header_height);
							lblNewLabel.setBackgroundImage(new Image(display, scaledTo));
						}
					}catch (Exception e) {
						e.printStackTrace();
					}
					Control[] children = shell.getChildren();
					for (Control control : children) {
						if(control instanceof Label){
							Label lbl = (Label)control;
							String sql = (String)lbl.getData("sql");
							String executeStringSQL = DatabaseConnector.executeStringSQL(sql,lastValue);
							LOGGER.info("更新数据{}",executeStringSQL.trim());
							lbl.setText(executeStringSQL);
						}
					}
				}
		});
	}
	
}
