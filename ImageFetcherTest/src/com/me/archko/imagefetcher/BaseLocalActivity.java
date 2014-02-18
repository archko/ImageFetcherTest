package com.me.archko.imagefetcher;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import com.andrew.apollo.utils.ApolloUtils;
import com.me.archko.imagefetcher.model.FlickrImage;
import com.me.archko.imagefetcher.model.FlickrResponsePhotos;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

/**
 * @author archko
 */
public class BaseLocalActivity extends BaseFlickrPictureActivity {

    protected int COUNT=200;
    final int maxSize=1024000;
    final int minSize=20000;
    protected ArrayList<File> mDataList=new ArrayList<File>();
    protected File dir=new File(Environment.getExternalStorageDirectory().getPath()+"/Android/data/cn.archko.microblog/cache/auil/");

    /**
     * This will not work so great since the heights of the imageViews
     * are calculated on the iamgeLoader callback ruining the offsets. To fix this try to get
     * the (intrinsic) image width and height and set the views height manually. I will
     * look into a fix once I find extra time.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("", "getDataDirectory:"+Environment.getDataDirectory().getAbsolutePath());
        Log.d("", "getDownloadCacheDirectory:"+Environment.getDownloadCacheDirectory().getAbsolutePath());
        Log.d("", "getExternalStorageDirectory:"+Environment.getExternalStorageDirectory().getAbsolutePath());
        Log.d("", "getRootDirectory:"+Environment.getRootDirectory().getAbsolutePath());
        Log.d("", "getExternalCacheDir:"+this.getExternalCacheDir());
        //02-18 14:13:59.795: D/(30117): getExternalCacheDir:/storage/emulated/0/Android/data/com.me.archko.imagefetcher/cache
    }

    @Override
    public void flickerGetImagesRequest() {
        ApolloUtils.execute(false, new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object... params) {
                if (dir.exists()) {
                    File[] files=dir.listFiles(new FileFilter() {
                        @Override
                        public boolean accept(File pathname) {
                            int i=0;
                            i++;
                            if (i>COUNT) {
                                return false;
                            }

                            if (pathname.length()>minSize&&pathname.length()<maxSize) {
                                return true;
                            }
                            return false;
                        }
                    });
                    if (files.length>0) {
                        for (File f : files) {
                            mDataList.add(f);
                        }
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                stopProgress();
                if (mDataList.size()>0) {
                    parseFlickrImageResponse(null);
                } else {
                    showToast("init data failed.");
                }
            }
        });
    }

    @Override
    public ArrayList<FlickrImage> parseFlickrImageResponse(FlickrResponsePhotos response) {
        ArrayList<FlickrImage> list=new ArrayList<FlickrImage>();
        File tmp;
        for (int index=0; index<mDataList.size(); index++) {
            tmp=mDataList.get(index);
            FlickrImage flkrImage=new FlickrImage();
            flkrImage.setTitle(tmp.getAbsolutePath());
            flkrImage.url=tmp.getAbsolutePath();
            flkrImage.filesize=tmp.length();
            list.add(flkrImage);
        }

        return list;
    }

    public void deleteDialog(String title, int msg, final int pos) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(title).setMessage(msg)
            .setNegativeButton(getResources().getString(R.string.cancel),
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.cancel();
                    }
                }).setPositiveButton(getResources().getString(R.string.confirm),
            new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    delete(pos);
                    arg0.cancel();
                }
            }).create().show();
    }

    private void delete(final int pos) {
        ApolloUtils.execute(false, new AsyncTask<Object, Object, File>() {
            @Override
            protected File doInBackground(Object... params) {
                return doDelete(pos);
            }

            @Override
            protected void onPostExecute(File o) {
                //Log.d("onPostExecute", "delete"+(o));
                afterDelete(o);
            }
        });
    }

    public File doDelete(int pos) {

        return null;
    }

    public void afterDelete(File o) {

    }
}
