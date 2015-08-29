package com.example.customviewtest;

import java.util.Random;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/** һ���Զ���View��ʾ������ */
public class LogicView extends View {
	// ����
	private String contentStr;
	private int sleepTime;

	private Paint paint = new Paint();
	private float rx = 0;
	private MyThread myThread = new MyThread();
	private RectF rectF = new RectF(0, 60, 100, 160);
	private float sweepAngle = 0;

	public LogicView(Context context) {// ����������Ĺ��캯����JAVAʹ�õ�
		super(context);
	}

	public LogicView(Context context, AttributeSet attrs) {// ������캯���ǲ���xmlʹ�õ�
		super(context, attrs);
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LogicView);
		contentStr = typedArray.getString(R.styleable.LogicView_contentStr);// ע��˴�д��
		sleepTime = typedArray.getInt(R.styleable.LogicView_sleepTime, 0);
		typedArray.recycle();

	}

	@Override
	final protected void onDraw(Canvas canvas) {// �Ӹ�final�����Ͳ����ٱ���д�ˣ���Ϊ��д�����ǲ����޸ķ��ʿռ�ģ������Լ�final
		// super.onDraw(canvas);//View��Ϊ��������onDraw()�����ǿյģ����Բ�дҲ����ν
		paint.setTextSize(30);
		canvas.drawText(contentStr, rx, 30, paint);
		// paint.setStyle(Style.STROKE);//����ͼ�ο���
		canvas.drawArc(rectF, 0, sweepAngle, true, paint);// ���սǶȻ���Բ��

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
	protected void onDetachedFromWindow() {// ���Ǹ�ʲô��
		super.onDetachedFromWindow();
	}

}
