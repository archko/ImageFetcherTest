package com.me.archko.imagefetcher;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.me.archko.imagefetcher.maurycyw.StaggeredFlickrImageAdapter;
import com.me.archko.imagefetcher.model.FlickrImage;
import com.me.archko.imagefetcher.model.FlickrResponsePhotos;

import java.io.File;
import java.util.ArrayList;

public class ImageFetcherActivity extends BaseLocalActivity {

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
                Utils.startPictureViewer(flickrImage.getImageUrl(), ImageFetcherActivity.this);
            }
        });
        mStaggeredGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                FlickrImage flickrImage=(FlickrImage) mAdapter.getItem(position);
                deleteDialog(getString(R.string.dialog_title)+" size:"+(flickrImage.filesize/1000)+"k", R.string.dialog_msg, position);
                return true;
            }
        });

        mAdapter=new StaggeredFlickrImageAdapter(ImageFetcherActivity.this);

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

    public File doDelete(int pos) {
        if (mAdapter!=null||mAdapter.getCount()>0) {
            FlickrImage flickrImage=(FlickrImage) mAdapter.getItem(pos);
            File file=new File(flickrImage.getTitle());
            boolean flag=file.delete();
            Log.d("doDelete", "pos:"+pos+" flag:"+flag+" delete file:"+file);
            return file;
        }
        return null;
    }

    public void afterDelete(File o) {
        if (null!=o) {
            ArrayList<FlickrImage> list=mAdapter.getDatas();
            int index=0;
            for (FlickrImage flickrImage : list) {
                if (o.getAbsolutePath().equals(flickrImage.getTitle())) {
                    break;
                }
                index++;
            }

            try {
                mDataList.remove(o);
                if (index<list.size()) {
                    list.remove(index);
                    mAdapter.setDatas(list);
                    mAdapter.notifyDataSetChanged();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
