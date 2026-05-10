package com.hemant.db.resource;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hemant.db.model.Profile;
import com.hemant.db.service.ProfileService;

@Validated
@RestController
@RequestMapping(value = "/rest/Profile")
public class ProfileResource {
    private final ProfileService profileService;

    public ProfileResource(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping(value = "/all")
    public List<Profile> getAll() {
        return profileService.getAllProfiles();
    }

    @PostMapping(value = "/insert")
    public ResponseEntity<Profile> persist(@Valid @RequestBody final Profile profile) {
        return ResponseEntity.ok(profileService.createProfile(profile));
    }

    @GetMapping("/findbygender")
    public List<Profile> fetchDataByGender(@RequestParam("gender") @NotBlank String gender) {
        return profileService.findByGender(gender);
    }

    @GetMapping("/findbyhobbies")
    public List<Profile> fetchDataByHobbies(@RequestParam("hobbies") @NotBlank String hobbies) {
        return profileService.findByHobbies(hobbies);
    }

    @PostMapping("/updatehobbies")
    public ResponseEntity<Profile> updateHobbies(
            @RequestParam("Id") Integer id,
            @RequestParam("hobbies") @NotBlank String hobbies) {
        return ResponseEntity.ok(profileService.updateHobbies(id, hobbies));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteProfile(@RequestParam("Id") Integer id) {
        profileService.deleteProfile(id);
        return ResponseEntity.ok("Success");
    }
}
