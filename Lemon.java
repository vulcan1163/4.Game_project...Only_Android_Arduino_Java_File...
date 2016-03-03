package com.hbe.lemondash;

public class Lemon {
	
	public final static int ARRAY_LEFT		= 0x01;
	public final static int ARRAY_RIGHT		= 0x02;
	public final static int ARRAY_TOP		= 0x03;
	public final static int ARRAY_BOTTOM	= 0x04;
	public final static int ARRAY_NONE		= 0x05;
	
	private double mX;
	private double mY;
	private double mMoveTermX;
	private double mMoveTermY;
	private int mArrayX, mArrayY;
	private int mImgWidth, mImgHeight;
	
	public Lemon(int x, int y, double xTerm, double yTerm, int imgW, int imgH){
		mX = x;
		mY = y;
		mMoveTermX = xTerm;
		mMoveTermY = yTerm;

		if(mMoveTermX<0) mArrayX = ARRAY_LEFT;
		else if(mMoveTermX>0) mArrayX = ARRAY_RIGHT;
		else mArrayX = ARRAY_NONE;

		if(mMoveTermY<0) mArrayY = ARRAY_BOTTOM;
		else if(mMoveTermY>0) mArrayY = ARRAY_TOP;
		else mArrayY = ARRAY_NONE;
		
		mImgWidth = imgW;
		mImgHeight = imgH;
	}
	
	public void move(){
		mX += mMoveTermX;
		mY += mMoveTermY;
	}
	
	public int getX(){
		return (int)mX;
	}
	
	public int getY(){
		return (int)mY;
	}
	
	public double getTermX(){
		return mMoveTermX;
	}
	
	public double getTermY(){
		return mMoveTermY;
	}
	
	public boolean isCorner(int screen_width, int screen_height, int width, int height){
		if(mArrayX==ARRAY_LEFT && mX<=0-width) return true;
		else if(mArrayX==ARRAY_RIGHT && mX>=screen_width) return true;
		else if(mArrayY==ARRAY_TOP && mY<=0-height) return true;
		else if(mArrayY==ARRAY_BOTTOM && mY>=screen_height) return true;
		else if(mArrayX==ARRAY_NONE && mArrayY==ARRAY_NONE) return true;
		else return false;
	}

	public boolean isCrashed(int x, int y, int width, int height){
		int correction = 5;
		if(mX+mImgWidth>=x+correction && mX<=x+width-correction && mY+mImgHeight>=y+correction && mY<=y+height-correction) return true;
		else return false;
	}
}
