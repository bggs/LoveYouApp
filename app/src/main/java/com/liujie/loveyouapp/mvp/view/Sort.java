package com.liujie.loveyouapp.mvp.view;

import com.liujie.loveyouapp.mvp.Utils.PinyinUtils;
import com.liujie.loveyouapp.mvp.model.SecretResponse;

import java.util.Comparator;

/**
 * 通讯录排序
 */
public class Sort {
    public static class AddressCompartor implements Comparator<SecretResponse> {
        @Override
        public int compare(SecretResponse o1, SecretResponse o2) {
            String o1f = PinyinUtils.getFirstLetter(o1.getName());
            String o2f = PinyinUtils.getFirstLetter(o2.getName());
            if (o1f.equals("#")) {
                return 1;
            } else if (o2f.equals("#")) {
                return -1;
            } else {
                return o1f.compareTo(o2f);
            }
        }
    }
}
