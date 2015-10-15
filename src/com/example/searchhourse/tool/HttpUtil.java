package com.example.searchhourse.tool;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.os.Handler;

public class HttpUtil {

	public static final int TYPE_TEXT = 0;
	public static final int TYPE_IMAGE = 1;

	public static interface CallBack {
		public boolean isCanclled(String url);

		public void response(byte[] bytes,String url);
	}

	private static Handler handler = new Handler();
	private static ExecutorService service = Executors.newFixedThreadPool(5);

	public static void gets(final int type, final String url,
			final CallBack callBacke) {
		service.execute(new Runnable() {

			@Override
			public void run() {
				try {
					HttpURLConnection conn = (HttpURLConnection) new URL(url)
							.openConnection();
					InputStream in = conn.getInputStream();
					if (conn.getResponseCode() == 200) {
						ByteArrayOutputStream bos = new ByteArrayOutputStream();
						byte[] arr = new byte[10 * 1024];
						int len = -1;
						while ((len = in.read(arr)) != -1) {
							bos.write(arr, 0, len);
							//判断是否取消下载
							if (callBacke.isCanclled(url)) {
								return;
							}
						}
						in.close();

						final byte[] bytes = bos.toByteArray();
						
						if(TYPE_IMAGE == type){
							ImageUtil.saveImage(bytes,url);
						}

						handler.post(new Runnable() {
							@Override
							public void run() {
								callBacke.response(bytes, url);
							}
						});
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});
	}
}
