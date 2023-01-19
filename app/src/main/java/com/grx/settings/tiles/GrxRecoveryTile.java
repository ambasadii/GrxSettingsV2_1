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

import com.grx.settings.utils.RootPrivilegedUtils;

@TargetApi(Build.VERSION_CODES.N)
public class GrxRecoveryTile extends TileService {


    //public Icon mIcon=null;

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
        }

    @Override
    public void onStartListening() {
        super.onStartListening();
        setUpTileStuff();
        refreshTileState();
       }

    @Override
    public void onStopListening() {
        super.onStopListening();
    }

    @Override
    public void onClick() {
            RootPrivilegedUtils.runRebootInRecoveryMode();

        }


    private void setUpTileStuff(){
        /*int id =getResources().getIdentifier("grx_tile_recovery", "drawable", getPackageName();
        mIcon = Icon.createWithResource(this,id);
        */

    }


    private void refreshTileState(){

        Tile mTile = getQsTile();
        //mTile.setIcon(mIcon);
        mTile.setState(Tile.STATE_ACTIVE);
        mTile.updateTile();
    }



}
