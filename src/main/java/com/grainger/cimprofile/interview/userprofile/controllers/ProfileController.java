package com.grainger.cimprofile.interview.userprofile.controllers;

import com.grainger.cimprofile.interview.userprofile.controllers.resources.Profile;
import com.grainger.cimprofile.interview.userprofile.services.ProfileService;
import com.grainger.cimprofile.interview.userprofile.services.ProfileServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class ProfileController {

  private final ProfileService profileService;

  @GetMapping("/{id}")
  public Profile greeting(@PathVariable String id) {
    return profileService.getProfile(UUID.fromString(id)).orElseThrow();
  }

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public Profile createProfile(@RequestBody Profile profile) {
    return profileService.createProfile(profile);
  }

}
