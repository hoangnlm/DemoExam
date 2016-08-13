package vn.t3h.demoexam.viewmodel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import vn.t3h.demoexam.R;
import vn.t3h.demoexam.model.Email;

/**
 * Created by Hoang on 8/12/16.
 */

public class EmailListAdapter extends ArrayAdapter<Email> {
    private Context context;
    private List<Email> emailList;

    public EmailListAdapter(Context context, List<Email> emailList) {
        super(context, R.layout.listview_item_layout, emailList);
        this.context = context;
        this.emailList = emailList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView==null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.ivLogo = (ImageView) convertView.findViewById(R.id.ivLogo);
            viewHolder.ivRate = (ImageView) convertView.findViewById(R.id.ivRate);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
            viewHolder.tvDesc = (TextView) convertView.findViewById(R.id.tvDesc);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Email email = emailList.get(position);
        viewHolder.ivLogo.setImageResource(R.mipmap.ic_launcher);
        viewHolder.ivRate.setImageResource(android.R.drawable.btn_star_big_off);
        viewHolder.tvTitle.setText(email.getTitle());
        viewHolder.tvDesc.setText(email.getDescription());
        viewHolder.tvDate.setText(email.getDate()+"");
        return convertView;
    }

    private class ViewHolder{
        ImageView ivLogo, ivRate;
        TextView tvTitle, tvDate, tvDesc;
    }
}
