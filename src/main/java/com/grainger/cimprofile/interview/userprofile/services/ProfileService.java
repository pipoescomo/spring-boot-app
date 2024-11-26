package com.grainger.cimprofile.interview.userprofile.services;

import com.grainger.cimprofile.interview.userprofile.controllers.resources.Profile;

import java.util.Optional;
import java.util.UUID;

public interface ProfileService {
  Optional<Profile> getProfile(UUID id);
  Profile createProfile(Profile profile);
}
