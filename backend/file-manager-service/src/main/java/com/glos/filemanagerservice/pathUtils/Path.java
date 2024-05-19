package com.glos.filemanagerservice.pathUtils;

import com.glos.filemanagerservice.pathUtils.pathnode.PathNode;

import java.util.List;

public interface Path {

     String getPath();
     List<PathNode> getNodes();
     PathNode getFirst();
     PathNode getLast();

}
