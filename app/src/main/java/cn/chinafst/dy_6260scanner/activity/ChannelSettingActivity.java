package cn.chinafst.dy_6260scanner.activity;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import cn.chinafst.dy_6260scanner.R;
import cn.chinafst.dy_6260scanner.base.CommonBaseActivity;
import cn.chinafst.dy_6260scanner.service.BluetoothLeService;
import cn.chinafst.dy_6260scanner.utils.DecodeUtils;

/**
 * Created by Administrator on 2018/1/4.
 */

public class ChannelSettingActivity extends CommonBaseActivity {
    private BluetoothLeService mBluetoothLeService;
    private ArrayList<String> devices;
    private ProgressBar pb;


    Handler handler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case 0:
                    pb.setVisibility(View.GONE);
                    break;
                default:break;
            }
        }
    };
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                finish();
            }
            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService.connect(devices.get(0));
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };
    @Override
    protected void setRoorView(TextView tittle, FrameLayout centerView) {
        devices= getIntent().getStringArrayListExtra("devices");
        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
        tittle.setText("通道设置");
        View view= LayoutInflater.from(context).inflate(R.layout.activity_channel_setting,null);
        centerView.addView(view);
         pb= (ProgressBar)view.findViewById(R.id.pb_process);

        Message message= new Message();
        message.what=0;
        handler.sendMessageDelayed(message,4000);
    }

    @Override
    protected void doClick(View v) {
        switch (v.getId()){
            case R.id.btn_cn1:
                    mBluetoothLeService.write(DecodeUtils.EXIT_CARD);
                    AlertDialog.Builder builder=new AlertDialog.Builder(this);
                    builder.setTitle("现在出卡的是通道一").setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            sp.edit().putString("channel1",devices.get(0)).apply();
                            sp.edit().putString("channel2",devices.get(1)).apply();
                            mBluetoothLeService.write(DecodeUtils.ENTER_CARD);
                        }
                    }).setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            sp.edit().putString("channel1",devices.get(1)).apply();
                            sp.edit().putString("channel2",devices.get(0)).apply();
                            mBluetoothLeService.write(DecodeUtils.ENTER_CARD);
                        }
                    }).show();


                break;

            default:break;
        }
    }

    @Override
    protected void setButton(LinearLayout llLayout, TextView page, Button bt1, Button bt2, Button bt3, Button bt4, Button bt5) {

    }
}
