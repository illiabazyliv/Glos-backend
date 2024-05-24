package com.glos.accessservice.pathUtils;


import com.glos.accessservice.pathUtils.pathnode.PathNode;

import java.util.List;

/**
 * Represents a path consisting of nodes.
 * <pre>
 * Examples:
 *   "$user1"
 *   "$user1%file.txt"
 *   "$user1/dir%file.txt"
 *   "$user1/dir$repo%file.txt"
 *   "$user1$repo"
 *   "$user1/dir#archive.zip"
 *   "$user1/dir#archive.zip%file.txt"
 *   "$user1/dir#archive.zip/adir%file.txt"
 * </pre>
 *
 * @author Mykola Melnyk
 */
public interface Path {

     String getPath();
     List<PathNode> getNodes();
     PathNode getFirst();
     PathNode getLast();

}
