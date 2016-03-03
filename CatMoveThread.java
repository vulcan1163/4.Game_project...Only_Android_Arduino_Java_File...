package com.hbe.lemondash;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.SurfaceHolder;

public class CatMoveThread extends Thread {

	private final int BACK = 1;
	private final int BACK_LEFT = 2;
	private final int BACK_RIGHT = 3;
	private final int FRONT = 4;
	private final int FRONT_LEFT = 5;
	private final int FRONT_RIGHT = 6;
	private final int LEFT = 7;
	private final int RIGHT = 8;
	static int Tot = 0;
	static int bossCheck =0;
	static int miss =0;
	private boolean flag = true;
	
	SurfaceHolder mHolder;
	Context mContext;
	
	long lastTime;
	long lastBulletTime;
	long boss_effect;
	long lastAttack;
	long lastAttack_2;
	long lastAttack_boss;

	int background_x = 0, background_y = 0;
	int target_x = 0, target_y = 0;
	int gunSound, bomb;
	static int city = 250;
	int rotate=0;
	static boolean launch = false;
	
	SoundPool mSound;

	ArrayList<Missile> mMissile = new ArrayList<Missile>();
	ArrayList<enemy> mEnemy = new ArrayList<enemy>();
	ArrayList<enemy_2> mEnemy_2 = new ArrayList<enemy_2>();
	ArrayList<bullet> mBullet = new ArrayList<bullet>();
	ArrayList<enemy_bomb> mEnemy_bomb = new ArrayList<enemy_bomb>();
	ArrayList<Score> mScore = new ArrayList<Score>();
	ArrayList<Boss> mBoss = new ArrayList<Boss>();
	ArrayList<Boss_effect> mBoss_effect = new ArrayList<Boss_effect>();
	ArrayList<enemy_attack>mEnemy_attack = new ArrayList<enemy_attack>();
	ArrayList<enemy_search>mEnemy_search = new ArrayList<enemy_search>();
	ArrayList<boss_bomb> mBoss_bomb = new ArrayList<boss_bomb>();
	

	Score totScore;

