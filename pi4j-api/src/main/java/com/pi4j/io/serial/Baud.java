package com.pi4j.io.serial;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  Baud.java
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

/**
 * <p>Baud class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public enum Baud {

    _50(50),
    _75(75),
    _110(110),
    _134(134),
    _150(150),
    _200(200),
    _300(300),
    _600(600),
    _1200(1200),
    _1800(1800),
    _2400(2400),
    _4800(4800),
    _9600(9600),
    _19200(19200),
    _38400(38400),
    _57600(57600),
    _115200(115200),
    _230400(230400);

    private int baud = 0;

    private Baud(int baud){
        this.baud = baud;
    }

    /**
     * <p>getValue.</p>
     *
     * @return a int.
     */
    public int getValue(){
        return this.baud;
    }
    /**
     * <p>value.</p>
     *
     * @return a int.
     */
    public int value(){
        return this.baud;
    }

    /**
     * <p>getInstance.</p>
     *
     * @param baud_rate a int.
     * @return a {@link com.pi4j.io.serial.Baud} object.
     */
    public static Baud getInstance(int baud_rate){
        for(Baud b : Baud.values()){
            if(b.getValue() == baud_rate){
                return b;
            }
        }
        return null;
    }
}
