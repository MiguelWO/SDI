#pragma once

["java:package:sdi"]
module cifradorIce {
    interface Cifrador {
        string cifra(string input, string clave);
        string descifra(string input, string clave);
    }
}
