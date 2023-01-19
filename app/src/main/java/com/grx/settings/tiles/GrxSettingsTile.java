package com.grx.settings.tiles;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;

@TargetApi(Build.VERSION_CODES.N)
public class GrxSettingsTile extends TileService {

    public Icon mIcon=null;

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
        //Toast.makeText(this,"onTileRemoved",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStartListening() {
        super.onStartListening();
        refreshTileState();
        //Toast.makeText(this,"OnStartListening",Toast.LENGTH_SHORT).show();



    }

    @Override
    public void onStopListening() {
        super.onStopListening();
        //Toast.makeText(this,"onStopListening",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick() {
        super.onClick();
        //Toast.makeText(this,"onclick",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setComponent(new ComponentName("com.ambasadii.settings", "com.grx.settings.GrxSettingsActivity"));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivityAndCollapse(intent);
        } catch (ActivityNotFoundException ignored) {}
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void refreshTileState(){
        mIcon = Icon.createWithResource(this, getResources().getIdentifier("grx_tile_romcontrol",
                "drawable", "com.ambasadii.settings"));
        Tile mTile = getQsTile();
        mTile.setIcon(mIcon);
        mTile.setState(Tile.STATE_ACTIVE);
        mTile.updateTile();
    }

}
