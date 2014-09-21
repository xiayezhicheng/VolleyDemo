package com.wanghao.volleydemo.data;

import java.io.File;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.widget.ImageView;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.wanghao.volleydemo.app.VolleyApp;
import com.wanghao.volleydemo.util.CacheUtils;

public class RequestManager {
	  public static RequestQueue mRequestQueue = newRequestQueue();

	    // ȡ�����ڴ���ֵ��1/3��ΪͼƬ����
	    private static final int MEM_CACHE_SIZE = 1024 * 1024 * ((ActivityManager)VolleyApp.getContext()
	            .getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass() / 3;

	    private static ImageLoader mImageLoader = new ImageLoader(mRequestQueue, new BitmapLruCache(MEM_CACHE_SIZE));

	    private static DiskBasedCache mDiskCache = (DiskBasedCache) mRequestQueue.getCache();

	    private RequestManager(){

	    }

	    private static Cache openCache() {
	        return new DiskBasedCache(CacheUtils.getExternalCacheDir(VolleyApp.getContext()),
	                10 * 1024 * 1024);
	    }

	    private static RequestQueue newRequestQueue() {
	        RequestQueue requestQueue = new RequestQueue(openCache(), new BasicNetwork(new HurlStack()));
	        requestQueue.start();
	        return requestQueue;
	    }

	    public static void addRequest(Request request, Object tag) {
	        if (tag != null) {
	            request.setTag(tag);
	        }
	        mRequestQueue.add(request);
	    }
	    
	    public static RequestQueue getRequestQueue() {
	        if (mRequestQueue != null) {
	            return mRequestQueue;
	        } else {
	            throw new IllegalStateException("RequestQueue not initialized");
	        }
	    }
	    public static void cancelAll(Object tag) {
	        mRequestQueue.cancelAll(tag);
	    }

	    public static File getCachedImageFile(String url) {
	        return mDiskCache.getFileForKey(url);
	    }

	    public static ImageLoader.ImageContainer loadImage(String requestUrl,
	            ImageLoader.ImageListener imageListener) {
	        return loadImage(requestUrl, imageListener, 0, 0);
	    }

	    public static ImageLoader.ImageContainer loadImage(String requestUrl,
	            ImageLoader.ImageListener imageListener, int maxWidth, int maxHeight) {
	        return mImageLoader.get(requestUrl, imageListener, maxWidth, maxHeight);
	    }

	    public static ImageLoader.ImageListener getImageListener(final ImageView view,
	            final Drawable defaultImageDrawable, final Drawable errorImageDrawable) {
	        return new ImageLoader.ImageListener() {
	            @Override
	            public void onErrorResponse(VolleyError error) {
	                if (errorImageDrawable != null) {
	                    view.setImageDrawable(errorImageDrawable);
	                }
	            }

	            @Override
	            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
	                if (response.getBitmap() != null) {
	                    if (!isImmediate && defaultImageDrawable != null) {
	                        TransitionDrawable transitionDrawable = new TransitionDrawable(
	                                new Drawable[] {
	                                        defaultImageDrawable,
	                                        new BitmapDrawable(VolleyApp.getContext().getResources(),
	                                                response.getBitmap())
	                                });
	                        transitionDrawable.setCrossFadeEnabled(true);
	                        view.setImageDrawable(transitionDrawable);
	                        transitionDrawable.startTransition(100);
	                    } else {
	                        view.setImageBitmap(response.getBitmap());
	                    }
	                } else if (defaultImageDrawable != null) {
	                    view.setImageDrawable(defaultImageDrawable);
	                }
	            }
	        };
	    }
	    
	    public static ImageLoader getImageLoader(){
	    	return mImageLoader;
	    }
}
