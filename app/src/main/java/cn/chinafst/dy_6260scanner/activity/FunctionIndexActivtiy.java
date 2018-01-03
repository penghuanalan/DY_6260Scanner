package cn.chinafst.dy_6260scanner.activity;

import android.content.Intent;
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

/**
 * Created by Administrator on 2017/12/8.
 */

public class FunctionIndexActivtiy extends CommonBaseActivity implements AdapterView.OnItemClickListener{
    private GridView gridView;
    @Override
    protected void setRoorView(TextView tittle, FrameLayout centerView) {
        tittle.setText("主菜单");
        View view=LayoutInflater.from(context).inflate(R.layout.activtiy_function_index,null);
        gridView=(GridView)view.findViewById(R.id.gv_function);
        gridView.setOnItemClickListener(this);
        gridView.setAdapter(new FunctionAdapter());
        centerView.addView(view);
    }

    @Override
    protected void doClick(View v) {

    }


    @Override
    protected void setButton(LinearLayout llLayout, TextView page, Button bt1, Button bt2, Button bt3, Button bt4, Button bt5) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i){
            case 0:
                startActivity(new Intent(context,SampleNameActivity.class));
                break;
            default:break;
        }

    }

    class FunctionAdapter extends BaseAdapter{
        private int[] imgs={R.drawable.the_sample_testing,R.drawable.query_log,R.drawable.project_settings,
                R.drawable.data_management,R.drawable.system_settings,R.drawable.log_out,};
        private String[] items={"样品检测","查询记录","项目设置","数据管理","系统设置","退出系统"};

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
            View view1= LayoutInflater.from(context).inflate(R.layout.item_function_index,viewGroup,false);
            ImageView imageView= (ImageView)view1.findViewById(R.id.iv_icon);
            TextView textView=(TextView)view1.findViewById(R.id.tv_item);
            imageView.setImageResource(imgs[i]);
            textView.setText(items[i]);
            return view1;
        }
    }
}
