package android.preference;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GrxEmbedLayout extends GrxBasePreference {

    private String layoutName="";

    public GrxEmbedLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributes(context, attrs);

    }

    public GrxEmbedLayout(Context context, AttributeSet attrs, int defStyleAttr){
        super(context,attrs,defStyleAttr);
        initAttributes(context, attrs);

    }

    private void initAttributes(Context context, AttributeSet attrs){
        initStringPrefsCommonAttributes(context,attrs,false, false);
        layoutName = myPrefAttrsInfo.getMyStringDefValue();
    }

    @Override
    protected View onCreateView(ViewGroup parent) {
        View view = (View) super.onCreateView(parent);
        int layoutid = getContext().getResources().getIdentifier(layoutName,"layout",getContext().getPackageName());
        if(layoutid!=0){

            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(layoutid,null);
            return view;
        }
        return view;
    }

}
