package com.xiaoheyu.reader.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Utils {
    public static  String MD5Digest(String source,Integer salt){
        char[] chars = source.toCharArray();
        //混淆原数据
        for (int i=0;i<chars.length;i++){
            chars[i]=(char) (chars[i]+salt);
        }
        String target=new String(chars);
        String md5=DigestUtils.md5Hex(target);
        return md5;
    }
}
