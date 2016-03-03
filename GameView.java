package com.hbe.lemondash;

import java.util.ArrayList;
import java.util.Random;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.WindowManager;

public class GameView extends SurfaceView implements  Callback  {

	private final static int CAT_DOWN = 0x00;
	private final static int CAT_JUMP = 0x01;
	private final static int CAT_FRONT1 = 0x02;
	private final static int CAT_FRONT2 = 0x03;
	private final static int CAT_BACK1 = 0x04;
	private final static int CAT_BACK2 = 0x05;
	private final static int CAT_LEFT1 = 0x06;
	private final static int CAT_LEFT2 = 0x07;
	private final static int CAT_RIGHT1 = 0x08;
	private final static int CAT_RIGHT2 = 0x09;
	private final static int CAT_JUMP2 = 0x0A;
	private final static int LOCATION_TOP = 0xA0;
	private final static int LOCATION_BOTTOM = 0xA1;
	private final static int LOCATION_LEFT = 0xA2;
	private final static int LOCATION_RIGHT = 0xA3;

	Bitmap mImgLemon, mImgShield, fly, target, missile, enemy, hud, bar, fiter1_dead, fiter2_dead, fiter3_dead,hud2,hud3,hud4,hud5;
	Bitmap[] mImgCat = new Bitmap[11];
	static Bitmap  mImgBg,mImgBg_2,mImgBg_3,mImgBg_4,mImgBg_5;
	int target_x, target_y;
	int mCat_x, mCat_y, mRealCat_x, mRealCat_y, mFly_x, mFly_y;
	int mShield_x, mShield_y;
	int mCatMode;
	int mWeightHeight, mWeightWidth;
	static int mScreenWidth, mScreenHeight;
	 double mZoomWidth, mZoomHeight, mZoomValue;
	 int mBgRows, mBgCols;
	 int mImageTurn;
	 int mMovePointX, mMovePointY;

	//private ArrayList<Lemon> mLemonArray = new ArrayList<Lemon>();

	Context mContext;
	Paint mPaint = new Paint();

	boolean isShieldOn = false;

	Random mRand = new Random();

	String mSurviveTime = "";
	String mShieldTime = "";
	String mFinalScore = "";
	SurfaceHolder holder;
	CatMoveThread mCatThread;


	
	public GameView(Context context, AttributeSet attr){
		super(context, attr);

		holder = getHolder();
		holder.addCallback(this);
	
		mMovePointX = 11;
		mMovePointY = 10;

		// mPaint.setColor(Color.WHITE);
		mPaint.setTextSize(mPaint.getTextSize() + 10);

		mContext = context;
		 
		mCatThread = new CatMoveThread(holder, mContext);
		
		AppManager.getInstance().setGameView(this);

		initScreenSize();
		initImageData();
		initImageLocation();
		initBgCount();
		

		setFocusable(true);
	}

	// //////////////////////////////////////
	// 실시간 그리기
	// //////////////////////////////////////


	// //////////////////////////////////////
	// 터치 이벤트
	// //////////////////////////////////////

	public boolean onTouchEvent(MotionEvent event) {
		// int x = (int)event.getX();
		// int y = (int)event.getY();

		if (event.getAction() == MotionEvent.ACTION_DOWN) {

			
			//mCatThread.makeMissile();
			// if(isShieldOn) ((GameActivity)mContext).setShield(false);
			// else ((GameActivity)mContext).setShield(true);

			return true;
		}

		return super.onTouchEvent(event);
	}

	// //////////////////////////////////////
	// 원본 이미지 그리기
	// //////////////////////////////////////
	private Bitmap createImageOriginal(int r) {
		Resources res = getResources();
		return BitmapFactory.decodeResource(res, r);
	}

	// //////////////////////////////////////
	// 게임 이미지 만들기
	// //////////////////////////////////////
	private Bitmap createImage(int r) {
		Resources res = getResources();
		Bitmap imgPrevConv = BitmapFactory.decodeResource(res, r);

		int w = (int) (imgPrevConv.getWidth() * mZoomValue);
		int h = (int) (imgPrevConv.getHeight() * mZoomValue);

		return Bitmap.createScaledBitmap(imgPrevConv, w, h, true);
	}
	
