package com.hemant.db.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.hemant.db.exception.ResourceNotFoundException;
import com.hemant.db.model.Profile;
import com.hemant.db.repository.ProfileRepository;

@Service
@Transactional
public class ProfileService {
    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    public Profile createProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    public List<Profile> findByGender(String gender) {
        return profileRepository.findByGender(gender);
    }

    public List<Profile> findByHobbies(String hobbies) {
        return profileRepository.findByHobbies(hobbies);
    }

    public Profile updateHobbies(Integer id, String hobbies) {
        Profile profile = findById(id);
        profile.setHobbies(hobbies);
        return profileRepository.save(profile);
    }

    public void deleteProfile(Integer id) {
        Profile profile = findById(id);
        profileRepository.delete(profile);
    }

    private Profile findById(Integer id) {
        return profileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profile", id));
    }
}
