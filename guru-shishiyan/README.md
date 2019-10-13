# lynk-hack-2.0

**Assumption:**
  - Since various cases like mobile network, information integrity from the victims cannot be guarnteed. We are assuming there will be a NGO serving(like delivering foods) offline in a particular area, primarily a hub. 

**Description:**
  -  Distribution of resources (Foods, Bedsheets etc.,.) from donors to the hubs

**Users Involved:**
  - Anonymous user enters a place availablity which can be used as distribution centre in the application. (Ex :  Marriage Hall, Schools etc.,.) 
  - NGO can use the centre's to maintain the resources and distribute them wisely among the nearby areas.
  - Volunteer's can register themselves in the application to facilitate the logistics part of the resources from donors to the hubs.
  - Donors add their resource specification and man power required to transport it.
  
** Application Flow **
  - Anonymous users keeps on adding the place that is available with location information. Coordinates will extracted from the location information and saved in MongoDB.
  - NGO can use any of the available places to maintain their resources
  - Volunteers registers themselves with their location information.
  - Suppliers add the resources and the nearest hub as well as the nearest volunteer will be assigned to pick up and drop the resources.
  - Later on, after delivery the volunteers can update their location and make themselves available for the next session.

**Tech stack:**
  - Java, Spring boot, Mongodb, Kafka (Server Side)
  - jQuery (Client Side)

**Building the application**
  - Install Java, Maven, MongoDB, Kafka.
