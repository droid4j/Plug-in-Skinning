package com.dapan.skin.support;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.dapan.skin.attr.SkinAttr;
import com.dapan.skin.attr.SkinType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by per4j
 * on 2020-04-04
 */
public class SkinAttrSupport {
    public static List<SkinAttr> getSkinAttrs(Context context, AttributeSet attrs) {
        List<SkinAttr> skinAttrs = new ArrayList<>();
        int count = attrs.getAttributeCount();
        for (int i = 0; i < count; i++) {
            String attributeName = attrs.getAttributeName(i);
            String attributeValue = attrs.getAttributeValue(i);
            SkinType skinType = getSkinType(attributeName);
            if (skinType != null) {
                String resName = getResName(context, attributeValue);
                if (TextUtils.isEmpty(resName)) {
                    continue;
                }
                SkinAttr skinAttr = new SkinAttr(resName, skinType);
                skinAttrs.add(skinAttr);
            }
        }
        return skinAttrs;
    }

    private static String getResName(Context context, String attrValue) {
        if (attrValue.startsWith("@")) {
            attrValue = attrValue.substring(1);
            int resId = Integer.parseInt(attrValue);
            return context.getResources().getResourceEntryName(resId);
        }
        return null;
    }

    private static SkinType getSkinType(String attrName) {
        SkinType skinType = SkinType.getSkinType(attrName);
        return skinType;
    }
}
