var mysql = require('mysql');

module.exports = {
    volunteerauth: function (data) {
        return new Promise(function (resolve, reject) {
            var connection = mysql.createPool({
                host: '139.59.94.48',
                user: 'playtowindbuser',
                password: 'Gameuser@123',
                database: 'LynkHack'
            });

            connection.getConnection(function (err) {
                var queryValue = "SELECT * FROM LynkHack.Victims as Vi JOIN Volunteer as Vo on Vi.PhoneNo = Vo.PhoneNo";
                if (err) {
                    resolve({ "status": 400, "error": err });
                    connection.end();
                    return;
                }
                if (data.phoneNo) {
                    queryValue += " where Vi.PhoneNo = " + data.phoneNo;
                } else {
                    resolve({ "status": 400, "error": "No phoneNo field found" });
                    connection.end();
                    return;

                }
                if (data.lowLimit && data.HighLimit) {
                    queryValue += " LIMIT " + data.lowLimit + "," + data.HighLimit;
                }

                connection.query(queryValue, function (err, result, fields) {
                    if (err) {
                        resolve({ "status": 400, "error": err });
                        connection.end();
                        return;
                    }
                    if (result.length && result.length > 0) {
                        resolve({ "status": 200, "data": result, "isNewUser": false, "isSuccess": true });
                    } else {
                        resolve({ "status": 200, "isNewUser": true, "isSuccess": true });
                    }


                    connection.end();
                    return;

                });
            });


        });
    },
    volunteerinsert: function (data) {
        return new Promise(function (resolve, reject) {
            var connection = mysql.createPool({
                host: '139.59.94.48',
                user: 'playtowindbuser',
                password: 'Gameuser@123',
                database: 'LynkHack'
            });
            connection.getConnection(function (err) {
                var queryValue = "INSERT INTO `LynkHack`.`Volunteer` ( `PhoneNo`, `Name`, `Address`, `Latitude`, `Longitude`, `AreaID`, `createdOn`, `updatedOn`, `ipAddress`, `isActive`) VALUES (";
                var queryValue2 = "INSERT INTO `LynkHack`.`Victims` ( `PhoneNo`, `Name`, `Address`, `Latitude`, `Longitude`, `AreaID`, `createdOn`, `updatedOn`, `ipAddress`, `isActive`) VALUES (";
                if (err) {
                    resolve({ "status": 400, "error": err });
                    connection.end();
                    return;
                }

                if (data.phoneNo) {
                    queryValue += "'" + data.phoneNo + "',";
                    queryValue2 += "'" + data.phoneNo + "',";
                } else {
                    resolve({ "status": 400, "error": "No phoneNo field found" });
                    connection.end();
                    return;

                }

                if (data.Name) {
                    queryValue += "'" + data.Name + "',";
                    queryValue2 += "'" + data.Name + "',";
                } else {
                    resolve({ "status": 400, "error": "No Name field found" });
                    connection.end();
                    return;

                }

                if (data.Address) {
                    queryValue += "'" + data.Address + "',";
                    queryValue2 += "'" + data.Address + "',";
                } else {
                    resolve({ "status": 400, "error": "No Address field found" });
                    connection.end();
                    return;

                }

                if (data.Latitude) {
                    queryValue += "'" + data.Latitude + "',";
                    queryValue2 += "'" + data.Latitude + "',";
                } else {
                    resolve({ "status": 400, "error": "No Latitude field found" });
                    connection.end();
                    return;

                }
                if (data.Longitude) {
                    queryValue += "'" + data.Longitude + "',";
                    queryValue2 += "'" + data.Longitude + "',";
                } else {
                    resolve({ "status": 400, "error": "No Longitude field found" });
                    connection.end();
                    return;

                }

                if (data.AreaID) {
                    queryValue += "'" + data.AreaID + "',";
                    queryValue2 += "'" + data.AreaID + "',";
                } else {
                    resolve({ "status": 400, "error": "No AreaID field found" });
                    connection.end();
                    return;

                }
                queryValue += "'2019-01-01 00:00:00','2019-01-01 00:00:00','1.1.1.1','1')";
                queryValue2 += "'2019-01-01 00:00:00','2019-01-01 00:00:00','1.1.1.1','1')";
                var queryValue3 = "SELECT * FROM LynkHack.Victims as Vi JOIN Volunteer as Vo on Vi.PhoneNo = Vo.PhoneNo";
                if (data.phoneNo) {
                    queryValue3 += " where Vi.PhoneNo =" + "'" + data.phoneNo + "';";;
                } else {
                    resolve({ "status": 400, "error": "No phoneNo field found" });
                    connection.end();
                    return;

                }

                connection.query(queryValue, function (err, result, fields) {
                    if (err) {
                        resolve({ "status": 400, "error": err });
                        connection.end();
                        return;
                    }
                    connection.query(queryValue2, function (err, result, fields) {
                        if (err) {
                            resolve({ "status": 400, "error": err });
                            connection.end();
                            return;
                        }
                        connection.query(queryValue3, function (err, result, fields) {
                            if (err) {
                                resolve({ "status": 400, "error": err });
                                connection.end();
                                return;
                            }
                            if (result) {
                                resolve({ "status": 200, "data": result, "isNewUser": false, "isSuccess": true });
                            }
                            connection.end();
                            return;

                        });

                    });

                });
            });


        });
    },
    ngoauth: function (data) {
        return new Promise(function (resolve, reject) {
            var connection = mysql.createPool({
                host: '139.59.94.48',
                user: 'playtowindbuser',
                password: 'Gameuser@123',
                database: 'LynkHack'
            });

            connection.getConnection(function (err) {
                var queryValue = "SELECT * FROM LynkHack.Organizations ";
                if (err) {
                    resolve({ "status": 400, "error": err });
                    connection.end();
                    return;
                }
                if (data.phoneNo) {
                    queryValue += "where PhoneNo = " + data.phoneNo;
                } else {
                    resolve({ "status": 400, "error": "No phoneNo field found" });
                    connection.end();
                    return;

                }
                if (data.lowLimit && data.HighLimit) {
                    queryValue += " LIMIT " + data.lowLimit + "," + data.HighLimit;
                }

                connection.query(queryValue, function (err, result, fields) {
                    if (err) {
                        resolve({ "status": 400, "error": err });
                        connection.end();
                        return;
                    }
                    if (result.length && result.length > 0) {
                        resolve({ "status": 200, "data": result, "isNewUser": false, "isSuccess": true });
                    } else {
                        resolve({ "status": 200, "isNewUser": true, "isSuccess": true });
                    }


                    connection.end();
                    return;

                });
            });


        });
    },
    ngoinsert: function (data) {
        return new Promise(function (resolve, reject) {
            var connection = mysql.createPool({
                host: '139.59.94.48',
                user: 'playtowindbuser',
                password: 'Gameuser@123',
                database: 'LynkHack'
            });
            connection.getConnection(function (err) {
                var queryValue = "INSERT INTO `LynkHack`.`Organizations` ( `PhoneNo`, `Name`,`GSTIn`,`Description`, `createdOn`, `updatedOn`, `isActive`) VALUES (";
                if (err) {
                    resolve({ "status": 400, "error": err });
                    connection.end();
                    return;
                }

                if (data.phoneNo) {
                    queryValue += "'" + data.phoneNo + "',";
                } else {
                    resolve({ "status": 400, "error": "No phoneNo field found" });
                    connection.end();
                    return;

                }

                if (data.Name) {
                    queryValue += "'" + data.Name + "',";
                } else {
                    resolve({ "status": 400, "error": "No Name field found" });
                    connection.end();
                    return;

                }

                if (data.GSTIn) {
                    queryValue += "'" + data.GSTIn + "',";
                } else {
                    resolve({ "status": 400, "error": "No GSTIn field found" });
                    connection.end();
                    return;

                }

                if (data.Description) {
                    queryValue += "'" + data.Description + "',";
                } else {
                    resolve({ "status": 400, "error": "No Description field found" });
                    connection.end();
                    return;

                }
            
                queryValue += "'2019-01-01 00:00:00','2019-01-01 00:00:00','1')";


                connection.query(queryValue, function (err, result, fields) {
                    if (err) {
                        resolve({ "status": 400, "error": err });
                        connection.end();
                        return;
                    }
                    resolve({ "status": 200, "data": result, "isSuccess": true });

                    connection.end();
                    return;

                });
            });


        });
    },
    getstates: function (data) {
        return new Promise(function (resolve, reject) {
            var connection = mysql.createPool({
                host: '139.59.94.48',
                user: 'playtowindbuser',
                password: 'Gameuser@123',
                database: 'LynkHack'
            });

            connection.getConnection(function (err) {
                var queryValue = "select * from States";
                if (err) {
                    resolve({ "status": 400, "error": err });
                    connection.end();
                    return;
                }
                connection.query(queryValue, function (err, result, fields) {
                    if (err) {
                        resolve({ "status": 400, "error": err });
                        connection.end();
                        return;
                    }
                    resolve({ "status": 200, "data": result, "isSuccess": true });

                    connection.end();
                    return;

                });
            });


        });
    },
    getcities: function (data) {
        return new Promise(function (resolve, reject) {
            var connection = mysql.createPool({
                host: '139.59.94.48',
                user: 'playtowindbuser',
                password: 'Gameuser@123',
                database: 'LynkHack'
            });

            connection.getConnection(function (err) {
                var queryValue = "select * from City";
                if (err) {
                    resolve({ "status": 400, "error": err });
                    connection.end();
                    return;
                }
                if (data.StateID) {
                    queryValue += " where StateID = " + data.StateID;
                } else {
                    resolve({ "status": 400, "error": "No StateID field found" });
                    connection.end();
                    return;

                }
                connection.query(queryValue, function (err, result, fields) {
                    if (err) {
                        resolve({ "status": 400, "error": err });
                        connection.end();
                        return;
                    }
                    resolve({ "status": 200, "data": result, "isSuccess": true });

                    connection.end();
                    return;

                });
            });


        });
    },
    getareas: function (data) {
        return new Promise(function (resolve, reject) {
            var connection = mysql.createPool({
                host: '139.59.94.48',
                user: 'playtowindbuser',
                password: 'Gameuser@123',
                database: 'LynkHack'
            });

            connection.getConnection(function (err) {
                var queryValue = "select * from Areas";
                if (err) {
                    resolve({ "status": 400, "error": err });
                    connection.end();
                    return;
                }
                if (data.CityID) {
                    queryValue += " where CityID = " + data.CityID;
                } else {
                    resolve({ "status": 400, "error": "No CityID field found" });
                    connection.end();
                    return;

                }
                connection.query(queryValue, function (err, result, fields) {
                    if (err) {
                        resolve({ "status": 400, "error": err });
                        connection.end();
                        return;
                    }
                    resolve({ "status": 200, "data": result, "isSuccess": true });

                    connection.end();
                    return;

                });
            });


        });
    },
    getvictimshelpmap: function (data) {
        return new Promise(function (resolve, reject) {
            var connection = mysql.createPool({
                host: '139.59.94.48',
                user: 'playtowindbuser',
                password: 'Gameuser@123',
                database: 'LynkHack'
            });

            connection.getConnection(function (err) {
                var queryValue = "select * from VictimsHelpMap";
                if (err) {
                    resolve({ "status": 400, "error": err });
                    connection.end();
                    return;
                }
                if (data.VictimID) {
                    queryValue += " where isCompleted=0 and VictimID = " + data.VictimID;
                } else {
                    resolve({ "status": 400, "error": "No CityID field found" });
                    connection.end();
                    return;

                }
                connection.query(queryValue, function (err, result, fields) {
                    if (err) {
                        resolve({ "status": 400, "error": err });
                        connection.end();
                        return;
                    }
                    resolve({ "status": 200, "data": result, "isSuccess": true });

                    connection.end();
                    return;

                });
            });


        });
    },
    getvolunteerhelpmap: function (data) {
        return new Promise(function (resolve, reject) {
            var connection = mysql.createPool({
                host: '139.59.94.48',
                user: 'playtowindbuser',
                password: 'Gameuser@123',
                database: 'LynkHack'
            });

            connection.getConnection(function (err) {
                var queryValue = "select * from VolunteerResourceMap";
                if (err) {
                    resolve({ "status": 400, "error": err });
                    connection.end();
                    return;
                }
                if (data.VolunteerID) {
                    queryValue += " where isCompleted=0 and VolunteerID = " + data.VolunteerID;
                } else {
                    resolve({ "status": 400, "error": "No VolunteerID field found" });
                    connection.end();
                    return;

                }
                connection.query(queryValue, function (err, result, fields) {
                    if (err) {
                        resolve({ "status": 400, "error": err });
                        connection.end();
                        return;
                    }
                    resolve({ "status": 200, "data": result, "isSuccess": true });

                    connection.end();
                    return;

                });
            });


        });
    },
    creategroup: function (data) {
        return new Promise(function (resolve, reject) {
            var connection = mysql.createPool({
                host: '139.59.94.48',
                user: 'playtowindbuser',
                password: 'Gameuser@123',
                database: 'LynkHack'
            });
            connection.getConnection(function (err) {
                var queryValue = "INSERT INTO `LynkHack`.`Groups` (`VolunteerID`, `AreaID`, `Name`, `Description`, `Members`, `link`, `createdOn`, `updatedOn`, `isCompleted`,`latitude`,`longitude`, `isActive`) VALUES (";
                if (err) {
                    resolve({ "status": 400, "error": err });
                    connection.end();
                    return;
                }

                if (data.VolunteerID) {
                    queryValue += "'" + data.VolunteerID + "',";
                } else {
                    resolve({ "status": 400, "error": "No VolunteerID field found" });
                    connection.end();
                    return;

                }

                if (data.AreaID) {
                    queryValue += "'" + data.AreaID + "',";
                } else {
                    resolve({ "status": 400, "error": "No AreaID field found" });
                    connection.end();
                    return;

                }

                if (data.Name) {
                    queryValue += "'" + data.Name + "',";
                } else {
                    resolve({ "status": 400, "error": "No Name field found" });
                    connection.end();
                    return;

                }

                if (data.Description) {
                    queryValue += "'" + data.Description + "',";
                } else {
                    resolve({ "status": 400, "error": "No Description field found" });
                    connection.end();
                    return;

                }
                if (data.Members) {
                    queryValue += "'" + data.Members + "',";
                } else {
                    resolve({ "status": 400, "error": "No Members field found" });
                    connection.end();
                    return;

                }

                if (data.link) {
                    queryValue += "'" + data.link + "',";
                } else {
                    resolve({ "status": 400, "error": "No AreaID field found" });
                    connection.end();
                    return;

                }

                queryValue += "'2019-01-01 00:00:00','2019-01-01 00:00:00'";
                if (data.latitude) {
                    queryValue += ",'" + data.latitude + "',";
                } else {
                    resolve({ "status": 400, "error": "No latitude field found" });
                    connection.end();
                    return;

                }
                if (data.longitude) {
                    queryValue += "'" + data.longitude + "',";
                } else {
                    resolve({ "status": 400, "error": "No longitude field found" });
                    connection.end();
                    return;

                }
                if (data.isCompleted) {
                    queryValue += "'" + data.isCompleted + "',";
                } else {
                    resolve({ "status": 400, "error": "No isCompleted field found" });
                    connection.end();
                    return;

                }
                queryValue += "'1')";


                connection.query(queryValue, function (err, result, fields) {
                    if (err) {
                        resolve({ "status": 400, "error": err });
                        connection.end();
                        return;
                    }
                    if (result) {
                        resolve({ "status": 200, "data": result, "isSuccess": true });
                    }
                    connection.end();
                    return;

                });
            });


        });
    },
    updatearea: function (data) {
        return new Promise(function (resolve, reject) {
            var connection = mysql.createPool({
                host: '139.59.94.48',
                user: 'playtowindbuser',
                password: 'Gameuser@123',
                database: 'LynkHack'
            });
            connection.getConnection(function (err) {
                var queryValue = "UPDATE `LynkHack`.`Volunteer` SET `AreaID` = ";

                if (err) {
                    resolve({ "status": 400, "error": err });
                    connection.end();
                    return;
                }
                if (data.AreaID) {
                    queryValue += "'" + data.AreaID + "'";
                } else {
                    resolve({ "status": 400, "error": "No AreaID field found" });
                    connection.end();
                    return;

                }
                queryValue += " WHERE `VolunteerID` =";

                if (data.VolunteerID) {
                    queryValue += "'" + data.VolunteerID + "';";
                } else {
                    resolve({ "status": 400, "error": "No VolunteerID field found" });
                    connection.end();
                    return;

                }

                connection.query(queryValue, function (err, result, fields) {
                    if (err) {
                        resolve({ "status": 400, "error": err });
                        connection.end();
                        return;
                    }
                    if (result) {
                        resolve({ "status": 200, "data": result, "isSuccess": true });
                    }
                    connection.end();
                    return;

                });
            });


        });
    },
    getgroupdetails: function (data) {
        return new Promise(function (resolve, reject) {
            var connection = mysql.createPool({
                host: '139.59.94.48',
                user: 'playtowindbuser',
                password: 'Gameuser@123',
                database: 'LynkHack'
            });
            connection.getConnection(function (err) {
                var queryValue = "Select * from Groups where AreaID= ";

                if (err) {
                    resolve({ "status": 400, "error": err });
                    connection.end();
                    return;
                }
                if (data.AreaID) {
                    queryValue += " '" + data.AreaID + "'";
                } else {
                    resolve({ "status": 400, "error": "No AreaID field found" });
                    connection.end();
                    return;

                }

                connection.query(queryValue, function (err, result, fields) {
                    if (err) {
                        resolve({ "status": 400, "error": err });
                        connection.end();
                        return;
                    }
                    if (result) {
                        resolve({ "status": 200, "data": result, "isSuccess": true });
                    }
                    connection.end();
                    return;

                });
            });


        });
    },
    getvictimdetails: function (data) {
        return new Promise(function (resolve, reject) {
            var connection = mysql.createPool({
                host: '139.59.94.48',
                user: 'playtowindbuser',
                password: 'Gameuser@123',
                database: 'LynkHack'
            });
            connection.getConnection(function (err) {
                var queryValue = "Select * from VictimsHelpMap where isCompleted=0 and AreaID= ";

                if (err) {
                    resolve({ "status": 400, "error": err });
                    connection.end();
                    return;
                }
                if (data.AreaID) {
                    queryValue += " '" + data.AreaID + "'";
                } else {
                    resolve({ "status": 400, "error": "No AreaID field found" });
                    connection.end();
                    return;

                }

                connection.query(queryValue, function (err, result, fields) {
                    if (err) {
                        resolve({ "status": 400, "error": err });
                        connection.end();
                        return;
                    }
                    if (result) {
                        resolve({ "status": 200, "data": result, "isSuccess": true });
                    }
                    connection.end();
                    return;

                });
            });


        });
    },
    getprofiledetails: function (data) {
        return new Promise(function (resolve, reject) {
            var connection = mysql.createPool({
                host: '139.59.94.48',
                user: 'playtowindbuser',
                password: 'Gameuser@123',
                database: 'LynkHack'
            });
            connection.getConnection(function (err) {
                var queryValue = "Select * from Volunteer where VolunteerID= ";

                if (err) {
                    resolve({ "status": 400, "error": err });
                    connection.end();
                    return;
                }
                if (data.VolunteerID) {
                    queryValue += " '" + data.VolunteerID + "'";
                } else {
                    resolve({ "status": 400, "error": "No VolunteerID field found" });
                    connection.end();
                    return;

                }

                connection.query(queryValue, function (err, result, fields) {
                    if (err) {
                        resolve({ "status": 400, "error": err });
                        connection.end();
                        return;
                    }
                    if (result) {
                        resolve({ "status": 200, "data": result, "isSuccess": true });
                    }
                    connection.end();
                    return;

                });
            });


        });
    },
    gethelpertypes: function (data) {
        return new Promise(function (resolve, reject) {
            var connection = mysql.createPool({
                host: '139.59.94.48',
                user: 'playtowindbuser',
                password: 'Gameuser@123',
                database: 'LynkHack'
            });
            connection.getConnection(function (err) {
                var queryValue = "Select * from HelpType ";

                if (err) {
                    resolve({ "status": 400, "error": err });
                    connection.end();
                    return;
                }

                connection.query(queryValue, function (err, result, fields) {
                    if (err) {
                        resolve({ "status": 400, "error": err });
                        connection.end();
                        return;
                    }
                    if (result) {
                        resolve({ "status": 200, "data": result, "isSuccess": true });
                    }
                    connection.end();
                    return;

                });
            });


        });
    },
    helpsomeone: function (data) {
        return new Promise(function (resolve, reject) {
            var connection = mysql.createPool({
                host: '139.59.94.48',
                user: 'playtowindbuser',
                password: 'Gameuser@123',
                database: 'LynkHack'
            });
            connection.getConnection(function (err) {
                var queryValue = "INSERT INTO `LynkHack`.`VolunteerResourceMap` (`VolunteerID`, `AreaID`,`HelpID`, `Description`, `createdOn`, `updatedOn`, `isCompleted`, `isActive`) VALUES (";
                if (err) {
                    resolve({ "status": 400, "error": err });
                    connection.end();
                    return;
                }


                if (data.VolunteerID) {
                    queryValue += "'" + data.VolunteerID + "',";
                } else {
                    resolve({ "status": 400, "error": "No VolunteerID field found" });
                    connection.end();
                    return;

                }


                if (data.AreaID) {
                    queryValue += "'" + data.AreaID + "',";
                } else {
                    resolve({ "status": 400, "error": "No VolunteerID field found" });
                    connection.end();
                    return;

                }

                if (data.HelpID) {
                    queryValue += "'" + data.HelpID + "',";
                } else {
                    resolve({ "status": 400, "error": "No HelpID field found" });
                    connection.end();
                    return;

                }

                if (data.Description) {
                    queryValue += "'" + data.Description + "',";
                } else {
                    resolve({ "status": 400, "error": "No Description field found" });
                    connection.end();
                    return;

                }
                queryValue += "'2019-01-01 00:00:00','2019-01-01 00:00:00',"

                if (data.isCompleted) {
                    queryValue += "'" + data.isCompleted + "',";
                } else {
                    resolve({ "status": 400, "error": "No isCompleted field found" });
                    connection.end();
                    return;

                }

                queryValue += "'1')";


                connection.query(queryValue, function (err, result, fields) {
                    if (err) {
                        resolve({ "status": 400, "error": err });
                        connection.end();
                        return;
                    }
                    if (result) {
                        resolve({ "status": 200, "data": result, "isNewUser": false, "isSuccess": true });
                    }
                    connection.end();
                    return;

                });
            });


        });
    },
    victiminsert: function (data) {
        return new Promise(function (resolve, reject) {
            var connection = mysql.createPool({
                host: '139.59.94.48',
                user: 'playtowindbuser',
                password: 'Gameuser@123',
                database: 'LynkHack'
            });
            connection.getConnection(function (err) {
                var queryValue = "INSERT INTO `LynkHack`.`Victims` ( `PhoneNo`, `Name`, `Address`, `Latitude`, `Longitude`, `AreaID`, `createdOn`, `updatedOn`, `ipAddress`, `isActive`) VALUES (";
                if (err) {
                    resolve({ "status": 400, "error": err });
                    connection.end();
                    return;
                }

                if (data.phoneNo) {
                    queryValue += "'" + data.phoneNo + "',";
                } else {
                    resolve({ "status": 400, "error": "No phoneNo field found" });
                    connection.end();
                    return;

                }

                if (data.Name) {
                    queryValue += "'" + data.Name + "',";
                } else {
                    resolve({ "status": 400, "error": "No Name field found" });
                    connection.end();
                    return;

                }

                if (data.Address) {
                    queryValue += "'" + data.Address + "',";
                } else {
                    resolve({ "status": 400, "error": "No Address field found" });
                    connection.end();
                    return;

                }

                if (data.Latitude) {
                    queryValue += "'" + data.Latitude + "',";
                } else {
                    resolve({ "status": 400, "error": "No Latitude field found" });
                    connection.end();
                    return;

                }
                if (data.Longitude) {
                    queryValue += "'" + data.Longitude + "',";
                } else {
                    resolve({ "status": 400, "error": "No Longitude field found" });
                    connection.end();
                    return;

                }

                if (data.AreaID) {
                    queryValue += "'" + data.AreaID + "',";
                } else {
                    resolve({ "status": 400, "error": "No AreaID field found" });
                    connection.end();
                    return;

                }
                queryValue += "'2019-01-01 00:00:00','2019-01-01 00:00:00','1.1.1.1','1')";


                connection.query(queryValue, function (err, result, fields) {
                    if (err) {
                        resolve({ "status": 400, "error": err });
                        connection.end();
                        return;
                    }
                    if (result) {
                        resolve({ "status": 200, "data": result, "isNewUser": false, "isSuccess": true });
                    }
                    connection.end();
                    return;

                });
            });
        });
    },
    getvolunteerdetails: function (data) {
        return new Promise(function (resolve, reject) {
            var connection = mysql.createPool({
                host: '139.59.94.48',
                user: 'playtowindbuser',
                password: 'Gameuser@123',
                database: 'LynkHack'
            });
            connection.getConnection(function (err) {
                var queryValue = "SELECT * FROM LynkHack.VolunteerResourceMap as VRMap JOIN Volunteer as Vol on VRMap.VolunteerID = Vol.VolunteerID where isCompleted=0 and VRMap.AreaID= ";

                if (err) {
                    resolve({ "status": 400, "error": err });
                    connection.end();
                    return;
                }
                if (data.AreaID) {
                    queryValue += " '" + data.AreaID + "'";
                } else {
                    resolve({ "status": 400, "error": "No AreaID field found" });
                    connection.end();
                    return;

                }

                connection.query(queryValue, function (err, result, fields) {
                    if (err) {
                        resolve({ "status": 400, "error": err });
                        connection.end();
                        return;
                    }
                    if (result) {
                        resolve({ "status": 200, "data": result, "isSuccess": true });
                    }
                    connection.end();
                    return;

                });
            });


        });
    },
    posthelpvictim: function (data) {
        return new Promise(function (resolve, reject) {
            var connection = mysql.createPool({
                host: '139.59.94.48',
                user: 'playtowindbuser',
                password: 'Gameuser@123',
                database: 'LynkHack'
            });
            connection.getConnection(function (err) {
                var queryValue = "INSERT INTO `LynkHack`.`VictimsHelpMap` (`VictimID`,`HelpID`, `AreaID`, `Description`, `Members`, `Male`, `Female`, `Children`, `latitude`, `longitude`, `PhoneNo`, `createdOn`, `updatedOn`, `isCompleted`, `isActive`) VALUES (";
                if (err) {
                    resolve({ "status": 400, "error": err });
                    connection.end();
                    return;
                }
                if (data.VictimID) {
                    queryValue += "'" + data.VictimID + "',";
                } else {
                    resolve({ "status": 400, "error": "No VictimID field found" });
                    connection.end();
                    return;

                }
                if (data.HelpID) {
                    queryValue += "'" + data.HelpID + "',";
                } else {
                    resolve({ "status": 400, "error": "No HelpID field found" });
                    connection.end();
                    return;

                }
                if (data.AreaID) {
                    queryValue += "'" + data.AreaID + "',";
                } else {
                    resolve({ "status": 400, "error": "No AreaID field found" });
                    connection.end();
                    return;

                }
                if (data.Description) {
                    queryValue += "'" + data.Description + "',";
                } else {
                    resolve({ "status": 400, "error": "No Description field found" });
                    connection.end();
                    return;

                }
                if (data.Members) {
                    queryValue += "'" + data.Members + "',";
                } else {
                    resolve({ "status": 400, "error": "No Members field found" });
                    connection.end();
                    return;

                }
                if (data.Male) {
                    queryValue += "'" + data.Male + "',";
                } else {
                    resolve({ "status": 400, "error": "No Male field found" });
                    connection.end();
                    return;

                }
                if (data.Female) {
                    queryValue += "'" + data.Female + "',";
                } else {
                    resolve({ "status": 400, "error": "No Female field found" });
                    connection.end();
                    return;

                }
                if (data.Children) {
                    queryValue += "'" + data.Children + "',";
                } else {
                    resolve({ "status": 400, "error": "No Children field found" });
                    connection.end();
                    return;

                }

                if (data.latitude) {
                    queryValue += "'" + data.latitude + "',";
                } else {
                    resolve({ "status": 400, "error": "No latitude field found" });
                    connection.end();
                    return;

                }
                if (data.longitude) {
                    queryValue += "'" + data.longitude + "',";
                } else {
                    resolve({ "status": 400, "error": "No longitude field found" });
                    connection.end();
                    return;

                }
                if (data.PhoneNo) {
                    queryValue += "'" + data.PhoneNo + "',";
                } else {
                    resolve({ "status": 400, "error": "No PhoneNo field found" });
                    connection.end();
                    return;

                }

                queryValue += "'2019-01-01 00:00:00','2019-01-01 00:00:00',"
                if (data.isCompleted) {
                    queryValue += "'" + data.isCompleted + "'";
                } else {
                    resolve({ "status": 400, "error": "No VictimID field found" });
                    connection.end();
                    return;

                }
                queryValue += ",'1')";

                connection.query(queryValue, function (err, result, fields) {
                    if (err) {
                        resolve({ "status": 400, "error": err });
                        connection.end();
                        return;
                    }
                    if (result) {
                        resolve({ "status": 200, "data": result, "isNewUser": false, "isSuccess": true });
                    }
                    connection.end();
                    return;

                });
            });
        });
    },
    updateprofile: function (data) {
        return new Promise(function (resolve, reject) {
            var connection = mysql.createPool({
                host: '139.59.94.48',
                user: 'playtowindbuser',
                password: 'Gameuser@123',
                database: 'LynkHack'
            });
            connection.getConnection(function (err) {
                var queryValue = "UPDATE `LynkHack`.`Volunteer` SET `Name` = '" + data.Name + "', `Address` = '" + data.Address + "', `Latitude` = '" + data.Latitude + "', `Longitude` = '" + data.Longitude + "', `AreaID` = '" + data.AreaID + "' WHERE `VolunteerID` = '" + data.VolunteerID + "';";
                var queryValue2 = "UPDATE `LynkHack`.`Victims` SET `Name` = '" + data.Name + "', `Address` = '" + data.Address + "', `Latitude` = '" + data.Latitude + "', `Longitude` = '" + data.Longitude + "', `AreaID` = '" + data.AreaID + "' WHERE `VictimID` = '" + data.VictimID + "';";

                if (err) {
                    resolve({ "status": 400, "error": err });
                    connection.end();
                    return;
                }

                connection.query(queryValue, function (err, result, fields) {
                    if (err) {
                        resolve({ "status": 400, "error": err });
                        connection.end();
                        return;
                    }
                    connection.query(queryValue2, function (err, result, fields) {
                        if (err) {
                            resolve({ "status": 400, "error": err });
                            connection.end();
                            return;
                        }
                        if (result) {
                            resolve({ "status": 200, "data": result, "isSuccess": true });
                        }
                        connection.end();
                        return;

                    });

                });
            });


        });
    },
    iscompletevolunteer: function (data) {
        return new Promise(function (resolve, reject) {
            var connection = mysql.createPool({
                host: '139.59.94.48',
                user: 'playtowindbuser',
                password: 'Gameuser@123',
                database: 'LynkHack'
            });
            connection.getConnection(function (err) {
                var queryValue = "UPDATE `LynkHack`.`VolunteerResourceMap` SET `isCompleted` = '" + '1' + "' WHERE `VRMapID` = '" + data.VRMapID + "';";

                connection.query(queryValue, function (err, result, fields) {
                    if (err) {
                        resolve({ "status": 400, "error": err });
                        connection.end();
                        return;
                    }
                    if (result) {
                        resolve({ "status": 200, "data": result, "isSuccess": true });
                    }
                    connection.end();
                    return;

                });
            });


        });
    },
    iscompletevictim: function (data) {
        return new Promise(function (resolve, reject) {
            var connection = mysql.createPool({
                host: '139.59.94.48',
                user: 'playtowindbuser',
                password: 'Gameuser@123',
                database: 'LynkHack'
            });
            connection.getConnection(function (err) {
                var queryValue = "UPDATE `LynkHack`.`VictimsHelpMap` SET `isCompleted` = '" + '1' + "' WHERE `VHMapID` = '" + data.VHMapID + "';";

                connection.query(queryValue, function (err, result, fields) {
                    if (err) {
                        resolve({ "status": 400, "error": err });
                        connection.end();
                        return;
                    }
                    if (result) {
                        resolve({ "status": 200, "data": result, "isSuccess": true });
                    }
                    connection.end();
                    return;

                });
            });


        });
    },
    getngodetails: function (data) {
        return new Promise(function (resolve, reject) {
            var connection = mysql.createPool({
                host: '139.59.94.48',
                user: 'playtowindbuser',
                password: 'Gameuser@123',
                database: 'LynkHack'
            });

            connection.getConnection(function (err) {
                var queryValue = "select * from Organizations";
                if (err) {
                    resolve({ "status": 400, "error": err });
                    connection.end();
                    return;
                }
                connection.query(queryValue, function (err, result, fields) {
                    if (err) {
                        resolve({ "status": 400, "error": err });
                        connection.end();
                        return;
                    }
                    resolve({ "status": 200, "data": result, "isSuccess": true });

                    connection.end();
                    return;

                });
            });


        });
    },
    getngofunddetails: function (data) {
        return new Promise(function (resolve, reject) {
            var connection = mysql.createPool({
                host: '139.59.94.48',
                user: 'playtowindbuser',
                password: 'Gameuser@123',
                database: 'LynkHack'
            });

            connection.getConnection(function (err) {
                var queryValue = "select * from OrganizationsFunds";
                if (err) {
                    resolve({ "status": 400, "error": err });
                    connection.end();
                    return;
                }
                connection.query(queryValue, function (err, result, fields) {
                    if (err) {
                        resolve({ "status": 400, "error": err });
                        connection.end();
                        return;
                    }
                    resolve({ "status": 200, "data": result, "isSuccess": true });

                    connection.end();
                    return;

                });
            });


        });
    },
    ngofundinsert: function (data) {
        return new Promise(function (resolve, reject) {
            var connection = mysql.createPool({
                host: '139.59.94.48',
                user: 'playtowindbuser',
                password: 'Gameuser@123',
                database: 'LynkHack'
            });
            connection.getConnection(function (err) {
                var queryValue = "INSERT INTO `LynkHack`.`OrganizationsFunds` (`OrgID`, `Name`, `Description`, `Sponsor`, `Link`, `createdOn`, `updatedOn`, `isActive`) VALUES (";
                if (err) {
                    resolve({ "status": 400, "error": err });
                    connection.end();
                    return;
                }

                if (data.OrgID) {
                    queryValue += "'" + data.OrgID + "',";
                } else {
                    resolve({ "status": 400, "error": "No OrgID field found" });
                    connection.end();
                    return;

                }

                if (data.Name) {
                    queryValue += "'" + data.Name + "',";
                } else {
                    resolve({ "status": 400, "error": "No Name field found" });
                    connection.end();
                    return;

                }

                if (data.Description) {
                    queryValue += "'" + data.Description + "',";
                } else {
                    resolve({ "status": 400, "error": "No Description field found" });
                    connection.end();
                    return;

                }

                if (data.Sponsor) {
                    queryValue += "'" + data.Sponsor + "',";
                } else {
                    resolve({ "status": 400, "error": "No Sponsor field found" });
                    connection.end();
                    return;

                }
                if (data.Link) {
                    queryValue += "'" + data.Link + "',";
                } else {
                    resolve({ "status": 400, "error": "No Link field found" });
                    connection.end();
                    return;

                }
            
                queryValue += "'2019-01-01 00:00:00','2019-01-01 00:00:00','1')";


                connection.query(queryValue, function (err, result, fields) {
                    if (err) {
                        resolve({ "status": 400, "error": err });
                        connection.end();
                        return;
                    }
                    resolve({ "status": 200, "data": result, "isSuccess": true });

                    connection.end();
                    return;

                });
            });


        });
    }
}