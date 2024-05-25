package com.glos.accessservice.entities.pathUtils.pathnode;


import com.glos.accessservice.entities.pathUtils.NodeType;

public interface PathNode {

    String getRootPath();
    String getRootName();
    String getRootFullName();
    NodeType getType();
    String getRootProp(String prop);

}
