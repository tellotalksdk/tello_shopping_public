package com.tilismtech.tellotalk_shopping_sdk.gallery.Fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.tilismtech.tellotalk_shopping_sdk.R;
import com.tilismtech.tellotalk_shopping_sdk.gallery.Adapters.BucketsAdapter;
import com.tilismtech.tellotalk_shopping_sdk.gallery.OpenGallery;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class TwoFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<String> bucketNames = new ArrayList<>();
    private List<String> bitmapList = new ArrayList<>();
    private final String[] projection = new String[]{MediaStore.Video.Media.BUCKET_DISPLAY_NAME, MediaStore.Video.Media.DATA};
    private final String[] projection2 = new String[]{MediaStore.Video.Media.DISPLAY_NAME, MediaStore.Video.Media.DATA};
    public static List<String> videosList = new ArrayList<>();
    public static List<Boolean> selected = new ArrayList<>();
    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Bucket names reloaded
        bucketNames.clear();
        bitmapList.clear();
        videosList.clear();
        getVideoBuckets();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_one, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        populateRecyclerView();
        return v;
    }

    private void populateRecyclerView() {
        BucketsAdapter mAdapter = new BucketsAdapter(bucketNames, bitmapList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(context, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                getVideos(bucketNames.get(position));
                Intent intent = new Intent(context, OpenGallery.class);
                intent.putExtra("FROM", "Videos");
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
        mAdapter.notifyDataSetChanged();
    }

    public void getVideos(String bucket) {
        selected.clear();
        Cursor cursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection2,
                MediaStore.Video.Media.BUCKET_DISPLAY_NAME + " =?", new String[]{bucket}, MediaStore.Video.Media.DATE_ADDED);
        ArrayList<String> imagesTEMP = null;
        if (cursor != null) {
            imagesTEMP = new ArrayList<>(cursor.getCount());
            HashSet<String> albumSet = new HashSet<>();
            File file;
            if (cursor.moveToLast()) {
                do {
                    if (Thread.interrupted()) {
                        return;
                    }
                    String path = cursor.getString(cursor.getColumnIndex(projection2[1]));
                    file = new File(path);
                    if (file.exists() && !albumSet.contains(path)) {
                        imagesTEMP.add(path);
                        albumSet.add(path);
                        selected.add(false);
                    }
                } while (cursor.moveToPrevious());
            }
            cursor.close();
            videosList.clear();
            videosList.addAll(imagesTEMP);
        }
    }

    public void getVideoBuckets() {
        Cursor cursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection,
                null, null, MediaStore.Video.Media.DATE_ADDED);
        ArrayList<String> bucketNamesTEMP = null;
        if (cursor != null) {
            bucketNamesTEMP = new ArrayList<>(cursor.getCount());
            ArrayList<String> bitmapListTEMP = new ArrayList<>(cursor.getCount());
            HashSet<String> albumSet = new HashSet<>();
            File file;
            if (cursor.moveToLast()) {
                do {
                    if (Thread.interrupted()) {
                        return;
                    }
                    String album = cursor.getString(cursor.getColumnIndex(projection[0]));
                    String image = cursor.getString(cursor.getColumnIndex(projection[1]));
                    file = new File(image);
                    if (file.exists() && !albumSet.contains(album)) {
                        bucketNamesTEMP.add(album);
                        bitmapListTEMP.add(image);
                        albumSet.add(album);
                    }
                } while (cursor.moveToPrevious());
            }
            cursor.close();
            bucketNames.clear();
            bitmapList.clear();
            bucketNames.addAll(bucketNamesTEMP);
            bitmapList.addAll(bitmapListTEMP);
        }
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    }
}