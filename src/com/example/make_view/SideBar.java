package com.example.make_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class SideBar extends View {
	// 定义字母数组
	public String[] characters = new String[] { "A", "B", "C", "D", "E", "F",
			"G", "H", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "W",
			"X", "Y", "Z" };

	private Paint paint;
	private int textSize = 20;
	private int defaultTextColor = Color.parseColor("#2DB7E1");
	private int selectedTextColor = Color.WHITE;
	private int touchedBgColor = Color.parseColor("#E0F0F4");
	private TextView  text_dialog;

	private OnTouchingLetterChangedListener onTouchingLetterChangedListener;

	private int position = -1;

	public SideBar(Context context) {
		super(context);
	}

	public SideBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public SideBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	public void setTextDialog(TextView textView){
		this.text_dialog = textView;
	} 

	private void init() {
		paint = new Paint();
		paint.setAntiAlias(true);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		int height = getHeight();// 获取对应高度
		int width = getWidth(); // 获取对应宽度
		int singleHeight = height / characters.length;// 获取每一个字母的高度

		for (int i = 0; i < characters.length; i++) {
			if (i == position) {
				paint.setColor(selectedTextColor);
			} else {
				paint.setColor(defaultTextColor);
			}
			paint.setTextSize(textSize);
			// x坐标等于中间-字符串宽度的一半.
			float xPos = width / 2 - paint.measureText(characters[i]) / 2;
			float yPos = singleHeight * i + singleHeight;
			canvas.drawText(characters[i], xPos, yPos, paint);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		float y = event.getY();
		position = (int) (y / (getHeight() / characters.length));
		onTouchingLetterChangedListener.onTouchingLetterChanged(position);
		switch (action) {
		case MotionEvent.ACTION_UP:
			setBackgroundColor(Color.TRANSPARENT);// 透明
			position = -1;
			invalidate();
			if(text_dialog != null){
				text_dialog.setVisibility(View.INVISIBLE);
			}
			break;
		default:
			setBackgroundColor(touchedBgColor);
			invalidate();
			text_dialog.setText(characters[position]);
			text_dialog.setVisibility(View.VISIBLE);
			break;
		}
		return true;
	}

	public void setOnTouchingLetterChangedListener(
			OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
		this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
	}

	/**
	 * 接口
	 */
	public interface OnTouchingLetterChangedListener {
		public void onTouchingLetterChanged(int position);
	}

}
