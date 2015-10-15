package com.example.searchhourse.tool;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class VolleyUtils {

	public static RequestQueue mQueue;
	public static ImageLoader mLoader;

	private static LruCache<String, Bitmap> lruCache;// 强引用缓存，一级缓存，特点是使用最近最少算吧
	private static Map<String, SoftReference<Bitmap>> softCache;// 软引用缓存，二级缓存

	public static RequestQueue getRequestQueue(Context context) {
		if (mQueue == null)
			mQueue = Volley.newRequestQueue(context);
		return mQueue;
	}
	
	public static ImageLoader getImageLoader(Context context){
		if(mLoader == null){
			// 实例化二级缓存
			softCache = new HashMap<String, SoftReference<Bitmap>>();
			// 实例化一级缓存
			lruCache = new LruCache<String, Bitmap>(2 * 1024 * 1024) {
				@Override
				protected int sizeOf(String key, Bitmap value) {
					// 计算存放成员大小，返回字节大小
					return value.getRowBytes() * value.getHeight();
				}

				@Override
				protected void entryRemoved(boolean evicted, String key,
						Bitmap oldValue, Bitmap newValue) {
					if (evicted) {
						// 移除旧成员存放到二级缓存中
						softCache.put(key, new SoftReference<Bitmap>(oldValue));
					}
					super.entryRemoved(evicted, key, oldValue, newValue);
				}
			};
			mLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {

				@Override
				public void putBitmap(String url, Bitmap bitmap) {
					// 将图片存放到一级缓存中
					lruCache.put(url, bitmap);
					// 将图片存放到扩展卡
					try {
						ImageUtil.saveImage(bitmap, url);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				@Override
				public Bitmap getBitmap(String url) {
					// 先从一级缓存获取
					Bitmap b = lruCache.get(url);
					if (b == null) {
						// 从二级缓存中读取
						SoftReference<Bitmap> ref = softCache.get(url);
						if (ref != null) {
							b = ref.get();
							if (b != null) {
								// 将图片存在一级缓存
								lruCache.put(url, b);
								// 从二级缓存中移除
								softCache.remove(ref);
							}
						}else{
							b = ImageUtil.getImage(url);
							if(b!=null){
								lruCache.put(url, b);
							}
						}
					}
					return null;
				}
			});
		}
		return mLoader;
	}
}
