package com.example.searchhourse.tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.os.StatFs;

public class ImageUtil {

	public static final String CACHEDIR = Environment
			.getExternalStorageDirectory() + "/datas/images";

	public static Boolean isMounted() {
		return Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState());
	}

	public static void saveImage(byte[] bytes, String url) throws IOException {
		if (!isMounted()) {
			return;
		}
		// �ж���չ��ʣ��ռ��Ƿ���
		// �ļ�ϵͳ״̬�������
		StatFs fs = new StatFs(Environment.getExternalStorageDirectory()
				.getAbsolutePath());
		int count = fs.getFreeBlocks();// ���е����ݿ����
		int size = fs.getBlockSize();// ����ÿ�����ݿ�Ĵ�С

		// ʣ��ռ��С
		long total = count * size;// ��λ���ֽ�
		if (total > bytes.length) {

			File imageFile = new File(CACHEDIR);
			if (!imageFile.exists()) {
				imageFile.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(new File(imageFile,
					getName(url)));
			fos.write(bytes);
			fos.close();
		}
	}
	
	public static void saveImage(Bitmap bitmap, String url) throws IOException {
		if (!isMounted()) {
			return;
		}
		File imageFile = new File(CACHEDIR);
		if (!imageFile.exists()) {
			imageFile.mkdirs();
		}
		bitmap.compress(getFormat(url), 100, new FileOutputStream(new File(
				imageFile, getName(url))));
	}

	private static CompressFormat getFormat(String url) {
		String fileName = getName(url);
		if (fileName.endsWith("png")) {
			return CompressFormat.PNG;
		}
		return CompressFormat.JPEG;
	}

	public static Bitmap getImage(String url) {
		if (!isMounted()) {
			return null;
		}
		File imageFile = new File(CACHEDIR, getName(url));
		if (imageFile.exists()) {
			Bitmap bitmap = BitmapFactory.decodeFile(imageFile
					.getAbsolutePath());
			return bitmap;
		}
		return null;
	}

	public static String getName(String url) {
		String s = url.substring(0, url.lastIndexOf("/"));
		s = s.substring(s.lastIndexOf("/") + 1);
		return s;
	}

}
