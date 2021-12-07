package hotel.service;

import java.util.Optional;

import hotel.model.User;
import hotel.repository.UserRepository;
import hotel.repository.UserRepositoryPostgres;

public class UserServiceImpl implements UserService {
    public UserRepository db;

    public UserServiceImpl() {
        db = new UserRepositoryPostgres();
    }
    
    public Optional<User> getUserByName(User u) throws Exception {
        if (u.getUsername().isEmpty()) {
            return Optional.empty();
        }
        try {
            Optional<User> currentUser = db.getUserByName(u);
            return currentUser;
        } catch (Exception e) {
            throw new Exception("UserServiceImpl.getUserByName: " + e.toString());
        }
    }

    public Optional<User> createUser(User u) throws Exception {
        if (u.getUsername().isEmpty()) {
            return Optional.empty();
        }

        try {
            db.create(u);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return Optional.of(u);
    }
}
