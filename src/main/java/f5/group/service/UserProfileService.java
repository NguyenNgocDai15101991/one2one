package f5.group.service;

import f5.group.model.UserProfile;

import java.util.List;
import java.util.Optional;

public interface UserProfileService {
    List<UserProfile> findAll();
    Optional<UserProfile> findById(Long id);
    void save(UserProfile userProfile);
    void delete(Long id);
}
