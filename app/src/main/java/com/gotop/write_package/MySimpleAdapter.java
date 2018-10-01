package com.gotop.write_package;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/7.
 */

public class MySimpleAdapter extends SimpleAdapter {
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View v = super.getView(position, convertView, parent);

        ImageButton btn=(ImageButton) v.findViewById(R.id.icon);
        btn.setTag(position);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                //mDataList.remove((int)v.getTag());
                //notifyDataSetChanged();
                //Toast.makeText(parent.getContext(), "ssss"+v.getTag(), 1).show();

            }
        });
        return v;
    }

    public MySimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        // TODO Auto-generated constructor stub
    }
}
