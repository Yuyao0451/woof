package com.woof.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class WriteBlob {

    public static InputStream getPictureStream(String path) throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(path);
        return fis;
    }

    public static byte[] getPictureBtyeArray(String path) throws IOException {
        FileInputStream fis = new FileInputStream(path);
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();
        return buffer;
    }
}
