package com.hbe.lemondash;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class boss_bomb extends SpriteAnimation{
	
	boolean dead = false;

	public boss_bomb(int _x, int _y) {
		super(AppManager.getInstance().getBitmap(R.drawable.boss_bomb));
		// TODO Auto-generated constructor stub
		
		m_x = _x;
		m_y = _y;
		
		
		
		m_bitmap = createFlyImage(R.drawable.boss_bomb );
		this.InitSpriteDate(m_bitmap.getHeight(), m_bitmap.getWidth()/5, 15, 5);
		
		mbReply = true;
	}
	
	public void move(){	
		m_y += 3;
	
	if(m_y > GameView.mScreenHeight){
		dead =true;
		mbReply = false;
	}
		
	}
	
	public void update(long GameTime){
		super.Update(GameTime);
		
	
		if(this.getAnimationEnd()){
			dead = true;
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
