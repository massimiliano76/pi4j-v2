package com.pi4j.library.pigpio.test;
/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: JNI Wrapper for PIGPIO Library
 * FILENAME      :  TestSpiRaw.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2019 Pi4J
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 *
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import com.pi4j.library.pigpio.internal.PIGPIO;
import com.pi4j.library.pigpio.util.StringUtil;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Random;

/**
 * <p>Main class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class TestSpiRaw {
    /**
     * <p>main.</p>
     *
     * @param args an array of {@link String} objects.
     * @throws Exception if any.
     */
    public static void main(String[] args) throws Exception {
        System.out.println("PIGPIO VERSION   : " + PIGPIO.gpioVersion());
        System.out.println("PIGPIO HARDWARE  : " + PIGPIO.gpioHardwareRevision());

        int init = PIGPIO.gpioInitialise();
        System.out.println("PIGPIO INITIALIZE: " + init);
        if(init < 0){
            System.err.println("ERROR; PIGPIO INIT FAILED; ERROR CODE: " + init);
            System.exit(init);
        }

        int handle = PIGPIO.spiOpen(0, 50000, 0);
        System.out.println("PIGPIO SPI OPEN  : " + handle);
        if(handle < 0) {
            System.err.println("ERROR; SPI OPEN FAILED: ERROR CODE: " + handle);
            System.exit(handle);
        }

        // iterate over BYTE range of values, WRITE the byte then immediately
        // READ back the byte value and compare to make sure they are the same values.
        for(int b = 0; b < 256; b++) {
            System.out.print("[W/R BYTE]");
            // WRITE :: SINGLE RAW BYTE
            System.out.print(" (WRITE) 0x" + Integer.toHexString(b));
            int result = PIGPIO.spiWrite(handle, new byte[]{ (byte)b }, 1);
            if(result < 0) {
                System.err.println("\nERROR; SPI WRITE FAILED: ERROR CODE: " + result);
                System.exit(result);
            }

            // READ :: SINGLE RAW BYTE
            byte rx[] = new byte[1];

            result = PIGPIO.spiRead(handle, rx, 1);
            System.out.print(" (READ) 0x" + StringUtil.toHexString(rx));
            System.out.println();
            if(result < 0) {
                System.err.println("\nERROR; SPI READ FAILED: ERROR CODE: " + result);
                System.exit(result);
            }

            int expected = b;
            int received = Byte.toUnsignedInt(rx[0]);
            if(received != expected) {
                System.err.println("\nERROR; SPI READ FAILED: BYTE MISMATCH: expected=" + expected + "; received=" + received);
                System.exit(0);
            }
        }

        for(int b = 0; b < 256; b++) {
            System.out.print("[XFER BYTE]");
            // WRITE :: SINGLE RAW BYTE
            byte tx[] = new byte[] {(byte)(b)};
            byte rx[] = new byte[] {0};
            System.out.print(" (WRITE) 0x" + StringUtil.toHexString(tx));
            int result = PIGPIO.spiXfer(handle, tx, rx, 1);
            System.out.print(" (READ) 0x" + StringUtil.toHexString(rx));
            System.out.println();
            if(result < 0) {
                System.err.println("\nERROR; SPI XFER FAILED: ERROR CODE: " + result);
                System.exit(result);
            }
            if(b>0) { // ignore first read
                int expected = b-1;
                int received = Byte.toUnsignedInt(rx[0]);
                if (received != expected) {
                    System.err.println("\nERROR; SPI XFER FAILED: BYTE MISMATCH: expected=" + expected + "; received=" + received);
                    System.exit(0);
                }
            }
        }

        // iterate over series of test values, WRITE the byte then immediately
        // READ back the byte value and compare to make sure they are the same values.
        for(int x = 1; x < 50; x++) {
            System.out.print("[W/R BUFFER]");

            Random r = new Random();
            int len = r.nextInt((20)) + 2; // minimum of 2 bytes
            byte[] writeBuffer = new byte[len];
            r.nextBytes(writeBuffer);

            // WRITE :: MULTI-BYTE
            System.out.print(" (WRITE) 0x" + StringUtil.toHexString(writeBuffer));
            int result = PIGPIO.spiWrite(handle, writeBuffer, len);
            if(result < 0) {
                System.err.println("\nERROR; SPI WRITE FAILED: ERROR CODE: " + result);
                System.exit(result);
            }

            // take a breath to allow time for the serial data
            // to get updated in the serial receive buffer
            //Thread.sleep(50);

            // READ :: MULTI-BYTE
            byte[] readBuffer = new byte[len];
            result = PIGPIO.spiRead(handle, readBuffer, len);
            System.out.print(" (READ) 0x" + StringUtil.toHexString(readBuffer));
            System.out.println();
            if(result < 0) {
                System.err.println("\nERROR; SPI READ FAILED: ERROR CODE: " + result);
                System.exit(result);
            }

            // validate read length
            if(result != len) {
                System.err.println("\nERROR; SPI READ FAILED: LENGTH MISMATCH: " + result);
                System.exit(result);
            }

            // the test harness will return a byte array where the first byte value in the array
            // is the last byte written to the SPI device followed by bytes from the original write
            // buffer from left to right (excluding the last byte)
            // example : WRITE="ABCDEF" / READ="FABCDE"
            ByteBuffer expectBuffer = ByteBuffer.allocate(len);
            expectBuffer.put(writeBuffer[writeBuffer.length-1]);
            expectBuffer.put(Arrays.copyOfRange(writeBuffer, 0, writeBuffer.length-1));

            if(expectBuffer.get(0) != readBuffer[0]) {
                System.err.println("\nERROR; SPI READ FAILED: BYTE MISMATCH: expected=" +
                        StringUtil.toHexString(expectBuffer) + "; received=" +
                        StringUtil.toHexString(readBuffer));
                System.exit(0);
            }
        }

        // close SPI channel
        PIGPIO.spiClose(handle);

        PIGPIO.gpioTerminate();
        System.out.println("PIGPIO TERMINATED");
    }
}
