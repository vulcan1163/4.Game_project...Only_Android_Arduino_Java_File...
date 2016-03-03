package com.hbe.lemondash;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;


class Score {
	public int x, y, sw, sh;			// ��ǥ, ��ü �̹��� ũ��
	public Bitmap imgScore;				// ���� ��¿� �̹���
	
	private Bitmap fonts[] = new Bitmap[10];
	private int loop = 0;				// loop counter
	
	//-------------------------------------
	//  ������
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
	//  ���� �̹���
	//-------------------------------------
	public void MakeScore(int _score) {
		String score = "" + _score;			// ���ڷ� ��ȯ
		int L = score.length();				// ���ڿ��� ����

		// �� ��Ʈ���� �����
		imgScore = Bitmap.createBitmap(fonts[0].getWidth() * L, fonts[0].getHeight(), Config.ARGB_8888);

		// ��Ʈ�ʿ� �̹����� �׷����� Canvas ����
		Canvas canvas = new Canvas();
		// Canvas�� ��Ʈ�ʿ� ����ϵ��� ���� - ������ ��� ����� imgScore�� �׷����� 
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
