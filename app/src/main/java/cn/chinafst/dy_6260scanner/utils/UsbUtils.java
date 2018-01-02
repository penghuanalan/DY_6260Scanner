package cn.chinafst.dy_6260scanner.utils;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Administrator on 2017/12/8.
 */

public class UsbUtils {
    public static UsbManager mUsbManager;
    public static  HashMap<String, UsbDevice> deviceList;
    public static UsbDevice[] devices=new UsbDevice[3];
    private static String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";


    public static boolean initPremission(final Context mContext){

        mUsbManager = (UsbManager)mContext.getSystemService(Context.USB_SERVICE);
        if (mUsbManager != null) {
            deviceList = mUsbManager.getDeviceList();
            if(deviceList!=null&&deviceList.size()>0){
                Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
                int i = 0;
                ArrayList<UsbDevice> deviceTemp=new ArrayList<>();
                while (deviceIterator.hasNext()) {
                    UsbDevice device = deviceIterator.next();
                    if (device != null) {
                        if(device.getVendorId()==6790||device.getVendorId()==1155){
                            deviceTemp.add(device);
                        }
                    }
                }
                if(deviceTemp.size()>0){
                    PendingIntent mPermissionIntent = PendingIntent.getBroadcast(mContext, 0,new Intent(ACTION_USB_PERMISSION), 0);
                    IntentFilter permissionFilter = new IntentFilter(ACTION_USB_PERMISSION);
                     mContext.registerReceiver(new BroadcastReceiver(){

                        @Override
                        public void onReceive(Context context, Intent intent) {
                            String action = intent.getAction();
                            if (ACTION_USB_PERMISSION.equals(action)) {
                                if (!intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                                    LogPrint.toast(mContext,"用户拒绝授权");
                                }
                                initPremission(mContext);
                            }
                        }
                    }, permissionFilter);
                    for(int j=0;j<deviceTemp.size();j++){
                        if (!mUsbManager.hasPermission(deviceTemp.get(j))) {
                            mUsbManager.requestPermission(deviceTemp.get(j), mPermissionIntent);
                        }
                    }
                }else{
                    LogPrint.toast(mContext,"设备异常");
                }

            }
        }

        return true;
    }
}
