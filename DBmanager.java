package com.hbe.lemondash;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBmanager extends SQLiteOpenHelper {

	public DBmanager(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		
	}


	public void onCreate(SQLiteDatabase db) {
		
		 db.execSQL("CREATE TABLE Score_LIST( _id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, price INTEGER);");
		 db.execSQL("CREATE TABLE ID_LIST( _id INTEGER PRIMARY KEY AUTOINCREMENT, id TEXT, pw TEXT);");

	}
	

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

    
    ///////////////////////////////////
   //	query insert
   ///////////////////////////////////
    public void insert(String _query) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();     
    }

    
    ///////////////////////////////////
   //	query update
   ///////////////////////////////////
    public void update(String _query) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();     
    }
    
    
     ///////////////////////////////////
    //	query delete
    ///////////////////////////////////
    public void delete(String _query) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();     
    }
    
    
     ////////////////////////////////////
    //		Score list print
    ////////////////////////////////////
    public String PrintData() {
        SQLiteDatabase db = getReadableDatabase();
        String str = "";
        int count = 0; 
        
        Cursor cursor = db.rawQuery("select * from Score_LIST order by price DESC", null);
      
        
        
        
        while(cursor.moveToNext()) {
            str += ++count +" . "
                + "Score : "
                + cursor.getInt(2)
                + "\n";
            
            if(count == 10) return str;
        }
         
        return str;
    }
    
    
    ///////////////////////////////////
    //			ID / PW check
    ////////////////////////////////////
    public boolean checkID(String ID, String PW){
    	
    	 SQLiteDatabase db = getReadableDatabase();
         String str = "";
          
         Cursor cursor = db.rawQuery("select * from ID_LIST", null);
         while(cursor.moveToNext()){
        	 
        	 if(ID.equals(cursor.getString(1))  && PW.equals(cursor.getString(2)) ) return true;
        
         }
    	
    	return false;
    }
    
    
    
    public String testPrintData() {
        SQLiteDatabase db = getReadableDatabase();
        String str = "";
         
        Cursor cursor = db.rawQuery("select * from ID_LIST", null);
        while(cursor.moveToNext()) {
            str += cursor.getInt(0)
                + " : PlayerID :"
                + cursor.getString(1)
                + ", : PW :"
                + cursor.getString(2)
                + "\n";
        }
         
        return str;
    }
}
