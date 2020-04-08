package com.test;

import org.apache.commons.collections.map.HashedMap;

import java.util.Map;

public class test {

    public static void main(String[] args) {
        Map<String, Object> map = new HashedMap();
        map.put("id", 1l);
        map.getOrDefault("id", "none");

    }
}
