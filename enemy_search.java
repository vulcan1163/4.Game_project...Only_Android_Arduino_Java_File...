package com.hbe.lemondash;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class enemy_search {
	
	int x,y;
	boolean dead = false;
	Bitmap spot;
	
	public enemy_search(int _x, int _y){
		x = _x;
		y = _y;
		
		spot = createFlyImage(R.drawable.spot);
	}

	public Bitmap createFlyImage(int r) {
		Resources res = AppManager.getInstance().getGameView().getResources();
		Bitmap imgPrevConv = BitmapFactory.decodeResource(res, r);

		int w = (int) (imgPrevConv.getWidth() * AppManager.getInstance().getGameView().mZoomValue);
		int h = (int) (imgPrevConv.getHeight() * AppManager.getInstance().getGameView().mZoomValue);

		return Bitmap.createScaledBitmap(imgPrevConv, w, h, true);
	}

}
