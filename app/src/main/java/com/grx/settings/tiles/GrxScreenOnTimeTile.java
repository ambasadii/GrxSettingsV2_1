package com.grx.settings.tiles;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.database.ContentObserver;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;




@TargetApi(Build.VERSION_CODES.N)
public class GrxScreenOnTimeTile extends TileService {

    private gObserver mObserver;
    private String[] mTexts;
    private int[] mIconIds;
    private int[] mTimes;

    public Icon mIcon=null;

    public int mCurrentIndex;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public void onStartListening() {
        super.onStartListening();
        setUpTileStuff();
        setIndex();
        refreshTileState();
        //Toast.makeText(this,"OnStartListening",Toast.LENGTH_SHORT).show();
    }





    private void setUpTileStuff() {
        mObserver = new GrxScreenOnTimeTile.gObserver(new Handler());

        mTexts=new String[6];
        mTexts[0]="15 s"; mTexts[1]="30 s";mTexts[2]="1 m"; mTexts[3]="2 m";mTexts[4]="5 m"; mTexts[5]="10 m";

        mTimes = new int[6];
        mTimes[0]=0x3a98;
        mTimes[1]=0x7530;
        mTimes[2]=0xea60;
        mTimes[3]=0x1d4c0;
        mTimes[4]=0x493e0;
        mTimes[5]=0x927c0;
        mIconIds=new int[6];
        mIconIds[0]=getResources().getIdentifier("grx15s","drawable",getPackageName());
        mIconIds[1]=getResources().getIdentifier("grx30s","drawable",getPackageName());
        mIconIds[2]=getResources().getIdentifier("grx1m","drawable",getPackageName());
        mIconIds[3]=getResources().getIdentifier("grx2m","drawable",getPackageName());
        mIconIds[4]=getResources().getIdentifier("grx5m","drawable",getPackageName());
        mIconIds[5]=getResources().getIdentifier("grx10m","drawable",getPackageName());
      }

      public void setIndex(){
          mCurrentIndex = getCurrentIndex(getCurrentValue());
      }


    private int getCurrentIndex(int time){
        for(int i = 0; i<6;i++) {
            if(mTimes[i]==time) return i;
        }
        return 0;
    }


    private int getValueForIndex(int index){
        return mTimes[index];
    }


    private int getCurrentValue(){
        int defval = 30000;
        ContentResolver contentResolver = getContentResolver();
        try{
            return Settings.System.getInt(contentResolver, "screen_off_timeout",defval );
        }catch (Exception e){

        }
        return defval;
    }


    private void saveValue(int value){
        try{
            Settings.System.putInt(getContentResolver(), "screen_off_timeout",value);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onTileAdded() {
        super.onTileAdded();

        // Toast.makeText(this,"ontileadded",Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onClick() {
        super.onClick();

        if(mCurrentIndex == 5) mCurrentIndex = 0;
        else  mCurrentIndex ++ ;
        saveValue(mTimes[mCurrentIndex]);
        refreshTileState();
    }


    private void refreshTileState(){
        mIcon = Icon.createWithResource(this,mIconIds[mCurrentIndex]);
        Tile mTile = getQsTile();
        mTile.setIcon(mIcon);
        mTile.setState(Tile.STATE_ACTIVE);
        mTile.updateTile();
    }

    @Override
    public void onTileRemoved() {
        super.onTileRemoved();
        if(mObserver!=null) mObserver.unregister();
        //Toast.makeText(this,"onTileRemoved",Toast.LENGTH_SHORT).show();
    }


    public class gObserver extends ContentObserver {

        public gObserver(android.os.Handler handler){
            super(handler);
            register();
        }

        @Override
        public void onChange(boolean selfChange) {
            this.onChange(selfChange, null);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            setIndex();
            refreshTileState();
        }

        public void register() {

            Uri uri = Settings.System.getUriFor("screen_off_timeout");
            getContentResolver().registerContentObserver(uri, false, this);
        }

        public  void unregister(){
            getContentResolver().unregisterContentObserver(this);
        }

    }

}
