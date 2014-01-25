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
package org.primeoservices.cfgateway.twitter.utils;

import twitter4j.TwitterStream;

/**
 * @author Jean-Bernard van Zuylen
 */
public class TwitterUtils
{
  /**
   * This utilities class should never be initialized
   */
  private TwitterUtils()
  {
  }

  /**
   * Unconditionally close a <code>TwitterStream</code>
   * 
   * @param stream the stream to be closed
   */
  public static void closeQuietly(final TwitterStream stream)
  {
    if (stream == null) return;
    try
    {
      stream.cleanUp();
      stream.shutdown();
    }
    catch (Throwable t)
    {
      // ignore
    }
  }
}
