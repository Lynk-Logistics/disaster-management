# Catamaran 

Every year, the primary way of communication between the people who need help and the people on the ground is through whatsapp forward chains. It is everywhere, cheap, decentralized and very effective. But, the volunteeers are not very happy about this setup. There's no way to stop the circulation of these messages even the victim has received the much needed help. Some volunteers might get the message 3 days after the fact and will rush to the spot thinking that it's new only to realize that it is a dead issue.
This is a huge waste of resources, if you think about it.

Catamaran aims to solve this!


### Technologies used
- React
- Akka-http
- Scala
- Slick
- Postgres

It also uses `Twilio` to send messages through whatsapp.

### Architecture
```
+---------------------------------------------------------------------------+
|                                API                                        |
|   +--------------------------------------------------------------------+  |
|   |                           Service layer                            |  |
|   |   +-----------------------------------------------------------+    |  |
|   |   |                          DAO                              |    |  |
|   |   |    +-------------------------------------------------+    |    |  |
|   |   |    |                                                 |    |    |  |
|   |   |    |                                                 |    |    |  |
|   |   |    |                                                 |    |    |  |
|   |   |    |                                                 |    |    |  |
|   |   |    |                                                 |    |    |  |
|   |   |    |                   Domain Model                  |    |    |  |
|   |   |    |                                                 |    |    |  |
|   |   |    |                                                 |    |    |  |
|   |   |    |                                                 |    |    |  |
|   |   |    |                                                 |    |    |  |
|   |   |    |                                                 |    |    |  |
|   |   |    |                                                 |    |    |  |
|   |   |    |                                                 |    |    |  |
|   |   |    +-------------------------------------------------+    |    |  |
|   |   |                                                           |    |  |
|   |   |                                                           |    |  |
|   |   |                                                           |    |  |
|   |   +-----------------------------------------------------------+    |  |
|   |                                                                    |  |
|   |                                                                    |  |
|   +--------------------------------------------------------------------+  |
|                                                                           |
|                                                                           |
+---------------------------------------------------------------------------+
```
We use onion architecture in which the layers talk only to the layer immediately below them. 

## Domain models: 
- Tickets
- Volunteers



