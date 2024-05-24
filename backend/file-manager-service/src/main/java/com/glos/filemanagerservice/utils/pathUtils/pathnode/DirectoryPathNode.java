package com.glos.filemanagerservice.utils.pathUtils.pathnode;

import com.glos.filemanagerservice.utils.pathUtils.AbstractPathNode;
import com.glos.filemanagerservice.utils.pathUtils.NodeType;

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
