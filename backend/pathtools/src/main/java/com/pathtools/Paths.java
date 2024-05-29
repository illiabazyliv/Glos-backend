package com.pathtools;

import java.util.ArrayList;

public class Paths {

    public static final Path EMPTY_PATH = new PathImpl("", new ArrayList<>());

    public static final boolean isRootName(String rootName) {
        for(NodeType type : NodeType.values())
            if (rootName.charAt(0) == type.nodeChar())
                return true;
        return false;
    }

}
