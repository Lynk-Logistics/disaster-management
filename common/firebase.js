var admin = require('firebase-admin');

var serviceAccount = require("@config/serviceAccountKey.json");

admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    databaseURL: require("@config/firebaseurl.js")
});


module.exports.pushNotification = (data, token) => {
    var message = {
        message: data,
        token: token
    }
    admin.messaging().send(message).then(response => {
        console.log(response)
    }).catch(error => { console.log(error) })
}


