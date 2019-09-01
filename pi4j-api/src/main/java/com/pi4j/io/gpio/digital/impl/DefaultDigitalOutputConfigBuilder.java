package com.pi4j.io.gpio.digital.impl;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  DefaultDigitalOutputConfigBuilder.java
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

import com.pi4j.config.impl.AddressConfigBuilderBase;
import com.pi4j.io.gpio.digital.DigitalOutputConfig;
import com.pi4j.io.gpio.digital.DigitalOutputConfigBuilder;
import com.pi4j.io.gpio.digital.DigitalState;

/**
 * <p>DefaultDigitalOutputConfigBuilder class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class DefaultDigitalOutputConfigBuilder
        extends AddressConfigBuilderBase<DigitalOutputConfigBuilder, DigitalOutputConfig>
        implements DigitalOutputConfigBuilder {

    /**
     * PRIVATE CONSTRUCTOR
     */
    protected DefaultDigitalOutputConfigBuilder(){
        super();
    }

    /**
     * <p>newInstance.</p>
     *
     * @return a {@link com.pi4j.io.gpio.digital.DigitalOutputConfigBuilder} object.
     */
    public static DigitalOutputConfigBuilder newInstance() {
        return new DefaultDigitalOutputConfigBuilder();
    }

    /** {@inheritDoc} */
    @Override
    public DigitalOutputConfigBuilder shutdown(DigitalState state) {
        this.properties.put(DigitalOutputConfig.SHUTDOWN_STATE_KEY, state.toString());
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public DigitalOutputConfigBuilder initial(DigitalState state) {
        this.properties.put(DigitalOutputConfig.INITIAL_STATE_KEY, state.toString());
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public DigitalOutputConfig build() {
        DigitalOutputConfig config = new DefaultDigitalOutputConfig(properties);
        return config;
    }
}
