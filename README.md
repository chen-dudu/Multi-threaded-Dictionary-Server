# Multi-thread Dictionary Server

## System Description
The overall system has a client-server architecture.  

### Server
The server supports concurrent users to search meaning(s) of a word, 
add a new word, and remove an existing word.

A GUI is implemented to manage the server, e.g. launch/shut down server, set
the max numbers of concurrent users

### Client
The client uses the implemented GUI to perform all the operations that the server 
supports, as mentioned above.

## Execution
### To run the server
```
java -jar DictionaryServer.jar <port> <dictionary-file>
```
### To run the client
```
java -jar DictionaryClient.jar <server-address> <server-port>
```
Note: The jar files are produced using Java 8. To successfully run the program, please use a Java that is higher or equal to 8.