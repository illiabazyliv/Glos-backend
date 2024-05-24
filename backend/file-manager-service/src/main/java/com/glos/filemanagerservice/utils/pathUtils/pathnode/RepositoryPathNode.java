package com.glos.filemanagerservice.utils.pathUtils.pathnode;

import com.glos.filemanagerservice.utils.pathUtils.AbstractPathNode;
import com.glos.filemanagerservice.utils.pathUtils.NodeType;

public class RepositoryPathNode extends AbstractPathNode {

    public RepositoryPathNode() {
        super(NodeType.REPOSITORY);
    }

    public RepositoryPathNode(String rootFullName) {
        super(NodeType.REPOSITORY, rootFullName);
    }

    public RepositoryPathNode(String rootPath, String rootName, String rootFullName) {
        super(NodeType.REPOSITORY, rootPath, rootName, rootFullName);
    }

}
