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
import java.util.Map;

import org.primeoservices.cfgateway.twitter.ArgumentType;

import railo.runtime.gateway.GatewayEngine;
import railo.runtime.type.Struct;
import twitter4j.DirectMessage;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.TwitterStream;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.UserStreamListener;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.json.DataObjectFactory;

/**
 * @author Jean-Bernard van Zuylen
 */
public class RailoTwitterUserStreamGateway extends AbstractRailoTwitterGateway implements UserStreamListener
{
  public static final String ALL_REPLIES = "enableAllReplies";
  
  private ArgumentType argType = ArgumentType.getDefault();
  
  @Override
  @SuppressWarnings("rawtypes")
  public void init(final GatewayEngine engine, final String id, final String cfcPath, final Map config) throws IOException
  {
    try
    {
      this.argType = ArgumentType.fromConfigValue((String) config.get(ARGUMENT_TYPE));
      final ConfigurationBuilder cb = new ConfigurationBuilder();
      cb.setOAuthConsumerKey((String) config.get(OAUTH_CONSUMER_KEY));
      cb.setOAuthConsumerSecret((String) config.get(OAUTH_CONSUMER_SECRET));
      cb.setOAuthAccessToken((String) config.get(OAUTH_ACCESS_TOKEN));
      cb.setOAuthAccessTokenSecret((String) config.get(OAUTH_ACCESS_SECRET));
      cb.setUserStreamRepliesAllEnabled(Boolean.valueOf((String) config.get(ALL_REPLIES)));
      cb.setJSONStoreEnabled("JSON".equals(config.get(ARGUMENT_TYPE)));
      super.init(engine, id, cb.build());
    }
    catch (Throwable t)
    {
      final IOException ex = new IOException("Unable to initialize gateway", t);
      this.onException(ex);
      throw ex; 
    }
  }

  @Override
  protected void initStream(final TwitterStream stream)
  {
    stream.addListener(this);
    stream.user();
  }

  @Override
  @SuppressWarnings("rawtypes")
  public String sendMessage(final Map arg0) throws IOException
  {
    throw new UnsupportedOperationException("sendMessage");
  }

  @Override
  @SuppressWarnings("unchecked")
  public void onDeletionNotice(final StatusDeletionNotice statusDeletionNotice)
  {
    final Struct data = RailoUtils.createStruct();
    data.put("statusId", statusDeletionNotice.getStatusId());
    data.put("userId", statusDeletionNotice.getUserId());
    this.invokeListener("onDeletionNotice", data);
  }

  @Override
  public void onScrubGeo(final long userId, final long upToStatusId)
  {
    this.onException(new UnsupportedOperationException("onScrubGeo"));
  }

  @Override
  public void onStallWarning(final StallWarning warning)
  {
    this.onException(new UnsupportedOperationException("onStallWarning"));
  }
  
  @Override
  @SuppressWarnings("unchecked")
  public void onStatus(final Status status)
  {
    final Struct data = RailoUtils.createStruct();
    data.put("format", this.argType.toString());
    if (ArgumentType.JSON.equals(this.argType))
    {
      data.put("status", DataObjectFactory.getRawJSON(status));
    }
    else
    {
      data.put("status", status);
    }
    this.invokeListener("onStatus", data);
  }

  @Override
  public void onTrackLimitationNotice(final int numberOfLimitedStatuses)
  {
    this.onException(new UnsupportedOperationException("onTrackLimitationNotice"));
  }

  @Override
  public void onBlock(final User source, final User blockedUser) 
  {
    this.onException(new UnsupportedOperationException("onBlock"));
  }

  @Override
  @SuppressWarnings("unchecked")
  public void onDeletionNotice(final long directMessageId, final long userId) 
  {
    final Struct data = RailoUtils.createStruct();
    data.put("directMessageId", directMessageId);
    data.put("userId", userId);
    this.invokeListener("onDeletionNotice", data);
  }

  @Override
  @SuppressWarnings("unchecked")
  public void onDirectMessage(final DirectMessage directMessage)
  {
    final Struct data = RailoUtils.createStruct();
    data.put("format", this.argType.toString());
    if (ArgumentType.JSON.equals(this.argType))
    {
      data.put("directMessage", DataObjectFactory.getRawJSON(directMessage));
    }
    else
    {
      data.put("directMessage", directMessage);
    }
    this.invokeListener("onDirectMessage", data);
  }

  @Override
  public void onFavorite(final User source, final User target, final Status favoritedStatus) 
  {
    this.onException(new UnsupportedOperationException("onFavorite"));
  }

  @Override
  public void onFollow(final User source, final User followedUser)
  {
    this.onException(new UnsupportedOperationException("onFollow"));
  }

  @Override
  public void onFriendList(final long[] friendIds) 
  {
    this.onException(new UnsupportedOperationException("onFriendList"));
  }

  @Override
  public void onUnblock(final User source, final User unblockedUser) 
  {
    this.onException(new UnsupportedOperationException("onUnblock"));
  }

  @Override
  public void onUnfavorite(final User source, final User target, final Status unfavoritedStatus) 
  {
    this.onException(new UnsupportedOperationException("onUnfavorite"));
  }

  @Override
  public void onUserListCreation(final User listOwner, final UserList list) 
  {
    this.onException(new UnsupportedOperationException("onUserListCreation"));
  }

  @Override
  public void onUserListDeletion(final User listOwner, final UserList list) 
  {
    this.onException(new UnsupportedOperationException("onUserListDeletion"));
  }

  @Override
  public void onUserListMemberAddition(final User addedMember, final User listOwner, final UserList list)
  {
    this.onException(new UnsupportedOperationException("onUserListMemberAddition"));
  }

  @Override
  public void onUserListMemberDeletion(final User deletedMember, final User listOwner, final UserList list) 
  {
    this.onException(new UnsupportedOperationException("onUserListMemberDeletion"));
  }

  @Override
  public void onUserListSubscription(final User subscriber, final User listOwner, final UserList list) 
  {
    this.onException(new UnsupportedOperationException("onUserListSubscription"));
  }

  @Override
  public void onUserListUnsubscription(final User subscriber, final User listOwner, final UserList list) 
  {
    this.onException(new UnsupportedOperationException("onUserListUnsubscription"));
  }

  @Override
  public void onUserListUpdate(final User listOwner, final UserList list) 
  {
    this.onException(new UnsupportedOperationException("onUserListUpdate"));
  }

  @Override
  public void onUserProfileUpdate(final User updatedUser) 
  {
    this.onException(new UnsupportedOperationException("onUserProfileUpdate"));
  }
}
