package com.hbe.lemondash;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class HowtoView extends View {
	
	private int mScreenWidth, mScreenHeight;
	
	Bitmap mImgBg;
	
	Context mContext;
	Paint mPaint = new Paint();

	public HowtoView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
		
		initScreenSize();
		initImageData();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawBitmap(mImgBg, 0, 0, mPaint);
		super.onDraw(canvas);
	}
	
	private Bitmap createImageAllScreen(int r){
		Resources res = getResources();
		Bitmap imgPrevConv = BitmapFactory.decodeResource(res, r);
		int w = mScreenWidth;
		int h = mScreenHeight;
		return Bitmap.createScaledBitmap(imgPrevConv, w, h, true);
	}
	
	private void initScreenSize(){
		Display display = ((WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		Point point = new Point();
		display.getSize(point);
		
		mScreenWidth = point.x;
		mScreenHeight = point.y;
	}
	
	private void initImageData(){
		mImgBg = createImageAllScreen(R.drawable.howto);
	}

}
