/* password : $2a$10$R9k6hZ4M.xVl/KHZLCVBAuQ95Csg4GMR5EOkSPaZOayGAqOiHTFBu */


db.role.insert([{"_id":"1","name":"Admin User"}, {"_id":"2","name":"Operations User"}, 
				{"_id":"3","name":"Customer User"}]);
db.user.insert({"firstName" : "adminfirstname",
    "lastName" : "adminuserlastname",
    "email" : "admin@mars.com",
    "password" : "$2a$10$h4wDBlImGz.6SAIAu/QWaetvZqiWkMKb0LpSxJdV.UuesGZ8UWskG",
    "status" : "REGISTERED",
     "role" : {
          "_id" : "1",
         "name" : "Admin Operations User"
    }
});
