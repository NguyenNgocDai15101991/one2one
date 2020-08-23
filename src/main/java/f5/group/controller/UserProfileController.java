package f5.group.controller;

import f5.group.model.User;
import f5.group.model.UserProfile;
import f5.group.service.impl.UserProfileServiceImpl;
import f5.group.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/userProfile")
public class UserProfileController {
    @Autowired
    private UserProfileServiceImpl userProfileService;
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/get")
    public ResponseEntity<List<UserProfile>> getAll() {
        try {
            List<UserProfile> list = userProfileService.findAll();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        }

    @GetMapping("/get/{id}")
    public ResponseEntity<UserProfile> getById(@PathVariable("id") Long id) {
        Optional<UserProfile> userProfile = userProfileService.findById(id);
        if (userProfile.isPresent()) {
            return new ResponseEntity<>(userProfile.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/user/{user_id}/userProfile")
        public ResponseEntity<UserProfile> addUserProfile(@PathVariable Long user_id,
                                                          @RequestBody UserProfile userProfile) {
        try {
            User user = userService.findById(user_id).orElseThrow(() -> new Exception("user_id not found" + user_id));
            userProfile.setUser(user);
            userProfileService.save(userProfile);
            return new ResponseEntity<>(userProfile, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("user/{user_id}/userProfile/{id}")
    public ResponseEntity<UserProfile> updateUserProfile(@PathVariable("user_id") Long user_id,
                                                         @PathVariable("id") Long id,
                                                         @RequestBody UserProfile userProfile) throws Exception {

        User user = userService.findById(user_id).orElseThrow(() -> new Exception("user_id not found" + user_id));
        Optional<UserProfile> userProfileData = userProfileService.findById(id);
        if (userProfileData.isPresent()) {
            UserProfile _userProfile = userProfileData.get();
            _userProfile.setPhoneNumber(userProfile.getPhoneNumber());
            _userProfile.setAddress(userProfile.getAddress());
            _userProfile.setUser(user);
           userProfileService.save(_userProfile);
            return new ResponseEntity<>(_userProfile, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
//     else {
//         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<UserProfile> deleteUserProfile(@PathVariable("id") Long id) {
        try {
            userProfileService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    }

