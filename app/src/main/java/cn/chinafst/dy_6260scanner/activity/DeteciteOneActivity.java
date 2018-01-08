package cn.chinafst.dy_6260scanner.activity;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import cn.chinafst.dy_6260scanner.R;
import cn.chinafst.dy_6260scanner.base.CheckRecordBean;
import cn.chinafst.dy_6260scanner.base.CommonBaseActivity;
import cn.chinafst.dy_6260scanner.base.DetectItemBeans;
import cn.chinafst.dy_6260scanner.fragment.DeteciteNameFragment;
import cn.chinafst.dy_6260scanner.service.BluetoothLeService;
import cn.chinafst.dy_6260scanner.service.BluetoothLeService2;
import cn.chinafst.dy_6260scanner.utils.DecodeUtils;
import cn.chinafst.dy_6260scanner.utils.DyUtils;
import cn.chinafst.dy_6260scanner.utils.GreenDaoUtils;
import cn.chinafst.dy_6260scanner.utils.LogPrint;
import static cn.chinafst.dy_6260scanner.utils.DecodeUtils.CARD_STATE;
import static cn.chinafst.dy_6260scanner.utils.DecodeUtils.ENTER_CARD;
import static cn.chinafst.dy_6260scanner.utils.DecodeUtils.EXIT_CARD;
import static cn.chinafst.dy_6260scanner.utils.DecodeUtils.SCAN_CARD;

/**
 * Created by Administrator on 2018/1/3.
 */

public class DeteciteOneActivity extends CommonBaseActivity  {
   private DetectItemBeans beans;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeService mBluetoothLeService;
    private BluetoothLeService2 mBluetoothLeService2;
    private String channel;
    private FrameLayout frameLayout;
    private final int READ_DATE=200;
    private final int GET_DATE=201;
    private int currentFlag=DecodeUtils.STATE_EXIT;
    private LinearLayout llSampleDetail;
    private  FragmentTransaction transaction;
    DeteciteNameFragment fragment;


    //获取到的数据
   private ArrayList<Double> list=new ArrayList<>();

    private BroadcastReceiver receiver= new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothLeService.EXTRA_DATA.equals(action)) {
                Bundle bundle = intent.getBundleExtra(BluetoothLeService.EXTRA_DATA);
                byte[] byteArray = bundle.getByteArray(BluetoothLeService.EXTRA_DATA);
                int cmd = byteArray[1];
                int len = (byteArray[2] << 8) | (byteArray[3] & 0x0FF);
                LogPrint.e("获取的广播----"+len);
                switch (cmd) {
                    case 0x14:
                        /* 卡状态0x02 无卡  0x01 有卡* */
                        if(byteArray[4]==0x02){
                        LogPrint.toast(context,"当前为无卡状态");
                        }else if(byteArray[4]==0x01){
                            currentFlag=DecodeUtils.STATE_READ;
                            bt4.setVisibility(View.GONE);
                            bt5.setEnabled(false);
                            mBluetoothLeService.write(SCAN_CARD);
                            handler.sendEmptyMessageDelayed(READ_DATE,11000);
                        }
                        break;
                    case 0x16:
                            try{
                                for (int i = 0; i < len; i = i + 2) {
                                    double data01 = (byteArray[5 + i] & 0x0FF) * 256 + (byteArray[4 + i] & 0x0FF);
                                    list.add(data01);
                                }

                            }catch (Exception e){

                            }

                        handler.sendEmptyMessage(GET_DATE);
                        break;

                    case 0x12:
                            /* 进出卡* */

                        break;

                    default:
                        break;
                }
            }
        }
    };


    @Override
    protected void onResume() {
        super.onResume();

    }



    @Override
    protected void setRoorView(TextView tittle, FrameLayout centerView) {
        //获取蓝牙服务
        LogPrint.e("加载了");
        initService();
        beans= (DetectItemBeans) getIntent().getSerializableExtra("bean");
        tittle.setText(beans.getDetect_item_name());
        channel= getIntent().getStringExtra("channel");
        View view= LayoutInflater.from(context).inflate(R.layout.activity_detecite_one,null);
        frameLayout=(FrameLayout) view.findViewById(R.id.fl_detect_one);
        llSampleDetail=(LinearLayout) view.findViewById(R.id.ll_sample_dateil);
        centerView.addView(view);

       // initFragment();
    }



    @Override
    protected void doMessage(Message msg) {

        switch (msg.what){
            //读取数据
            case READ_DATE:
                mBluetoothLeService.write(DecodeUtils.READ_DATA);
                break;
            //解析数据
            case GET_DATE:
                llSampleDetail.setVisibility(View.GONE);
                bt5.setEnabled(true);
                dealData();
                break;
            default:break;
        }
    }

    private void dealData() {
        frameLayout.setVisibility(View.VISIBLE);

        double[] sourse=new double[list.size()];
        for(int i=0;i<list.size();i++){
            sourse[i]=list.get(i);
        }
        double[] doubles = DyUtils.dyMath(sourse);
        ArrayList<Double> doubles1 = DyUtils.doubles;
        double[] points=new double[doubles1.size()] ;
        for(int i=0;i<points.length;i++){
            points[i]= doubles1.get(i);
        }

        //数据库操作
        CheckRecordBean bean= new CheckRecordBean();
        //bean.setId();

        GreenDaoUtils.getDaoSession().getCheckRecordBeanDao().insert(bean);



        LogPrint.e("获取的长度------"+list.size()+"--points"+points.length);
        FragmentManager fm = getSupportFragmentManager();
        transaction = fm.beginTransaction();
         fragment= new DeteciteNameFragment();

        Bundle bundle=new Bundle();
        bundle.putDoubleArray("data",points);
        fragment.setArguments(bundle);
        transaction.replace(R.id.fl_detect_one,fragment);
        transaction.commit();

    }


    /*
      * 连接两个蓝牙设备
      * */
    private void initService() {
        Intent serviceIntent1 = new Intent(this, BluetoothLeService.class);
        Intent serviceIntent2 = new Intent(this, BluetoothLeService2.class);
        bindService(serviceIntent1, mServiceConnection, BIND_AUTO_CREATE);
        bindService(serviceIntent2, mServiceConnection, BIND_AUTO_CREATE);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.EXTRA_DATA);
        registerReceiver(receiver,intentFilter);
    }

    @Override
    protected void doClick(View v) {
        switch (v.getId()){
            case R.id.bt4:
                mBluetoothLeService.write(CARD_STATE);
                break;
            case R.id.btn_back:
                if(currentFlag==DecodeUtils.STATE_EXIT){
                    mBluetoothLeService.write(ENTER_CARD);
                    unbindService(mServiceConnection);
                    unregisterReceiver(receiver);
                    finish();
                }else if(currentFlag==DecodeUtils.STATE_READ){
                    list.clear();
                    fragment=null;
                    mBluetoothLeService.write(EXIT_CARD);
                    currentFlag=DecodeUtils.STATE_EXIT;
                    transaction.remove(fragment);
                    bt4.setVisibility(View.VISIBLE);
                    frameLayout.setVisibility(View.GONE);
                }

                break;
            default:break;
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
                mBluetoothLeService.write(EXIT_CARD);
               // handler.sendEmptyMessageDelayed(READ_DATE,100);
                // 判断是否已经初始化蓝牙服务
            } else if (name.getShortClassName().equals(".service.BluetoothLeService2")) {
                mBluetoothLeService2 = ((BluetoothLeService2.LocalBinder) service).getService();
               // mBluetoothLeService2.write(EXIT_CARD);
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
