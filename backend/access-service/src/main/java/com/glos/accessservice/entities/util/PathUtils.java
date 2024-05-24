package com.glos.accessservice.entities.util;


import com.glos.accessservice.entities.File;
import com.glos.accessservice.entities.Repository;

import java.nio.file.Path;

public final class PathUtils {

    public static String normalizeForUrl(String path) {
        if(path == null)
            return path;
        return path.replaceAll("[/\\\\]", "+");
    }

    public static String originalPath(String path) {
        if (path == null) {
            return path;
        }
        return path.replaceAll("\\+", "/");
    }

    public static String[] splitPathFilename(String fullName) {
        if (fullName == null) {
            return null;
        }
        Path path = Path.of(fullName);
        Path parent = path.getParent();
        String pathFile = (parent != null) ? parent.toString() : "";
        String filename = path.toFile().getName();
        pathFile = pathFile.replaceAll("[\\\\]", "/");
        return new String[] {pathFile, filename};
    }
    
    public static String[] splitNormalizedFilename(String filename) {
        if (filename == null) {
            return null;
        }
        return filename.split("\\+");
    }

    public static void normalizePathsFile(File file) {
        file.setRootPath(normalizeForUrl(file.getRootPath()));
        file.setRootFullName(normalizeForUrl(file.getRootFullName()));
        file.setDisplayPath(normalizeForUrl(file.getDisplayPath()));
        file.setDisplayFullName(normalizeForUrl(file.getDisplayFullName()));
    }

    public static void normalizePathsRepository(Repository repository) {
        repository.setRootPath(normalizeForUrl(repository.getRootPath()));
        repository.setRootFullName(normalizeForUrl(repository.getRootFullName()));
        repository.setDisplayPath(normalizeForUrl(repository.getDisplayPath()));
        repository.setDisplayFullName(normalizeForUrl(repository.getDisplayFullName()));
    }

    public static void ordinalPathsFile(File file) {
        file.setRootPath(originalPath(file.getRootPath()));

        file.setRootFullName(originalPath(file.getRootFullName()));

        file.setDisplayPath(originalPath(file.getDisplayPath()));

        file.setDisplayFullName(originalPath(file.getDisplayFullName()));
    }

    public static void ordinalPathsRepository(Repository repository) {
        repository.setRootPath(originalPath(repository.getRootPath()));
        repository.setRootFullName(originalPath(repository.getRootFullName()));
        repository.setDisplayPath(originalPath(repository.getDisplayPath()));
        repository.setDisplayFullName(originalPath(repository.getDisplayFullName()));
    }
}
