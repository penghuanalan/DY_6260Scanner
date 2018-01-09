package cn.chinafst.dy_6260scanner.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

import cn.chinafst.dy_6260scanner.R;
import cn.chinafst.dy_6260scanner.utils.LogPrint;

/**
 * Created by Administrator on 2018/1/5.
 */

public class DeteciteNameFragment extends Fragment {
    private TextView etSampleNmae,tvResult,tvConclution;
    private LineChart chart;
    private double[] data;
    private double[] result;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_detecite_name,container,false);

        chart=(LineChart)view.findViewById(R.id.chart_one);
        etSampleNmae=view.findViewById(R.id.et_sample_name);
        tvConclution=(TextView) view.findViewById(R.id.tv_sample_conclusion);
        tvResult=(TextView) view.findViewById(R.id.tv_sample_result);
        data= getArguments().getDoubleArray("data");
        result=getArguments().getDoubleArray("result");
        tvResult.setText(result[1]+"");
        if(result[0]>0.05){
            if(result[1]>0.05){
                tvConclution.setText("阴性");
            }else if(result[1]<0.02){
                tvConclution.setText("阳性");
            }else{
                tvConclution.setText("可疑");
            }
        }else{
            tvConclution.setText("无效");
        }
        LogPrint.e("fragment长度"+data.length);
        chart.setScaleEnabled(true);
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<String>();
        for(int i=0;i<data.length;i++){
            yVals.add(new Entry(i,(float)data[i]));
            xVals.add(i+"");
        }
        LogPrint.e(yVals.toString());



        LineDataSet set1=new LineDataSet(yVals,"table");

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1);
        LineData data = new LineData(dataSets);
        chart.setData(data);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        chart.clear();
    }
}
