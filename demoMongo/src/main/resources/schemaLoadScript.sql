/* password : Welcome12# */


db.role.insert([{"_id":"1","name":"Admin User"}, {"_id":"2","name":"Operations User"}, 
				{"_id":"3","name":"Customer User"}]);
db.user.insert({"firstName" : "adminfirstname",
    "lastName" : "adminuserlastname",
    "email" : "admin@mars.com",
    "password" : "$2a$10$jHWy/wPcjVzBeedW9IAd8eiEmn6FC5eRy3HISNMa/1.DxrIT3QJGy",
     "role" : {
          "_id" : "1",
         "name" : "Admin User"
    }
});
