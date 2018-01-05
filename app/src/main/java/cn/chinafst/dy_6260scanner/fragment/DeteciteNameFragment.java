package cn.chinafst.dy_6260scanner.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import cn.chinafst.dy_6260scanner.R;

/**
 * Created by Administrator on 2018/1/5.
 */

public class DeteciteNameFragment extends Fragment {
    EditText etSampleNmae;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_detecite_name,container,false);
        etSampleNmae=view.findViewById(R.id.et_sample_name);
        return view;
    }
}
