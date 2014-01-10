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
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import railo.runtime.gateway.Gateway;
import railo.runtime.gateway.GatewayEngine;
import twitter4j.Status;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.UserStreamAdapter;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

/**
 * @author Jean-Bernard van Zuylen
 */
public class RailoTwitterUserStreamGateway extends UserStreamAdapter implements Gateway
{
	private String id;

	private int state = Gateway.STOPPED;
	
	private GatewayEngine engine;
	
	private Configuration config;
	
	private TwitterStream stream;

	@Override
	@SuppressWarnings("rawtypes")
	public void init(final GatewayEngine engine, final String id, final String cfcPath, final Map config) throws IOException
	{
		this.id = id;
		this.engine = engine;
		final ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setOAuthConsumerKey((String) config.get("oAuthConsumerKey"));
		cb.setOAuthConsumerSecret((String) config.get("oAuthConsumerSecret"));
		cb.setOAuthAccessToken((String) config.get("oAuthAccessToken"));
		cb.setOAuthAccessTokenSecret((String) config.get("oAuthAccessTokenSecret"));
		this.config = cb.build(); 
	}

	@Override
	public String getId()
	{
		return this.id;
	}

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

	@Override
	public void doStart() throws IOException
	{
		this.state = STARTING;
		try
		{
			this.stream = new TwitterStreamFactory(this.config).getInstance();
			this.stream.addListener(this);
			this.stream.user();
			this.state = RUNNING;
		}
		catch (Throwable t)
		{
			this.state = FAILED;
			final StringWriter sw = new StringWriter();
			final PrintWriter pw = new PrintWriter(sw);
			pw.println("Unable to start gateway");
			t.printStackTrace(pw);
			pw.close();
			this.engine.log(this, GatewayEngine.LOGLEVEL_ERROR, sw.toString());
			throw new IOException("Unable to start gateway", t);
		}
	}

	@Override
	public void doStop() throws IOException
	{
		this.state = STOPPING;
		try
		{
			this.stream.cleanUp();
			this.stream.shutdown();
			this.stream = null;
			this.state = STOPPED;
		}
		catch (Throwable t)
		{
			this.state = FAILED;
			this.engine.log(this, GatewayEngine.LOGLEVEL_ERROR, "Unable to stop gateway");
			throw new IOException("Unable to stop gateway", t);
		}
	}
	
	@Override
	public void doRestart() throws IOException
	{
		this.doStop();
		this.doStart();
	}

	@Override
	@SuppressWarnings("rawtypes")
	public String sendMessage(final Map arg0) throws IOException
	{
		throw new UnsupportedOperationException("sendMessage");
	}

	@Override
	public void onStatus(final Status status)
	{
		this.engine.log(this, GatewayEngine.LOGLEVEL_ERROR, "On status: " + status.getText());
		/*
		final Map event = new HashMap();
		this.engine.invokeListener(this, "onStatus", event);
		*/
	}
}
