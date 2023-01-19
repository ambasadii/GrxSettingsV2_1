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
import android.util.Log;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.N)
public class GrxRefreshRateTile extends TileService {


    private int[] mIconsIds;
    private String[] mModeValues;
    private int mCurrentMode ;
    public Icon mIcon=null;

    private GrxRefreshRateTile.modeObserver mObserver;



    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Toast.makeText(this,"ondestroy",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTileAdded() {
        super.onTileAdded();
        // Toast.makeText(this,"ontileadded",Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onTileRemoved() {
        super.onTileRemoved();
        mObserver.unregister();
        //Toast.makeText(this,"onTileRemoved",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStartListening() {
        super.onStartListening();
        setUpTileStuff();
        readCurrentSavedMode();
        refreshTileState();
        //Toast.makeText(this,"OnStartListening",Toast.LENGTH_SHORT).show();



    }

    @Override
    public void onStopListening() {
        super.onStopListening();
        mObserver.unregister();
        //Toast.makeText(this,"onStopListening",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick() {
        super.onClick();
        //Toast.makeText(this,"onclick",Toast.LENGTH_SHORT).show();
        switch (mCurrentMode)  {
            case 0: mCurrentMode=1;
                    break;
            case 1: mCurrentMode = 2;
                    break;
            case 2: mCurrentMode = 0;
                    break;
        }
        saveCurrentMode();
        refreshTileState();

    }


    private void setUpTileStuff(){
        mObserver= new GrxRefreshRateTile.modeObserver(new Handler());
        mIconsIds = new int[3];
        mIconsIds[0] = getResources().getIdentifier("grx60hz", "drawable", getPackageName());
        mIconsIds[1] = getResources().getIdentifier("grx96hz", "drawable", getPackageName());
        mIconsIds[2] = getResources().getIdentifier("grx120hz", "drawable", getPackageName());
        mModeValues = new String[3];
        mModeValues[0] = "60.0";
        mModeValues[1] = "96.0";
        mModeValues[2] = "120.0";
        mCurrentMode = 0;

    }

    private void readCurrentSavedMode(){
            mCurrentMode=0;
            try{
                mCurrentMode = Settings.Secure.getInt(getContentResolver(),"refresh_rate_mode",0);
            }catch (Exception e){
                Toast.makeText(this,"onclick",Toast.LENGTH_SHORT).show();
            }
    }

    private void refreshTileState(){
        mIcon = Icon.createWithResource(this,mIconsIds[mCurrentMode]);
        Tile mTile = getQsTile();
        mTile.setIcon(mIcon);
        mTile.setState(Tile.STATE_ACTIVE);
        mTile.updateTile();
    }

    private void saveCurrentMode(){
        try {
            Settings.Secure.putInt(getContentResolver(),"refresh_rate_mode",mCurrentMode);
        }catch (Exception e) { // to test logic in VD or without settings permissions.
            Toast.makeText(this,"onclick",Toast.LENGTH_SHORT).show();
            Log.d("GrxRefreshRateTile", e.toString());
        }

        try {
            Settings.System.putString(getContentResolver(),"peak_refresh_rate",mModeValues[mCurrentMode]);
        }catch (Exception e) { // to test logic in VD or without settings permissions.
            Toast.makeText(this,"onclick",Toast.LENGTH_SHORT).show();
            Log.d("GrxRefreshRateTile", e.toString());
        }
    }



    public class modeObserver extends ContentObserver {

        public modeObserver(android.os.Handler handler){
            super(handler);
            register();
        }

        @Override
        public void onChange(boolean selfChange) {
            this.onChange(selfChange, null);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            readCurrentSavedMode();
            refreshTileState();
        }

        public void register() {

            Uri uri = Settings.Secure.getUriFor("refresh_rate_mode");
            getContentResolver().registerContentObserver(uri, false, this);
        }

        public  void unregister(){
            getContentResolver().unregisterContentObserver(this);
        }

    }

}
