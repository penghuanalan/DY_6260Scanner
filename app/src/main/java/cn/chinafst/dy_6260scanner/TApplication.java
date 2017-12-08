package cn.chinafst.dy_6260scanner;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import android_serialport_api.SerialPort;
import android_serialport_api.SerialPortFinder;

public class TApplication extends Application {
		public SerialPortFinder mSerialPortFinder = new SerialPortFinder();
		private SerialPort mSerialPort = null;

		public SerialPort getSerialPort() throws SecurityException, IOException, InvalidParameterException {
			try {
				if (mSerialPort == null) {
					int baudrate = 9600;
					 String path = "/dev/ttyS4";
					/* Check parameters */
					if ( (path.length() == 0) || (baudrate == -1)) {
						throw new InvalidParameterException();
					}

					/* Open the serial port */
					mSerialPort = new SerialPort(new File(path), baudrate, 0);
				}
			
			}catch (Exception e) {
				// TODO: handle exception
			}
		
			return mSerialPort;
		}

		public void closeSerialPort() {
			if (mSerialPort != null) {
				mSerialPort.close();
				mSerialPort = null;
			}
		}

		
	private ArrayList<Activity> listActivity = new ArrayList<Activity>();
	public static TApplication instance;
	private static SharedPreferences sp ;

	public static TApplication getInstance(){
		if(null==instance){
			instance=new TApplication();
		}
		return instance;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		/**/try {
			instance=new TApplication();
		//	CrashHandler crashHandler = new CrashHandler(this);
			//Thread.setDefaultUncaughtExceptionHandler(crashHandler);
		} catch (Exception e) {
			//Toast.makeText(getApplicationContext(), "�쳣����", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}
	public void exit(){
		finishActivity();
	}

	public void addActivity(Activity activity) {
		listActivity.add(activity);
	}

	

	public void finishActivity() {
		for (Activity activity : listActivity) {
			activity.finish();
		}
		System.exit(0);
	}

	public void removeActivity(Activity activity) {
		listActivity.remove(activity);
	}
	
}
