package net.engineeringdigest.journalApp.services;


import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


// here we will write the business login and we will use this in controllerV2
@Component
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;   // dependency injection of the @Collection that is this is the bean



    public void saveEntry(User user) {
        userRepository.save(user);

    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId id) {    // optional is nothing but a box saying it can either contain content or it can be empty
        return userRepository.findById(id);
    }

    public void deleteById(ObjectId id) {
        userRepository.deleteById(id);
    }

    public  User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }


}
