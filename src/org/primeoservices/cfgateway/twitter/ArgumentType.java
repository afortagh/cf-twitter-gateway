/*
 * Copyright 2014 Jean-Bernard van Zuylen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.primeoservices.cfgateway.twitter;

/**
 * @author Jean-Bernard van Zuylen
 */
public enum ArgumentType
{
  JSON("JSON"),
  JAVA("Java objects");

  private String configValue;
  
  private ArgumentType(final String configValue)
  {
    this.configValue = configValue;
  }
  
  /**
   * Returns the <code>ArgumentType</code> for the specified value
   * 
   * @param configValue the value fr
   * 
   * @return the <code>ArgumentType</code> for the given value
   */
  public static ArgumentType fromConfigValue(final String configValue)
  {
    if (configValue == null || configValue.length() == 0) return null;
    for (ArgumentType value : values())
    {
      if (value.configValue.equalsIgnoreCase(configValue))
      {
        return value;
      }
    }
    throw new IllegalArgumentException("Unknown argument type: " + configValue);
  }

  /**
   * Returns the default type for the argument to be passed to
   * the listener
   * 
   * @return
   */
  public static ArgumentType getDefault()
  {
    return JSON;
  }
}
