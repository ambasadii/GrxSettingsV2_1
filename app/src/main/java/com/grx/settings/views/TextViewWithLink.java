package com.grx.settings.views;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.grx.settings.utils.Common;
import com.grx.settings.utils.GrxPrefsUtils;

/*
 * Grouxho - espdroids.com - 2018

 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.

 */

public class TextViewWithLink extends TextView implements View.OnClickListener{

    private String mUrl;
    private boolean mAnimateText;

    public TextViewWithLink(Context context){
        super(context);
    }

    public TextViewWithLink(Context context, AttributeSet attrs){
        super(context, attrs);
        ini_params(context,attrs);
    }

    private void ini_params(Context context, AttributeSet attributeSet) {
        mUrl = attributeSet.getAttributeValue(null, Common.INFO_ATTR_ULR);
        mAnimateText = attributeSet.getAttributeBooleanValue(null, Common.INFO_ATTR_ANIMATE_TEXT,false);
        if(mUrl!=null) {
            setClickable(true);
            setOnClickListener(this);
        }
        if(mAnimateText){
            setSingleLine();
            GrxPrefsUtils.animateTextviewMarqueeForever(this);
            setSelected(true);
        }
    }

    @Override
    public void onClick(View view){
        if(mUrl!=null){
            Intent myintent=new Intent(Intent.ACTION_VIEW);
            myintent.setData(Uri.parse(mUrl));
            getContext().startActivity(myintent);
        }
    }

}
