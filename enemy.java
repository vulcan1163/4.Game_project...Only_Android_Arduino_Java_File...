package com.hbe.lemondash;

import java.util.Random;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class enemy {
	
	int x,y; // 위치
	int m_x, m_y; // 크기
	boolean dead = false;
	boolean move = false;
	boolean bombCheck = true;
	Context context;
	Bitmap enemy[] = new Bitmap[4];
	int line, check, life =3;
	
	
	public enemy(Context _context, int _x, int _y){
		x= _x;
		y= _y;
		m_x = 1;
		m_y = 1;
		context = _context;
		//enemy = createImageBlock(R.drawable.fiter);

		Random rnd = new Random();
		line = rnd.nextInt(GameView.mScreenHeight/4) +GameView.mScreenHeight/5;
		check = rnd.nextInt(2) == 0 ? -5 : 5; 
		m_x = rnd.nextInt(10) -5;
		enemy[0] = createFlyImage(R.drawable.fiter1);
		enemy[1] = createFlyImage(R.drawable.fiter1_right);
		enemy[2] = createFlyImage(R.drawable.fiter1_left);
		enemy[3] = createFlyImage(R.drawable.fiter1_dead);
		
	}
	
	public void move(){
		
		Random rnd = new Random();
	 int rnad = new Random().nextInt(2)== 0 ?  -3 : 3;
		
		if(line >= y ){
			y += 3;
			x += m_x;
		}
		if(line < y){
			move = true;
			x += check;
	
		}
		if(x < -100 || x > GameView.mScreenWidth ){
			
			CatMoveThread.miss++;
			/*
			if(CatMoveThread.miss >= 30){
				AppManager.getInstance().getGameActivity().exitGame();
			}
			*/
			dead = true;
		}
		
		if(life <= 0){
			y += 5;
			x += rnad;
			if(y > GameView.mScreenHeight) dead = true;
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
