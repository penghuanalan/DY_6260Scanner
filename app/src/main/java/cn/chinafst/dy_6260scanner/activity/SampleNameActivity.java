package cn.chinafst.dy_6260scanner.activity;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.chinafst.dy_6260scanner.R;
import cn.chinafst.dy_6260scanner.base.CommonBaseActivity;

/**
 * Created by Administrator on 2018/1/2.
 */

public class SampleNameActivity extends CommonBaseActivity{
    @Override
    protected void getBegin(View view) {

    }

    @Override
    protected int setLayout() {
        return R.layout.avtivity_sample_name;
    }

    @Override
    protected void doClick(View v) {

    }

    @Override
    protected void settitle(TextView tittle) {
        tittle.setText("样品检测");
    }

    @Override
    protected void setButton(LinearLayout llLayout, TextView page, Button bt1, Button bt2, Button bt3, Button bt4, Button bt5) {

    }
}
