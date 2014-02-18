package com.me.archko.imagefetcher.maurycyw;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.me.archko.imagefetcher.BaseFlickrPictureActivity;
import com.me.archko.imagefetcher.R;
import com.me.archko.imagefetcher.Utils;
import com.me.archko.imagefetcher.model.FlickrImage;
import com.me.archko.imagefetcher.model.FlickrResponsePhotos;

import java.util.ArrayList;

/**
 * @author archko
 */
public class TestMasterActivity extends BaseFlickrPictureActivity {

    ListView mStaggeredGridView;
    StaggeredFlickrImageAdapter mAdapter;

    /**
     * This will not work so great since the heights of the imageViews
     * are calculated on the iamgeLoader callback ruining the offsets. To fix this try to get
     * the (intrinsic) image width and height and set the views height manually. I will
     * look into a fix once I find extra time.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_main);

        mStaggeredGridView=(ListView) this.findViewById(R.id.staggeredGridView1);
        mStaggeredGridView.setSelector(R.drawable.holo_selector);

        mStaggeredGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FlickrImage flickrImage=(FlickrImage) mAdapter.getItem(position);
                Log.d("", "item:"+position+" image:"+flickrImage);
                Utils.startPictureViewer(flickrImage.getImageUrl(), TestMasterActivity.this);
            }
        });

        mAdapter=new StaggeredFlickrImageAdapter(TestMasterActivity.this);

        mStaggeredGridView.setAdapter(mAdapter);
        initData();
    }

    @Override
    public ArrayList<FlickrImage> parseFlickrImageResponse(FlickrResponsePhotos response) {
        ArrayList<FlickrImage> list=super.parseFlickrImageResponse(response);
        if (null!=list) {
            mAdapter.setDatas(list);

            mAdapter.notifyDataSetChanged();
        }
        return null;
    }
}
