package com.pathtools;

import com.pathtools.pathnode.PathNode;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class PathNodeReader {

    private final Path path;

    public PathNodeReader(String path) {
        this.path = PathParser.getInstance().parse(path);
    }

    public PathNodeReader(Path path) {
        this.path = path;
    }

    public PathNode first() {
        return path.getFirst();
    }

    public PathNode first(int index) {
        checkIndex(index, path.length(), false);
        int i = 0;
        for(PathNode node : path) {
            if (i++ == index) {
                return node;
            }
        }
        return null;
    }

    public PathNode first(String rootFullName) {
        Objects.requireNonNull(rootFullName);
        if (Paths.isRootName(rootFullName)) {
            for (PathNode node : path) {
                if (node.getRootFullName().equals(rootFullName)) {
                    return node;
                }
            }
        } else {
            for (PathNode node : path) {
                if (node.getSimpleName().equals(rootFullName)) {
                    return node;
                }
            }
        }
        return null;
    }

    public PathNode first(NodeType type) {
        Iterator<PathNode> iter = path.iterator();
        while(iter.hasNext()){
            final PathNode node = iter.next();
            if (node.getType() == type) {
                return node;
            }
        }
        return null;
    }

    public PathNode last() {
        return path.getLast();
    }

    public PathNode last(int index) {
        checkIndex(index, path.length(), false);
        int i = path.length() - 1;
        for(PathNode node : path) {
            if (i-- == index) {
                return node;
            }
        }
        return null;
    }

    public PathNode last(String rootFullName) {
        Objects.requireNonNull(rootFullName);
        if (!Paths.isRootName(rootFullName)) {
            Iterator<PathNode> iter = ((LinkedList<PathNode>)path.getNodes()).descendingIterator();
            while(iter.hasNext()){
                final PathNode node = iter.next();
                if (node.getRootFullName().equals(rootFullName)) {
                    return node;
                }
            }
        } else {
            Iterator<PathNode> iter = ((LinkedList<PathNode>)path.getNodes()).descendingIterator();
            while(iter.hasNext()){
                final PathNode node = iter.next();
                if (node.getSimpleName().equals(rootFullName)) {
                    return node;
                }
            }
        }
        return null;
    }

    public List<PathNode> range(int start, int end) {
        return path.getNodes().subList(start, end);
    }

    public List<PathNode> range(String rootNameStart, String rootNameEnd) {
        int start = indexOf(rootNameStart);
        int end = indexOf(rootNameEnd);
        return path.getNodes().subList(start, end);
    }

    public List<PathNode> range(NodeType type, String rootName) {
        int start = indexOf(type);
        int end = indexOf(rootName);
        return path.getNodes().subList(start, end);
    }

    public List<PathNode> range(String rootName, NodeType type) {
        int start = indexOf(rootName);
        int end = indexOf(type);
        return path.getNodes().subList(start, end);
    }

    public PathNode last(NodeType type) {
        Iterator<PathNode> iter = ((LinkedList<PathNode>)path.getNodes()).descendingIterator();
        while(iter.hasNext()){
            final PathNode node = iter.next();
            if (node.getType() == type) {
                return node;
            }
        }
        return null;
    }

    public Stream<PathNode> asStream() {
        return StreamSupport.stream(path.spliterator(), false);
    }

    public Path parent() {
        if (path.length() < 2) {
            return null;
        }
        LinkedList<PathNode> list = new LinkedList<>(path.getNodes());
        list.removeLast();
        return new PathImpl(list.getLast().getRootFullName(), list);
    }

    public Path parent(NodeType type) {
        if (path.length() < 2) {
            return null;
        }
        LinkedList<PathNode> list = new LinkedList<>(path.getNodes());
        PathNode node = list.getLast();
        while(!list.isEmpty()) {
            node = list.getLast();
            if (node.getType() == type) {
                return new PathImpl(list.getLast().getRootFullName(), list);
            }
            list.removeLast();
        }
        return null;
    }

    public int indexOf(PathNode node) {
        return indexOf(node.getRootFullName());
    }

    public int indexOf(String rootName) {
        Objects.requireNonNull(rootName);
        if (rootName.isEmpty()) {
            throw new IllegalArgumentException("rootName is empty");
        }
        int i = 0;
        for(PathNode node : path) {
            if (node.getRootFullName().equals(rootName)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public int indexOf(NodeType type) {
        int i = 0;
        for (PathNode node : path) {
            if (node.getType() == type)
                return i;
            i++;
        }
        return -1;
    }

    public int lastIndexOf(PathNode node) {
        return lastIndexOf(node.getRootFullName());
    }

    public int lastIndexOf(String rootName) {
        Objects.requireNonNull(rootName);
        if (rootName.isEmpty()) {
            throw new IllegalArgumentException("rootName is empty");
        }
        int i = path.length() - 1;
        for(PathNode node : path) {
            if (node.getRootFullName().equals(rootName)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public int lastIndexOf(NodeType type) {
        int i = path.length() - 1;
        Iterator<PathNode> iter = ((LinkedList<PathNode>)path.getNodes()).descendingIterator();
        while(iter.hasNext()){
            final PathNode node = iter.next();
            if (node.getType() == type) {
                return i;
            }
            i--;
        }
        return -1;
    }

    public List<PathNode> getAll() {
        return path.getNodes();
    }

    public List<PathNode> getAllByType(NodeType type) {
        return asStream()
                .filter(x -> x.getType() == type)
                .toList();
    }

    public List<PathNode> getAllByProp(String prop, String value) {
        return asStream()
                .filter(x -> x.getRootProp(prop) != null && x.getRootProp(prop).equals(value))
                .toList();
    }

    public List<PathNode> getAllAfter(int index) {
        checkIndex(index, path.length(), false);
        return path.getNodes().subList(index, path.length() - 1);
    }

    public List<PathNode> getAllBefore(int index) {
        checkIndex(index, path.length(), false);
        return path.getNodes().subList(0, index);
    }

    public List<PathNode> getAllAfterByProp(String prop, String value) {
        Objects.requireNonNull(prop);
        Objects.requireNonNull(value);
        if (prop.isEmpty()) {
            throw new IllegalArgumentException("prop is empty");
        }
        final List<PathNode> nodes = new LinkedList<>();
        boolean isFoundNode = false;
        for(PathNode node : path) {
            if (!isFoundNode && node.getRootProp(prop).equals(value))
                isFoundNode = true;
            if (isFoundNode)
                nodes.add(node);
        }
        return nodes;
    }

    public List<PathNode> getAllBefore(String rootName) {
        Objects.requireNonNull(rootName);
        if (rootName.isEmpty())
            throw new IllegalArgumentException("rootName is empty");
        final List<PathNode> nodes = new LinkedList<>();
        boolean isFoundNode = false;
        for(PathNode node : path) {
            if (!isFoundNode && node.getRootName().equals(rootName))
                isFoundNode = true;
            if (isFoundNode)
                nodes.add(node);
        }
        return nodes;
    }

    public List<PathNode> getAllBeforeByProp(String prop) {
        Objects.requireNonNull(prop);
        if (prop.isEmpty())
            throw new IllegalArgumentException("prop is empty");
        final List<PathNode> nodes = new LinkedList<>();
        boolean isFoundNode = false;
        for(PathNode node : path) {
            if (!isFoundNode && node.getRootName().equals(prop))
                isFoundNode = true;
            if (isFoundNode)
                nodes.add(node);
        }
        return nodes;
    }


    private int checkIndex(int index, int size, boolean includeSize) {
        if (index < 0 || index > size || index == size && !includeSize) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        return index;
    }

}
