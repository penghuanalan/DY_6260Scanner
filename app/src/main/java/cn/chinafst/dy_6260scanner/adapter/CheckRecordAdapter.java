package cn.chinafst.dy_6260scanner.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import cn.chinafst.dy_6260scanner.base.CheckRecordBean;

/**
 * Created by Administrator on 2018/1/9.
 */

public class CheckRecordAdapter extends BaseAdapter {
    private Context context;
    private List<CheckRecordBean> list;

    public CheckRecordAdapter(Context context,List<CheckRecordBean> list){
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
        CheckRecordViewHolder viewHolder=null;
      //  if(){}
        return null;
    }
    class CheckRecordViewHolder{
        TextView tvCheckNo,tvSampleName,tvCheckItem,tvCheckValue,tvCheckResult,tvCheckTime;
        CheckBox checkBox;
    }
}
