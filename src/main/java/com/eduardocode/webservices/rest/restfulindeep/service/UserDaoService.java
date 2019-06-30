package com.eduardocode.webservices.rest.restfulindeep.service;

import com.eduardocode.webservices.rest.restfulindeep.model.Post;
import com.eduardocode.webservices.rest.restfulindeep.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * <h1>UserDaoService</h1>
 * Class to represent user data transactions between db and controllers
 *
 * @author Eduardo Rasgado Ruiz
 */
@Component
public class UserDaoService {

    private static List<User> userRepository = new ArrayList<>();;
    private static int userCount = 3;
    private static int postCount = 0;

    public UserDaoService() {

    }

    static {

        userRepository.add(new User(1, "Denis", "Rocha", new Date(), new ArrayList<Post>()));
        userRepository.add(new User(2, "Alejandro", "Franco", new Date(), new ArrayList<Post>()));
        userRepository.add(new User(3, "Jimena", "Sandoval", new Date(), new ArrayList<Post>()));
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
     * Method to get an existing user given its postId
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

    /**
     * Method to delete a user if it exists
     * @param id
     * @return
     */
    public User deleteById(Integer id) {
        Iterator<User> i = userRepository.iterator();
        while (i.hasNext()) {
            User user = i.next();
            if(user.getId().equals(id)) {
                i.remove();
                return user;
            }
        }
        return null;
    }

    /**
     * Method to return if a user exists in database or not
     * @param id
     * @return
     */
    public boolean userExists(Integer id) {
        for(User user : userRepository) {
            if(user.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * Method to create a post for certain user
     * @param idUser
     * @param post
     * @return
     */
    public Post createPost(Integer idUser, Post post) {
        if(idUser > 0 && idUser <= userCount && post != null) {
            List<Post> posts = null;
            try {
                // obteniendo la lista de post del usuario deseado
                for(User u : userRepository) {
                    if(u.getId().equals(idUser)){
                        posts = u.getPosts();
                    }
                }
                post.setPostId(++postCount);
                posts.add(post);

                // actualizando la lista vieja de posts del usuario, por la lista nueva
                for(int i = 0; i < userRepository.size(); i++ ) {
                    User u = userRepository.get(i);

                    if(u.getId().equals(idUser)) {
                        u.setPosts(posts);
                        userRepository.set(i, u);
                    }
                }
                // retornamos el post que se ha guardado
                return this.findPostByUserIdAndByPostId(idUser, postCount);
            } catch (NullPointerException e) {
                System.out.println("Error al agregar un nuevo post en los posts de un usuario");
            }
        }
        return null;
    }

    /**
     * Method to retreive a post given a user postId and a post postId
     * @param userId
     * @param postId
     * @return
     */
    public Post findPostByUserIdAndByPostId(Integer userId, Integer postId){
        for(User u : userRepository) {
            if(u.getId().equals(userId)){
                for(Post p : u.getPosts()){
                    if(p.getPostId().equals(postId)){
                        return p;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Retreive all the posts from a user
     * @param userId
     * @return
     */
    public List<Post> findAllPostsByUserId(Integer userId) {
        for(User u : userRepository) {
            if(u.getId().equals(userId)) {
                return u.getPosts();
            }
        }
        return null;
    }

}
