package com.company;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Random;
import java.util.Scanner;

public class Main {

    static ByteBuffer buff;

    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("../../../covfefe_00403000.bin");
        byte[] byteBuff = new byte[0x5000];
        fis.read(byteBuff);
        buff = ByteBuffer.wrap(byteBuff);
        buff.order(ByteOrder.LITTLE_ENDIAN);
        buff.putInt(0x444 + 8, new Random().nextInt() % 8);
        MutateNTestPass(8, 0x1100, 0x463);
    }

    static int MutateNTestPass (int dataMain, int numMax, int data){
        int first, second, third, dataA, dataB;
        boolean testInfo;
        dataB = dataA = data;
        Scanner sc = null;
        while (data  + 3 <= numMax) {
            first = buff.getInt(dataMain + data * 4);
            second = buff.getInt(dataMain + data * 4 + 4);
            third = buff.getInt(dataMain + data * 4 + 8);
            testInfo = StoreNTest(dataMain, first, second, third);
            if(!testInfo) {
                dataA = buff.getInt(dataB + 3);
            } else {
                dataA = buff.getInt(dataMain + dataA * 4 + 8);
                if (dataA == -1){
                    return buff.put(dataA, (byte)1).get(dataA);
                }
                dataB = dataA;
            }
            if(buff.getInt(dataMain + 0x10) == 1){
                System.out.print((char)buff.getInt(dataMain + 8));
                buff.putInt(dataMain + 0x10, 0);
                buff.putInt(dataMain + 8, 0);
            }
            if(buff.getInt(dataMain + 12) == 1) {
                if (sc == null) {
                    sc = new Scanner(System.in);
                }
                buff.putInt(dataA + 4, (int)sc.nextByte());
                dataA = dataMain;
                buff.putInt(dataA + 12, 0);
            }
        }
        return buff.put(dataA, (byte)1).get(dataA);
    }

    static boolean StoreNTest(int data, int first, int second, int third){
        byte retVal;
        int storeVal = buff.getInt(data + second * 4) - buff.getInt(data + first * 4);
        buff.putInt(data + second * 4, storeVal);
        if(third != 0){
            if (buff.getInt(data + second * 4) > 0) {
                return false;
            }
        }
        return true;
    }
}