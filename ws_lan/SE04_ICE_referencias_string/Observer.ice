#pragma once
["java:package:sdi"]
module observerIce {
    interface Observer {
        string eventCallback(string arg);
    }
    interface Trigger {
        string addListener(string observerIOR);
        bool removeListener(string observerId);
    }
}
