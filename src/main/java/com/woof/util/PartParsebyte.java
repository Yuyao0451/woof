package com.woof.util;

import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PartParsebyte {
    public static byte[] partToByteArray(Part part) throws IOException {
//        InputStream inputStream = part.getInputStream();
//        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//        int nRead;
//        byte[] data = new byte[1024];
//        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
//            buffer.write(data, 0, nRead);
//        }
//        buffer.flush();
//        byte[] byteArray = buffer.toByteArray();
//        inputStream.close();
//        buffer.close();

        InputStream is = part.getInputStream();
        byte[] photo = new byte[is.available()];
        is.read(photo);
        is.close();


        return photo;
    }
}
