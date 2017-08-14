/* password : ipcdefaultopadminsuser */


db.role.insert([{"_id":"1","name":"Admin Operations User"}, {"_id":"2","name":"Operations User"}, 
				{"_id":"3","name":"Customer End User"}, {"_id":"4","name":"Customer Admin User"}]);
db.user.insert({"firstName" : "defaultopsadminuserfirstname",
    "lastName" : "defaultopsadminuserlastname",
    "email" : "defaultunigy360opsadminuser@ipc.com",
    "password" : "$2a$10$h4wDBlImGz.6SAIAu/QWaetvZqiWkMKb0LpSxJdV.UuesGZ8UWskG",
    "status" : "REGISTERED",
     "role" : {
          "_id" : "1",
         "name" : "IPC Admin Operations User"
    }
});

db.product.insert([{"_id":"1","name":"PULSE"}, {"_id":"2","name":"IQMAX"}]);