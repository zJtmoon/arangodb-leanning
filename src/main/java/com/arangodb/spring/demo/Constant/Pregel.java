/*
 * Copyright: 2020 dingxiang-inc.com Inc. All rights reserved.
 */

package com.arangodb.spring.demo.Constant;

/**
 * @FileName: Pregel.java
 * @Description: Pregel.java类说明
 * @Author: zjt
 * @Date: 20-8-26 上午3:00
 */
public enum Pregel {

    PAGERANK("pagerank"), CONNECTEDCOMPONENTS("connectedcomponents"), SCC("scc"), LINERANK("linerank"), SLPA("slpa"), LABELPROPAGATION("labelpropagation"), HITS("hits");

    private final String type;

    private Pregel(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public static Pregel getByType(String type) {
        for (Pregel pregel : values()) {
            if (pregel.getType().equals(type)) {
                return pregel;
            }

        }
        return null;

    }

}
