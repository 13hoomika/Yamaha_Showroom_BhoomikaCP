package com.bcp.yamaha.util;

import org.apache.commons.io.FilenameUtils;

public class MimeTypeUtil {

    public static String getContentTypeByExtension(String filename) {
        if (filename == null) return "application/octet-stream";
        String extension = FilenameUtils.getExtension(filename);

        if ("jpg".equalsIgnoreCase(extension) || "jpeg".equalsIgnoreCase(extension)) {
            return "image/jpeg";
        } else if ("png".equalsIgnoreCase(extension)) {
            return "image/png";
        } else if ("gif".equalsIgnoreCase(extension)) {
            return "image/gif";
        } else if ("webp".equalsIgnoreCase(extension)) {
            return "image/webp";
        } else if ("avif".equalsIgnoreCase(extension)) {
            return "image/avif";
        }

        return "application/octet-stream"; // Default fallback
    }
}

