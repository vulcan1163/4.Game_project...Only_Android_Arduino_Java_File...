package com.hbe.lemondash;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Boss_effect extends SpriteAnimation{
	
	boolean dead = false;
	
	public Boss_effect(int _x, int _y){
		super(AppManager.getInstance().getBitmap(R.drawable.boss_attack));
		
		m_x = _x;
		m_y = _y;
		
		m_bitmap = createFlyImage(R.drawable.boss_attack);
		this.InitSpriteDate(m_bitmap.getHeight(), m_bitmap.getWidth()/2, 5, 2);
		
		mbReply = false;
		
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

		int w = (int) (imgPrevConv.getWidth()
				*  2);
		int h = (int) (imgPrevConv.getHeight()
				*  2);

		return Bitmap.createScaledBitmap(imgPrevConv, w, h, true);
	}

}
