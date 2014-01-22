<cfcomponent displayname="TwitterUserStreamListener" extends="Gateway" output="false">
  <cfset fields = array(
    group("Connection", "Connection", 3),
    field("Consumer key", "oAuthConsumerKey", "", true, "", "text"),
    field("Consumer secret", "oAuthConsumerSecret", "", true, "", "password"),
    field("Access token", "oAuthAccessToken", "", true, "", "text"),
    field("Access token secret", "oAuthAccessTokenSecret", "", true, "", "password"),
    group("Stream settings", "Stream settings", 3),
    field("Enable all replies", "allReplies", "false", true, "Should we watch the directory and all subdirectories too", "checkbox"),
    group("Listener settings", "Listener settings", 3),
    field("Argument type", "argumentType", "JSON", "true", "Indicates whether the data is to be passed to the listener in JSON format or as Java objects", "radio", "JSON,Java objects")
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
    <cfreturn "Watch a certain twitter account for new statuses" />
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
