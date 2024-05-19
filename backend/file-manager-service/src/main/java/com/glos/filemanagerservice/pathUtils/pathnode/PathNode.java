package com.glos.filemanagerservice.pathUtils.pathnode;

import com.glos.filemanagerservice.pathUtils.NodeType;

public interface PathNode {

    String getRootPath();
    String getRootName();
    String getRootFullName();
    NodeType getType();
    String getRootProp(String prop);

}
