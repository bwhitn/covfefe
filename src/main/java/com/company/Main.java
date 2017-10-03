package com.company;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Random;

public class Main {

    static ByteBuffer buff = ByteBuffer.wrap(new byte[0x2000]);

    public static void main(String[] args) {
        buff.order(ByteOrder.LITTLE_ENDIAN);
        buff.putInt(444 + 8, new Random().nextInt() % 8);
        MutateNTestPass(8, 0x1100, 0x463);
    }

    /*
    if (!al10) {
            v6 = (struct s0*)((int32_t)v6 + 3);
        } else {
            eax5 = *(struct s0**)((int32_t)a1 + (int32_t)v6 * 4 + 8);
            if ((int1_t)(eax5 == -1))
                goto addr_0x4010d1_6;
            v6 = eax5;
        }
        if (a1->f16 == 1) {
            v11 = a1->f8;
            fun_4011ea("%c", v11);
            a1->f16 = 0;
            a1->f8 = 0;
        }
        eax5 = (struct s0*)4;
        if (a1->f12 == 1) {
            fun_4011f0("%c", (int32_t)ebp4 - 1);
            a1->f4 = static_cast<int32_t>(v12);
            eax5 = a1;
            eax5->f12 = 0;
        }
     */
    static void MutateNTestPass (int dataMain, int numMax, int data){
        while (data  + 3 <= numMax) {
            int first = buff.get(dataMain + data * 4);
            int second = buff.get(dataMain + data * 4 + 4);
            int third = buff.get(dataMain + data * 4 + 8);
            boolean testInfo = StoreNTest(dataMain, first, second, third);
            if(!testInfo) {

            }
        }
    }

    static boolean StoreNTest(int data, int first, int second, int third){
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
