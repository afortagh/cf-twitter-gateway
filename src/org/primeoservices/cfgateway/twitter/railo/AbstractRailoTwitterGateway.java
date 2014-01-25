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
package org.primeoservices.cfgateway.twitter.railo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.primeoservices.cfgateway.twitter.TwitterGatewayConstants;
import org.primeoservices.cfgateway.twitter.utils.TwitterUtils;

import railo.runtime.gateway.Gateway;
import railo.runtime.gateway.GatewayEngine;
import railo.runtime.type.Struct;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.Configuration;

/**
 * @author Jean-Bernard van Zuylen
 */
public abstract class AbstractRailoTwitterGateway implements TwitterGatewayConstants, Gateway
{
  private static final String GATEWAY_ID_KEY = "GATEWAYID";

  private static final String GATEWAY_TYPE_KEY = "GATEWAYTYPE";

  //private static final String ORIGINATOR_ID_KEY = "ORIGINATORID";

  private static final String DATA_KEY = "DATA";
  
  private String id;

  private int state = Gateway.STOPPED;

  private GatewayEngine engine;

  private Configuration config;

  private TwitterStream stream;

  protected void init(final GatewayEngine engine, final String id, final Configuration config)
  {
    this.id = id;
    this.engine = engine;
    this.config = config;
  }
  
  /**
   * Returns the id of this gateway
   * 
   * @return the id of the gateway
   */
  @Override
  public String getId()
  {
    return this.id;
  }

  /**
   * Returns the state of this gateway
   * 
   * @return the state of the gateway
   */
  @Override
  public int getState()
  {
    return this.state;
  }

  @Override
  public Object getHelper()
  {
    return null;
  }

  /**
   * Starts this gateway
   */
  @Override
  public void doStart() throws IOException
  {
    this.state = STARTING;
    try
    {
      this.stream = new TwitterStreamFactory(this.config).getInstance();
      this.initStream(this.stream);
      this.state = RUNNING;
    }
    catch (Throwable t)
    {
      this.state = FAILED;
      final IOException ex = new IOException("Unable to start gateway", t);
      this.onException(ex);
      throw ex;
    }
  }

  /**
   * Initialize the specified stream
   * 
   * @param stream the stream to be initialized
   */
  protected abstract void initStream(final TwitterStream stream);

  /**
   * Stops this gateway
   */
  @Override
  public void doStop() throws IOException
  {
    this.state = STOPPING;
    try
    {
      TwitterUtils.closeQuietly(this.stream);
      this.stream = null;
      this.state = STOPPED;
    }
    catch (Throwable t)
    {
      this.state = FAILED;
      final IOException ex = new IOException("Unable to stop gateway", t);
      this.onException(ex);
      throw ex;
    }
  }
  
  /**
   * Restarts this gateway
   */
  @Override
  public void doRestart() throws IOException
  {
    this.doStop();
    this.doStart();
  }

  /**
   * Handles the specified exception
   * 
   * @param ex the exception to be handled
   */
  public void onException(final Exception ex) 
  {
    this.engine.log(this, GatewayEngine.LOGLEVEL_ERROR, RailoUtils.createLogMessage(ex));
  }

  /**
   * Invokes the listener of this gateway
   * 
   * @param method the name of the method to be invoked on the listener
   * @param data the date to be sent to the listener
   */
  @SuppressWarnings("unchecked")
  protected void invokeListener(final String method, final Struct data)
  {
    final Struct event = RailoUtils.createStruct();
    event.put(GATEWAY_ID_KEY, this.getId());
    event.put(GATEWAY_TYPE_KEY, GATEWAY_TYPE);
    event.put(DATA_KEY, data);
    final Map<String, Struct> arguments = new HashMap<String, Struct>(1);
    arguments.put("event", event);
    this.engine.invokeListener(this, method, arguments);
  }
}
