package com.company;

import java.nio.ByteBuffer;
import java.util.Random;

public class Main {

    ByteBuffer buff = ByteBuffer.wrap(new byte[0x2000]);

    public static void main(String[] args) {
        int rand = new Random().nextInt() % 8;

    }
}
