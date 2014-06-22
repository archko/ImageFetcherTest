package com.andrew.apollo.cache;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * @author: archko 14-2-18 :下午1:55
 */
public class ImageOption {

    private int maxWidth;
    private int maxHeight;
    private Bitmap.Config mConfig;
    /**
     * 加载成功或失败的id,如果是0,则用黑底的.如果是-1,则不设置
     * 这个用处不大,而且是统一的,全局变量.应该要在传入的参数作一些修改,然后可以加入不同的值.
     */
    private int imageResOnLoading=0;
    private int imageResForEmptyUri=0;
    private int imageResOnFail=0;
    private Drawable imageOnLoading=null;
    private Drawable imageForEmptyUri=null;
    private Drawable imageOnFail=null;
    private boolean cacheInMemory;
    private boolean cacheOnDisk;

    public ImageOption() {
        maxWidth=ImageFetcher.DEFAULT_MAX_IMAGE_WIDTH;
        maxHeight=ImageFetcher.DEFAULT_MAX_IMAGE_HEIGHT;
        mConfig=Bitmap.Config.RGB_565;
    }

    public ImageOption(Builder builder) {
        maxWidth=ImageFetcher.DEFAULT_MAX_IMAGE_WIDTH;
        maxHeight=ImageFetcher.DEFAULT_MAX_IMAGE_HEIGHT;
        mConfig=builder.mConfig;
        imageResOnLoading=builder.imageResOnLoading;
        imageResForEmptyUri=builder.imageResForEmptyUri;
        imageResOnFail=builder.imageResOnFail;
        imageOnLoading=builder.imageOnLoading;
        imageForEmptyUri=builder.imageForEmptyUri;
        imageOnFail=builder.imageOnFail;
    }

    public void setMaxWidth(int maxWidth) {
        this.maxWidth=maxWidth;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight=maxHeight;
    }

