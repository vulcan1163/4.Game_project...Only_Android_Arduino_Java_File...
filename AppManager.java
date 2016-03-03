package  com.hbe.lemondash;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class AppManager {
	
	
	public Resources m_resources;

	
	public GameActivity mGameActivity;
	public CatMoveThread mCatMoveThread;
	public GameView m_gameview;


	
	public Bitmap getBitmap(int r){
		return BitmapFactory.decodeResource(m_resources, r);
	}
	

	public void setGameView(GameView gameview){
		m_gameview = gameview;
		
	}

	public void setGameActivity(GameActivity tmp){
		mGameActivity = tmp;
	}
	
	public void setCatMoveThread(CatMoveThread tmp){
		mCatMoveThread = tmp;
	}

	public void setResources(Resources resources){
		m_resources = resources;
	}
	
	public GameView getGameView(){
		return m_gameview;
	}
	
	public GameActivity getGameActivity()	{
		return mGameActivity;
	}
	
	public CatMoveThread getCatMoveThread(){
		return mCatMoveThread;
	}
	

	public Resources getResources(){
		return m_resources;
	}


	
	private static AppManager m_instance;
	
	public static AppManager getInstance(){
		
		if(m_instance == null){
			m_instance = new AppManager();
		}
		
		return m_instance;
	}

}
