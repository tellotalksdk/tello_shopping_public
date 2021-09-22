package com.tilismtech.tellotalk_shopping_sdk_app.gallery.Adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.tilismtech.tellotalk_shopping_sdk_app.R;

import java.util.List;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.MyViewHolder> {
    private List<String> bitmapList;
    private List<Boolean> selected;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView thumbnail;
        public ImageView check;

        MyViewHolder(View view) {
            super(view);
            thumbnail = view.findViewById(R.id.image);
            check = view.findViewById(R.id.image2);
        }
    }

    public MediaAdapter(List<String> bitmapList, List<Boolean> selected) {
        this.bitmapList = bitmapList;
        this.selected = selected;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.media_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onViewRecycled(@NonNull MyViewHolder holder) {
        super.onViewRecycled(holder);
        holder.thumbnail.setImageURI("");
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.thumbnail.setTag("file://" + bitmapList.get(position));
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse("file://" + bitmapList.get(position)))
                .setResizeOptions(new ResizeOptions(153, 160))
                .build();
        holder.thumbnail.setController(
                Fresco.newDraweeControllerBuilder()
                        .setOldController(holder.thumbnail.getController())
                        .setImageRequest(request)
                        .build());
        if (selected.size() > position && selected.get(position).equals(true)) {
            holder.check.setVisibility(View.VISIBLE);
            holder.check.setAlpha(150);
        } else {
            holder.check.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return bitmapList.size();
    }
}