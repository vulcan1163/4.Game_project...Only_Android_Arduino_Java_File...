package com.hbe.lemondash;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/*
 * 	
 * 	스프라이트를 구동시키는 곳 
 * 	물체가 앞으로 이동하면 늘고 
 *  물체가 뒤로 이동하면 줄여라 
 * 
 * 
 */

public class SpriteAnimation extends GraphicObject {
	
	private Rect m_Rect;
	private int m_fps;
	private int m_iFrames;
	
	private int m_CurrentFrame;
	private int m_SpriteWidth;
	private int m_SpriteHeight;
	private long m_FrameTimer;
	
	protected boolean mbEnd = false;
	protected boolean mbReply = true;
	
	public SpriteAnimation(Bitmap bitmap){
		super(bitmap);
		m_Rect = new Rect(0,0,0,0);
		m_FrameTimer = 0;
		m_CurrentFrame = 0;
	}

	public void InitSpriteDate(int height, int width ,int fps, int iframe ){
		
		m_SpriteWidth = width;
		m_SpriteHeight = height;
		m_iFrames = iframe;
		m_Rect.top = 0;
		m_Rect.bottom= m_SpriteHeight;
		m_Rect.left=0;
		m_Rect.right= m_SpriteWidth;
		m_fps = 1000/fps;

	}
	
	public void Draw(Canvas canvas){
		Rect dest = new Rect(m_x, m_y, m_x + m_SpriteWidth, m_y + m_SpriteHeight);
		canvas.drawBitmap(m_bitmap, m_Rect, dest, null);
	}
	public void Update(long GameTime){
		if(!mbEnd){
			
		if(GameTime > m_FrameTimer + m_fps){
			m_FrameTimer = GameTime;
			m_CurrentFrame += 1;
			
				if(m_CurrentFrame >= m_iFrames){
					
					if(mbReply)
					m_CurrentFrame = 0;
					else{
						mbEnd = true;
					
					}
						
				}
			}
		}
		m_Rect.left = m_CurrentFrame * m_SpriteWidth;
		m_Rect.right = m_Rect.left + m_SpriteWidth;
	}
	
	public boolean getAnimationEnd(){
		return mbEnd;
	}
}
