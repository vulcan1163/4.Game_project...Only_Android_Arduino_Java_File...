package com.hbe.lemondash;

import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Missile {
	
	int x,y, target_x, target_y;
	Context mContext;
	Bitmap missile;
	
	int sy = 5;
	
	boolean dead = false;
	
	public Missile(Context _context, int _x, int _y, int _target_x, int _target_y){
		x = _x;
		y = _y;
		
		target_x = _target_x;
		target_y = _target_y;
		
		mContext = _context;

		moveMissile();
	}
	
	public void moveMissile(){
		
		if(x > target_x){
			x -=5;
		}else if(x < target_x){
			x += 5;
		}else if(y > target_y){
			y -= 5;
		}
	//	y -= sy;
		
		if(x == target_x && y == target_y) dead = true;
		
		
	}
	
	private static double getAngle(int x1,int y1, int x2,int y2){
		   int dx = x2 - x1;
		   int dy = y2 - y1;
		   
		   double rad= Math.atan2(dx, dy);
		   double degree = (rad*180)/Math.PI ;
		 
		   return degree;
		  }

	

	public int getX(){
		return (int)x;
	}
	
	public int getY(){
		return (int)y;
	}


}
