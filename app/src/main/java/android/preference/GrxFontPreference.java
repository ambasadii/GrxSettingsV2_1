package android.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grx.settings.GrxPreferenceScreen;
import com.grx.settings.prefs_dlgs.DlgFrMultiSelect;
import com.grx.settings.utils.Common;
import com.grx.settings.utils.GrxPrefsUtils;

import java.util.regex.Pattern;

public class GrxFontPreference extends GrxSingleSelection{
    private String mLabel;
    int optionsArray, valuesArray;
    TextView summaryView = null;
    Typeface typeface=null;

    public GrxFontPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributes(context, attrs);
    }

    public GrxFontPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributes(context, attrs);
    }


    private void initAttributes(Context context, AttributeSet attrs){
        //setWidgetLayoutResource(0);
        initStringPrefsCommonAttributes(context,attrs,false, false);
        if(myPrefAttrsInfo.getMySummary()==null || myPrefAttrsInfo.getMySummary().isEmpty()) sep_summary ="";
        String defval = myPrefAttrsInfo.getMyStringDefValue();
        if(defval==null) defval="";
        setDefaultValue(defval);
        optionsArray =  getContext().getResources().getIdentifier("pref_font_options","array",getContext().getPackageName());
        valuesArray =  getContext().getResources().getIdentifier("pref_font_values","array",getContext().getPackageName());
    }


    @Override
    protected View onCreateView(ViewGroup parent) {
        View view = super.onCreateView(parent);
        summaryView = (TextView) view.findViewById(android.R.id.summary);
        return view;
    }


    @Override
    public void configStringPreference(String value){
        setWidgetIcon(null);
        String vals_array[] = getContext().getResources().getStringArray(valuesArray);
        int pos = -1;
        for(int i=0;i<vals_array.length;i++) {
            if (value.equals(vals_array[i])) {
                pos = i;
                break;
            }
        }
        if(pos!=-1) {
            String opt_array[] = getContext().getResources().getStringArray(optionsArray);
            mLabel = opt_array[pos];
        }else {
            mLabel= myPrefAttrsInfo.getMySummary();
        }

        String summary = myPrefAttrsInfo.getMySummary();
        if(mLabel!=null && !mLabel.isEmpty() ) summary+= sep_summary +mLabel;
        if(!TextUtils.isEmpty(value) ){
            String font = "fonts/" +  value;
            typeface =Typeface.createFromAsset(getContext().getAssets(),font);
            if(summaryView!=null) summaryView.setTypeface(typeface);
        } else typeface = null;

        setSummary(summary);
    }

    @Override
    public void resetPreference(){
        mStringValue= myPrefAttrsInfo.getMyStringDefValue();
        if(mStringValue==null) mStringValue="";
        configStringPreference(mStringValue);
        saveNewStringValue(mStringValue);
    }

    @Override
    public void showDialog(){
        GrxPreferenceScreen prefsScreen = (GrxPreferenceScreen) getOnPreferenceChangeListener();
        if(prefsScreen!=null){

            DlgFrMultiSelect dlg = (DlgFrMultiSelect) prefsScreen.getFragmentManager().findFragmentByTag(Common.TAG_DLGFRGRMULTISELECT);
            if(dlg==null){
                dlg = DlgFrMultiSelect.newInstance(this, Common.TAG_PREFSSCREEN_FRAGMENT, myPrefAttrsInfo.getMyKey(), myPrefAttrsInfo.getMyTitle(),mStringValue,
                        optionsArray, valuesArray, 0, iconsValueTint,"", 1);
                dlg.show(prefsScreen.getFragmentManager(),Common.TAG_DLGFRGRMULTISELECT);
            }
        }
    }

    @Override
    public void onBindView(View view) {
        super.onBindView(view);
        refreshView();
        if(summaryView!=null) {
            if(typeface!=null) summaryView.setTypeface(typeface);
        }
        String summary = myPrefAttrsInfo.getMySummary();
        if(mLabel!=null && !mLabel.isEmpty() ) summary+= sep_summary +mLabel;
        if(summaryView!=null && typeface !=null) summaryView.setTypeface(typeface);
        setSummary(summary);
    }


    public void GrxSetMultiSelect(String value){
        if(!mStringValue.equals(value)){
            mStringValue=value;
            saveNewStringValue(mStringValue);
            configStringPreference(mStringValue);
        }
    }

}
