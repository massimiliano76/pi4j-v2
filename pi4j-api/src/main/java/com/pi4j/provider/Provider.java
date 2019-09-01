package com.pi4j.provider;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  Provider.java
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

import com.pi4j.common.Descriptor;
import com.pi4j.config.Config;
import com.pi4j.extension.Extension;
import com.pi4j.io.IO;
import com.pi4j.io.IOType;

/**
 * <p>Provider interface.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public interface Provider<PROVIDER_TYPE extends Provider, IO_TYPE extends IO, CONFIG_TYPE extends Config> extends Extension<PROVIDER_TYPE> {

    /**
     * <p>create.</p>
     *
     * @param config a CONFIG_TYPE object.
     * @return a IO_TYPE object.
     * @throws java.lang.Exception if any.
     */
    IO_TYPE create(CONFIG_TYPE config) throws Exception;

    /**
     * <p>type.</p>
     *
     * @return a {@link com.pi4j.io.IOType} object.
     */
    default IOType type() { return IOType.getByProviderClass(this.getClass()); };
    /**
     * <p>getType.</p>
     *
     * @return a {@link com.pi4j.io.IOType} object.
     */
    default IOType getType() { return type(); }
    /**
     * <p>isType.</p>
     *
     * @param type a {@link com.pi4j.io.IOType} object.
     * @return a boolean.
     */
    default boolean isType(IOType type) { return this.type().isType(type); }

    /** {@inheritDoc} */
    @Override
    default Descriptor describe() {
        Descriptor descriptor = Extension.super.describe();
        //descriptor.category(this.type().name());
        descriptor.category("PROVIDER");
        return descriptor;
    }
}
