package cn.chinafst.dy_6260scanner.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;
import cn.chinafst.dy_6260scanner.R;
import cn.chinafst.dy_6260scanner.base.CheckRecordBean;
import cn.chinafst.dy_6260scanner.base.CommonBaseActivity;
import cn.chinafst.dy_6260scanner.utils.GreenDaoUtils;
import cn.chinafst.dy_6260scanner.utils.ListViewWithMore;

/**
 * Created by Administrator on 2018/1/9.
 */

public class CheckRecordActivity extends CommonBaseActivity {
    private ListViewWithMore listView;
    private List<CheckRecordBean> list;

    @Override
    protected void setRoorView(TextView tittle, FrameLayout centerView) {
        tittle.setText("数据查询");
        View view= LayoutInflater.from(context).inflate(R.layout.activity_check_record,null);
        listView=(ListViewWithMore) view.findViewById(R.id.lv_record);
        initData();
    }

    private void initData() {
       list = GreenDaoUtils.getDaoSession().getCheckRecordBeanDao().queryBuilder().limit(20).list();

    }

    @Override
    protected void doClick(View v) {

    }

    @Override
    protected void setButton(LinearLayout llLayout, TextView page, Button bt1, Button bt2, Button bt3, Button bt4, Button bt5) {
        bt5.setText("返回");
        bt5.setVisibility(View.VISIBLE);

        bt4.setText("导出");
        bt4.setVisibility(View.VISIBLE);

        bt3.setText("删除");
        bt3.setVisibility(View.VISIBLE);

        bt2.setText("上传");
        bt2.setVisibility(View.VISIBLE);

        bt1.setText("打印");
        bt1.setVisibility(View.VISIBLE);
    }
}
