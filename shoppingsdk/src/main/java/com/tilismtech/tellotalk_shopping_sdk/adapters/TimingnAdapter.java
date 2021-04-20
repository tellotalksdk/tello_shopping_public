package com.tilismtech.tellotalk_shopping_sdk.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tilismtech.tellotalk_shopping_sdk.R;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GetTimingsResponse;

import java.util.List;

public class TimingnAdapter extends RecyclerView.Adapter<TimingnAdapter.TimingVH> {

    List<GetTimingsResponse.Request> timings;
    Context myCtx;

    public TimingnAdapter(Context myCtx, List<GetTimingsResponse.Request> requestList) {
        this.myCtx = myCtx;
        this.timings = requestList;
    }

    @NonNull
    @Override
    public TimingVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_timings_layout, parent, false);
        return new TimingVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TimingVH holder, int position) {
        GetTimingsResponse.Request request = timings.get(position);
        holder.Day.setText(request.getShopDayName());
        holder.timings.setText(request.getShopStartTime() + " / " + request.getShopEndTime());

        if (request.getShopStatusDaywise().equals("Y")) {
            holder.relativeLayout.setVisibility(View.VISIBLE);
        } else {
            holder.relativeLayout.setAlpha(0.5f);
        }
    }

    @Override
    public int getItemCount() {
        return timings.size();
    }

    public class TimingVH extends RecyclerView.ViewHolder {
        public TextView Day, timings;
        public RelativeLayout relativeLayout;

        public TimingVH(@NonNull View itemView) {
            super(itemView);
            Day = itemView.findViewById(R.id.Day);
            timings = itemView.findViewById(R.id.timings);
            this.relativeLayout = itemView.findViewById(R.id.RLouter);
        }
    }
}
