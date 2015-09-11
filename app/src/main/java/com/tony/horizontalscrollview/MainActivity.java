package com.tony.horizontalscrollview;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private InputStream inputStream;
    List<String> xml = new ArrayList<>();
    private RadioGroup mRadioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWindow();
    }

    private void initWindow() {
        mRadioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        inputStream = getClass().getResourceAsStream("/assets/ED_SoftwareShortcuts.xml");
        try {
            xml = XmlParse.getXML(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0;i < xml.size();i ++){
            RadioButton mRadio = new RadioButton(this);
            mRadio.setText(xml.get(i));
            mRadio.setId(i);
            if (i == 0)
                mRadio.setChecked(true);
            mRadio.setTag(xml.get(i));
            mRadio.setTextSize(getResources().getDimension(R.dimen.text_size_big_plus));
            mRadio.setButtonDrawable(R.color.transparent);
            mRadio.setTextSize(20);
            @SuppressWarnings("ResourceType")
            ColorStateList colors = getResources().getColorStateList(R.drawable.selector_keyshead_tab);
            mRadio.setTextColor(colors);
            RadioGroup.LayoutParams mRadioParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT);
            mRadioParams.setMargins(22, 0, 22, 0);
            mRadioGroup.addView(mRadio, mRadioParams);
            TextView mTextView = new TextView(this);
            mTextView.setBackgroundColor(getResources().getColor(R.color.key_line));
            LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(1, RadioGroup.LayoutParams.WRAP_CONTENT);
            mRadioGroup.addView(mTextView, mLayoutParams);
        }
    }
}
