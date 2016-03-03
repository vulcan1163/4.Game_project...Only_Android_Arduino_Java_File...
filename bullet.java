package com.hbe.lemondash;


import java.util.Random;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class bullet extends SpriteAnimation {
	
	boolean dead = false;

	public bullet(int _x , int _y) {
		super(AppManager.getInstance().getBitmap(R.drawable.bullet3));
		m_x = _x;
		m_y = _y;
		
		Random rnd = new Random();
		int rad = rnd.nextInt(2);
		
		m_bitmap = createFlyImage(R.drawable.bullet3);

		this.InitSpriteDate(m_bitmap.getHeight(), m_bitmap.getWidth()/4, 15, 4);
		
		mbReply = false;
	}

	public void Update(long GameTime) {
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