package cn.chinafst.dy_6260scanner.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.chinafst.dy_6260scanner.base.CommonBaseActivity;
import cn.chinafst.dy_6260scanner.base.DetectItemBeans;
import cn.chinafst.dy_6260scanner.service.BluetoothLeService;

/**
 * Created by Administrator on 2018/1/3.
 */

public class DeteciteOneActivity extends CommonBaseActivity {
   private DetectItemBeans beans;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeService mBluetoothLeService;
    private BluetoothDevice Testdevice;
    // 开始扫描的接口回调,返回扫描回来的device


    // 代码管理服务生命周期
    private ServiceConnection mServiceConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName name, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            // 判断是否已经初始化蓝牙服务
            if (!mBluetoothLeService.initialize()) {
               // Log.e("a", "无法初始化蓝牙");
                finish();
            }
            // 自动连接到装置上成功启动初始化。
            if(Testdevice!=null){
                mBluetoothLeService.connect(Testdevice.getAddress());
            }

        }

        public void onServiceDisconnected(ComponentName name) {
            mBluetoothLeService.disconnect();
            mBluetoothLeService = null;
            Log.e("a", "服务停止");
        }
    };





    @Override
    protected void setRoorView(TextView tittle, FrameLayout centerView) {
        beans= (DetectItemBeans) getIntent().getSerializableExtra("bean");
        tittle.setText(beans.getDetect_item_name());
    }

    @Override
    protected void doClick(View v) {

    }

    @Override
    protected void setButton(LinearLayout llLayout, TextView page, Button bt1, Button bt2, Button bt3, Button bt4, Button bt5) {

    }
}
