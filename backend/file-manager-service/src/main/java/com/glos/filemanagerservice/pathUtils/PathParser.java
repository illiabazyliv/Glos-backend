package com.glos.filemanagerservice.pathUtils;

import com.glos.filemanagerservice.pathUtils.pathnode.PathNode;
import com.glos.filemanagerservice.pathUtils.pathnode.PathNodeProps;
import org.apache.commons.lang.NullArgumentException;

import java.util.LinkedList;
import java.util.Map;

/**
 * @author Mykola Melnyk
 */
public class PathParser {

    public Path parse(final String pathStr) {
        if (pathStr == null) {
            throw new NullArgumentException("Argument 'pathStr' is null");
        }
        final String pathTrim = pathStr.trim();

        if (pathTrim.isEmpty()) {
            return Paths.EMPTY_PATH;
        }

        final StringBuilder builder = new StringBuilder();
        final LinkedList<PathNode> nodes = new LinkedList<>();

        final int len = pathTrim.length();

        int i = 0;
        char c = ' ';

        final StringBuilder nodeBuilder = new StringBuilder();
        nodeBuilder.append(pathTrim.charAt(i));
        c = pathTrim.charAt(i);
        NodeType prevType = NodeType.fromNodeChar(c);
        NodeType curType = NodeType.NONE;
        while(++i < len) {
            c = pathTrim.charAt(i);
            if (curType != NodeType.NONE) {
                prevType = curType;
                nodeBuilder.append(prevType.nodeChar());
            }
            while(NodeType.fromNodeChar(c) == NodeType.NONE && ++i < len) {
                nodeBuilder.append(c);
                c = pathTrim.charAt(i);
                if (i == len - 1)
                    nodeBuilder.append(c);
                curType = NodeType.fromNodeChar(c);
            }
            final String rootPath = builder.toString();
            builder.append(nodeBuilder);
            final String rootFullname = builder.toString();
            final String rootName = nodeBuilder.toString();
            nodeBuilder.setLength(0);

            PathNode node = getPathNode(prevType, rootPath, rootName, rootFullname);
            if (node != null) {
                nodes.add(node);
            }
        }

        return new PathImpl(builder.toString(), nodes);
    }

    private PathNode getPathNode(NodeType type, String rootPath, String rootName, String rootFullName) {
        return type.node(Map.of(
                PathNodeProps.ROOT_PATH, rootPath,
                PathNodeProps.ROOT_NAME, rootName,
                PathNodeProps.ROOT_FULL_NAME, rootFullName
        ));
    }

}
