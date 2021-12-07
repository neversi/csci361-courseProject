package hotel.service;

import java.util.Optional;
import hotel.model.User;

public interface UserService {
    Optional<User> getUserByName(User u) throws Exception;
    Optional<User> createUser(User u) throws Exception;
}
