package hotel.repository;

import java.util.Optional;

import hotel.model.User;

public interface UserRepository  extends ICRUDRepository<User> {
    public Optional<User> getUserByName(User u) throws Exception;
}
