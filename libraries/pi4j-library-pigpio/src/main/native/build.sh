#!/bin/bash -e
###
# #%L
# **********************************************************************
# ORGANIZATION  :  Pi4J
# PROJECT       :  Pi4J :: JNI Native Library
# FILENAME      :  build.sh
#
# This file is part of the Pi4J project. More information about
# this project can be found here:  https://pi4j.com/
# **********************************************************************
# %%
# Copyright (C) 2012 - 2019 Pi4J
# %%
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
# #L%
###

# set executable permissions on build scripts
chmod +x build-prerequisites.sh
chmod +x build-pigpio.sh
chmod +x build-pi4j.sh

# ------------------------------------------------------
# INSTALL BUILD PREREQUISITES
# ------------------------------------------------------
ARCHITECTURE=$(uname -m)
echo "PLATFORM ARCH: $ARCHITECTURE"
if [[ ( "$ARCHITECTURE" = "armv7l") || ("$ARCHITECTURE" = "armv6l") ]]; then
   echo
   echo "**********************************************************************"
   echo "*                                                                    *"
   echo "*                 INSTALLING Pi4J BUILD PREREQUISITES                *"
   echo "*                                                                    *"
   echo "**********************************************************************"
   echo
   # download and install development prerequisites
   ./build-prerequisites.sh
fi

# ------------------------------------------------------
# JAVA_HOME ENVIRONMENT VARIABLE
# ------------------------------------------------------
echo
echo "**********************************************************************"
echo "*                                                                    *"
echo "*           CHECKING JAVA_HOME ENVIRONMENT VARIABLE                  *"
echo "*                                                                    *"
echo "**********************************************************************"
echo
if [[ -n "$JAVA_HOME" ]]; then
   echo "'JAVA_HOME' already defined as: $JAVA_HOME";
else
   export JAVA_HOME=$(readlink -f /usr/bin/javac | sed "s:bin/javac::")
   echo "'JAVA_HOME' was not defined; attempting to use: $JAVA_HOME";
fi

# ------------------------------------------------------
# PIGPIO
# ------------------------------------------------------
echo
echo "**********************************************************************"
echo "*                                                                    *"
echo "*             BUILDING PIGPIO LIBRARY (DEPENDENCY)                   *"
echo "*                                                                    *"
echo "**********************************************************************"
echo
if [[ -d "pigpio" ]] ; then
    echo "The 'pigpio' library already exists; if you wish to rebuild, run a CLEAN build."
else
    ./build-pigpio.sh $@
fi


# ------------------------------------------------------
# Pi4J JNI Native Library
# ------------------------------------------------------
echo
echo "**********************************************************************"
echo "*                                                                    *"
echo "*                BUILDING Pi4J JNI -> PIGPIO LIBRARY                 *"
echo "*                                                                    *"
echo "**********************************************************************"
echo
./build-pi4j.sh $@

echo
echo "**********************************************************************"
echo "*                                                                    *"
echo "*                       Pi4J JNI BUILD COMPLETE                      *"
echo "*                                                                    *"
echo "**********************************************************************"
echo
ls -la lib*
