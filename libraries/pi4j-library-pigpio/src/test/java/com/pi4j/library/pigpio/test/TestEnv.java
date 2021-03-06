package com.pi4j.library.pigpio.test;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: JNI Wrapper for PIGPIO Library
 * FILENAME      :  TestEnv.java
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

import com.pi4j.library.pigpio.PiGpio;
import com.pi4j.test.harness.ArduinoTestHarness;

import java.io.IOException;

public class TestEnv {

    public static ArduinoTestHarness createTestHarness() throws IOException {
        return new ArduinoTestHarness(System.getProperty("pi4j.test.harness.port", "tty.usbserial-00000000"));
        //return new ArduinoTestHarness(System.getProperty("pi4j.test.harness.port", "tty.usbmodem142301"));
    }

    public static PiGpio createPiGpio() throws IOException {
        return PiGpio.newSocketInstance(System.getProperty("pi4j.pigpio.host", "rpizero-1"),
                                        System.getProperty("pi4j.pigpio.port", "8888"));
        //return PiGpio.newNativeInstance();
    }
}
