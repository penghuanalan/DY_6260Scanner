package cn.chinafst.dy_6260scanner.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.chinafst.dy_6260scanner.R;
import cn.chinafst.dy_6260scanner.base.CommonBaseActivity;
import cn.chinafst.dy_6260scanner.base.DetectItemBeans;
import cn.chinafst.dy_6260scanner.service.BluetoothLeService;
import cn.chinafst.dy_6260scanner.service.BluetoothLeService2;
import cn.chinafst.dy_6260scanner.utils.DecodeUtils;
import cn.chinafst.dy_6260scanner.utils.LogPrint;

/**
 * Created by Administrator on 2018/1/3.
 */

public class DeteciteOneActivity extends CommonBaseActivity  {
   private DetectItemBeans beans;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeService mBluetoothLeService;
    private BluetoothLeService2 mBluetoothLeService2;
    private String channel;
    private FrameLayout centerView;

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void doMessage(Message msg) {
       switch (msg.what){
          // case DecodeUtils.READ_DATE:

            //   break;
       }

    }

    @Override
    protected void setRoorView(TextView tittle, FrameLayout centerView) {
        //获取蓝牙服务
        initService();
        beans= (DetectItemBeans) getIntent().getSerializableExtra("bean");
        tittle.setText(beans.getDetect_item_name());
        channel= getIntent().getStringExtra("channel");
        View view= LayoutInflater.from(context).inflate(R.layout.activity_detecite_one,null);
        centerView=(FrameLayout) view.findViewById(R.id.fl_detect_one);
        centerView.addView(view);

    }

    /*
  * 连接两个蓝牙设备
  * */
    private void initService() {
        Intent serviceIntent1 = new Intent(this, BluetoothLeService.class);
        Intent serviceIntent2 = new Intent(this, BluetoothLeService2.class);
        bindService(serviceIntent1, mServiceConnection, BIND_AUTO_CREATE);
        bindService(serviceIntent2, mServiceConnection, BIND_AUTO_CREATE);

    }

    @Override
    protected void doClick(View v) {
        switch (v.getId()){
            case R.id.bt4:
                mBluetoothLeService.write(DecodeUtils.SCAN_CARD);
                handler.sendEmptyMessageDelayed(DecodeUtils.READ_DATE,3000);
                break;
            case R.id.btn_back:
                break;
        }
    }

    @Override
    protected void setButton(LinearLayout llLayout, TextView page, Button bt1, Button bt2, Button bt3, Button bt4, Button bt5) {
        bt5.setText("返回");
        bt5.setVisibility(View.VISIBLE);
        bt4.setText("开始检测");
        bt4.setVisibility(View.VISIBLE);
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName name, IBinder service) {
            if (name.getShortClassName().equals(".service.BluetoothLeService")) {
                mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
                mBluetoothLeService.write(DecodeUtils.EXIT_CARD);
                // 判断是否已经初始化蓝牙服务
            } else if (name.getShortClassName().equals(".service.BluetoothLeService2")) {
                mBluetoothLeService2 = ((BluetoothLeService2.LocalBinder) service).getService();
                mBluetoothLeService2.write(DecodeUtils.EXIT_CARD);
                // 判断是否已经初始化蓝牙服务
            }
        }

        public void onServiceDisconnected(ComponentName name) {
            mBluetoothLeService.disconnect();
            mBluetoothLeService = null;
            Log.e("a", "服务停止");
        }
    };


}
