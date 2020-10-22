package com.codbking.calendar.exaple;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.codbking.calendar.CaledarAdapter;
import com.codbking.calendar.CalendarBean;
import com.codbking.calendar.CalendarDateView;
import com.codbking.calendar.CalendarLayout;
import com.codbking.calendar.CalendarUtil;
import com.codbking.calendar.CalendarView;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.codbking.calendar.exaple.Utils.px;


public class DingdingActivity extends AppCompatActivity {

    @BindView(R.id.calendarDateView)
    CalendarDateView mCalendarDateView;
    @BindView(R.id.calendarLayout)
    CalendarLayout mCalendarLayout;
    @BindView(R.id.list)
    ListView mList;
    @BindView(R.id.title)
    TextView mTitle;
    private DingdingActivity mContext;
    private boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = this;

        mCalendarDateView.setAdapter(new CaledarAdapter() {
            //            @SuppressLint("ResourceType")
            @Override
            public View getView(View convertView, ViewGroup parentView, CalendarBean bean) {
                TextView tv_num;
                ImageView iv_dot;
                ImageView current_day_bg;
                if (convertView == null) {
                    convertView = LayoutInflater.from(parentView.getContext()).inflate(R.layout.item_calendar, null);
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(px(48), px(48));
                    convertView.setLayoutParams(params);
                }

                tv_num = (TextView) convertView.findViewById(R.id.text);
                iv_dot = (ImageView) convertView.findViewById(R.id.dot);
                current_day_bg = (ImageView) convertView.findViewById(R.id.current_day_bg);

                tv_num.setText("" + bean.day);

                //判断是否是当月的日期
                if (bean.mothFlag != 0) {
                    tv_num.setTextColor(getResources().getColorStateList(R.color.selector_item_text_color_gray));
                } else {
                    tv_num.setTextColor(getResources().getColorStateList(R.color.selector_item_text_color_nomal));
                }

                //设置1号 标记小蓝点
                if (bean.day == 1) {
                    iv_dot.setBackgroundResource(R.drawable.dot_item);
                } else {
                    iv_dot.setBackgroundColor(Color.TRANSPARENT);
                }

                //标记当天背景为浅灰色
                if (CalendarUtils.getYear() == bean.year && CalendarUtils.getMonth() == bean.moth && CalendarUtils.getDay() == bean.day) {
                    current_day_bg.setBackgroundResource(R.drawable.shape_bg_item);
                }


                return convertView;
            }
        });

        mCalendarDateView.setOnItemClickListener(new CalendarView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion, CalendarBean bean) {
                mTitle.setText(bean.year + "/" + getDisPlayNumber(bean.moth) + "/" + getDisPlayNumber(bean.day));
            }
        });

        int[] data = CalendarUtil.getYMD(new Date());
        mTitle.setText(data[0] + "/" + data[1] + "/" + data[2]);

        mList.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 100;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(DingdingActivity.this).inflate(android.R.layout.simple_list_item_1, null);
                }

                TextView textView = (TextView) convertView;
                textView.setText("item" + position);

                return convertView;
            }
        });

    }

    private String getDisPlayNumber(int num) {
        return num < 10 ? "0" + num : "" + num;
    }

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }

    @OnClick(R.id.openCalendarLayout)
    public void open() {
        if (!isOpen) {
            mCalendarLayout.open();
        } else {
            mCalendarLayout.flod();
        }

        isOpen = !isOpen;

    }
}
