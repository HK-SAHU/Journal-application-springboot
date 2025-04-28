package net.engineeringdigest.journalApp.services;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByUserName(username);
        if(user!=null){
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUserName())
                    .password(user.getPassword())
                    .roles(user.getRoles().toArray(new String[0]))
                    .build();
        }

         throw new UsernameNotFoundException("User not found with username: "+username);

    }
}

// this is the authentication class

/*
ye user details maang rha hai, aur hum load kar rahe hai user ko using its username
par ye user details hai kya?
    write User in if condition and select core wala
    then add .builder().username(user.getUserName()).password(user.getPassword())

    and this . notation is getter setter (User user= new User()) and we could also do user.setUsername()/ user.setPassword()
    .roles() kind of roles and it should be comma separated and it convert it to array and type of array we want and if the size of the roles is bigger than it will resize the array

    then .build() it

 */
