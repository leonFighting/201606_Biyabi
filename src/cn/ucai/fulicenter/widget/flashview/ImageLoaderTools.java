package cn.ucai.fulicenter.widget.flashview;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import cn.ucai.fulicenter.R;

//UIL
public class ImageLoaderTools{

	private static ImageLoaderTools mImageLoaderWrapper;
	private static ImageLoader mImageLoader;
	private static final int DISK_CACHE_SIZE_BYTES = 50 * 1024 * 1024;
	private static final int MEMORY_CACHE_SIZE_BYTES = 2 * 1024 * 1024;

	private ImageLoaderTools(Context context){
		setImageLoader(initImageLoader(context));
	}
	
	public static ImageLoaderTools getInstance(Context context){
		if(mImageLoaderWrapper == null){
			mImageLoaderWrapper = new ImageLoaderTools(context);
			return mImageLoaderWrapper;
		}else{
			return mImageLoaderWrapper;
		}
	}


    private static ImageLoader initImageLoader(Context context) {
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
        .cacheInMemory(true)
        .cacheOnDisc(true)
        .showStubImage(R.drawable.image_holder)
        .showImageForEmptyUri(R.drawable.image_holder)
        .showImageOnFail(R.drawable.image_holder)
        .build();
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
		.defaultDisplayImageOptions(defaultOptions)//
		.discCacheSize(DISK_CACHE_SIZE_BYTES)
		.memoryCacheSize(MEMORY_CACHE_SIZE_BYTES)
		.build();
		
		ImageLoader tmpIL = ImageLoader.getInstance();
		tmpIL.init(config);
		return tmpIL;
		
	}

	public ImageLoader getImageLoader() {
		return mImageLoader;
	}

	private static void setImageLoader(ImageLoader mImageLoader) {
		ImageLoaderTools.mImageLoader = mImageLoader;
	}


	public void displayImage(String mResName, ImageView imageView) {
		if(mResName.startsWith("http://")){
			mImageLoader.displayImage(mResName, imageView);
		}else if(mResName.startsWith("assets://"))
		{
			mImageLoader.displayImage(mResName, imageView);
		}
		else if(mResName.startsWith("file:///mnt"))
		{
			mImageLoader.displayImage(mResName, imageView);
		}
		else if(mResName.startsWith("content://"))
		{
			mImageLoader.displayImage(mResName, imageView);
		}
		else if(mResName.startsWith("drawable://"))
		{
			mImageLoader.displayImage(mResName, imageView);
		}
		else{
			Uri uri = Uri.parse(mResName);
			imageView.setImageURI(uri);
		}
		
	}
}
