package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;


/*
@Component
This tells Spring to automatically detect this interface during component scanning and register it as a Spring Bean.

Meaning: You don't have to manually instantiate UserRepository; Spring will inject it wherever needed.
 */
@Component
public interface UserRepository extends MongoRepository<User, ObjectId> {    //<entry, and type of the id>
    User findByUserName(String username);

    User deleteByUserName(String username);
}

/*
UserRepository is an interface that extends MongoRepository, which comes from Spring Data MongoDB.

MongoRepository<User, ObjectId> means:

User → the type of the document you are working with (your model/class).

ObjectId → the type of the ID field (the primary key).
(Usually, MongoDB's _id is of type org.bson.types.ObjectId.)
 */


/*
User findByUserName(String username);

This is a custom query method.

Spring Data automatically understands that you want to find a User by the userName field.

It will create the MongoDB query behind the scenes, something like:
db.user.findOne({ "userName" : username })
Note:

The method name must follow the findBy<FieldName> pattern.

Make sure your User class has a field named exactly userName (case-sensitive)
 */
