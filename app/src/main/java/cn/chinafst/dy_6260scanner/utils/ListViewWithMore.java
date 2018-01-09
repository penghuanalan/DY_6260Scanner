package cn.chinafst.dy_6260scanner.utils;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class ListViewWithMore extends ListView {

	Context context = this.getContext();
	FootViewListener footViewListener;
	TextView textView;

	public interface FootViewListener {
		void onClick();
	}

	public void setFootViewListener(FootViewListener footViewListener) {
		this.footViewListener = footViewListener;
	}

	public ListViewWithMore(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void showFootView() {

		if (this.getFooterViewsCount() == 0) {

			textView = new TextView(context);
			textView.setGravity(Gravity.CENTER);
			textView.setPadding(10, 20, 10, 20);
			textView.setTextColor(Color.BLACK);
			textView.setTextSize(17);
			textView.setText("加载更多");
			textView.setLayoutParams(new LayoutParams(
					android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
					android.view.ViewGroup.LayoutParams.WRAP_CONTENT));

			textView.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_DOWN
							|| event.getAction() == MotionEvent.ACTION_MOVE) {
						textView.setTextColor(Color.WHITE);
						textView.setBackgroundColor(Color.parseColor("#3D6CE0"));
					} else {
						textView.setTextColor(Color.parseColor("#333333"));
						textView.setBackgroundColor(Color.TRANSPARENT);
					}

					return false;
				}
			});

			textView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					footViewListener.onClick();
				}
			});

			this.addFooterView(textView);
		}
	}

	public void removeFootView() {
		if (this.getFooterViewsCount() > 0) {
			this.removeFooterView(textView);
		}
	}
}
