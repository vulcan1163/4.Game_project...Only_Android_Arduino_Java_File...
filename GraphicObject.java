package com.hbe.lemondash;


import android.graphics.Bitmap;
import android.graphics.Canvas;

public class GraphicObject {
	public Bitmap m_bitmap;
	public int m_x;
	public int m_y;
	public int s_width, s_height;

	
	public GraphicObject(Bitmap bitmap){
		m_bitmap = bitmap;
		
		m_x=0;
		m_y=0;
		
	}
	
	public void Draw(Canvas canvas){
		
		//m_bitmap = Bitmap.createScaledBitmap(m_bitmap, s_width, s_height, false);
		canvas.drawBitmap(m_bitmap, m_x, m_y, null);
		
	}
	
	
	public void SetPosition(int x, int y){
		m_x = x;
		m_y = y;
	}
	
	public int GetX(){
		return m_x;
	}
	public int Gety(){
		return m_y;
	}

}
