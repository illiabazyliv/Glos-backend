package com.glos.filemanagerservice.utils.pathUtils.pathnode;

import com.glos.filemanagerservice.utils.pathUtils.NodeType;

public interface PathNode {

    String getRootPath();
    String getRootName();
    String getRootFullName();
    NodeType getType();
    String getRootProp(String prop);

}