	private Bitmap createImageHud(int r) {
		Resources res = getResources();
		Bitmap imgPrevConv = BitmapFactory.decodeResource(res, r);

		int w = (int) (imgPrevConv.getWidth() * mZoomValue)+80;
		int h = (int) (imgPrevConv.getHeight() * mZoomValue)+60;

		return Bitmap.createScaledBitmap(imgPrevConv, w, h, true);
	}
	
	private Bitmap createBarImage(int r) {
		Resources res = getResources();
		Bitmap imgPrevConv = BitmapFactory.decodeResource(res, r);

		int w = (int) (imgPrevConv.getWidth() * mZoomValue);
		int h = (int) (imgPrevConv.getHeight() * mZoomValue)+50;

		return Bitmap.createScaledBitmap(imgPrevConv, w, h, true);
	}

	private Bitmap createMissileImage(int r) {
		Resources res = getResources();
		Bitmap imgPrevConv = BitmapFactory.decodeResource(res, r);

		int w = (int) (imgPrevConv.getWidth() * (mZoomValue-1f));
		int h = (int) (imgPrevConv.getHeight() *( mZoomValue-1f));

		return Bitmap.createScaledBitmap(imgPrevConv, w, h, true);
	}

	// //////////////////////////////////////
	// 게임 이미지 만들기
	// //////////////////////////////////////
	public Bitmap createFlyImage(int r) {
		Resources res = getResources();
		Bitmap imgPrevConv = BitmapFactory.decodeResource(res, r);

		int w = (int) (imgPrevConv.getWidth() * mZoomValue);
		int h = (int) (imgPrevConv.getHeight() * mZoomValue);

		return Bitmap.createScaledBitmap(imgPrevConv, w, h, true);
	}

	// //////////////////////////////////////
	// 게임 이미지 만들기(전체화면)
	// //////////////////////////////////////
	private Bitmap createAllScreenImageBack(int r) {
		Resources res = getResources();
		Bitmap imgPrevConv = BitmapFactory.decodeResource(res, r);

		int w = mScreenWidth + 500;
		int h = mScreenHeight +500;

		return Bitmap.createScaledBitmap(imgPrevConv, w, h, true);
	}
	
	private Bitmap createAllScreenImage(int r) {
		Resources res = getResources();
		Bitmap imgPrevConv = BitmapFactory.decodeResource(res, r);

		int w = mScreenWidth ;
		int h = mScreenHeight ;

		return Bitmap.createScaledBitmap(imgPrevConv, w, h, true);
	}

	// //////////////////////////////////////
	// 화면 크기 가져오기
	// //////////////////////////////////////

