package com.pi4j.plugin.raspberrypi.provider.pwm;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: PLUGIN   :: RaspberryPi Platform & Providers
 * FILENAME      :  RpiPwmProvider.java
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

import com.pi4j.io.pwm.PwmProvider;
import com.pi4j.plugin.raspberrypi.RaspberryPi;

/**
 * <p>RpiPwmProvider interface.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public interface RpiPwmProvider extends PwmProvider {
    /** Constant <code>NAME="RaspberryPi.PWM_PROVIDER_NAME"</code> */
    String NAME = RaspberryPi.PWM_PROVIDER_NAME;
    /** Constant <code>ID="RaspberryPi.PWM_PROVIDER_ID"</code> */
    String ID = RaspberryPi.PWM_PROVIDER_ID;
    /**
     * <p>newInstance.</p>
     *
     * @return a {@link com.pi4j.plugin.raspberrypi.provider.pwm.RpiPwmProvider} object.
     */
    static RpiPwmProvider newInstance() {
        return new RpiPwmProviderImpl();
    }
}
