package f5.group.service;

import f5.group.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    Optional<User> findById(Long id);
    void save(User user);
    void delete(Long id);
}
