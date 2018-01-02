package cn.chinafst.dy_6260scanner;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.SharedPreferences;

import android_serialport_api.SerialPort;
import android_serialport_api.SerialPortFinder;

public class Tapplication extends Application {
		public SerialPortFinder mSerialPortFinder = new SerialPortFinder();
		private SerialPort mSerialPort = null;
		private static BluetoothManager bluetoothManager;

	public static Tapplication instance;
	private static SharedPreferences sp ;

	
	@Override
	public void onCreate() {
		super.onCreate();
		try {
			instance=new Tapplication();
			bluetoothManager=(BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static BluetoothAdapter getBlueAdapter(){
		if(bluetoothManager!=null){
			return 	bluetoothManager.getAdapter();
		}else{
			return null;
		}

	}

	
}
