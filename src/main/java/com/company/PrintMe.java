package com.company;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@SuppressWarnings("FieldCanBeLocal")
public class PrintMe {

    private static ByteBuffer buff;

    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream(PrintMe.class.getResource("/covfefe_00403000.bin").getPath());
        byte[] byteBuff = new byte[0x5000];
        fis.read(byteBuff);
        buff = ByteBuffer.wrap(byteBuff);
        buff.order(ByteOrder.LITTLE_ENDIAN);
        for (int i = 8; i < 0x5000 - 0x800; i += 4) {
            int val = buff.getInt(i);
            if (val == (val & 0x7f)) {
                System.out.print((char) val);
            } else {
                while ((val * 4 + 8) < 0x5000 - 0x800 && val > 0) {
                    val = buff.getInt(val * 4 + 8);
                    if (val == (val & 0x7f)) {
                        System.out.print((char) val);
                        break;
                    }
                    //System.out.printf("%08X\n", buff.getInt(val * 4 + 8));
                }
            }
        }
    }
}
