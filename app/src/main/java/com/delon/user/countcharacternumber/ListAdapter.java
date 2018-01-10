package com.delon.user.countcharacternumber;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by User on 2016/06/01.
 */

public class ListAdapter extends ArrayAdapter<ListItem> {

    private LayoutInflater layoutinflater;

    ListAdapter(Context context, int textViewResourceId, List<ListItem> objects){
        super(context, textViewResourceId, objects);
        layoutinflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //指定行のデータを取得
        ListItem listItem = (ListItem)getItem(position);

        //nullの場合のみ再作成
        if (convertView == null){
            convertView = layoutinflater.inflate(R.layout.row,null);
        }

        //行のデータを項目へ設定
        TextView titleText = (TextView)convertView.findViewById(R.id.title_textView);
        titleText.setText(listItem.getTitle());

        TextView countText = (TextView)convertView.findViewById(R.id.the_number_of_count_textView);
        countText.setText(String.valueOf(listItem.getNumber()));

        //返却
        return convertView;

    }

}
