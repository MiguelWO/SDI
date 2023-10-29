#pragma once
["java:package:sdi"]
module observerIce {
    interface Observer {
        string eventCallback(string arg);
    }
    interface Trigger {
        string addListener(Observer *obs);
        bool removeListener(string observerId);
    }
}
