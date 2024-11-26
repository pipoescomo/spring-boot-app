package com.grainger.cimprofile.interview.userprofile.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grainger.cimprofile.interview.userprofile.controllers.resources.Profile;
import com.grainger.cimprofile.interview.userprofile.models.ProfileModel;
import com.grainger.cimprofile.interview.userprofile.repositories.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

  private final ProfileRepository profileRepository;
  private final KafkaTemplate<String, String> kafkaTemplate;

  @Override
  public Optional<Profile> getProfile(UUID id) {
    return profileRepository.findById(id)
        .map(profileModel -> new Profile(profileModel.getId(), profileModel.getName(), profileModel.getEmail()));
  }

  @Override
  public Profile createProfile(Profile profile) {
    ProfileModel profileModel = ProfileModel.builder()
      .name(profile.name())
      .email(profile.email())
      .build();

    profileRepository.save(profileModel);

    try {
      var mapper = new ObjectMapper();
      kafkaTemplate.send("profile", mapper.writeValueAsString(profileModel));
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

    return new Profile(profileModel.getId(), profileModel.getName(), profileModel.getEmail());
  }
}
