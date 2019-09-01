package com.pi4j.io.i2c;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  I2CBase.java
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

import com.pi4j.context.Context;
import com.pi4j.exception.ShutdownException;
import com.pi4j.io.IOBase;
import com.pi4j.io.i2c.impl.DefaultI2CRegister;

import java.io.IOException;

/**
 * <p>Abstract I2CBase class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public abstract class I2CBase extends IOBase<I2C, I2CConfig, I2CProvider> implements I2C {

    protected boolean isOpen = false;

    /**
     * <p>Constructor for I2CBase.</p>
     *
     * @param provider a {@link com.pi4j.io.i2c.I2CProvider} object.
     * @param config a {@link com.pi4j.io.i2c.I2CConfig} object.
     */
    public I2CBase(I2CProvider provider, I2CConfig config) {
        super(provider, config);
        this.name = config.name();
        this.id = config.id();
        this.description = config.description();
        this.isOpen = true;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isOpen() {
        return this.isOpen;
    }

    /** {@inheritDoc} */
    @Override
    public void close() throws IOException {
        this.isOpen = false;
    }

    /**
     * {@inheritDoc}
     *
     * Get an encapsulated interface for reading and writing to a specific I2C device register
     */
    public I2CRegister getRegister(int address){
        return new DefaultI2CRegister(this, address);
    }

    /** {@inheritDoc} */
    @Override
    public I2C shutdown(Context context) throws ShutdownException {
        // if this I2C device is still open, then we need to close it since we are shutting down
        if(this.isOpen()) {
            try {
                this.close();
            } catch (Exception e) {
                throw new ShutdownException(e);
            }
        }
        return (I2C)this;
    }
}
