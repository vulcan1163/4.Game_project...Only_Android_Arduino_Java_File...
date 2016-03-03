package com.hbe.lemondash;

import com.hbe.usb.HBEState;
import com.hbe.usb.HBEUSB;
import com.hbe.usb.HBEUSBListener;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity implements HBEUSBListener {
	
	private final static byte[] SENSOR_ON = {0x76,0x00,(byte)0x80,0x00,0x01,0x00,(byte)0x81};
	private final static byte[] SENSOR_OFF = {0x76,0x00,(byte)0x80,0x00,0x00,0x00,(byte)0x80};
	
	MainView mView;
	CatMoveThread mCatThread;
	LemonThread mLemonThread;
	SoundPool mSoundPool;
	int mCatSound;
	HBEUSB mConnector;
	byte[] mBuffer = new byte[20];
	int mBufferPt;
	boolean isStartingGame = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mView = new MainView(this);
		setContentView(mView);
		
		mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		mCatSound = mSoundPool.load(this, R.raw.gamestart, 1);
		
		mConnector = new HBEUSB(this);
		mConnector.setListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		menu.add("About");
		return true;
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
    	
		if (item.getTitle().equals("About")) {
    	//	Toast.makeText(this, "Cat's Lemon Dash\r\n(ver.HBE-Arduino-Capstone I)\r\nby Hanback Electronics", Toast.LENGTH_LONG).show();
    		return true;
        }
    	
    	return super.onOptionsItemSelected(item);
    }
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		mCatThread.stopThread();
		mCatThread = null;
		mLemonThread.stopThread();
		mLemonThread = null;
		
		mConnector.send(SENSOR_OFF);
		mConnector.disconnect();
		
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		isStartingGame = false;
		
		mCatThread = new CatMoveThread();
		mCatThread.start();
		mLemonThread = new LemonThread();
		mLemonThread.start();
		
		mConnector.connect();
		mConnector.send(SENSOR_ON);
		
		super.onResume();
	}
	
	protected void startGame(){
		if(isStartingGame) return;
		isStartingGame = true;
		
		Intent intent = new Intent(this, GameActivity.class);
		startActivity(intent);
	}
	
	protected void startHowto(){
		Intent intent = new Intent(this, HowtoActivity.class);
		startActivity(intent);
	}
	
class LemonThread extends Thread {
		
		private boolean flag;
		
		public LemonThread(){
			flag = true;
		}
		
		public void stopThread(){
			flag = false;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			int rotate = 0;
			
			while(flag){
				if(mView==null) break;

				try{
					mView.rotateLemon(rotate+=10);
					mView.moveLemon();
					if(rotate==360) rotate=0;
					Thread.sleep(40);
				} catch(Exception e){
					e.printStackTrace();
				}
			}
			
			mView.initCatLocation();
			
			super.run();
		}
		
	}

	class CatMoveThread extends Thread {
		
		private boolean flag;
		
		public CatMoveThread(){
			flag = true;
		}
		
		public void stopThread(){
			flag = false;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			int array = 0;
			
			while(flag){
				if(mView==null) break;
				
				
				try{
					if(array==0){
						for(int i=0;i<25;i++){
							if(!flag) break;
							mView.setCatDown();
							Thread.sleep(40);
						}
						array = 1;
					} else {
						for(int i=0;i<25;i++){
							if(!flag) break;
							mView.setCatUp();
							Thread.sleep(40);
						}
						array = 0;
					}
				} catch(Exception e){
					e.printStackTrace();
				}
			}
			
			mView.initCatLocation();
			
			super.run();
		}
		
	}

	@Override
	public void onReceive(byte[] buff) {
		// TODO Auto-generated method stub
		for(int i=0;i<buff.length;i++){
			byte data = buff[i];
			if(data==0x76){
				mBufferPt = 0;
				mBuffer[mBufferPt++] = data;
			} else if(mBufferPt>0) {
				mBuffer[mBufferPt++] = data;
				if(mBufferPt>=7){
					mBufferPt = 0;
					if(mBuffer[5]==16) startGame();
				}
			}
		}
	}

	@Override
	public void onState(int state) {
		// TODO Auto-generated method stub
		if(state==HBEState.CONNECTED){

		} else if(state==HBEState.DISCONNECTED) {

		}
	}

}
