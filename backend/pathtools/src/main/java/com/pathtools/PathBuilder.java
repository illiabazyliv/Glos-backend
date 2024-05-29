package com.pathtools;

import com.pathtools.pathnode.ArchivePathNode;
import com.pathtools.pathnode.FilePathNode;
import com.pathtools.pathnode.PathNode;

import java.util.Objects;

public class PathBuilder {

    private final StringBuilder builder;

    public PathBuilder() {
        this.builder = new StringBuilder();
    }

    public PathBuilder(String str) {
        this.builder = new StringBuilder(str);
    }

    public PathBuilder repository(String name) {
        return add(name, NodeType.REPOSITORY);
    }

    public PathBuilder repositories(Iterable<String> names) {
        Objects.requireNonNull(names);
        names.forEach(name -> add(name, NodeType.REPOSITORY));
        return this;
    }

    public PathBuilder repositories(String...names) {
        Objects.requireNonNull(names);
        for(String name : names) {
            add(name, NodeType.REPOSITORY);
        }
        return this;
    }


    public PathBuilder directory(String name) {
        return add(name, NodeType.DIRECTORY);
    }

    public PathBuilder directories(Iterable<String> names) {
        Objects.requireNonNull(names);
        names.forEach(name -> add(name, NodeType.DIRECTORY));
        return this;
    }

    public PathBuilder directories(String...names) {
        Objects.requireNonNull(names);
        for(String name : names) {
            add(name, NodeType.DIRECTORY);
        }
        return this;
    }

    public PathBuilder archive(String name) {
        return add(name, NodeType.ARCHIVE);
    }

    public PathBuilder archive(String name, String format) {
        return node(new ArchivePathNode("", name, "", format));
    }

    public PathBuilder file(String name) {
        return add(name, NodeType.FILE);
    }

    public PathBuilder file(String name, String format) {
        Objects.requireNonNull(name, format);
        if (name.isEmpty()) {
            throw new IllegalArgumentException("argument name is empty");
        }
        builder.append(NodeType.FILE.nodeChar());
        return node(new FilePathNode("", name+'.'+format, "", format));
    }

    public PathBuilder add(String name, NodeType nodeType) {
        Objects.requireNonNull(name);
        if (name.isEmpty()) {
            throw new IllegalArgumentException("argument name is empty");
        } else if (name.charAt(0) != nodeType.nodeChar()) {
            builder.append(nodeType.nodeChar());
        }
        builder.append(name);
        return this;
    }

    public PathBuilder node(PathNode node) {
        //return add(node.getRootName(), node.getType());
        builder.append(node.getRootName());
        return this;
    }

    public Path build() {
        return PathParser.getInstance().parse(builder.toString());
    }

}
