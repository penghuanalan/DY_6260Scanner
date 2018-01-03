package cn.chinafst.dy_6260scanner;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.SharedPreferences;

import android_serialport_api.SerialPort;
import android_serialport_api.SerialPortFinder;
import cn.chinafst.dy_6260scanner.utils.GreenDaoUtils;

public class Tapplication extends Application {
		public SerialPortFinder mSerialPortFinder = new SerialPortFinder();
		private SerialPort mSerialPort = null;

	public static Tapplication instance;
	private static SharedPreferences sp ;
	public  static  Context context;
	public static Context getContext(){
		return context;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		context=getApplicationContext();
		GreenDaoUtils.initDatabase();
	}

}
