package cn.chinafst.dy_6260scanner.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.chinafst.dy_6260scanner.R;
import cn.chinafst.dy_6260scanner.base.CommonBaseActivity;
import cn.chinafst.dy_6260scanner.service.BluetoothLeService;
import cn.chinafst.dy_6260scanner.utils.LogPrint;

/**
 * Created by Administrator on 2018/1/3.
 */

public class SearchAndBoudDeviceActivity extends CommonBaseActivity {
    private ListView listView;
    private BluetoothAdapter adapter;
    private ArrayList<BluetoothDevice> devices = new ArrayList<>();
    private DeviceAdapter deviceAdapter;
    private ArrayList<String> deviceMac=new ArrayList<>();
    private   BluetoothManager manager;
    private BluetoothLeService mBluetoothLeService;
    private ArrayList<String> bundDevices=new ArrayList<>();


    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {

        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {

            if (!deviceMac.contains(device.getAddress())) {
                devices.add(device);
                deviceMac.add(device.getAddress());
                deviceAdapter.notifyDataSetChanged();
                LogPrint.e("发现设备"+device.getName()+"--"+device.getAddress());
            }
        }
    };


    @Override
    protected void setRoorView(TextView tittle, FrameLayout centerView) {
        tittle.setText("模块设置");
        View view = LayoutInflater.from(context).inflate(R.layout.avtivity_search_bond, null);
        listView = (ListView) view.findViewById(R.id.lv_devics);
        deviceAdapter=new DeviceAdapter(context,devices);
        centerView.addView(view);
        doSearch();
    }

    private void doSearch() {
         manager = (BluetoothManager)context.getSystemService(Context.BLUETOOTH_SERVICE);
        adapter = manager.getAdapter();


        adapter.startLeScan(mLeScanCallback);
        listView.setAdapter(deviceAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                // 连接新装置
                adapter.stopLeScan(mLeScanCallback);
                bundDevices.add(devices.get(i).getAddress());
                if(bundDevices.size()==2){
                    Intent intent= new Intent(context,ChannelSettingActivity.class);
                    intent.putStringArrayListExtra("devices",bundDevices);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    protected void doClick(View v) {

    }

    @Override
    protected void setButton(LinearLayout llLayout, TextView page, Button bt1, Button bt2, Button bt3, Button bt4, Button bt5) {

    }

    class DeviceAdapter extends BaseAdapter{

        private Context context;
        private ArrayList<BluetoothDevice> list;

        public DeviceAdapter(Context context,ArrayList<BluetoothDevice> list){
            this.context=context;
            this.list=list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            DeviceHolder holder=null;
            if(view==null){
                view=LayoutInflater.from(context).inflate(R.layout.item_device_search,null);
                holder=new DeviceHolder();
                holder.deviceNmae=(TextView) view.findViewById(R.id.tv_device_name);
                holder.deviceAddress=(TextView) view.findViewById(R.id.tv_device_address);
                view.setTag(holder);
            }else{
                holder= (DeviceHolder) view.getTag();
            }
            holder.deviceNmae.setText(list.get(i).getName());
            holder.deviceAddress.setText(list.get(i).getAddress());
            return view;
        }
    }
    class DeviceHolder{
        TextView deviceNmae;
        TextView deviceAddress;
    }
}
