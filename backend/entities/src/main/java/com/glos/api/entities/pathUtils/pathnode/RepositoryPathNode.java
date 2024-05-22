package com.glos.api.entities.pathUtils.pathnode;

import com.glos.api.entities.pathUtils.NodeType;
import com.glos.api.entities.pathUtils.AbstractPathNode;

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
