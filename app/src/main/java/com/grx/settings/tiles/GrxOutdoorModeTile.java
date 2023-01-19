package com.grx.settings.tiles;

import android.annotation.TargetApi;
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
public class GrxOutdoorModeTile extends TileService {


    private boolean mIsEnabled = false;
    private GrxOutdoorModeTile.modeObserver mObserver;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onTileAdded() {
        super.onTileAdded();
    }

    @Override
    public void onTileRemoved() {
        super.onTileRemoved();
        mObserver.unregister();
    }

    @Override
    public void onStartListening() {
        super.onStartListening();
        mObserver= new GrxOutdoorModeTile.modeObserver(new Handler());
        readCurrentSavedMode();
        refreshTileState();
        //Toast.makeText(this,"OnStartListening",Toast.LENGTH_SHORT).show();



    }

    @Override
    public void onStopListening() {
        super.onStopListening();
        mObserver.unregister();
    }

    @Override
    public void onClick() {
        super.onClick();
        readCurrentSavedMode();
        if(mIsEnabled) mIsEnabled=false;
        else mIsEnabled=true;
        saveCurrentMode();
        refreshTileState();
    }



    private void readCurrentSavedMode(){
        mIsEnabled=false;
        try{
            mIsEnabled = Settings.System.getInt(getContentResolver(),"display_outdoor_mode",0) == 0 ? false : true;

        }catch (Exception e){
            Toast.makeText(this,"onclick",Toast.LENGTH_SHORT).show();
        }
    }

    private void refreshTileState(){
        Tile mTile = getQsTile();

        mTile.setState(mIsEnabled ? Tile.STATE_ACTIVE : Tile.STATE_INACTIVE);
        mTile.updateTile();
    }

    private void saveCurrentMode(){
        try {
            Settings.System.putInt(getContentResolver(),"display_outdoor_mode", mIsEnabled ? 1 : 0);
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
