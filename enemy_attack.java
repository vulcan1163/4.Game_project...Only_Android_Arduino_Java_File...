package com.hbe.lemondash;

import java.util.Random;

import com.hbe.lemondash.R.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class enemy_attack {
	
	int x,y;
	
	Bitmap attack;
	
	boolean dead = false;
	
	public enemy_attack(int _x, int _y){
		
		x = _x;
		y = _y;
		
		attack = createFlyImage(R.drawable.bomb2);
	}
	
	public void move(){
		y += 6;
		int rand = new Random().nextInt(GameView.mImgBg.getHeight()/2)+(int)(GameView.mImgBg.getHeight()*(0.6f));
	
		
		if(y > GameView.mImgBg.getHeight()){
			dead = true;
			CatMoveThread.city--;
			if(CatMoveThread.city <= 0){
				AppManager.getInstance().getGameActivity().exitGame();
			}
		}
	}

	public Bitmap createFlyImage(int r) {
		Resources res = AppManager.getInstance().getGameView().getResources();
		Bitmap imgPrevConv = BitmapFactory.decodeResource(res, r);

		int w = (int) (imgPrevConv.getWidth() * AppManager.getInstance().getGameView().mZoomValue);
		int h = (int) (imgPrevConv.getHeight() * AppManager.getInstance().getGameView().mZoomValue);

		return Bitmap.createScaledBitmap(imgPrevConv, w, h, true);
	}



}
