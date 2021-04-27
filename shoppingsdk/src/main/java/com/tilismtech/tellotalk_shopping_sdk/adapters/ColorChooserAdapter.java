package com.tilismtech.tellotalk_shopping_sdk.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.tilismtech.tellotalk_shopping_sdk.R;
import com.tilismtech.tellotalk_shopping_sdk.pojos.ColorChooserPojo;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ColorThemeResponse;

import java.util.List;

public class ColorChooserAdapter extends RecyclerView.Adapter<ColorChooserAdapter.ColorViewHolder> {

    List<ColorChooserPojo> colorList;
    Context myCtx;
    OnColorChooserListener onColorChooserListener;

    public ColorChooserAdapter(List<ColorChooserPojo> colorList, Context myCtx, OnColorChooserListener onColorChooserListener) {
        this.colorList = colorList;
        this.myCtx = myCtx;
        this.onColorChooserListener = onColorChooserListener;
    }

    public void setColorList(List<ColorChooserPojo> colorList) {
        this.colorList = colorList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ColorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_choose_color_item, parent, false);
        return new ColorViewHolder(v, this.onColorChooserListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ColorViewHolder holder, int position) {
        ColorChooserPojo colorChooserPojo = colorList.get(position);

        if (colorChooserPojo.isSelected()) {
            holder.circles.setImageDrawable(myCtx.getResources().getDrawable(R.drawable.ic_tick));
        } else {
            //holder.circles.setImageDrawable(myCtx.getResources().getDrawable(R.drawable.circle));
            holder.circles.setImageResource(android.R.color.transparent);
        }


         // holder.circles.setBackgroundColor(Color.parseColor(colorChooserPojo.getColor()));
       // holder.card.setCardBackgroundColor(Color.parseColor(colorChooserPojo.getColor()));

        holder.linearLayout.getBackground().setColorFilter(Color.parseColor(colorChooserPojo.getColor()), PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    public int getItemCount() {
        return colorList.size();
    }

    public class ColorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView circles;
        OnColorChooserListener onColorChooserListener;
        CardView card;
        LinearLayout linearLayout;

        public ColorViewHolder(@NonNull View itemView, OnColorChooserListener onColorChooserListener) {
            super(itemView);
            circles = itemView.findViewById(R.id.colorChooser);
          //  card = itemView.findViewById(R.id.card);
            linearLayout = itemView.findViewById(R.id.linearLL);
            this.onColorChooserListener = onColorChooserListener;
            circles.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.colorChooser) {
                this.onColorChooserListener.onColorClick(getAdapterPosition(), circles);
            }
        }
    }

    public interface OnColorChooserListener {
        void onColorClick(int position, ImageView circles);
    }
}
