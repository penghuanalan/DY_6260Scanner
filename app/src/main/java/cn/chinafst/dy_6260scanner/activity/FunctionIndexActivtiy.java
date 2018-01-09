package cn.chinafst.dy_6260scanner.activity;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cn.chinafst.dy_6260scanner.R;
import cn.chinafst.dy_6260scanner.base.CommonBaseActivity;
import cn.chinafst.dy_6260scanner.service.BluetoothLeService;
import cn.chinafst.dy_6260scanner.service.BluetoothLeService2;
import cn.chinafst.dy_6260scanner.utils.DecodeUtils;
import cn.chinafst.dy_6260scanner.utils.LogPrint;

/**
 * Created by Administrator on 2017/12/8.
 */

public class FunctionIndexActivtiy extends CommonBaseActivity implements AdapterView.OnItemClickListener {
    private GridView gridView;
    private BluetoothLeService mBluetoothLeService;
    private BluetoothLeService2 mBluetoothLeService2;
    private ServiceConnection mServiceConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName name, IBinder service) {

            if (name.getShortClassName().equals(".service.BluetoothLeService")) {
                mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
                // 判断是否已经初始化蓝牙服务
                if (!mBluetoothLeService.initialize()) {
                    finish();
                }
                mBluetoothLeService.connect(sp.getString("channel1", ""));
            } else if (name.getShortClassName().equals(".service.BluetoothLeService2")) {
                mBluetoothLeService2 = ((BluetoothLeService2.LocalBinder) service).getService();
                // 判断是否已经初始化蓝牙服务
                if (!mBluetoothLeService2.initialize()) {
                    finish();
                }
                mBluetoothLeService2.connect(sp.getString("channel2", ""));
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
        tittle.setText("主菜单");
        View view = LayoutInflater.from(context).inflate(R.layout.activtiy_function_index, null);
        gridView = (GridView) view.findViewById(R.id.gv_function);
        gridView.setOnItemClickListener(this);
        gridView.setAdapter(new FunctionAdapter());
        centerView.addView(view);
        initService();
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
            case R.id.btn_back:
                finish();
                break;
            default:break;
        }
    }


    @Override
    protected void setButton(LinearLayout llLayout, TextView page, Button bt1, Button bt2, Button bt3, Button bt4, Button bt5) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0:
                startActivity(new Intent(context, SampleNameActivity.class));
                break;
            case 1:
                startActivity(new Intent(context,CheckRecordActivity.class));
                break;
            default:
                break;
        }

    }

    class FunctionAdapter extends BaseAdapter {
        private int[] imgs = {R.drawable.the_sample_testing, R.drawable.query_log, R.drawable.project_settings,
                R.drawable.data_management, R.drawable.system_settings, R.drawable.log_out,};
        private String[] items = {"样品检测", "查询记录", "项目设置", "数据管理", "系统设置", "退出系统"};

        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = LayoutInflater.from(context).inflate(R.layout.item_function_index, viewGroup, false);
            ImageView imageView = (ImageView) view1.findViewById(R.id.iv_icon);
            TextView textView = (TextView) view1.findViewById(R.id.tv_item);
            imageView.setImageResource(imgs[i]);
            textView.setText(items[i]);
            return view1;
        }
    }
}
