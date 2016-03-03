package com.hbe.lemondash;

import java.util.Random;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Boss {

	int x, y;
	Bitmap boss[] =new Bitmap[4];
	Bitmap base;
	boolean dead = false;
	boolean move = true;
	boolean move_2 = false;
	boolean total = true;
	int count = 0;

	int life = 500;

	public Boss() {

		x = GameView.mScreenWidth / 4;
		y = -900;

		base =  createFlyImage(R.drawable.boss);
		
		boss[0] = createFlyImage(R.drawable.boss);

		boss[1] = createFlyImage(R.drawable.boss1);

		boss[2] = createFlyImage(R.drawable.boss2);

		boss[3] = createFlyImage(R.drawable.boss3);
		

	}

	public void move() {
		
		Random rnd = new Random();
		int _x = rnd.nextInt(40)-20;
		if(life < 300)x += _x;
		if (total) {
			if (move) {
				if (y < GameView.mScreenHeight / 4) {
					y += 1;
					if (y >= GameView.mScreenHeight / 4) {
						move = false;
						move_2 = true;
						count++;
					}
				}
			}

			if (move_2) {
				if (y > 0) {
					y -= 1;
					if (y <= 0) {
						move_2 = false;
						move = true;
						count++;
					}
				}
			}
		}
		
		if (count == 6){
			total = false;
			CatMoveThread.bossCheck = 0;
				y -= 1;
		}
		
		if(life <= 0){	
			y += 3;
		//	if(AppManager.getInstance().getCatMoveThread().mBoss_bomb.size() < 5)
		//	AppManager.getInstance().getCatMoveThread().mBoss_bomb.add(new boss_bomb(x+rnd_x, y+rnd_y));
		}
		if (y <= -1000)
			dead = true;
		if(y > GameView.mScreenHeight)
			dead = true;
		
	
	}

	public Bitmap createFlyImage(int r) {
		Resources res = AppManager.getInstance().getGameView().getResources();
		Bitmap imgPrevConv = BitmapFactory.decodeResource(res, r);

		int w = (int) (imgPrevConv.getWidth()
				*  2);
		int h = (int) (imgPrevConv.getHeight()
				*  2);

		return Bitmap.createScaledBitmap(imgPrevConv, w, h, true);
	}

}
