package com.tilismtech.tellotalk_shopping_sdk.gallery;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.tilismtech.tellotalk_shopping_sdk.gallery.Adapters.MediaAdapter;
import com.tilismtech.tellotalk_shopping_sdk.gallery.Fragments.OneFragment;
import com.tilismtech.tellotalk_shopping_sdk.gallery.Fragments.TwoFragment;
import com.tilismtech.tellotalk_shopping_sdk.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class OpenGallery extends AppCompatActivity {

    private MediaAdapter mAdapter;
    private List<String> mediaList = new ArrayList<>();
    public static List<Boolean> selected = new ArrayList<>();
    public static ArrayList<MediaAttachment> imagesSelected = new ArrayList<>();
    public static String parent;
    public Toolbar toolbar;
    RecyclerView recycler_view;
    // ActivityOpenGalleryBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_open_gallery);
      /*  toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        findViewById(R.id.fab).setOnClickListener(view -> finish());
        recycler_view = findViewById(R.id.recycler_view);
    /*    toolbar.setNavigationIcon(R.drawable.arrow_back_sdk);
        toolbar.setTitleTextColor(Color.BLACK);*/
        setTitle(Gallery.title);
        if (imagesSelected.size() > 0) {
            setTitle(String.valueOf(imagesSelected.size()) + " Selected");
        }
      //  toolbar.setNavigationOnClickListener(v -> onBackPressed());
        parent = getIntent().getStringExtra("FROM");
        mediaList.clear();
        selected.clear();
        if (parent.equals("Images")) {
            mediaList.addAll(OneFragment.imagesList);
            selected.addAll(OneFragment.selected);
        } else {
            mediaList.addAll(TwoFragment.videosList);
            selected.addAll(TwoFragment.selected);
        }
        populateRecyclerView();

    }


    private void populateRecyclerView() {
        for (int i = 0; i < selected.size(); i++) {
            if (mediaList.size() > i) {
                MediaAttachment attachment = new MediaAttachment();
                attachment.setFileUri(Uri.parse("file://" + mediaList.get(i)));
                if (imagesSelected.contains(attachment)) {
                    selected.set(i, true);
                } else {
                    selected.set(i, false);
                }
            }
        }
        mAdapter = new MediaAdapter(mediaList, selected);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 3, LinearLayoutManager.VERTICAL, false);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setHasFixedSize(true);
        recycler_view.setItemViewCacheSize(30);
        recycler_view.setDrawingCacheEnabled(true);
        recycler_view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recycler_view.getItemAnimator().setChangeDuration(0);
        recycler_view.setAdapter(mAdapter);
        recycler_view.addOnItemTouchListener(new RecyclerTouchListener(this, recycler_view, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                MediaAttachment attachment = new MediaAttachment();
                attachment.setFileUri(Uri.parse("file://" + mediaList.get(position)));
                if ("Images".equalsIgnoreCase(parent)) {
                    ContentResolver cR = getContentResolver();
                    MimeTypeMap mime = MimeTypeMap.getSingleton();
                    String type = mime.getExtensionFromMimeType(cR.getType(FileBackend.getInstance().getImageContentUri(OpenGallery.this, new File(mediaList.get(position)))));
                    if (type != null && type.contains("gif")) {
                        attachment.setMimeType("image/gif");
                    } else {
                        attachment.setMimeType("image/*");
                    }
                } else {
                    attachment.setMimeType("video/*");
                }
                if (!selected.get(position).equals(true) && imagesSelected.size() < Gallery.maxSelection) {
                    imagesSelected.add(attachment);
                    selected.set(position, !selected.get(position));
                    mAdapter.notifyItemChanged(position);
                } else if (selected.get(position).equals(true)) {
                    if (imagesSelected.indexOf(attachment) != -1) {
                        imagesSelected.remove(imagesSelected.indexOf(attachment));
                        selected.set(position, !selected.get(position));
                        mAdapter.notifyItemChanged(position);
                    }
                }
                Gallery.selectionTitle = imagesSelected.size();
                if (imagesSelected.size() != 0) {
                    setTitle(String.valueOf(imagesSelected.size()) + " Selected");
                } else {
                    setTitle(Gallery.title);
                }
            }

            @Override
            public void onLongClick(View view, int position) {
            }

        }));
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}