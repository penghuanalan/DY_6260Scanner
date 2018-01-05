package cn.chinafst.dy_6260scanner.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cn.chinafst.dy_6260scanner.R;
import cn.chinafst.dy_6260scanner.base.DetectItemBeans;
import cn.chinafst.dy_6260scanner.base.CommonBaseActivity;
import cn.chinafst.dy_6260scanner.utils.GreenDaoUtils;
import cn.chinafst.dy_6260scanner.utils.LogPrint;

/**
 * Created by Administrator on 2018/1/2.
 */

public class SampleNameActivity extends CommonBaseActivity implements AdapterView.OnItemClickListener {
    private GridView gridView;
    List<DetectItemBeans> list;
    private static boolean channelOneClick=false;
    private static boolean channelTwoClick=false;
    private Button channelOne,channelTwo;
    AlertDialog alertDialog;
    private DetectItemBeans detectItemBeans;


  public static void   getActivityStart(){}

    @Override
    protected void setRoorView(TextView tittle, FrameLayout centerView) {
        tittle.setText("样品检测");
        View view = LayoutInflater.from(context).inflate(R.layout.activtiy_function_index, null);
        gridView = (GridView) view.findViewById(R.id.gv_function);
        gridView.setOnItemClickListener(this);
        list = GreenDaoUtils.getDaoSession().getDetectItemBeansDao().queryBuilder().list();
        SampleListAdapter adapter = new SampleListAdapter(context, list);
        gridView.setAdapter(adapter);
        centerView.addView(view);
    }

    @Override
    protected void doClick(View v) {
        switch (v.getId()) {
            case R.id.ib_channel_one:
                if(channelOneClick){
                    channelOne.setSelected(false);
                    channelOneClick=false;
                }else{
                    channelOne.setSelected(true);
                    channelOneClick=true;
                }

                break;
            case R.id.ib_channel_two:
                if(channelTwoClick){
                    channelTwo.setSelected(false);
                    channelTwoClick=false;
                }else{
                    channelTwo.setSelected(true);
                    channelTwoClick=true;
                }
                break;
            case R.id.btn_confirm:

                Intent intent= new Intent(context,DeteciteOneActivity.class);
                intent.putExtra("bean",detectItemBeans);
                if(channelOneClick&&channelTwoClick){
                    intent.putExtra("channel","double");
                    channelOneClick=false;
                    channelTwoClick=false;
                }else if(channelOneClick&&!channelTwoClick){
                   intent.putExtra("channel","one");
                    channelOneClick=false;
                    channelTwoClick=false;
                }else if(!channelOneClick&&channelTwoClick){
                    intent.putExtra("channel","two");
                    channelOneClick=false;
                    channelTwoClick=false;
                }
                startActivity(intent);
                alertDialog.dismiss();
                break;
            case R.id.btn_no_back:
                channelOneClick=false;
                channelTwoClick=false;
                alertDialog.dismiss();
                break;
            case R.id.btn_back:
                finish();
                break;

        }
    }

    @Override
    protected void setButton(LinearLayout llLayout, TextView page, Button bt1, Button bt2, Button bt3, Button bt4, Button bt5) {
        bt5.setVisibility(View.VISIBLE);
        bt5.setText("返回");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        detectItemBeans=list.get(i);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        //自定义UI
        Window window = alertDialog.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = 0.7f;
        window.setAttributes(params);
        window.setContentView(R.layout.sample_detail);
        channelOne=(Button) window.findViewById(R.id.ib_channel_one);
        channelTwo=(Button) window.findViewById(R.id.ib_channel_two);
      /*  Intent intent = new Intent(context, DeteciteOneActivity.class);

        intent.putExtra("bean", list.get(i));
        startActivity(intent);*/
    }

    class SampleListAdapter extends BaseAdapter {
        private Context context;
        private List<DetectItemBeans> list;

        public SampleListAdapter(Context context, List<DetectItemBeans> list) {
            this.context = context;
            this.list = list;
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
            SampleHolder holder = null;
            if (view == null) {
                holder = new SampleHolder();
                view = LayoutInflater.from(context).inflate(R.layout.item_samle_grid, null);
                holder.sampleName = (TextView) view.findViewById(R.id.sample_name);
                holder.sampleType = (TextView) view.findViewById(R.id.tv_check_type);
                view.setTag(holder);
            } else {
                holder = (SampleHolder) view.getTag();
            }
            holder.sampleName.setText(list.get(i).getDetect_item_name());
            return view;
        }
    }

    class SampleHolder {
        TextView sampleName;
        TextView sampleType;
    }
}
