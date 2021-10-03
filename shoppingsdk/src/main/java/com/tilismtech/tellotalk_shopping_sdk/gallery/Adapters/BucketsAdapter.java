package com.tilismtech.tellotalk_shopping_sdk.gallery.Adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.tilismtech.tellotalk_shopping_sdk.R;

import java.util.List;

public class BucketsAdapter extends RecyclerView.Adapter<BucketsAdapter.MyViewHolder> {
    private List<String> bucketNames, bitmapList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        SimpleDraweeView thumbnail;

        MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            thumbnail = view.findViewById(R.id.image);
        }
    }

    public BucketsAdapter(List<String> bucketNames, List<String> bitmapList) {
        this.bucketNames = bucketNames;
        this.bitmapList = bitmapList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onViewRecycled(@NonNull MyViewHolder holder) {
        super.onViewRecycled(holder);
        holder.thumbnail.setImageURI("");
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        bucketNames.get(position);
        holder.title.setText(bucketNames.get(position));
        holder.thumbnail.setTag("file://" + bitmapList.get(position));
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse("file://" + bitmapList.get(position)))
                .setResizeOptions(new ResizeOptions(300, 300))
                .build();
        holder.thumbnail.setController(
                Fresco.newDraweeControllerBuilder()
                        .setOldController(holder.thumbnail.getController())
                        .setImageRequest(request)
                        .build());
    }

    @Override
    public int getItemCount() {
        return bucketNames.size();
    }
}