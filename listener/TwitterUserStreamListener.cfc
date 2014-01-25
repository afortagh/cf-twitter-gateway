<cfcomponent displayname="TwitterUserStreamListener" output="false">
  <!---
    Called when a new status (tweet) was published
  --->
  <cffunction name="onStatus" access="public" returntype="void" output="false">
    <cfargument name="event" type="struct" required="true" />

    <!---
      The data structure (arguments.event.data) contains the following elements:
        - format: the format in which the status (tweet) was transferred to the listener
                  (possible values are "JSON" and "JAVA")
        - status: the status (tweet) that was published. Check the following resources to
                  get more details about the content of this element:
                  If JSON -> https://dev.twitter.com/docs/platform-objects/tweets
                  If JAVA -> http://twitter4j.org/javadoc/twitter4j/Status.html
    --->

    <!--- Log the call to this method. To be replaced by your own implementation --->
    <cflog file="TwitterUserStream" type="information" text="Method onStatus() called" />
  </cffunction>

  <!---
    Called when a new direct message was sent
  --->
  <cffunction name="onDirectMessage" access="public" returntype="void" output="false">
    <cfargument name="event" type="struct" required="true" />

    <!---
      The data structure (arguments.event.data) contains the following elements:
        - format: the format in which the direct message was transferred to the listener
                  (possible values are "JSON" and "JAVA")
        - directMessage: the direct message that was sent. Check the following resources to
                         get more details about the content of this element:
                         If JSON: https://dev.twitter.com/docs/platform-objects/tweets
                         If JAVA: http://twitter4j.org/javadoc/twitter4j/DirectMessage.html
    --->

    <!--- Log the call to this method. To be replaced by your own implementation --->
    <cflog file="TwitterUserStream" type="information" text="Method onDirectMessage() called" />
  </cffunction>

  <!---
    Called when a status (tweet) or a direct message is deleted
  --->
  <cffunction name="onDeletionNotice" access="public" returntype="void" output="false">
    <cfargument name="event" type="struct" required="true" />

    <!---
      The data structure (arguments.event.data) contains the following elements:
        - statusId: in case of the deletion of a status (tweet), the id of the status
                    (tweet) that was deleted
        - directMessageId: in case of the deletion of a direct message, the is of the
                           direct message that was deleted
        - userId: the id of the user who deleted the status (tweet) or the direct
                  message
    --->

    <!--- Log the call to this method. To be replaced by your own implementation --->
    <cflog file="TwitterUserStream" type="information" text="Method onDeletionNotice() called" />
  </cffunction>
</cfcomponent>