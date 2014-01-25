<cfcomponent displayname="TwitterUserStreamListener" extends="Gateway" output="false">
  <cfset fields = array(
    group("Connection", "Connection", 3),
    field("Consumer key", "oAuthConsumerKey", "", true, "", "text"),
    field("Consumer secret", "oAuthConsumerSecret", "", true, "", "password"),
    field("Access token", "oAuthAccessToken", "", true, "", "text"),
    field("Access token secret", "oAuthAccessTokenSecret", "", true, "", "password"),

    group("Stream settings", "Stream settings", 3),
    field("Enable all replies", "enableAllReplies", "", false, "By default @replies are only sent from mutual followings. All @replies by followings can be enabled by checking this box. For example, if Alice follows Bob, but Alice doesn't follow Carol, by default if Bob @replies Carol, Alice does not see the tweet. If your application needs to get all @replies, enable this setting. See https://dev.twitter.com/docs/streaming-apis/streams/user##Replies", "checkbox", "true"),

    group("Listener settings", "Listener settings", 3),
    field("Argument type", "argumentType", "JSON", "true", "Indicates in which format the data is to be passed to the listener", "radio", "JSON,Java objects")
  ) />

  <cffunction name="getClass" access="public" returntype="string" output="false">
    <cfreturn "org.primeoservices.cfgateway.twitter.railo.RailoTwitterUserStreamGateway" />
  </cffunction>

  <cffunction name="getCFCPath" access="public" returntype="string" output="false">
    <cfreturn "" />
  </cffunction>

  <cffunction name="getLabel" access="public" returntype="string" output="false">
    <cfreturn "Twitter user stream" />
  </cffunction>

  <cffunction name="getDescription" access="public" returntype="string" output="false">
    <cfreturn "Creates a stream of data and events specific to a twitter account" />
  </cffunction>

  <cffunction name="onBeforeUpdate" access="public" returntype="void" output="false">
    <cfargument name="cfcPath" type="string" required="true" />
    <cfargument name="startupMode" type="string" required="true" />
    <cfargument name="custom" type="struct" required="true" />
  </cffunction>

  <cffunction name="getListenerCfcMode" access="public" returntype="string" output="false">
    <cfreturn "required" />
  </cffunction>

  <cffunction name="getListenerPath" access="public" returntype="string" output="false">
    <cfreturn "railo.extension.gateway.twitter.TwitterUserStreamListener" />
  </cffunction>
</cfcomponent>
