package tcking.github.com.rfm.aacplay.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import tcking.github.com.rfm.aacplay.R;
import tcking.github.com.rfm.aacplay.WebViewActivity;
import tcking.github.com.rfm.aacplay.pojos.latestvideo.Datum;

/**
 * Created by Admin on 4/9/2018.
 */

public class UpcomingVideoAdapter  extends RecyclerView.Adapter<UpcomingVideoAdapter.MyViewHolder> {


    private List<Datum> dataList=new ArrayList<>();
    Context context;



    public class MyViewHolder extends RecyclerView.ViewHolder {

        public SelectableRoundedImageView img_main;
        public TextView tv_title/*,tv_days*/;
        public RelativeLayout rl_main;


        public MyViewHolder(View view) {
            super(view);
            img_main = (SelectableRoundedImageView) view.findViewById(R.id.img_main);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
          //  tv_days = (TextView) view.findViewById(R.id.tv_days);
            rl_main= (RelativeLayout) view.findViewById(R.id.rl_main);
        }
    }


    public UpcomingVideoAdapter(Context context) {
        this.context = context;

    }


    public void addingData(List<Datum> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_latest_videos, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Datum datum = dataList.get(position);


        Picasso.with(context)
                .load(datum.getPoza())
                .resize(0, context.getResources().getDimensionPixelSize(R.dimen._200sdp))
                .placeholder(R.drawable.splash_two)
                .into(holder.img_main);



      /*  holder.img_main.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    // Wait until layout to call Picasso
                    @Override
                    public void onGlobalLayout() {
                        // Ensure we call this only once
                        holder.img_main.getViewTreeObserver()
                                .removeOnGlobalLayoutListener(this);


                        Picasso.with(context)
                                .load(datum.getPoza())
                                .placeholder(R.drawable.splash_two)
                                .resize(0, holder.img_main.getHeight())
                                .into(holder.img_main);
                    }
                });*/


        holder.tv_title.setText(datum.getTitlu());

        holder.rl_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(context, WebViewActivity.class);
                intent1.putExtra("url",datum.getUrl());
                context.startActivity(intent1);
            }
        });

      //  holder.tv_days.setText(""+getDisplayableTime(getTime(datum.getData(),"yyyy-MM-dd hh:mm:ss")));
    }

    @Override
    public int getItemCount() {
        //return 5;
        return  dataList.size();
    }


    public static long getTime(String time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        //sdf.setTimeZone(TimeZone.getDefault());
        sdf.setTimeZone(TimeZone.getDefault());
        String inputString = time;
        try {
            Date date = sdf.parse(inputString);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getDisplayableTime(long delta) {
        long difference = 0;
        Long mDate = System.currentTimeMillis();

        if (mDate > delta) {
            difference = mDate - delta;
            final long seconds = difference / 1000;
            final long minutes = seconds / 60;
            final long hours = minutes / 60;
            final long days = hours / 24;
            final long months = days / 31;
            final long years = days / 365;
            if (seconds < 0) {
                return "not yet";
            } else if (seconds < 60) {
                return "a minute ago";
                //return seconds == 1 ? "one second ago" : seconds + " seconds ago";
            } else if (seconds < 120) {
                return "a minute ago";
            } else if (seconds < 2700) // 45 * 60
            {
                return minutes + " minutes ago";
            } else if (seconds < 5400) // 90 * 60
            {
                return "an hour ago";
            } else if (seconds < 7200) // 120 * 60
            {
                return "an hour ago";
            } else if (seconds < 86400) // 24  60  60
            {
                return hours + " hours ago";
            } else if (seconds < 172800) // 48  60  60
            {
                return "Yesterday";
            } else if (seconds < 2592000) // 30  24  60 * 60
            {
                return days + " days ago";
            }  else {

             // return mySeenMessage+ getDisplayableLongTime(delta);
                return "a minute ago";
            }
        }else {

            return "a minute ago";
        }
    }


}
