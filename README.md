# disaster-management
Lynk Hackathon 2019 - Disaster Management

The technology we are using are:
For Backend, we are using Django-Framework of Python and for Frentend, we are using React.js- Framework of Javascript
We are using Free2SMS Api for sending messages to the users

The components we are using are:
1. Victims - They are the ones who requests for help from the volunteers and suppliers. They can sign in using their phone number and can request for goods such as Food, Clothes, Everyday Accessories, Medicines. They can ask for help if there is a medical emergency or in severe situtions they can ask the volunteers. 

2. Suppliers - These are the users who wants to donate or provide services to the volunteers for helping the victims. They have a list of choices from which they want to donate for the victims. From food, clothes, everydayAssessories to medicines, they donate everything to the volunteers or NGOs who distibute them among the victims.

3. Volunteers - These are the users who are ready to go and help out the victims by supplying them with the goods, medicines and in the case of medical emergencies, first aid service, the doctor who are ready to provide their services to the affected people are assigned to that victim. 

Working: We fetch the location of the victims, suppliers and volunteers. Then we use the location of the volunteer to assign them to the victiims near then based on victim's location and his/her requirement. If the victims wants food, then a volunteer near him based on his location will search for supplier near to him who wants to donate food and will provide victims with the same.Once a volunteer is busy with a victim, his status is set as busy till he/she completes the transaction. We created an interface where a victim can actually look into the location or the whereabouts of the volunteer, when he/she will be reaching so that they don't miss it. 

Victims can get the nearby location of the volunteers and suppliers and their number so thatthey can personally contact them. For finding the nearby location, we are writting a set of code which increases the latitude and longitude by 500 m(converting it into degrees), so that we don't miss any victims even if there is no volunteer available to the location near him/her.
Suppliers can get the nearby location of the victims and the volunteers and similarly the volunteers can get the nearby location of victims and volunteers so that they can get the products from the nearest supplier to the victim. For notifying the location and the contact number of the nearby respective people, we were using Fast2SMS API for sending the messages to the end user, for ex: A victim would get the message containing the location and the contact number of the nearest volunteer and supplier.We were trying to cover each and every scenario in which a victim can call for help in every possible way.  
