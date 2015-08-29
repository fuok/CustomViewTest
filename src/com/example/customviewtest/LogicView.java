package com.example.customviewtest;

import java.util.Random;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/** 一个自定义View的示例代码 */
public class LogicView extends View {
	// 参数
	private String contentStr;
	private int sleepTime;

	private Paint paint = new Paint();
	private float rx = 0;
	private MyThread myThread = new MyThread();
	private RectF rectF = new RectF(0, 60, 100, 160);
	private float sweepAngle = 0;

	public LogicView(Context context) {// 这个是正常的构造函数，JAVA使用的
		super(context);
	}

	public LogicView(Context context, AttributeSet attrs) {// 这个构造函数是布局xml使用的
		super(context, attrs);
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LogicView);
		contentStr = typedArray.getString(R.styleable.LogicView_contentStr);// 注意此处写法
		sleepTime = typedArray.getInt(R.styleable.LogicView_sleepTime, 0);
		typedArray.recycle();

	}

	@Override
	final protected void onDraw(Canvas canvas) {// 加个final这样就不能再被改写了，作为重写方法是不能修改访问空间的，但可以加final
		// super.onDraw(canvas);//View作为基类它的onDraw()方法是空的，所以不写也无所谓
		paint.setTextSize(30);
		canvas.drawText(contentStr, rx, 30, paint);
		// paint.setStyle(Style.STROKE);//设置图形空心
		canvas.drawArc(rectF, 0, sweepAngle, true, paint);// 按照角度绘制圆形

		myThread.run();
	}

	class MyThread extends Thread {
		Random random = new Random();

		@Override
		public void run() {
			super.run();
			rx++;
			if (rx > getWidth()) {
				rx = 0 - paint.measureText(contentStr);
			}

			sweepAngle++;
			if (sweepAngle > 360) {
				sweepAngle = 0;
			}

			int r = random.nextInt(256);
			int g = random.nextInt(256);
			int b = random.nextInt(256);
			paint.setARGB(255, r, g, b);

			postInvalidate();
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	protected void onDetachedFromWindow() {// 这是干什么的
		super.onDetachedFromWindow();
	}

}
