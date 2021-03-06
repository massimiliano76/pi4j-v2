package com.pi4j.example;
/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: EXAMPLE  :: Sample Code
 * FILENAME      :  Main.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2019 Pi4J
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.pi4j.Pi4J;
import com.pi4j.io.serial.Baud;
import org.slf4j.event.Level;

/**
 * <p>Main class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class Main {

    private static String SERIAL_DEVICE = "/dev/ttyS0";
    private static int BAUD_RATE = Baud._38400.value();

    /**
     * <p>Constructor for Main.</p>
     */
    public Main() {
    }

    /**
     * <p>main.</p>
     *
     * @param args an array of {@link java.lang.String} objects.
     * @throws java.lang.Exception if any.
     */
    public static void main(String[] args) throws Exception {

        // configure default lolling level, accept a log level as the fist program argument
        String loglevel = "INFO";
        if(args != null && args.length > 0){
            Level lvl = Level.valueOf(args[0].toUpperCase());
            loglevel = lvl.name();
        }
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", loglevel);


        // TODO :: REMOVE TEMPORARY PROPERTIES WHEN NATIVE PIGPIO LIB IS READY
        // this temporary property is used to tell
        // PIGPIO which remote Raspberry Pi to connect to
        //System.setProperty("pi4j.host", "rpi3bp.savage.lan");
        //System.setProperty("pi4j.remote", "true");

        // Initialize Pi4J with an auto context
        // An auto context includes AUTO-DETECT BINDINGS enabled
        // which will load all detected Pi4J extension libraries
        // (Platforms and Providers) in the class path
        //var pi4j = Pi4J.newAutoContext();

        var pi4j = Pi4J.newAutoContext();


//        DigitalOutputConfigBuilder builder = DigitalOutput.newConfigBuilder();
//        builder.provider(PiGpioDigitalInputProvider.class);
//        builder.address(0);
//        var c = builder.build();
//
//        try {
//            Class c1 = Class.forName("com.pi4j.plugin.pigpio.provider.gpio.digital.PiGpioDigitalInputProvider");
//            //ClassLoader classLoader = c1.getClassLoader();
//            //Class c2 = Class.forName("java.lang.String", true, classLoader);
//            System.out.println("Class = " + c1.getName());
//            System.out.println("Provider = " + Provider.class.isAssignableFrom(c1));
//        } catch (ClassNotFoundException e){
//            System.out.println("No Class");
//        }


        System.out.println(pi4j.hasProvider("com.pi4j.plugin.pigpio.provider.gpio.digital.PiGpioDigitalInputProvider"));

        pi4j.provider("com.pi4j.plugin.pigpio.provider.gpio.digital.PiGpioDigitalInputProvider").describe().print(System.out);


//        System.out.println("--------------------------------------------------------");
//        pi4j.describe().print(System.out);
//        System.out.println("--------------------------------------------------------");

        // shutdown Pi4J
        pi4j.shutdown();
    }
}
