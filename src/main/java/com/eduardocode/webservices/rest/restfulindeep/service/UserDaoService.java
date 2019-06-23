package com.eduardocode.webservices.rest.restfulindeep.service;

import com.eduardocode.webservices.rest.restfulindeep.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <h1>UserDaoService</h1>
 * Class to represent user data transactions between db and controllers
 *
 * @author Eduardo Rasgado Ruiz
 */
@Component
public class UserDaoService {

    private static List<User> userRepository;
    private static int userCount = 3;

    public UserDaoService() {
        userRepository = new ArrayList<>();
    }

    static {
        userRepository.add(new User(1, "Denis", "Rocha", new Date()));
        userRepository.add(new User(2, "Alejandro", "Franco", new Date()));
        userRepository.add(new User(3, "Jimena", "Sandoval", new Date()));
    }

    /**
     * Method to return all the members in the user repository
     * @return
     */
    public List<User> findAll() {
        return userRepository;
    }

    /**
     * Method to create a new user based on a given user
     * @param user
     * @return
     */
    public User save(User user) {
        user.setId(++userCount);
        userRepository.add(user);
        return this.findById(userCount);
    }

    /**
     * Method to get an existing user given its id
     * @param id
     * @return
     */
    public User findById(Integer id) {
        for (User userRepo : userRepository) {
            if(userRepo.getId().equals(id)) {
                return userRepo;
            }
        }
        return null;
    }


}
