package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import net.engineeringdigest.journalApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserControllerV2 {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

//    @GetMapping
//    public ResponseEntity<?> getAllUsers(){
//        List<User> all = userService.getAll();
//        if (all != null && !all.isEmpty()) {
//            return new ResponseEntity<>(all, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }  // only the admin can see all the users

//    @PostMapping
//    public ResponseEntity<?> createUser(@RequestBody User user){
//        try {
//            userService.saveNewUser(user);
//            return new ResponseEntity<>(user, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    } // we shifted this to PublicController as it doesn't require the authentication

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb = userService.findByUserName(userName);
        userInDb.setUserName(user.getUserName());
        userInDb.setPassword(user.getPassword());
        userService.saveNewUser(userInDb);   // same id mein save ho jayega
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @DeleteMapping
    public ResponseEntity<?> deleteUserById(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*
    here we will not update the user with id we will update the user with its username and the password with the help of @RequestBody
    we will find the user with username
    also create the method in UserRepository
     */

    /*
    Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
    String userName = authentication.getName();

    jab koi user authenticate hota hai toh uski details SecurityContextHolder mein save hoti hai
    aur fir hum ussi authentication se uska username nikal skate hai


     */


}
