package com.wanghao.volleydemo.ui;

import com.android.volley.toolbox.NetworkImageView;
import com.wanghao.volleydemo.R;
import com.wanghao.volleydemo.app.VolleyApp;
import com.wanghao.volleydemo.data.RequestManager;
import com.wanghao.volleydemo.vendor.VolleyApi;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

//大数据（large payloads ），流媒体，这些case，还需要使用原始的方法，比如Download Manager等
public class ImageRequestActivity extends BaseActivity{

	private ImageView imageViewloder,imageViewrequest; 
	private NetworkImageView mNetworkImageView; 
    private BitmapDrawable mDefaultAvatarBitmap;
    private Drawable mDefaultImageDrawable;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_imagerequest);
		
		mNetworkImageView=(NetworkImageView)findViewById(R.id.networkImageView);  
        imageViewloder=(ImageView) findViewById(R.id.imageViewloder);  
        imageViewrequest=(ImageView) findViewById(R.id.imageViewrequest); 
        
        mDefaultAvatarBitmap = (BitmapDrawable) VolleyApp.getContext()
                .getResources().getDrawable(R.drawable.default_avatar);
        mDefaultImageDrawable = new ColorDrawable(Color.argb(255, 201, 201, 201));
        
        requestByNetworkImageView();  
        requestByImageLoader();
        requestByImageRequest();
        
	}
	
	/*------------------------------------利用NetworkImageView显示网络图片 --------------------------------------*/
	private void requestByNetworkImageView() {
        mNetworkImageView.setDefaultImageResId(R.drawable.default_avatar);//默认图片
        mNetworkImageView.setErrorImageResId(R.drawable.error);//出错时的图片
        mNetworkImageView.setTag("networkimageview");
//      public ImageContainer get(String requestUrl, final ImageListener listener) {
//    		 return get(requestUrl, listener, 0, 0);
//  	}最后事实上调用的是下面这个ImageLoader里面的这个函数，而且是默认尺寸
        mNetworkImageView.setImageUrl(VolleyApi.image_url,RequestManager.getImageLoader()); //请求图片 
	}

	/*-------------------------------------------利用ImageLoader显示图片------------------------------------------	*/ 
    /** 
     * 利用Volley异步加载图片 
     * 无显示时有默认的替代图片
     * 有缓存，无网络时也可显示
     * ImageLoader会检查ImageCache,而且如果缓存里没有图片就会从网络上获取。
     * gif显示为静态图片，不是动图
     * 
     */ 
	private void requestByImageLoader() {
		
       RequestManager.loadImage(VolleyApi.image_url,
    		   RequestManager.getImageListener(imageViewloder, mDefaultAvatarBitmap, mDefaultImageDrawable));
	}


/*-----------------------------------------------  ImageRequest能够处理单张图片，返回bitmap--------------------------------------*/
	
	  private void requestByImageRequest() {
		  RequestManager.loadImage(VolleyApi.image_url, 
				  RequestManager.getImageListener(imageViewrequest, mDefaultAvatarBitmap, mDefaultImageDrawable), 
				  300, 300);
	  }
}
