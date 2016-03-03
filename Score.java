package com.hbe.lemondash;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;


class Score {
	public int x, y, sw, sh;			// 좌표, 전체 이미지 크기
	public Bitmap imgScore;				// 글자 출력용 이미지
	
	private Bitmap fonts[] = new Bitmap[10];
	private int loop = 0;				// loop counter
	
	//-------------------------------------
	//  생성자
	//-------------------------------------
	public Score(Context context, int _x, int _y, int _score) {
		x = _x;	
		y = _y;
		for (int i = 0; i <= 9; i++)
			fonts[i] = BitmapFactory.decodeResource(context.getResources(), R.drawable.f0 + i);
		MakeScore(_score);
		Move();
	}

	//-------------------------------------
	//  점수 이미지
	//-------------------------------------
	public void MakeScore(int _score) {
		String score = "" + _score;			// 문자로 변환
		int L = score.length();				// 문자열의 길이

		// 빈 비트맵을 만든다
		imgScore = Bitmap.createBitmap(fonts[0].getWidth() * L, fonts[0].getHeight(), Config.ARGB_8888);

		// 비트맵에 이미지를 그려넣을 Canvas 생성
		Canvas canvas = new Canvas();
		// Canvas가 비트맵에 출력하도록 설정 - 이후의 모든 출력은 imgScore에 그려진다 
		canvas.setBitmap(imgScore);
		
		int w = fonts[0].getWidth();	 
		for (int i = 0; i < L; i++) {
			int n = (int) score.charAt(i) - 48;
			canvas.drawBitmap(fonts[n], w * i, 0, null); 
		}

		sw = imgScore.getWidth() / 2;
		sh = imgScore.getHeight() / 2;
	}

	//-------------------------------------
	//  Move
	//-------------------------------------
	public boolean Move() {
		y -= 4;
		if (y < -20)
			return false;
		else
			return true;
	}
}
