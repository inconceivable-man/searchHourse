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

	private static LruCache<String, Bitmap> lruCache;// ǿ���û��棬һ�����棬�ص���ʹ������������
	private static Map<String, SoftReference<Bitmap>> softCache;// �����û��棬��������

	public static RequestQueue getRequestQueue(Context context) {
		if (mQueue == null)
			mQueue = Volley.newRequestQueue(context);
		return mQueue;
	}
	
	public static ImageLoader getImageLoader(Context context){
		if(mLoader == null){
			// ʵ������������
			softCache = new HashMap<String, SoftReference<Bitmap>>();
			// ʵ����һ������
			lruCache = new LruCache<String, Bitmap>(2 * 1024 * 1024) {
				@Override
				protected int sizeOf(String key, Bitmap value) {
					// �����ų�Ա��С�������ֽڴ�С
					return value.getRowBytes() * value.getHeight();
				}

				@Override
				protected void entryRemoved(boolean evicted, String key,
						Bitmap oldValue, Bitmap newValue) {
					if (evicted) {
						// �Ƴ��ɳ�Ա��ŵ�����������
						softCache.put(key, new SoftReference<Bitmap>(oldValue));
					}
					super.entryRemoved(evicted, key, oldValue, newValue);
				}
			};
			mLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {

				@Override
				public void putBitmap(String url, Bitmap bitmap) {
					// ��ͼƬ��ŵ�һ��������
					lruCache.put(url, bitmap);
					// ��ͼƬ��ŵ���չ��
					try {
						ImageUtil.saveImage(bitmap, url);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				@Override
				public Bitmap getBitmap(String url) {
					// �ȴ�һ�������ȡ
					Bitmap b = lruCache.get(url);
					if (b == null) {
						// �Ӷ��������ж�ȡ
						SoftReference<Bitmap> ref = softCache.get(url);
						if (ref != null) {
							b = ref.get();
							if (b != null) {
								// ��ͼƬ����һ������
								lruCache.put(url, b);
								// �Ӷ����������Ƴ�
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
