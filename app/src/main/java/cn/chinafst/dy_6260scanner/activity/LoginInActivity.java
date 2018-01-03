package cn.chinafst.dy_6260scanner.activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;

import cn.chinafst.dy_6260scanner.R;
import cn.chinafst.dy_6260scanner.Tapplication;
import cn.chinafst.dy_6260scanner.utils.LogPrint;

import static cn.chinafst.dy_6260scanner.Tapplication.context;

public class LoginInActivity extends Activity implements View.OnClickListener{
    private Context mContext;
    private EditText etUserName,etPassword;
    private Button btnContact,btnLock,btnCircle01,btnCircle02,btnLogin,btnExit;
    private TextView version;
    private String versionName="";
    private SharedPreferences sp;
    private static final String DB_PATH = "/data/data/cn.chinafst.dy_6260scanner/databases/";
    private static final String DB_NAME = "baseDB";
     private   BluetoothAdapter adapter;
    private ArrayList<String> blueToothMac=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        sp= PreferenceManager.getDefaultSharedPreferences(mContext);
        setContentView(R.layout.activity_main);
        initViews();
        copyDB();
        initBlue();
       // UsbUtils.initPremission(mContext);


    }

    private void initBlue() {
        BluetoothManager manager= (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        adapter=manager.getAdapter();

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            LogPrint.toast(context,"不支持BLE蓝牙设备");
            finish();
        }
        if(!adapter.isEnabled()){
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 100);
          }


    }

    private void scanBlueTooth() {

        Set<BluetoothDevice> bondedDevices = adapter.getBondedDevices();
      for(BluetoothDevice device:bondedDevices){
          if(device.equals("JDY-08")&&!blueToothMac.contains(device.getAddress())){
              blueToothMac.add(device.getAddress());
          }
      }
      if(blueToothMac.size()!=2){
          LogPrint.toast(context,"模块初始化错误,请先设置");
          startActivity(new Intent(context,SearchAndBoudDeviceActivity.class));
      }else{
          startActivity(new Intent(mContext,FunctionIndexActivtiy.class));
      }
    }

    private void initViews() {
        etUserName = (EditText) findViewById(R.id.et_user);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnContact = (Button) findViewById(R.id.btn_contact);
        btnLock = (Button) findViewById(R.id.btn_lock);
        btnCircle01 = (Button) findViewById(R.id.btn_circle_01);
        btnCircle02 = (Button) findViewById(R.id.btn_circle_02);
        btnLogin = (Button) findViewById(R.id.btn_login);


        btnExit = (Button) findViewById(R.id.btn_exit);
        version =(TextView) findViewById(R.id.tv_version);
        try {
            versionName=getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        version.setText("软件版本: "+versionName);
        btnLogin.setOnClickListener(this);
        btnExit.setOnClickListener(this);
        btnCircle01.setOnClickListener(this);
        btnCircle02.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                scanBlueTooth();


                break;
            default:break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == Activity.RESULT_CANCELED) {
            finish();
            return;
        } /*else if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            scanBlueTooth();
        }*/
    }

    private void copyDB(){
        if(sp.getBoolean("isFirst",true)){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String path =DB_PATH+DB_NAME;
                        InputStream myInput = mContext.getAssets().open("baseDB");
                        OutputStream myOutput = new FileOutputStream(path);
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = myInput.read(buffer)) > 0) {
                            myOutput.write(buffer, 0, length);
                        }
                        myOutput.flush();
                        myOutput.close();
                        myInput.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            sp.edit().putBoolean("isFirst",false).apply();
        }
    }
}
