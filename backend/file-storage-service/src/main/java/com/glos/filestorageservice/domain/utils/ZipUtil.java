package com.glos.filestorageservice.domain.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil
{
    public static byte[] createZip(List<byte[]> filesData, List<String> fileNames) throws IOException
    {
        if (filesData.size() != fileNames.size())
        {
            throw new IllegalArgumentException("Files data and file names lists must have the same size.");
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream))
        {
            for (int i = 0; i < filesData.size(); i++)
            {
                zipOutputStream.putNextEntry(new ZipEntry(fileNames.get(i)));
                zipOutputStream.write(filesData.get(i));
                zipOutputStream.closeEntry();
            }
        }
        return byteArrayOutputStream.toByteArray();
    }
}
