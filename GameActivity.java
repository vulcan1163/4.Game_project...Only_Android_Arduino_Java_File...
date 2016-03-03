package com.hbe.lemondash;

import java.util.Calendar;

import com.hbe.usb.HBEState;
import com.hbe.usb.HBEUSB;
import com.hbe.usb.HBEUSBListener;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.app.Activity;
import android.content.Context;

public class GameActivity extends Activity implements HBEUSBListener {
	
	long SHIELD_MAX_TIME = 50;	 //msec

	int CAT_BACK = 0x00;
	int CAT_BACK_LEFT = 0x01;
	int CAT_BACK_RIGHT = 0x02;
	
	int CAT_FRONT = 0x03;
	int CAT_FRONT_LEFT = 0x04;
	int CAT_FRONT_RIGHT = 0x05;
	
	int CAT_LEFT = 0x06;
	int CAT_RIGHT = 0x07;
	int CAT_DOWN = 0x08;
	int CAT_JUMP = 0x09;
	
	byte[] SENSOR_ON = {0x76,0x00,(byte)0x80,0x00,0x01,0x00,(byte)0x81};
	byte[] SENSOR_OFF = {0x76,0x00,(byte)0x80,0x00,0x00,0x00,(byte)0x80};
	
	GameView mView;
	CatMoveThread mCatThread;
	SoundPool mSoundPool;
	int mCatSound, mSOnSound, mSOffSound, gunSound, bomb;
	MediaPlayer mMP3;
	long mShieldTime = SHIELD_MAX_TIME;
	boolean isGameStart = false;
	int mCatArray = CAT_DOWN;
	
	HBEUSB mConnector;
	byte[] mBuffer = new byte[20];
	int mBufferPt;
	String mFinalTime = "";
	DBmanager dbManager;


