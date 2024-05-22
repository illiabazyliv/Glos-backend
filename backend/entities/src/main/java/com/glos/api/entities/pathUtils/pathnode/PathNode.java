package com.glos.api.entities.pathUtils.pathnode;

import com.glos.api.entities.pathUtils.NodeType;

public interface PathNode {

    String getRootPath();
    String getRootName();
    String getRootFullName();
    NodeType getType();
    String getRootProp(String prop);

}