    public void setConfig(Bitmap.Config mConfig) {
        this.mConfig=mConfig;
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public Bitmap.Config getConfig() {
        return mConfig;
    }

    public static class Builder {

        private int imageResOnLoading=0;
        private int imageResForEmptyUri=0;
        private int imageResOnFail=0;
        private Drawable imageOnLoading=null;
        private Drawable imageForEmptyUri=null;
        private Drawable imageOnFail=null;
        private boolean resetViewBeforeLoading=false;
        private boolean cacheInMemory=true;
        private boolean cacheOnDisk=true;
        private Bitmap.Config mConfig;
        private int delayBeforeLoading=0;
        private boolean considerExifParams=false;
        private Object extraForDownloader=null;
        //private Handler handler=null;
        private boolean isSyncLoading=false;

        public Builder() {
            mConfig=Bitmap.Config.RGB_565;
        }

        /**
         * Stub image will be displayed in {@link com.nostra13.universalimageloader.core.imageaware.ImageAware
         * image aware view} during image loading
         *
         * @param imageRes Stub image resource
         * @deprecated Use {@link #showImageOnLoading(int)} instead
         */
        @Deprecated
        public Builder showStubImage(int imageRes) {
            imageResOnLoading=imageRes;
            return this;
        }

        /**
         * Incoming image will be displayed in {@link com.nostra13.universalimageloader.core.imageaware.ImageAware
         * image aware view} during image loading
         *
         * @param imageRes Image resource
         */
        public Builder showImageOnLoading(int imageRes) {
            imageResOnLoading=imageRes;
            return this;
        }

        /**
         * Incoming drawable will be displayed in {@link com.nostra13.universalimageloader.core.imageaware.ImageAware
         * image aware view} during image loading.
         * This option will be ignored if {@link DisplayImageOptions.Builder#showImageOnLoading(int)} is set.
         */
        public Builder showImageOnLoading(Drawable drawable) {
            imageOnLoading=drawable;
            return this;
        }

        /**
         * Incoming image will be displayed in {@link com.nostra13.universalimageloader.core.imageaware.ImageAware
         * image aware view} if empty URI (null or empty
         * string) will be passed to <b>ImageLoader.displayImage(...)</b> method.
         *
         * @param imageRes Image resource
         */
        public Builder showImageForEmptyUri(int imageRes) {
            imageResForEmptyUri=imageRes;
            return this;
        }

        /**
         * Incoming drawable will be displayed in {@link com.nostra13.universalimageloader.core.imageaware.ImageAware
         * image aware view} if empty URI (null or empty
         * string) will be passed to <b>ImageLoader.displayImage(...)</b> method.
         * This option will be ignored if {@link DisplayImageOptions.Builder#showImageForEmptyUri(int)} is set.
         */
        public Builder showImageForEmptyUri(Drawable drawable) {
            imageForEmptyUri=drawable;
            return this;
        }

        /**
         * Incoming image will be displayed in {@link com.nostra13.universalimageloader.core.imageaware.ImageAware
         * image aware view} if some error occurs during
         * requested image loading/decoding.
         *
         * @param imageRes Image resource
         */
        public Builder showImageOnFail(int imageRes) {
            imageResOnFail=imageRes;
            return this;
        }

        /**
         * Incoming drawable will be displayed in {@link com.nostra13.universalimageloader.core.imageaware.ImageAware
         * image aware view} if some error occurs during
         * requested image loading/decoding.
         * This option will be ignored if {@link DisplayImageOptions.Builder#showImageOnFail(int)} is set.
         */
        public Builder showImageOnFail(Drawable drawable) {
            imageOnFail=drawable;
            return this;
        }

        /**
         * Loaded image will be cached in memory
         *
         * @deprecated Use {@link #cacheInMemory(boolean) cacheInMemory(true)} instead
         */
        @Deprecated
        public Builder cacheInMemory() {
            cacheInMemory=true;
            return this;
        }

        /**
         * Sets whether loaded image will be cached in memory
         */
        public Builder cacheInMemory(boolean cacheInMemory) {
            this.cacheInMemory=cacheInMemory;
            return this;
        }

        /**
         * Loaded image will be cached on disk
         *
         * @deprecated Use {@link #cacheOnDisk(boolean) cacheOnDisk(true)} instead
         */
        @Deprecated
        public Builder cacheOnDisc() {
            return cacheOnDisk(true);
        }

        /**
         * Sets whether loaded image will be cached on disk
         *
         * @deprecated Use {@link #cacheOnDisk(boolean)} instead
         */
        @Deprecated
        public Builder cacheOnDisc(boolean cacheOnDisk) {
            return cacheOnDisk(cacheOnDisk);
        }

        /**
         * Sets whether loaded image will be cached on disk
         */
        public Builder cacheOnDisk(boolean cacheOnDisk) {
            this.cacheOnDisk=cacheOnDisk;
            return this;
        }

        /**
         * Sets {@link Bitmap.Config bitmap config} for image decoding. Default value - {@link Bitmap.Config#ARGB_8888}
         */
        public Builder bitmapConfig(Bitmap.Config bitmapConfig) {
            if (bitmapConfig==null) throw new IllegalArgumentException("bitmapConfig can't be null");
            mConfig=bitmapConfig;
            return this;
        }

        public ImageOption build() {
            return new ImageOption(this);
        }
    }

    /**
     * Creates options appropriate for single displaying:
     * <ul>
     * <li>View will <b>not</b> be reset before loading</li>
     * <li>Loaded image will <b>not</b> be cached in memory</li>
     * <li>Loaded image will <b>not</b> be cached on disk</li>
     * <li>{@link ImageScaleType#IN_SAMPLE_POWER_OF_2} decoding type will be used</li>
     * <li>{@link Bitmap.Config#ARGB_8888} bitmap config will be used for image decoding</li>
     * <li>{@link SimpleBitmapDisplayer} will be used for image displaying</li>
     * </ul>
     * <p/>
     * These option are appropriate for simple single-use image (from drawables or from Internet) displaying.
     */
    public static ImageOption createSimple() {
        return new Builder().build();
    }
}
