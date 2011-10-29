package com.oogiyot.cheekdefender;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.widget.ImageView;

public class centeredImageView extends ImageView {
	public static final String TAG = "CheekDefender";
	private boolean firstTime = true;

	public centeredImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

	}

	public centeredImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	
	}

	public centeredImageView(Context context) {
		super(context);

	}

	@Override
	public void onDraw(Canvas canvas) {
		if (firstTime) {
			Matrix matrix = new Matrix();
			matrix.postTranslate(
					(this.getDrawable().getIntrinsicWidth() - this.getMeasuredWidth()) * -0.5f,	0);
			
			this.setImageMatrix(matrix);
			this.setPadding(0,100,0,0);
			firstTime = false;
		}
		super.onDraw(canvas);
	}

}