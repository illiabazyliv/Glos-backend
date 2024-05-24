package com.glos.filemanagerservice.utils.pathUtils;

import com.glos.filemanagerservice.utils.pathUtils.pathnode.PathNode;

import java.util.LinkedList;
import java.util.List;

public class PathImpl implements Path {

    private final String path;
    private final LinkedList<PathNode> nodes;

    PathImpl(String path, List<PathNode> nodes) {
        this.path = path;
        this.nodes = new LinkedList<>(nodes);
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public List<PathNode> getNodes() {
        return nodes;
    }

    @Override
    public PathNode getFirst() {
        return nodes.getFirst();
    }

    @Override
    public PathNode getLast() {
        return nodes.getLast();
    }

    @Override
    public String toString() {
        return path;
    }
}
