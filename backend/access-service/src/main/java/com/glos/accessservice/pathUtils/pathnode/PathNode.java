package com.glos.accessservice.pathUtils.pathnode;


import com.glos.accessservice.pathUtils.NodeType;

public interface PathNode {

    String getRootPath();
    String getRootName();
    String getRootFullName();
    NodeType getType();
    String getRootProp(String prop);

}
