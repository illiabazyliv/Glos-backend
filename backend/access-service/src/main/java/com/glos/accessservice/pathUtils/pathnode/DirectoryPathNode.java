package com.glos.accessservice.pathUtils.pathnode;


import com.glos.accessservice.pathUtils.AbstractPathNode;
import com.glos.accessservice.pathUtils.NodeType;

public class DirectoryPathNode extends AbstractPathNode {

    public DirectoryPathNode() {
        super(NodeType.DIRECTORY);
    }

    public DirectoryPathNode(NodeType type, String rootFullName) {
        super(type, rootFullName);
    }

    public DirectoryPathNode(String rootPath, String rootName, String rootFullName) {
        super(NodeType.DIRECTORY, rootPath, rootName, rootFullName);
    }

}
