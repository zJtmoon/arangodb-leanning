/*
 * Copyright: 2020 dingxiang-inc.com Inc. All rights reserved.
 */

package com.arangodb.spring.demo.Constant;

/**
 * @FileName: ConditionType.java
 * @Description: ConditionType.java类说明
 * @Author: zjt
 * @Date: 20-8-26 上午2:10
 */
public enum ConditionType {
    PAGERANK("pagerank"), CONNECTEDCOMPONENTS("connectedcomponents"),SCC("scc"),LINERANK("linerank"),SLPA("slpa"),LABELPROPAGATION("labelpropagation"), HITS("hits");

    private final String type;

    private ConditionType(String type) {
        this.type = type;
    }

    public String getType()

    {
        return this.type;
    }
}
