package com.tilismtech.tellotalk_shopping_sdk.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tilismtech.tellotalk_shopping_sdk.R;

import java.util.List;

public class ColorChooserAdapter extends RecyclerView.Adapter<ColorChooserAdapter.ColorViewHolder> {

    List<Integer> colorList ;
    Context myCtx;

    public ColorChooserAdapter(List<Integer> colorList, Context myCtx) {
        this.colorList = colorList;
        this.myCtx = myCtx;
    }

    @NonNull
    @Override
    public ColorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_choose_color_item, parent, false);
        return new ColorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ColorViewHolder holder, int position) {
        holder.circles.setImageDrawable(myCtx.getResources().getDrawable(colorList.get(position)));
    }

    @Override
    public int getItemCount() {
        return colorList.size();
    }

    public class ColorViewHolder extends RecyclerView.ViewHolder {

        ImageView circles;

        public ColorViewHolder(@NonNull View itemView) {
            super(itemView);
            circles = itemView.findViewById(R.id.colorChooser);
        }
    }
}
