package org.jan.taobao.activities;

import org.jan.taobao.utils.CheckAction;
import org.jan.taobao.utils.CheckGetUtil;
import org.jan.taobao.utils.ConmentConfig;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CheckView extends View implements CheckAction {

	Context mContext;
	public static final int CODE_SIZE = 26;
	public static final int STROKEWIDTH = 3;
	int[] CheckNum = { 0, 0, 0, 0 };
	Paint mTempPaint = new Paint();

	// 验证码

	public CheckView(Context context, AttributeSet attrs) {
		super(context, attrs);

		mContext = context;
		mTempPaint.setAntiAlias(true);
		mTempPaint.setTextSize(CODE_SIZE);
		mTempPaint.setStrokeWidth(STROKEWIDTH);
	}

	public void onDraw(Canvas canvas) {
		canvas.drawColor(Color.GREEN);

		// 绘制验证码
		final int height = getHeight();
		final int width = getWidth();
		int dx = 30;
		for (int i = 0; i < 4; i++) {
			canvas.drawText("" + CheckNum[i], dx,
					CheckGetUtil.getPositon(height), mTempPaint);
			dx += width / 5;
		}
		int[] line;
		for (int i = 0; i < ConmentConfig.LINE_NUM; i++) {
			line = CheckGetUtil.getLine(height, width);
			canvas.drawLine(line[0], line[1], line[2], line[3], mTempPaint);
		}
		// 绘制小圆点
		int[] point;
		for (int i = 0; i < ConmentConfig.POINT_NUM; i++) {
			point = CheckGetUtil.getPoint(height, width);
			canvas.drawCircle(point[0], point[1], 1, mTempPaint);
		}
	}

	public void setCheckNum(int[] chenckNum) {
		CheckNum = chenckNum;
	}

	public int[] getCheckNum() {
		return CheckNum;
	}

	public void invaliChenkNum() {
		invalidate();
	}

}