	public CatMoveThread(SurfaceHolder holder, Context context) {

		mHolder = holder;
		mContext = context;
		totScore = new Score(mContext, 0, 0, 0); // 총점 표시용 Class

		AppManager.getInstance().setCatMoveThread(this);
		mSound = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);

		
	}

	/*
	 * public void checkLaunch(){
	 * 
	 * long thisTime = System.currentTimeMillis(); if(launch){ if(thisTime -
	 * lastTime >= 500){
	 * 
	 * mMissile.add(new Missile(mContext,GameView.mScreenWidth/2-500,
	 * GameView.mScreenHeight-300,
	 * target_x+AppManager.getInstance().getGameView(
	 * ).target.getWidth()/2,target_y
	 * +AppManager.getInstance().getGameView().target.getHeight()/2));
	 * mMissile.add(new Missile(mContext,GameView.mScreenWidth/2+500,
	 * GameView.mScreenHeight-300,
	 * target_x+AppManager.getInstance().getGameView(
	 * ).target.getWidth()/2,target_y
	 * +AppManager.getInstance().getGameView().target.getHeight()/2)); lastTime
	 * = thisTime; launch = false; return; }
	 * 
	 * 
	 * }
	 * 
	 * }
	 */

	// ////////////////////////////////
	// 스레드 시작
	// ////////////////////////////////
	public CatMoveThread() {
		flag = true;
	}

	protected void AllonDraw(Canvas canvas) {

		long Gametime = System.currentTimeMillis();

		// canvas.drawBitmap(mImgCat[mCatMode], cat_real_x, cat_real_y, mPaint);

		// if(isShieldOn) canvas.drawBitmap(mImgShield, mShield_x, mShield_y,
		// mPaint);
		/*
		 * for(int k=0;k<mLemonArray.size();k++){ canvas.drawBitmap(mImgLemon,
		 * mLemonArray.get(k).getX(), mLemonArray.get(k).getY(), mPaint); }
		 */
		// if(mCatMode==CAT_JUMP) canvas.drawBitmap(mImgCat[CAT_JUMP2],
		// cat_real_x, cat_real_y, mPaint);
		// if(isShieldOn) canvas.drawBitmap(mImgShield, mShield_x, mShield_y,
		// mPaint);
		// canvas.drawText(mShieldTime, mScreenWidth/2,
		// mScreenHeight-mPaint.getTextSize()-10, mPaint);
		// canvas.drawText(mShieldTime,
		// cat_real_x+(mImgCat[mCatMode].getWidth()/2),
		// cat_real_y-mPaint.getTextSize()-5, mPaint);
		// canvas.drawText(mSurviveTime, 10, 24, mPaint);
		// canvas.drawText(mFinalScore, mScreenWidth/2,
		// mmMissile.get(i).xmScreenHeight/2, mPaint);
		/*
		 * // 배경 이미지 그리기 for(int i=0;i<mBgCols;i++){ for(int j=0;j<mBgRows;j++){
		 * canvas.drawBitmap(mImgBg, mImgBg.getWidth()*i, mImgBg.getHeight()*j,
		 * mPaint); } }
		 */

		int cat_real_x = AppManager.getInstance().getGameView().mCat_x
				+ AppManager.getInstance().getGameView().mWeightWidth; // X 좌표
																		// 이동
		int cat_real_y = AppManager.getInstance().getGameView().mCat_y
				+ AppManager.getInstance().getGameView().mWeightHeight; // Y 좌표
																		// 이동
		AppManager.getInstance().getGameView().target_x = AppManager
				.getInstance().getGameView().mCat_x * 2;
		AppManager.getInstance().getGameView().target_y = AppManager
				.getInstance().getGameView().mCat_y * 2;
		AppManager.getInstance().getGameView().mPaint.setTextAlign(Align.LEFT);
		AppManager.getInstance().getGameView().mPaint
				.setTextAlign(Align.CENTER);

		if (!AppManager.getInstance().getGameView().mFinalScore.equals("")) {

			float t_Size = AppManager.getInstance().getGameView().mPaint
					.getTextSize();

			AppManager.getInstance().getGameView().mPaint
					.setTextSize(t_Size * 1.5f);
			AppManager.getInstance().getGameView().mPaint.setTextSize(t_Size);
		}

		if(city > 200){
		canvas.drawBitmap(AppManager.getInstance().getGameView().mImgBg,
				background_x, background_y, AppManager.getInstance()
						.getGameView().mPaint);
		}else if(city <= 200 && city > 150){
			canvas.drawBitmap(AppManager.getInstance().getGameView().mImgBg_2,
					background_x, background_y, AppManager.getInstance()
							.getGameView().mPaint);
		}else if(city <= 150 && city > 100){
			canvas.drawBitmap(AppManager.getInstance().getGameView().mImgBg_3,
					background_x, background_y, AppManager.getInstance()
							.getGameView().mPaint);
		}else if(city <= 100 && city >50){
			canvas.drawBitmap(AppManager.getInstance().getGameView().mImgBg_4,
					background_x, background_y, AppManager.getInstance()
							.getGameView().mPaint);
		}else if(city <= 50){
			canvas.drawBitmap(AppManager.getInstance().getGameView().mImgBg_5,
					background_x, background_y, AppManager.getInstance()
							.getGameView().mPaint);
		}
	
		Paint p = new Paint();
		p.setTextSize(40);
		p.setColor(Color.GREEN);

		//canvas.drawText(" " +  , 500, 500, p);
		int m_x = 0, m_y = 0;
		/*
		 * for (int i = mMissile.size() - 1; i >= 0; i--) {
		 * 
		 * canvas.drawBitmap(AppManager.getInstance().getGameView().missile,
		 * mMissile.get(i).x, mMissile.get(i).y, null);
		 * 
		 * }
			 */

		for( int i = mEnemy_attack.size()-1; i >= 0; i--)	{
			canvas.drawBitmap(mEnemy_attack.get(i).attack, mEnemy_attack.get(i).x,
					mEnemy_attack.get(i).y, null);
		}
		
		for (int i = mEnemy.size() - 1; i >= 0; i--) {
					
					if(mEnemy.get(i).life <= 0)	{
						canvas.drawBitmap(mEnemy.get(i).enemy[3], mEnemy.get(i).x,
								mEnemy.get(i).y, null);
					}
					
					if (mEnemy.get(i).move == false && mEnemy.get(i).life > 0) {
						canvas.drawBitmap(mEnemy.get(i).enemy[0], mEnemy.get(i).x,
								mEnemy.get(i).y, null);
					} else {
						if (mEnemy.get(i).check > 0 && mEnemy.get(i).life > 0) {
							canvas.drawBitmap(mEnemy.get(i).enemy[1], mEnemy.get(i).x,
									mEnemy.get(i).y, null);
						} else if(mEnemy.get(i).check < 0 && mEnemy.get(i).life > 0){
							canvas.drawBitmap(mEnemy.get(i).enemy[2], mEnemy.get(i).x,
									mEnemy.get(i).y, null);
						}
					}
				}
			
		

		for (int i = mEnemy_2.size() - 1; i >= 0; i--) {
			
			if(mEnemy_2.get(i).life <= 0 && mEnemy_2.get(i).n ==0)	{
				canvas.drawBitmap(mEnemy_2.get(i).enemy[2], mEnemy_2.get(i).x,
						mEnemy_2.get(i).y, null);
			}else if(mEnemy_2.get(i).life <= 0 && mEnemy_2.get(i).n ==1){
				canvas.drawBitmap(mEnemy_2.get(i).enemy[3], mEnemy_2.get(i).x,
						mEnemy_2.get(i).y, null);
			}
			
			if(mEnemy_2.get(i).n ==0 && mEnemy_2.get(i).life > 0)
				canvas.drawBitmap(mEnemy_2.get(i).enemy[0], mEnemy_2.get(i).x,
						mEnemy_2.get(i).y, null);
			
			if(mEnemy_2.get(i).n ==1 && mEnemy_2.get(i).life > 0)
				canvas.drawBitmap(mEnemy_2.get(i).enemy[1], mEnemy_2.get(i).x,
						mEnemy_2.get(i).y, null);
		}
		
		
		for (int i = mBoss.size() - 1; i >= 0; i--) {
			
			if(mBoss.get(i).life >= 400)
			canvas.drawBitmap(mBoss.get(i).boss[0], mBoss.get(i).x,
					mBoss.get(i).y, null);
			
			else if(mBoss.get(i).life < 400 &&mBoss.get(i).life >= 300 )
				canvas.drawBitmap(mBoss.get(i).boss[1], mBoss.get(i).x,
						mBoss.get(i).y, null);
			
			else if(mBoss.get(i).life < 300 && mBoss.get(i).life >= 200)
				canvas.drawBitmap(mBoss.get(i).boss[2], mBoss.get(i).x,
						mBoss.get(i).y, null);
			
			else
				canvas.drawBitmap(mBoss.get(i).boss[3], mBoss.get(i).x,
						mBoss.get(i).y, null);
		}

		AppManager.getInstance().getGameView();
		AppManager.getInstance().getGameView();

		for (int i = mBullet.size() - 1; i >= 0; i--) {
			mBullet.get(i).Update(Gametime);
			mBullet.get(i).Draw(canvas);
		}

		for (int i = mEnemy_bomb.size() - 1; i >= 0; i--) {
			mEnemy_bomb.get(i).Update(Gametime);
			mEnemy_bomb.get(i).Draw(canvas);
		}

		for (int i = mBoss_effect.size() - 1; i >= 0; i--) {
			mBoss_effect.get(i).Update(Gametime);
			mBoss_effect.get(i).Draw(canvas);
		}
		
		for (int i = mBoss_bomb.size() - 1; i >= 0; i--) {
			mBoss_bomb.get(i).Update(Gametime);
			mBoss_bomb.get(i).Draw(canvas);
		}

		// canvas.rotate(cat_real_x + 90, GameView.mScreenWidth / 2,
		// (GameView.mScreenHeight / 2) + 600);

		// canvas.drawBitmap(AppManager.getInstance().getGameView().fly,
		// AppManager.getInstance().getGameView().mFly_x,
		// AppManager.getInstance().getGameView().mFly_y + 300,
		// AppManager.getInstance().getGameView().mPaint);
		canvas.drawBitmap(AppManager.getInstance().getGameView().target,
				target_x, target_y,
				AppManager.getInstance().getGameView().mPaint);

	
		canvas.drawBitmap(AppManager.getInstance().getGameView().hud, 0, 0,
				AppManager.getInstance().getGameView().mPaint);
		
			
			
		if(city <= 200 && city > 150){
			canvas.drawBitmap(AppManager.getInstance().getGameView().hud2,
					GameView.mScreenWidth *0.8f-190, GameView.mScreenHeight*0.8f-10, AppManager.getInstance()
							.getGameView().mPaint);
		}else if(city <= 150 && city > 100){
			canvas.drawBitmap(AppManager.getInstance().getGameView().hud3,
					GameView.mScreenWidth *0.8f-190, GameView.mScreenHeight*0.8f-10, AppManager.getInstance()
							.getGameView().mPaint);
		}else if(city <= 100 && city >50){
			canvas.drawBitmap(AppManager.getInstance().getGameView().hud4,
					GameView.mScreenWidth *0.8f-190, GameView.mScreenHeight*0.8f-10, AppManager.getInstance()
							.getGameView().mPaint);
		}else if(city <= 50){
			canvas.drawBitmap(AppManager.getInstance().getGameView().hud5,
					GameView.mScreenWidth *0.8f-190, GameView.mScreenHeight*0.8f-10, AppManager.getInstance()
							.getGameView().mPaint);
		}
	

		canvas.save();
		rotate++;
		canvas.rotate(rotate, GameView.mScreenWidth/2-65+ AppManager.getInstance().getGameView().bar.getWidth(), GameView.mScreenHeight/2 + 180+ AppManager.getInstance().getGameView().bar.getHeight());
		canvas.drawBitmap(AppManager.getInstance().getGameView().bar, GameView.mScreenWidth/2-55, GameView.mScreenHeight/2 + 185,
				AppManager.getInstance().getGameView().mPaint);
		
		canvas.restore();
		
		for (int i = mScore.size() - 1; i >= 0; i--)
			canvas.drawBitmap(mScore.get(i).imgScore,
					mScore.get(i).x - mScore.get(i).sw, mScore.get(i).y
							- mScore.get(i).sh, null);
		// 총점
		totScore.MakeScore(Tot); // 총점을 그래픽 이미지로 만들기
		canvas.drawBitmap(totScore.imgScore, GameView.mScreenWidth/2-700, GameView.mScreenHeight-240, null);
		canvas.drawText("점수 : ", GameView.mScreenWidth/2-850, GameView.mScreenHeight-270, p);
		canvas.drawText("적 공격 횟수 : ", GameView.mScreenWidth/2-850, GameView.mScreenHeight-120, p);
		totScore.MakeScore(city);
		canvas.drawBitmap(totScore.imgScore, GameView.mScreenWidth/2-700, GameView.mScreenHeight-70, null);
		
		for(int i = mEnemy_search.size()-1; i>=0; i--){
			canvas.drawBitmap(mEnemy_search.get(i).spot, mEnemy_search.get(i).x ,mEnemy_search.get(i).y ,null);
		}
		
		
	}
	
	public void canvasEnemy(Canvas canvas){
		
	
	}

	public void collisionCheck() {
		int score = new Random().nextInt(101) + 100;
		long thisTime = System.currentTimeMillis();
		long thiseffect = System.currentTimeMillis();
		Random rnd = new Random();
		int rad = rnd.nextInt(150) - 200;

		if (thisTime - lastBulletTime >= 100) {

			mBullet.add(new bullet(target_x
					+ AppManager.getInstance().getGameView().target.getWidth()
					/ 2 + rad, target_y
					+ AppManager.getInstance().getGameView().target.getHeight()
					/ 2 + rad));
			AppManager.getInstance().getGameActivity().playgun();

			lastBulletTime = thisTime;
		}

		for (int i = mEnemy.size() - 1; i >= 0; i--) {

			if (mEnemy.get(i).x < target_x
					+ AppManager.getInstance().getGameView().target.getWidth()
					/ 2
					&& mEnemy.get(i).x + mEnemy.get(i).enemy[0].getWidth() > target_x
							+ AppManager.getInstance().getGameView().target
									.getWidth() / 2
					&& mEnemy.get(i).y < target_y
							+ AppManager.getInstance().getGameView().target
									.getHeight() / 2
					&& mEnemy.get(i).y + mEnemy.get(i).enemy[0].getHeight() > target_y
							+ AppManager.getInstance().getGameView().target
									.getHeight() / 2) {
				mEnemy.get(i).life--;
				
				if(mEnemy.get(i).life <= 0 && mEnemy.get(i).bombCheck){
					
				mEnemy_bomb.add(new enemy_bomb(mEnemy.get(i).x, mEnemy.get(i).y));
				mScore.add(new Score(mContext, mEnemy.get(i).x,mEnemy.get(i).y, score));
			
				Tot += score;
				bossCheck += score;
				AppManager.getInstance().getGameActivity().bomb();
				mEnemy.get(i).bombCheck = false;
				mEnemy_search.remove(i);
				}

				//mEnemy.remove(i);
				launch = false;
			}
		}
		
		for (int i = mEnemy_2.size() - 1; i >= 0; i--) {
			
			if(mEnemy_2.get(i).n == 0){
				if (mEnemy_2.get(i).x < target_x
						+ AppManager.getInstance().getGameView().target.getWidth()
						/ 2
						&& mEnemy_2.get(i).x + mEnemy_2.get(i).enemy[0].getWidth() > target_x
								+ AppManager.getInstance().getGameView().target
										.getWidth() / 2
						&& mEnemy_2.get(i).y < target_y
								+ AppManager.getInstance().getGameView().target
										.getHeight() / 2
						&& mEnemy_2.get(i).y + mEnemy_2.get(i).enemy[0].getHeight() > target_y
								+ AppManager.getInstance().getGameView().target
										.getHeight() / 2) {
					
					mEnemy_2.get(i).life--;
					
					if(mEnemy_2.get(i).life <= 0 && mEnemy_2.get(i).bombCheck){
					
					mEnemy_bomb.add(new enemy_bomb(mEnemy_2.get(i).x, mEnemy_2.get(i).y));
					mScore.add(new Score(mContext, mEnemy_2.get(i).x,mEnemy_2.get(i).y, score));
					Tot += score;
					bossCheck += score;
					AppManager.getInstance().getGameActivity().bomb();
					mEnemy_search.remove(i);
					//mEnemy_2.remove(i);
					launch = false;
					mEnemy_2.get(i).bombCheck = false;
					}
				}
		}
		}
		
		for (int i = mEnemy_2.size() - 1; i >= 0; i--) {
			
			/*
			if(mEnemy_2.get(i).n == 0){
			if (mEnemy_2.get(i).x < target_x
					+ AppManager.getInstance().getGameView().target.getWidth()
					/ 2
					&& mEnemy_2.get(i).x + mEnemy_2.get(i).enemy[0].getWidth() > target_x
							+ AppManager.getInstance().getGameView().target
									.getWidth() / 2
					&& mEnemy_2.get(i).y < target_y
							+ AppManager.getInstance().getGameView().target
									.getHeight() / 2
					&& mEnemy_2.get(i).y + mEnemy_2.get(i).enemy[0].getHeight() > target_y
							+ AppManager.getInstance().getGameView().target
									.getHeight() / 2) {
				mEnemy_bomb
						.add(new enemy_bomb(mEnemy_2.get(i).x, mEnemy_2.get(i).y));
				mScore.add(new Score(mContext, mEnemy_2.get(i).x,
						mEnemy_2.get(i).y, score));
				Tot += score;
				bossCheck += score;
				mEnemy_2.remove(i);
				launch = false;
			}
			}
			*/ 
			if(mEnemy_2.get(i).n == 1){
				if (mEnemy_2.get(i).x < target_x
						+ AppManager.getInstance().getGameView().target.getWidth()
						/ 2
						&& mEnemy_2.get(i).x + mEnemy_2.get(i).enemy[1].getWidth() > target_x
								+ AppManager.getInstance().getGameView().target
										.getWidth() / 2
						&& mEnemy_2.get(i).y < target_y
								+ AppManager.getInstance().getGameView().target
										.getHeight() / 2
						&& mEnemy_2.get(i).y + mEnemy_2.get(i).enemy[1].getHeight() > target_y
								+ AppManager.getInstance().getGameView().target
										.getHeight() / 2) {
					mEnemy_2.get(i).life--;
					
					if(mEnemy_2.get(i).life <= 0 && mEnemy_2.get(i).bombCheck){
					
					mEnemy_bomb
							.add(new enemy_bomb(mEnemy_2.get(i).x, mEnemy_2.get(i).y));
					mScore.add(new Score(mContext, mEnemy_2.get(i).x,
							mEnemy_2.get(i).y, score));
					Tot += score;
					bossCheck += score;
					
					AppManager.getInstance().getGameActivity().bomb();
					mEnemy_search.remove(i);
					//mEnemy_2.remove(i);
					launch = false;
					mEnemy_2.get(i).bombCheck = false;
				}
				}
			}
		
		}
		

		for (int i = mBoss.size() - 1; i >= 0; i--) {
			if (target_x
					+ AppManager.getInstance().getGameView().target.getWidth()
					/ 2 > mBoss.get(i).x
					&& mBoss.get(i).x + mBoss.get(i).boss[0].getWidth() > target_x
							+ AppManager.getInstance().getGameView().target
									.getWidth() / 2
					&& mBoss.get(i).y < target_y
							+ AppManager.getInstance().getGameView().target
									.getHeight() / 2
					&& mBoss.get(i).y + mBoss.get(i).boss[0].getHeight() > target_y
							+ AppManager.getInstance().getGameView().target
									.getHeight() / 2) {
				mBoss.get(i).life--;
				if (thiseffect - boss_effect >= 1500) {
				//mBoss_effect.add(new Boss_effect(mBoss.get(i).x, mBoss.get(i).y));
				//boss_effect = thiseffect;
				//여기 보스 쳐맞는 모션 넣는곳
				}
				if(mBoss.get(i).life <= 0){
					bossCheck = 0;
					Random rnd2 = new Random();
					int rad_x = rnd2.nextInt(mBoss.get(i).boss[2].getWidth()/2);
					int rad_y = rnd2.nextInt(mBoss.get(i).boss[2].getHeight()/2);
					if(mBoss_bomb.size() < 4)
					mBoss_bomb.add(new boss_bomb(mBoss.get(i).x+rad_x,mBoss.get(i).y+rad_y-150));
		
				}

			}
		}
	}

	public void backgroundCheck(int n) {

		switch (n) {

		case BACK:
			if (background_y <= -500) {
				background_y = -500;
				break;
			}
			background_y -= 5;
			break;

		case BACK_LEFT:
			if (background_y <= -500) {
				background_y = -500;
				break;
			}
			if (background_x <= -500) {
				background_x = -500;
				break;
			}
			background_x -= 5;
			background_y -= 5;

			break;

		case BACK_RIGHT:
			if (background_x >= 0) {
				background_x = 0;
				break;
			}
			if (background_y <= -500) {
				background_y = -500;
				break;
			}
			background_x += 5;
			background_y -= 5;

			break;

		case FRONT:
			if (background_y >= 0) {
				background_y = 0;
				break;
			}
			background_y += 5;

			break;

		case FRONT_LEFT:
			if (background_x <= -500) {
				background_x = -500;
				break;
			}
			if (background_y >= 0) {
				background_y = 0;
				break;
			}
			background_x -= 5;
			background_y += 5;

			break;

		case FRONT_RIGHT:

			if (background_x >= 0) {
				background_x = 0;
				break;
			}
			if (background_y >= 0) {
				background_y = 0;
				break;
			}

			background_x += 5;
			background_y += 5;

			break;

		case LEFT:
			if (background_x <= -500) {
				background_x = -500;
				break;
			}
			background_x -= 5;
			break;

		case RIGHT:
			if (background_x >= 0) {
				background_x = 0;
				break;
			}
			background_x += 5;
			break;

		}
	}

	public void targetCheck(int n) {

		switch (n) {

		case FRONT:
			if (target_y <= 0) {
				target_y = 0;
				break;
			}
			target_y -= 18;
			break;

		case FRONT_RIGHT:
			if (target_y <= 0) {
				target_y = 0;
				break;
			}
			if (target_x <= 0) {
				target_x = 0;
				break;
			}
			target_x -= 18;
			target_y -= 18;

			break;

		case FRONT_LEFT:
			if (target_y <= 0) {
				target_y = 0;
				break;
			}
			if (target_x >= GameView.mScreenWidth) {
				target_x = GameView.mScreenWidth;
				break;
			}
			target_x += 18;
			target_y -= 18;

			break;

		case BACK:
			if (target_y >= GameView.mScreenHeight / 2) {
				target_y = GameView.mScreenHeight / 2;
				break;
			}
			target_y += 18;

			break;

		case BACK_RIGHT:
			if (target_x <= 0) {
				target_x = 0;
				break;
			}
			if (target_y >= GameView.mScreenHeight / 2) {
				target_y = GameView.mScreenHeight / 2;
				break;
			}
			target_x -= 18;
			target_y += 18;

			break;

		case BACK_LEFT:
			if (target_x >= GameView.mScreenWidth
					- AppManager.getInstance().getGameView().target.getWidth()) {
				target_x = GameView.mScreenWidth
						- AppManager.getInstance().getGameView().target
								.getWidth();
				break;
			}

			if (target_y >= GameView.mScreenHeight / 2) {
				target_y = GameView.mScreenHeight / 2;
				break;
			}
			target_x += 18;
			target_y += 18;

			break;

		case RIGHT:
			if (target_x <= 0) {
				target_x = 0;
				break;
			}
			target_x -= 18;
			break;

		case LEFT:
			if (target_x >= GameView.mScreenWidth
					- AppManager.getInstance().getGameView().target.getWidth()) {
				target_x = GameView.mScreenWidth
						- AppManager.getInstance().getGameView().target
								.getWidth();
				break;
			}
			target_x += 18;
			break;

		}
	}
	
	public void Enemy_attack(){
		
		long thisTime = System.currentTimeMillis();
		
		for(int i = mEnemy.size() -1; i >=0; i--){
			
			if(mEnemy.get(i).life > 0){
			if( thisTime - lastAttack >= 1000){
				mEnemy_attack.add(new enemy_attack(mEnemy.get(i).x+150, mEnemy.get(i).y+80));
				lastAttack = thisTime;
				}
			}
		}
		
		for(int i = mEnemy_2.size() -1; i >=0; i--){
					
					if(mEnemy_2.get(i).life > 0){
					if( thisTime - lastAttack_2 >= 1000){
						mEnemy_attack.add(new enemy_attack(mEnemy_2.get(i).x+150, mEnemy_2.get(i).y+80));
						lastAttack_2 = thisTime;
						}
					}
				}
		for(int i = mBoss.size() -1; i >=0; i--){
			
			if(mBoss.get(i).life > 250){
			
			if( thisTime - lastAttack_boss >= 2500){
				mEnemy_attack.add(new enemy_attack(mBoss.get(i).x+750, mBoss.get(i).y+500));
				lastAttack_boss = thisTime;
			}
		}
		}
	}

	public void moveMissile() {
		

		for (int i = mEnemy_attack.size() - 1; i >= 0; i--) {
			mEnemy_attack.get(i).move();
			if (mEnemy_attack.get(i).dead == true) {
				mEnemy_attack.remove(i);
			}
		}
		

		for (int i = mMissile.size() - 1; i >= 0; i--) {
			mMissile.get(i).moveMissile();
			if (mMissile.get(i).dead == true) {
				mMissile.remove(i);
			}
		}

		for (int i = mEnemy.size() - 1; i >= 0; i--) {
			mEnemy.get(i).move();
			if (mEnemy.get(i).dead == true) {
				mEnemy.remove(i);
			}
		}
		
		for (int i = mEnemy_2.size() - 1; i >= 0; i--) {
			mEnemy_2.get(i).move();
			if (mEnemy_2.get(i).dead == true) {
				mEnemy_2.remove(i);
			}
		}

		for (int i = mBoss.size() - 1; i >= 0; i--) {
			mBoss.get(i).move();
			if (mBoss.get(i).dead == true) {
				mBoss.remove(i);
			}
		}

		for (int i = mBullet.size() - 1; i >= 0; i--) {

			if (mBullet.get(i).dead == true) {
				mBullet.remove(i);
			}
		}

		for (int i = mEnemy_bomb.size() - 1; i >= 0; i--) {

			if (mEnemy_bomb.get(i).dead == true) {
				mEnemy_bomb.remove(i);
			}
		}
		
		for (int i = mBoss_bomb.size() - 1; i >= 0; i--) {
			
			mBoss_bomb.get(i).move();
			if (mBoss_bomb.get(i).dead == true) {
				mBoss_bomb.remove(i);
			}
		}

		for (int i = mScore.size() - 1; i >= 0; i--)
			if (mScore.get(i).Move() == false)
				mScore.remove(i);

		for (int i = mBoss_effect.size() - 1; i >= 0; i--) {

			if (mBoss_effect.get(i).dead == true) {
				mBoss_effect.remove(i);
			}

		}
		
		for (int i = mEnemy_search.size() - 1; i >= 0; i--) {

			if (mEnemy_search.get(i).dead == true) {
				mEnemy_search.remove(i);
			}

		}
	}

	public void makeEnemy() {

	

			int x_rnd, y_rnd;
			Random r = new Random();

			x_rnd = r.nextInt(GameView.mScreenWidth);
			y_rnd = r.nextInt(GameView.mScreenHeight/5)+GameView.mScreenHeight/6;
			int n = r.nextInt(2);

			// y_rnd = r.nextInt(GameView.mScreenHeight/2);

			if (mEnemy.size() < 3) {
				mEnemy.add(new enemy(mContext, x_rnd, -20));
			}
			
			
			if (mEnemy_2.size() < 2) {
				mEnemy_2.add(new enemy_2(mContext, GameView.mScreenWidth, y_rnd, n));
			}
			
			
			if (bossCheck >= 2000 && mBoss.size() < 1) {
				
			mBoss.add(new Boss());
		}
			
			
		for(int i = mEnemy.size()-1; i >= 0; i--){
			for(int a = mEnemy_search.size()-1; a>=0; a--){
			if(mEnemy.get(i).dead == true)mEnemy_search.get(a).dead =true;
		
			}
		}
		
		for(int i = mEnemy_2.size()-1; i >= 0; i--){
			for(int a = mEnemy_search.size()-1; a>=0; a--){
			if(mEnemy_2.get(i).dead == true)mEnemy_search.get(a).dead =true;
		
			}
		}
		

		Random rnd = new Random();
		int rand_x = rnd.nextInt(AppManager.getInstance().getGameView().bar.getWidth()*4-50)+GameView.mScreenWidth/3+200;
		int rand_y = rnd.nextInt(AppManager.getInstance().getGameView().bar.getHeight()*2-50)+GameView.mScreenHeight/2+185;
		
		if(mEnemy.size()+mEnemy_2.size() >mEnemy_search.size())
		mEnemy_search.add(new enemy_search(rand_x, rand_y));
		
	}

	/*
	 * public void makeMissile() {
	 * 
	 * long thisTime = System.currentTimeMillis();
	 * 
	 * if (thisTime - lastTime >= 300){ mMissile.add(new
	 * Missile(mContext,GameView.mScreenWidth/2-500, GameView.mScreenHeight-300,
	 * target_x
	 * +AppManager.getInstance().getGameView().target.getWidth()/2,target_y
	 * +AppManager.getInstance().getGameView().target.getHeight()/2));
	 * mMissile.add(new Missile(mContext,GameView.mScreenWidth/2+500,
	 * GameView.mScreenHeight-300,
	 * target_x+AppManager.getInstance().getGameView(
	 * ).target.getWidth()/2,target_y
	 * +AppManager.getInstance().getGameView().target.getHeight()/2)); lastTime
	 * = thisTime; }
	 * 
	 * }
	 */
	// //////////////////////////////////
	// 스레드 종료
	// //////////////////////////////////
	public void stopThread() {
		flag = false;
	}

	public void run() {

		Canvas canvas = null;

		while (flag) {

			canvas = mHolder.lockCanvas();

			try {

				synchronized (mHolder) {

					if (canvas == null)
						break;

					AllonDraw(canvas);
					Enemy_attack();
					makeEnemy();
					moveMissile();
					// checkLaunch();
					if (launch)
						collisionCheck();
					
					

					int delay = 150;

					if (AppManager.getInstance().getGameActivity().mCatArray == AppManager
							.getInstance().getGameActivity().CAT_BACK) {
						AppManager.getInstance().getGameActivity().mView
								.setBackCat();
						backgroundCheck(BACK);
						targetCheck(BACK);
					}

					else if (AppManager.getInstance().getGameActivity().mCatArray == AppManager
							.getInstance().getGameActivity().CAT_BACK_LEFT) {
						AppManager.getInstance().getGameActivity().mView
								.setBackLeftCat();
						backgroundCheck(BACK_LEFT);
						targetCheck(BACK_LEFT);
					}

					else if (AppManager.getInstance().getGameActivity().mCatArray == AppManager
							.getInstance().getGameActivity().CAT_BACK_RIGHT) {
						AppManager.getInstance().getGameActivity().mView
								.setBackRightCat();
						backgroundCheck(BACK_RIGHT);
						targetCheck(BACK_RIGHT);
					}

					else if (AppManager.getInstance().getGameActivity().mCatArray == AppManager
							.getInstance().getGameActivity().CAT_FRONT) {
						AppManager.getInstance().getGameActivity().mView
								.setFrontCat();
						backgroundCheck(FRONT);
						targetCheck(FRONT);
					}

					else if (AppManager.getInstance().getGameActivity().mCatArray == AppManager
							.getInstance().getGameActivity().CAT_FRONT_LEFT) {
						AppManager.getInstance().getGameActivity().mView
								.setFrontLeftCat();
						backgroundCheck(FRONT_LEFT);
						targetCheck(FRONT_LEFT);
					}

					else if (AppManager.getInstance().getGameActivity().mCatArray == AppManager
							.getInstance().getGameActivity().CAT_FRONT_RIGHT) {
						AppManager.getInstance().getGameActivity().mView
								.setFrontRightCat();
						backgroundCheck(FRONT_RIGHT);
						targetCheck(FRONT_RIGHT);
					}

					else if (AppManager.getInstance().getGameActivity().mCatArray == AppManager
							.getInstance().getGameActivity().CAT_LEFT) {
						AppManager.getInstance().getGameActivity().mView
								.setLeftCat();
						backgroundCheck(LEFT);
						targetCheck(LEFT);
					}

					else if (AppManager.getInstance().getGameActivity().mCatArray == AppManager
							.getInstance().getGameActivity().CAT_RIGHT) {
						AppManager.getInstance().getGameActivity().mView
								.setRightCat();
						backgroundCheck(RIGHT);
						targetCheck(RIGHT);
					}

					else if (AppManager.getInstance().getGameActivity().mCatArray == AppManager
							.getInstance().getGameActivity().CAT_DOWN) {
						AppManager.getInstance().getGameActivity().mView
								.setDownCat();
						// Thread.sleep(5000);
						// mView.makeMissile();

					} else if (AppManager.getInstance().getGameActivity().mCatArray == AppManager
							.getInstance().getGameActivity().CAT_JUMP) {
						// launch = true;
						// mView.setJumpCat();
						// mView.makeMissile();

					}

				}
			} finally {
				if (canvas != null)
					mHolder.unlockCanvasAndPost(canvas);

			}
		}

		super.run();
	}

} // 스레드 . Class 끝
