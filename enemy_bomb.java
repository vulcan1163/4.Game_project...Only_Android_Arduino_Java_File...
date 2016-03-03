package com.hbe.lemondash;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class enemy_bomb extends SpriteAnimation{

	boolean dead = false;
	
	public enemy_bomb(int _x , int _y) {
		super(AppManager.getInstance().getBitmap(R.drawable.bomb));
		
		m_x = _x;
		m_y = _y;
		
		m_bitmap = createFlyImage(R.drawable.bomb);
		

		this.InitSpriteDate(m_bitmap.getHeight(), m_bitmap.getWidth()/5, 15, 5);
		
		mbReply = false;

	}
	
	
	public void Update(long GameTime){
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
