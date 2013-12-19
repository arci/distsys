
#Project for Distributed Systems course

##Abstract

Develop a library that implements a secure group communication service. 
This library should provide:

- the communication layer, using IP [multicast][multicast_oracle] and allowing members to join and leave the group,
- the encryption layer, to encrypt each message that is sent to the group, and decrypt it when it is received by each member

The library has to handle the group shared key ("data encryption key", as we called it during the lessons) and the problem of its distribution to the members whenever a "join" or "leave" operation is performed, to maintain __forward__ and __backward secrecy__ (each member can decrypt only the subset of messages that were sent during her participation to the group). 

The library should be agnostic to the content of the message (text, binary...), and handle the encryption operations in a transparent way with respect to its users.

Develop an application using the above library (e.g., a secure chat), to show its mechanisms and its functionality.

Both the library and the application have to be developed using __Java__; use the [java.security][java_security] and [javax.crypto][java_crypto] functionality to perform data encryption/decryption and for creating (a)symmetric keys.


[multicast_oracle]: http://docs.oracle.com/javase/6/docs/api/java/net/MulticastSocket.html
[java_security]: http://www.oracle.com/technetwork/java/javase/tech/index-jsp-136007.html
[java_crypto]: http://docs.oracle.com/javase/7/docs/api/javax/crypto/package-summary.html