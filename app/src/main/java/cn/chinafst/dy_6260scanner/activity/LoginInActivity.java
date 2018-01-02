package cn.chinafst.dy_6260scanner.activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import cn.chinafst.dy_6260scanner.R;
import cn.chinafst.dy_6260scanner.Tapplication;

public class LoginInActivity extends Activity implements View.OnClickListener{
    private Context mContext;
    private EditText etUserName,etPassword;
    private Button btnContact,btnLock,btnCircle01,btnCircle02,btnLogin,btnExit;
    private TextView version;
    private String versionName="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        setContentView(R.layout.activity_main);
        initViews();
        initBlue();
       // UsbUtils.initPremission(mContext);
    }

    private void initBlue() {
        BluetoothManager manager= (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter adapter=manager.getAdapter();
        if(!adapter.isEnabled()){
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 100);
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
                startActivity(new Intent(mContext,FunctionIndexActivtiy.class));
                finish();
                break;
            default:break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == Activity.RESULT_CANCELED) {
            finish();
            return;
        } else if (requestCode == 100 && resultCode == Activity.RESULT_OK) {


        }
    }
}
