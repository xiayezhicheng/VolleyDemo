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

//�����ݣ�large payloads ������ý�壬��Щcase������Ҫʹ��ԭʼ�ķ���������Download Manager��
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
	
	/*------------------------------------����NetworkImageView��ʾ����ͼƬ --------------------------------------*/
	private void requestByNetworkImageView() {
        mNetworkImageView.setDefaultImageResId(R.drawable.default_avatar);//Ĭ��ͼƬ
        mNetworkImageView.setErrorImageResId(R.drawable.error);//����ʱ��ͼƬ
        mNetworkImageView.setTag("networkimageview");
//      public ImageContainer get(String requestUrl, final ImageListener listener) {
//    		 return get(requestUrl, listener, 0, 0);
//  	}�����ʵ�ϵ��õ����������ImageLoader��������������������Ĭ�ϳߴ�
        mNetworkImageView.setImageUrl(VolleyApi.image_url,RequestManager.getImageLoader()); //����ͼƬ 
	}

	/*-------------------------------------------����ImageLoader��ʾͼƬ------------------------------------------	*/ 
    /** 
     * ����Volley�첽����ͼƬ 
     * ����ʾʱ��Ĭ�ϵ����ͼƬ
     * �л��棬������ʱҲ����ʾ
     * ImageLoader����ImageCache,�������������û��ͼƬ�ͻ�������ϻ�ȡ��
     * gif��ʾΪ��̬ͼƬ�����Ƕ�ͼ
     * 
     */ 
	private void requestByImageLoader() {
		
       RequestManager.loadImage(VolleyApi.image_url,
    		   RequestManager.getImageListener(imageViewloder, mDefaultAvatarBitmap, mDefaultImageDrawable));
	}


/*-----------------------------------------------  ImageRequest�ܹ�������ͼƬ������bitmap--------------------------------------*/
	
	  private void requestByImageRequest() {
		  RequestManager.loadImage(VolleyApi.image_url, 
				  RequestManager.getImageListener(imageViewrequest, mDefaultAvatarBitmap, mDefaultImageDrawable), 
				  300, 300);
	  }
}
