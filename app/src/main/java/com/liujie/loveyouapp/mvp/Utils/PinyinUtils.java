package com.liujie.loveyouapp.mvp.Utils;

import android.text.TextUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 拼音的辅助类  注意添加jar包pinyin4j-2.5.0.jar
 */
public class PinyinUtils {
    /**
     * 返回首字母，大写
     */
    public static String getFirstLetter(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        } // 得到一个字符串的拼音的大写
        String pinyinStr = getPinyin(str).toUpperCase(); // 取拼音字符串的第一个字母
        char firstCahr = pinyinStr.charAt(0); // 不是A-Z字母
        if (firstCahr > 90 || firstCahr < 65) {
            return "#";
        } else { // 代表是A-Z
            return String.valueOf(firstCahr);
        }
    }

    /**
     * 得到一个字符串的拼音读音
     */
    public static String getPinyin(String chineseStr) {
        StringBuffer sb = new StringBuffer(); // 将汉字拆分成一个个的char
        char[] chars = chineseStr.toCharArray(); // 遍历汉字的每一个char
        for (int i = 0; i < chars.length; i++) {
            try { // 汉字的所有读音放在一个pinyins数组
                String[] strings = PinyinHelper.toHanyuPinyinStringArray(chars[i], getDefaultFormat());
                if (strings == null) {
                    sb.append(chars[i]);
                } else {
                    sb.append(strings[0]);
                }
            } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                badHanyuPinyinOutputFormatCombination.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * 设置默认的输出格式
     */
    public static HanyuPinyinOutputFormat getDefaultFormat() {
        HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat(); //去除声调
        outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE); //小写
        outputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE); // 包含Unicode特殊字符
        outputFormat.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
        return outputFormat;
    }
}
