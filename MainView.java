package com.hbe.lemondash;

import java.util.Random;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class MainView extends View {
	
	Bitmap mImgCat, mImgBg, mImgBtn1, mImgBtn2, mImgLemon, mImgOriginalLemon;
	int mCat_x, mCat_y, mBtn1_x, mBtn1_y, mBtn2_x, mBtn2_y, mInitCat_x, mInitCat_y, mLemon_x, mLemon_y, mOriginalLemon_x, mOriginalLemon_y;
	
	private int mScreenWidth, mScreenHeight;
	private double mZoomWidth, mZoomHeight;
	
	Context mContext;
	Paint mPaint = new Paint();

	public MainView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
		mLemonSpeed_x = mRand.nextInt(10)+5;
		mLemonSpeed_y = mRand.nextInt(10)+5;
		initScreenSize();
		initImageData();
		initImageLocation();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawBitmap(mImgBg, 0, 0, mPaint);
		canvas.drawBitmap(mImgBtn1, mBtn1_x, mBtn1_y, mPaint);
		canvas.drawBitmap(mImgBtn2, mBtn2_x, mBtn2_y, mPaint);
		//canvas.drawBitmap(mImgLemon, mLemon_x, mLemon_y, mPaint);
		canvas.drawBitmap(mImgCat, mCat_x, mCat_y, mPaint);
		
		super.onDraw(canvas);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		int x = (int)event.getX();
		int y = (int)event.getY();
		
		if(event.getAction()==MotionEvent.ACTION_DOWN){

			((MainActivity)mContext).startGame();
			
			if(isTouch(x, y, mBtn2_x, mBtn2_y, mImgBtn2)){
				((MainActivity)mContext).startHowto();
			}
			
			return true;
		}

		return super.onTouchEvent(event);
	}
	
	private Bitmap createImageAllScreen(int r){
		Resources res = getResources();
		Bitmap imgPrevConv = BitmapFactory.decodeResource(res, r);
		int w = mScreenWidth;
		int h = mScreenHeight;
		return Bitmap.createScaledBitmap(imgPrevConv, w, h, true);
	}
	
	private Bitmap createImage(int r){
		Resources res = getResources();
		Bitmap imgPrevConv = BitmapFactory.decodeResource(res, r);
		int w=0,h=0;
		
		if(mZoomWidth<mZoomHeight){
			w = (int)(imgPrevConv.getWidth()*mZoomWidth);
			h = (int)(imgPrevConv.getHeight()*mZoomWidth);
		} else {
			w = (int)(imgPrevConv.getWidth()*mZoomHeight);
			h = (int)(imgPrevConv.getHeight()*mZoomHeight);
		}
		/*
		if(Build.MODEL.equals("Nexus 7")){
			w = (int)(imgPrevConv.getWidth()*mZoomWidth*1.12); //nexus7
			h = (int)(imgPrevConv.getHeight()*mZoomHeight*1.12); //nexus7
		} else {
			w = (int)(imgPrevConv.getWidth()*mZoomWidth);
			h = (int)(imgPrevConv.getHeight()*mZoomHeight);
		}
		*/

		return Bitmap.createScaledBitmap(imgPrevConv, w, h, true);
	}
	
	private void initScreenSize(){
		Display display = ((WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		Point point = new Point();
		display.getSize(point);
		
		mScreenWidth = point.x;
		mScreenHeight = point.y;
		mZoomWidth = (double)mScreenWidth/(double)1280;
		mZoomHeight = (double)mScreenHeight/(double)800;
	}
	
	private void initImageData(){
		mImgBg = createImageAllScreen(R.drawable.bg2);
		mImgCat = createImage(R.drawable.index_cat);
		mImgBtn1 = createImage(R.drawable.index_btn1);
		mImgBtn2 = createImage(R.drawable.index_btn2);
		mImgOriginalLemon = createImage(R.drawable.lemon_big);
	}
	
	private void initImageLocation(){
		mBtn1_x = (int)((mScreenWidth/4*3)-(mImgBtn1.getWidth()/2));
		mBtn1_y = (int)((mScreenHeight/3)-(mImgBtn1.getHeight()/2))+100;

		mBtn2_x = (int)((mScreenWidth/4*3)-(mImgBtn2.getWidth()/2));
		mBtn2_y = (int)((mScreenHeight/3*2)-(mImgBtn2.getHeight()/2))+100;
		
		mCat_x = (int)((mBtn1_x/2)-(mImgCat.getWidth()/2));
		mCat_y = (int)((mScreenHeight/2)-(mImgCat.getHeight()/2)-(20*mZoomHeight));
		
		mInitCat_x = mCat_x;
		mInitCat_y = mCat_y;

		mOriginalLemon_x = 10;
		mOriginalLemon_y = 10;
		
		int OriLemonCenter_x = mOriginalLemon_x+(mImgOriginalLemon.getWidth()/2);
		int OriLemonCenter_y = mOriginalLemon_y+(mImgOriginalLemon.getHeight()/2);
		mLemon_x = OriLemonCenter_x-(mImgOriginalLemon.getWidth()/2);
		mLemon_y = OriLemonCenter_y-(mImgOriginalLemon.getHeight()/2);
	}
	
	public void setCatRight(){
		mCat_x += (int)(2*mZoomWidth);
		postInvalidate();
	}
	
	public void setCatLeft(){
		mCat_x -= (int)(2*mZoomWidth);
		postInvalidate();
	}
	
	public void setCatUp(){
		mCat_y -= (int)(2*mZoomHeight);
		postInvalidate();
	}
	
	public void setCatDown(){
		mCat_y += (int)(2*mZoomHeight);
		postInvalidate();
	}
	
	public void initCatLocation(){
		mCat_x = mInitCat_x;
		mCat_y = mInitCat_y;
	}
	
	private boolean isTouch(int x, int y, int chx, int chy, Bitmap img){
		if( x>=chx && x<=chx+img.getWidth() && y>=chy && y<=chy+img.getHeight()) return true;
		else return false; 
	}
	
	public void rotateLemon(int rotate){
		Matrix matrix = new Matrix(); 
		matrix.postRotate(rotate);
		mImgLemon = Bitmap.createBitmap(mImgOriginalLemon, 0, 0, mImgOriginalLemon.getWidth(), mImgOriginalLemon.getHeight(), matrix, true);
		
	}
	
	int mLemonSpeed_x, mLemonSpeed_y;
	Random mRand = new Random();
	
	public void moveLemon(){
		if(	!(mOriginalLemon_x>0-mImgOriginalLemon.getWidth()-5 && mOriginalLemon_x<mScreenWidth+mImgOriginalLemon.getWidth()+5 && 
			mOriginalLemon_y>0-mImgOriginalLemon.getHeight()-5 && mOriginalLemon_y<mScreenHeight+mImgOriginalLemon.getHeight()+5)){

			int n = mRand.nextInt(4);
			int arr1 = mRand.nextInt(2);
			int arr2 = mRand.nextInt(2);
			
			switch(n){
			case 0: // left
				mOriginalLemon_x = 0-mImgOriginalLemon.getWidth();
				mOriginalLemon_y = mRand.nextInt(mScreenHeight);
				mLemonSpeed_x = mRand.nextInt(10)+5;
				if(arr2==1) mLemonSpeed_y = mRand.nextInt(10)+5;
				else mLemonSpeed_y = (mRand.nextInt(10)+5)-1;
				break;
			case 1: // top
				mOriginalLemon_x = mRand.nextInt(mScreenWidth);
				mOriginalLemon_y = 0-mImgOriginalLemon.getHeight();
				if(arr1==1) mLemonSpeed_x = mRand.nextInt(10)+5;
				else mLemonSpeed_x = (mRand.nextInt(10)+5)*-1;
				mLemonSpeed_y = mRand.nextInt(10)+5;
				break;
			case 2: // right
				mOriginalLemon_x = mScreenWidth+mImgOriginalLemon.getWidth();
				mOriginalLemon_y = mRand.nextInt(mScreenHeight);
				mLemonSpeed_x = (mRand.nextInt(10)+5)*-1;
				if(arr2==1) mLemonSpeed_y = mRand.nextInt(10)+5;
				else mLemonSpeed_y = (mRand.nextInt(10)+5)-1;
				break;
			case 3: // bottom
				mOriginalLemon_x = mRand.nextInt(mScreenWidth);
				mOriginalLemon_y = mScreenHeight+mImgOriginalLemon.getHeight();
				if(arr1==1) mLemonSpeed_x = mRand.nextInt(10)+5;
				else mLemonSpeed_x = (mRand.nextInt(10)+5)*-1;
				mLemonSpeed_y = (mRand.nextInt(10)+5)*-1;
				break;
			}
		} else {
			mOriginalLemon_x += mLemonSpeed_x;
			mOriginalLemon_y += mLemonSpeed_y;
			int OriLemonCenter_x = mOriginalLemon_x+(mImgOriginalLemon.getWidth()/2);
			int OriLemonCenter_y = mOriginalLemon_y+(mImgOriginalLemon.getHeight()/2);
			mLemon_x = OriLemonCenter_x-(mImgLemon.getWidth()/2);
			mLemon_y = OriLemonCenter_y-(mImgLemon.getHeight()/2);
		}
	}

}
