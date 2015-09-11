package com.tony.horizontalscrollview;

import android.text.TextUtils;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/11.
 */
public class XmlParse {
    public static List<String> getXML(InputStream xmlSource) throws Exception{
        List<String> xml = null;
        String title = "";
        XmlPullParser pullParser = Xml.newPullParser();
        pullParser.setInput(xmlSource,"UTF-8");
        int event = pullParser.getEventType();
        while (event != XmlPullParser.END_DOCUMENT){
            switch (event){
                case XmlPullParser.START_DOCUMENT:
                    xml = new ArrayList<>();
                    break;
                case XmlPullParser.START_TAG:
                    if ("string".equals(pullParser.getName())){
                        title = pullParser.nextText();
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if ("dict".equals(pullParser.getName())){
                        xml.add(title);
                        title = "";
                    }
                    break;
            }
            event = pullParser.next();
        }
        return xml;
    }

    public static List<KeysItem> getKeys(InputStream xmlSource) throws Exception{
        List<KeysItem> xml = null;
        KeysItem mKeysItem = null;
        String indextitle = "";
        String index = "", title = "", value = "";
        XmlPullParser pullParser = Xml.newPullParser();
        pullParser.setInput(xmlSource,"UTF-8");
        int event = pullParser.getEventType();
        while (event != XmlPullParser.END_DOCUMENT){
            switch (event){
                case XmlPullParser.START_DOCUMENT:
                    xml = new ArrayList<>();
                    break;
                case XmlPullParser.START_TAG:
                    if ("array".equals(pullParser.getName())){
                        pullParser.next();
                        pullParser.next();
                        index = pullParser.getName();
                        for (int i = 0; i < 30; i++) {
                            if ("string".equals(pullParser.getName())) {
                                indextitle += pullParser.nextText();
                                pullParser.next();
                                pullParser.next();
                            }
                        }
                    } else if ("key".equals(pullParser.getName())) {
                        String key = pullParser.nextText();
                        if ("index".equals(key)) {
                            pullParser.next();
                            pullParser.next();
                            index = pullParser.nextText();
                        } else if ("title".equals(key)) {
                            pullParser.next();
                            pullParser.next();
                            title = pullParser.nextText();
                        } else if ("value".equals(key)) {
                            pullParser.next();
                            pullParser.next();
                            value = pullParser.nextText();
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if ("array".equals(pullParser.getName())) {
                        if (!TextUtils.isEmpty(indextitle)) {
                            indextitle = "";
                        }
                    } else if ("dict".equals(pullParser.getName())) {
                        if (!TextUtils.isEmpty(index) && !TextUtils.isEmpty(title) && !TextUtils.isEmpty(value)) {
                            mKeysItem = new KeysItem();
                            mKeysItem.setIndex(index);
                            mKeysItem.setTitle(title);
                            mKeysItem.setValue(value);
                            xml.add(mKeysItem);
                            index = "";
                            title = "";
                            value = "";
                        }
                    }
                    break;
            }
            event = pullParser.next();
        }
        return xml;
    }
}
