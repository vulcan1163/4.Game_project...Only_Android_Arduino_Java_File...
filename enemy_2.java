package com.hbe.lemondash;

import java.util.Random;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class enemy_2 {
	
	int x,y; // 위치
	int m_x, m_y; // 크기
	boolean dead = false;
	boolean move = false;
	boolean bombCheck = true;
	Context context;
	Bitmap enemy[] = new Bitmap[4];
	int line, check;
	int n, life = 3;
	
	public enemy_2(Context _context, int _x, int _y, int _n){
		
		n =_n;
		y= _y;
		m_x = 1;
		m_y = 1;
		context = _context;
		//enemy = createImageBlock(R.drawable.fiter);

		Random rnd = new Random();
		line = rnd.nextInt(GameView.mScreenHeight/3) +GameView.mScreenHeight/4;
		check = rnd.nextInt(2) == 0 ? -5 : 5; 
		m_x = rnd.nextInt(5) +5;
		
		switch(n){
		
		case 0:
		enemy[0] = createFlyImage(R.drawable.fiter_lfet);
		x= _x;
		break;
		
		case 1:
		enemy[1] = createFlyImage(R.drawable.fiter2_right);
		x = 0;
		break;
		}
		
		
		enemy[2] = createFlyImage(R.drawable.fiter3_dead);
		enemy[3] = createFlyImage(R.drawable.fiter2_dead);
		
		
	}
	
	public void move(){
		
		
		if(life <= 0){
			y += 5;
			if(y > GameView.mScreenHeight) dead = true;
		}
		
		Random rnd = new Random();
	
		if(n == 0){
		x -= m_x;
		
		if(x < -100  ){
			/*CatMoveThread.miss++;
			if(CatMoveThread.miss >= 30){
				AppManager.getInstance().getGameActivity().exitGame();
			}*/
			dead = true;
		}
	}
		
		if(n == 1){
			x += m_x;
			
			if(x > GameView.mScreenWidth  ){
				CatMoveThread.miss++;
				/*
				if(CatMoveThread.miss >= 30){
					AppManager.getInstance().getGameActivity().exitGame();
				}*/
				dead = true;
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
