package com.my.hash;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.util.ArrayList;

/**
 * Created by liangpw on 2016/9/13.
 */
public class TestHash {
    public static void main(String[] args) {
        HashFunction hf = Hashing.md5();
        //HashCode hc = hf.newHasher().putInt(6).hash();
        HashCode hc = hf.newHasher().putString("12345878",Charsets.UTF_8).hash();
        System.out.println(hc.asInt());

    }

}
