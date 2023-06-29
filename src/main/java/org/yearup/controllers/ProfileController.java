package org.yearup.controllers;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProfileDao;
import org.yearup.models.Profile;
import org.yearup.models.User;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileDao profileDao;

    public ProfileController(ProfileDao profileDao) {
        this.profileDao = profileDao;
    }

    @GetMapping
    public ResponseEntity<Profile> getProfile() {
        // Retrieve the current user's ID from the authenticated principal
        int userId = getCurrentUserId();

        // Fetch the profile associated with the user ID
        Profile profile = profileDao.getByUserId(userId);

        // Return the profile in the response
        return ResponseEntity.ok(profile);
    }

    @PutMapping
    public ResponseEntity<Profile> updateProfile(@RequestBody Profile profile) {
        // Retrieve the current user's ID from the authenticated principal
        int userId = getCurrentUserId();

        // Set the user ID in the profile
        profile.setUserId(userId);

        // Update the profile in the database
        Profile updatedProfile = profileDao.update(profile);

        // Return the updated profile in the response
        return ResponseEntity.ok(updatedProfile);
    }

    // Utility method to retrieve the current user's ID
    private int getCurrentUserId() {
        User user = getCurrentUser();
        if (user != null) {
            return user.getId();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }
    }

    // Utility method to retrieve the current authenticated user
    private User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            return (User) principal;
        } else {
            return null;
        }
    }
}