	///////////////////////////////////
	//		게임 생성시 실행
	///////////////////////////////////
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.gamemain);
		
		mView = new GameView(this, null);
		mConnector = new HBEUSB(this);
		mConnector.setListener(this);
		
		AppManager.getInstance().setGameActivity(this);
		AppManager.getInstance().setResources(getResources());
		
		mCatThread = new CatMoveThread(mView.holder, this);
		dbManager = new DBmanager(getApplicationContext(), "Score.db", null, 1);
		mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		mCatSound = mSoundPool.load(this, R.raw.end, 1);
		gunSound = mSoundPool.load(this, R.raw.gun, 3);
		bomb =  mSoundPool.load(this, R.raw.bomb, 3);
		
	}
	
	
	

	///////////////////////////////////
	//		일시 중지시 실행
	///////////////////////////////////
	protected void onPause() {
		
		isGameStart = false;
		
		if(mCatThread!=null){
			mCatThread.stopThread();
			mCatThread = null;
		}
	
		
		if(mMP3!=null){
			mMP3.stop();
			mMP3.release();
		}
		
		if(mSoundPool!=null){
			mSoundPool.release();
		}

		mConnector.send(SENSOR_OFF);
		mConnector.disconnect();
		
		super.onPause();
	}



	

	///////////////////////////////////
	//		게임 재실시 실행
	///////////////////////////////////
	protected void onResume() {
		
		if(mMP3==null){
			mMP3 = MediaPlayer.create(this, R.raw.wakeup);
			mMP3.setLooping(true);
			mMP3.setVolume(1f, 1f);
			mMP3.start();
		}
		
		if(mSoundPool==null){
			mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
			mCatSound = mSoundPool.load(this, R.raw.end, 1);
			gunSound = mSoundPool.load(this, R.raw.gun, 3);
			bomb =  mSoundPool.load(this, R.raw.bomb, 3);
		}
		
		mConnector.connect();
		mConnector.send(SENSOR_ON);

		super.onResume();
	}
	
	
	

	///////////////////////////////////
	//		게임 종료 루틴
	///////////////////////////////////
	public void exitGame(){

		String name = "guest";
		dbManager.insert("insert into Score_LIST values(null, '" +name  + "', " + CatMoveThread.Tot + ");");
		isGameStart = false;
		CatMoveThread.Tot =0;
		CatMoveThread.city =250;
		if(mMP3 != null)
		mMP3.stop();
		mSoundPool.play(mCatSound,  0.3f,  0.3f,  0,  0,  1);

		if(mCatThread!=null){
			mCatThread.stopThread();
			mCatThread = null;
		}
		
	

		mView.setFinalScore(mFinalTime);
		mView.postInvalidate();
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finish();
	}
	
	
	

	//////////////////////////////////////////
	//		쉴드 셋팅 (소리, OnOff
	//////////////////////////////////////////
	public void setShield(boolean b){
		
		if(!isGameStart) return;
		
		if(b) playShieldOff();
		else playShieldOn();
		
		mView.setShield(b);
	}
	
	
	

	///////////////////////////////////
	//		쉴드 생성 소리 
	///////////////////////////////////
	private void playShieldOn(){
		mSoundPool.play(mSOnSound,  0.3f,  0.3f,  0,  0,  1);
	}
	
	
	

	///////////////////////////////////
	//		쉴드 없어지는 소리
	///////////////////////////////////
	private void playShieldOff(){
		mSoundPool.play(mSOffSound,  0.3f,  0.3f,  0,  0,  1);
	}
	
	
	public void playgun(){
		mSoundPool.play(gunSound,  0.3f,  0.3f,  0,  0,  1);
	}

	public void bomb(){
		mSoundPool.play(bomb,  0.3f,  0.3f,  0,  0,  1);
	}

	//////////////////////////////////////
	//		게임 타이머 스레드
	//////////////////////////////////////
	
	
	
	
	
	///////////////////////////////////////////////
	//			고양이 움직임 스레드
	///////////////////////////////////////////////
	

	
	
	
	///////////////////////////////////////////
	//			아두이노 값 전달
	///////////////////////////////////////////
	public void onReceive(byte[] buff) {
		
		for(int i=0;i<buff.length;i++){
			byte data = buff[i];
			if(data==0x76){
				mBufferPt = 0;
				mBuffer[mBufferPt++] = data;
			} else if(mBufferPt>0) {
				mBuffer[mBufferPt++] = data;
				if(mBufferPt>=7){
					mBufferPt = 0;
					if(mBuffer[5]==16){
					//	CatMoveThread.launch = true;
					}
					int avoidance = mBuffer[4];
					int joystick = mBuffer[5];
				
					
					if(avoidance==1){
						CatMoveThread.launch = true;
						
					
						//if(!mView.isShieldOn() && mShieldTime>SHIELD_MAX_TIME/10) setShield(true);
					}
					else{
						if(mView.isShieldOn()) setShield(false);
					}
					
					if(joystick==1) mCatArray = CAT_RIGHT;
					
					
					else if(joystick==2) mCatArray = CAT_LEFT;
					else if(joystick==4) mCatArray = CAT_FRONT;
					else if(joystick==8) mCatArray = CAT_BACK;
					else if(joystick==16){
						mCatArray = CAT_JUMP;
					
						
					}
					else if(joystick==4+1) mCatArray = CAT_FRONT_RIGHT;
					else if(joystick==4+2) mCatArray = CAT_FRONT_LEFT;
					else if(joystick==8+1) mCatArray = CAT_BACK_RIGHT;
					else if(joystick==8+2) mCatArray = CAT_BACK_LEFT;
					else mCatArray = CAT_DOWN;
					
					//mView.setDebugText("receive:"+str);
				}
			}
		}
	}

	
	
	//////////////////////////////////////
	//			아두이노 상태 
	//////////////////////////////////////
	public void onState(int state) {

		if(state==HBEState.CONNECTED){
			if(mCatThread==null){
				
			}
		
			isGameStart = true;
		} else if(state==HBEState.DISCONNECTED) {
			if(mCatThread!=null){
			
			}
			
		}
	}

}