	private void initScreenSize() {
		Display display = ((WindowManager) mContext
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		Point point = new Point();
		display.getSize(point);

		mScreenWidth = point.x;
		mScreenHeight = point.y;
		mZoomWidth = (double) mScreenWidth / (double) 1280;
		mZoomHeight = (double) mScreenHeight / (double) 800;

		if (mZoomWidth < mZoomHeight) {
			mZoomValue = mZoomWidth;
		} else {
			mZoomValue = mZoomHeight;
		}
	}

	// //////////////////////////////////////
	// 이미지 등록부분
	// //////////////////////////////////////
	private void initImageData() {
		// mImgBg = createImageOriginal(R.drawable.grass);
		
		mImgBg = createAllScreenImageBack(R.drawable.back);
		mImgBg_2 = createAllScreenImageBack(R.drawable.back_2);
		mImgBg_3 = createAllScreenImageBack(R.drawable.back_3);
		mImgBg_4 = createAllScreenImageBack(R.drawable.back_4);
		mImgBg_5 = createAllScreenImageBack(R.drawable.back_5);
		
		mImgCat[CAT_DOWN] = createImage(R.drawable.cat_down);
		mImgCat[CAT_JUMP] = createImage(R.drawable.cat_jump1);
		mImgCat[CAT_FRONT1] = createImage(R.drawable.cat_front1);
		mImgCat[CAT_FRONT2] = createImage(R.drawable.cat_front2);
		mImgCat[CAT_BACK1] = createImage(R.drawable.cat_back1);
		mImgCat[CAT_BACK2] = createImage(R.drawable.cat_back2);
		mImgCat[CAT_LEFT1] = createImage(R.drawable.cat_left1);
		mImgCat[CAT_LEFT2] = createImage(R.drawable.cat_left2);
		mImgCat[CAT_RIGHT1] = createImage(R.drawable.cat_right1);
		mImgCat[CAT_RIGHT2] = createImage(R.drawable.cat_right2);
		mImgCat[CAT_JUMP2] = createImage(R.drawable.cat_jump2);
		
		mImgLemon = createImage(R.drawable.lemon);
		mImgShield = createImage(R.drawable.shield);
		fly = createFlyImage(R.drawable.fiter);
		target = createFlyImage(R.drawable.target_5);
		missile = createMissileImage(R.drawable.ping);
		enemy = createFlyImage(R.drawable.fiter);
		
		hud = createAllScreenImage(R.drawable.finalhud);
		hud2 = createImageHud(R.drawable.hud_2);
		hud3 = createImageHud(R.drawable.hud_3);
		hud4 = createImageHud(R.drawable.hud_4);
		hud5 = createImageHud(R.drawable.hud_5);
		
		
		bar = createBarImage(R.drawable.bar);
		
		fiter1_dead = createFlyImage(R.drawable.fiter1_dead);
		fiter2_dead = createFlyImage(R.drawable.fiter2_dead);
		fiter3_dead = createFlyImage(R.drawable.fiter3_dead);
	}

	// //////////////////////////////////////
	// 이미지 위치 설정
	// //////////////////////////////////////
	private void initImageLocation() {

		mCat_x = (int) ((mScreenWidth / 2) - (mImgCat[CAT_DOWN].getWidth() / 2));
		mCat_y = (int) ((mScreenHeight / 2) - (mImgCat[CAT_DOWN].getHeight() / 2));

		mCatMode = CAT_DOWN;

		mWeightHeight = (int) (34 * mZoomValue);
		mWeightWidth = (int) (10 * mZoomValue);

		mFly_x = (int) ((mScreenWidth / 2) - (fly.getWidth() / 2));
		mFly_y = (int) ((mScreenWidth / 2) - (fly.getHeight() / 2));

	}

	// ///////////////////////////////////////////////////
	// 배경이미지 그릴때 카운트
	// //////////////////////////////////////////////////

	private void initBgCount() {
		mBgCols = (mScreenWidth / mImgBg.getWidth()) + 1;
		mBgRows = (mScreenHeight / mImgBg.getHeight()) + 1;
	}

	// ///////////////////////////////////////////
	// 고양이 이동(앞으로)
	// ///////////////////////////////////////////

	public void setFrontCat() {
		if (mImageTurn == 0) {
			mCatMode = CAT_FRONT1;
			mImageTurn = 1;
		} else if (mImageTurn == 1) {
			mCatMode = CAT_FRONT2;
			mImageTurn = 0;
		}

		mWeightHeight = (int) (19 * mZoomValue);
		mWeightWidth = (int) (15 * mZoomValue);
		mCat_y += mMovePointY;

		if (isCatLastBottom())
			setCatLocation(LOCATION_BOTTOM);

		setShieldLocation();
	}

	// ///////////////////////////////////////////
	// 고양이 이동(앞, 왼쪽)
	// ///////////////////////////////////////////

	public void setFrontLeftCat() {
		if (mImageTurn == 0) {
			mCatMode = CAT_FRONT1;
			mImageTurn = 1;
		} else if (mImageTurn == 1) {
			mCatMode = CAT_FRONT2;
			mImageTurn = 0;
		}

		mWeightHeight = (int) (19 * mZoomValue);
		mWeightWidth = (int) (15 * mZoomValue);
		mCat_x -= mMovePointX;
		mCat_y += mMovePointY;

		if (isCatLastBottom())
			setCatLocation(LOCATION_BOTTOM);
		if (isCatLastLeft())
			setCatLocation(LOCATION_LEFT);

		setShieldLocation();
	}

	// ///////////////////////////////////////////
	// 고양이 이동(앞, 오른쪽)
	// ///////////////////////////////////////////

	public void setFrontRightCat() {
		if (mImageTurn == 0) {
			mCatMode = CAT_FRONT1;
			mImageTurn = 1;
		} else if (mImageTurn == 1) {
			mCatMode = CAT_FRONT2;
			mImageTurn = 0;
		}

		mWeightHeight = (int) (19 * mZoomValue);
		mWeightWidth = (int) (15 * mZoomValue);
		mCat_x += mMovePointX;
		mCat_y += mMovePointY;

		if (isCatLastBottom())
			setCatLocation(LOCATION_BOTTOM);
		if (isCatLastRight())
			setCatLocation(LOCATION_RIGHT);

		setShieldLocation();
	}

	// ///////////////////////////////////////////
	// 고양이 이동(뒤로)
	// ///////////////////////////////////////////

	public void setBackCat() {
		if (mImageTurn == 0) {
			mCatMode = CAT_BACK1;
			mImageTurn = 1;
		} else if (mImageTurn == 1) {
			mCatMode = CAT_BACK2;
			mImageTurn = 0;
		}

		mWeightHeight = (int) (21 * mZoomValue);
		mWeightWidth = (int) (12 * mZoomValue);
		mCat_y -= mMovePointY;

		if (isCatLastTop())
			setCatLocation(LOCATION_TOP);

		setShieldLocation();
	}

	// ///////////////////////////////////////////
	// 고양이 이동(뒤, 왼쪽)
	// ///////////////////////////////////////////
	public void setBackLeftCat() {
		if (mImageTurn == 0) {
			mCatMode = CAT_BACK1;
			mImageTurn = 1;
		} else if (mImageTurn == 1) {
			mCatMode = CAT_BACK2;
			mImageTurn = 0;
		}

		mWeightHeight = (int) (21 * mZoomValue);
		mWeightWidth = (int) (12 * mZoomValue);
		mCat_x -= mMovePointX;
		mCat_y -= mMovePointY;

		if (isCatLastTop())
			setCatLocation(LOCATION_TOP);
		if (isCatLastLeft())
			setCatLocation(LOCATION_LEFT);

		setShieldLocation();
	}

	// ///////////////////////////////////////////
	// 고양이 이동(뒤, 오른쪽)
	// ///////////////////////////////////////////
	public void setBackRightCat() {
		if (mImageTurn == 0) {
			mCatMode = CAT_BACK1;
			mImageTurn = 1;
		} else if (mImageTurn == 1) {
			mCatMode = CAT_BACK2;
			mImageTurn = 0;
		}

		mWeightHeight = (int) (21 * mZoomValue);
		mWeightWidth = (int) (12 * mZoomValue);
		mCat_x += mMovePointX;
		mCat_y -= mMovePointY;

		if (isCatLastTop())
			setCatLocation(LOCATION_TOP);
		if (isCatLastRight())
			setCatLocation(LOCATION_RIGHT);

		setShieldLocation();
	}

	// ///////////////////////////////////////////
	// 고양이 이동(왼쪽으로)
	// ///////////////////////////////////////////
	public void setLeftCat() {
		if (mImageTurn == 0) {
			mCatMode = CAT_LEFT1;
			mImageTurn = 1;
		} else if (mImageTurn == 1) {
			mCatMode = CAT_LEFT2;
			mImageTurn = 0;
		}

		mWeightHeight = (int) (20 * mZoomValue);
		mWeightWidth = 0;
		mCat_x -= mMovePointX;

		if (isCatLastLeft())
			setCatLocation(LOCATION_LEFT);

		setShieldLocation();
	}

	// ///////////////////////////////////////////
	// 고양이 이동(오른쪽으로)
	// ///////////////////////////////////////////

	public void setRightCat() {
		if (mImageTurn == 0) {
			mCatMode = CAT_RIGHT1;
			mImageTurn = 1;
		} else if (mImageTurn == 1) {
			mCatMode = CAT_RIGHT2;
			mImageTurn = 0;
		}

		mWeightHeight = (int) (20 * mZoomValue);
		mWeightWidth = 0;
		mCat_x += mMovePointX;

		if (isCatLastRight())
			setCatLocation(LOCATION_RIGHT);

		setShieldLocation();
	}

	// ///////////////////////////////////////////
	// 고양이 이동(점프)
	// ///////////////////////////////////////////

	public void setJumpCat() {
		mCatMode = CAT_JUMP;
		mWeightHeight = 0;
		mWeightWidth = (int) (13.5 * mZoomValue);
		setShieldLocation();
	}

	// ///////////////////////////////////////////
	// 고양이 이동(점프 후 다운)
	// ///////////////////////////////////////////

	public void setDownCat() {
		mCatMode = CAT_DOWN;
		mWeightHeight = (int) (49 * mZoomValue);
		mWeightWidth = (int) (10 * mZoomValue);
		setShieldLocation();
	}

	// ///////////////////////////////////////////
	// 쉴드 On, Off 체크
	// ///////////////////////////////////////////
	public void setShield(boolean b) {
		isShieldOn = b;
		setShieldLocation();
	}

	// ///////////////////////////////////////////
	// 고양이 쉴드 위치
	// ///////////////////////////////////////////
	public void setShieldLocation() {

		int cat_real_x = mCat_x + mWeightWidth;
		int cat_real_y = mCat_y + mWeightHeight;
		mShield_x = cat_real_x + (mImgCat[mCatMode].getWidth() / 2)
				- (mImgShield.getWidth() / 2);
		mShield_y = cat_real_y + (mImgCat[mCatMode].getHeight() / 2)
				- (mImgShield.getHeight() / 2);

	}

	// //////////////////////////////////////////////
	// 고양이 위치 벽 체크(왼쪽)
	// //////////////////////////////////////////////
	public boolean isCatLastLeft() {
		int cat_real_x = mCat_x + mWeightWidth;

		if (cat_real_x <= 0)
			return true;
		else
			return false;
	}

	// //////////////////////////////////////////////
	// 고양이 위치 벽 체크(오른쪽)
	// //////////////////////////////////////////////
	public boolean isCatLastRight() {
		int cat_real_x = mCat_x + mWeightWidth;
		int cat_real_x_right = cat_real_x + mImgCat[mCatMode].getWidth();
		if (cat_real_x_right >= mScreenWidth)
			return true;
		else
			return false;
	}

	// //////////////////////////////////////////////
	// 고양이 위치 벽 체크(위쪽)
	// //////////////////////////////////////////////
	public boolean isCatLastTop() {
		int cat_real_y = mCat_y + mWeightHeight;
		if (cat_real_y <= 0)
			return true;
		else
			return false;
	}

	// //////////////////////////////////////////////
	// 고양이 위치 벽 체크(아래쪽)
	// //////////////////////////////////////////////
	public boolean isCatLastBottom() {
		int cat_real_y = mCat_y + mWeightHeight;
		int cat_real_y_bottom = cat_real_y + mImgCat[mCatMode].getHeight();
		if (cat_real_y_bottom >= mScreenHeight)
			return true;
		else
			return false;
	}

	// //////////////////////////////////////////////
	// 고양이 위치 설정
	// //////////////////////////////////////////////
	private void setCatLocation(int loc) {
		if (loc == LOCATION_TOP)
			mCat_y = -mWeightHeight;
		else if (loc == LOCATION_BOTTOM)
			mCat_y = mScreenHeight - mImgCat[mCatMode].getHeight()
					- mWeightHeight;
		else if (loc == LOCATION_LEFT)
			mCat_x = -mWeightWidth;
		else if (loc == LOCATION_RIGHT)
			mCat_x = mScreenWidth - mImgCat[mCatMode].getWidth() - mWeightWidth;
	}

	// ////////////////////////////////////////////
	// Missile Move
	// ////////////////////////////////////////////
	

	// ////////////////////////////////////////////
	// Enemy make
	// ////////////////////////////////////////////
	

	// ////////////////////////////////////////////
	// Missile Make
	// ////////////////////////////////////////////

	// ////////////////////////////
	// 레몬 이동
	// ////////////////////////////
	
	 
	public void moveLemon() {

	//	for (int i = mLemonArray.size() - 1; i >= 0; i--) {
		//	mLemonArray.get(i).move();
		//	boolean corner = mLemonArray.get(i).isCorner(mScreenWidth,
		//			mScreenHeight, mImgLemon.getWidth(), mImgLemon.getHeight());
		//	if (corner)
		//		mLemonArray.remove(i);
		//	int cat_x = mCat_x + mWeightWidth;
		//	int cat_y = mCat_y + mWeightHeight;
		//	int cat_width = mImgCat[mCatMode].getWidth();
		///	int cat_height = mImgCat[mCatMode].getHeight();
		//	boolean crashed = mLemonArray.get(i).isCrashed(cat_x, cat_y,
			//		cat_width, cat_height);

			/*
			 * if(crashed){ if(isShieldOn) mLemonArray.remove(i);//continue;
			 * else if(mCatMode==CAT_JUMP) continue; else
			 * ((GameActivity)mContext).exitGame(); }
			 */
	//	}
			
		//postInvalidate();
	}
	
	// //////////////////////////////////////////////
	// 레몬 만들고 스피드 설정
	// //////////////////////////////////////////////

	public void createLemon() {
/*
		if (mLemonArray.size() >= 30)
			return;

		int num = mRand.nextInt(4);

		int x = 0, y = 0;
		int destX = 0, destY = 0;

		double speed = 200; // If value is high, speed is slow
		double speedX = 0, speedY = 0;

		if (num == 0) { // left
			x = 0;
			y = mRand.nextInt(mScreenHeight);
		} else if (num == 1) { // top
			x = mRand.nextInt(mScreenWidth);
			y = 0;
		} else if (num == 2) { // right
			x = mScreenWidth - mImgLemon.getWidth();
			y = mRand.nextInt(mScreenHeight);
		} else { // bottom
			x = mRand.nextInt(mScreenWidth);
			y = mScreenHeight - mImgLemon.getHeight();
		}
*/
		// cat location
		//destX = mCat_x + (mImgCat[mCatMode].getWidth() / 2);
	//	destY = mCat_y + (mImgCat[mCatMode].getHeight() / 2);

		// distance between lemon and cat
		//int distanceX = destX - x;
	//	int distanceY = destY - y;

		// lemon speed
		//if (distanceX != 0)
		//	speedX = distanceX / speed;
	//	if (distanceY != 0)
//			speedY = distanceY / speed;

	//	if (Math.abs(speedX) < 1 && Math.abs(speedY) < 1) {
		//	speedX *= 5;
		//	speedX *= 5;
	//	}

		// Lemon l = new Lemon(x, y, speedX, speedY, mImgLemon.getWidth(),
		// mImgLemon.getHeight());

		// mLemonArray.add(l);
	}

	// //////////////////////////////////////////////
	// 게임 시작 시간 체크
	// //////////////////////////////////////////////
	public void setSurviveTime(String time) {
		mSurviveTime = time;
	}

	// //////////////////////////////////////////////
	// 쉴드 시간 체크
	// //////////////////////////////////////////////
	public void setShieldTime(String time) {
		mShieldTime = time;
	}

	// //////////////////////////////////////////////
	// 쉴드 On Off 여부 확인
	// //////////////////////////////////////////////
	public boolean isShieldOn() {
		return isShieldOn;
	}

	// //////////////////////////////
	// 점수 체크
	// //////////////////////////////
	public void setFinalScore(String str) {
		mFinalScore = str;
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	
	
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		mCatThread.start();
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		mCatThread.stopThread();
	}

}
