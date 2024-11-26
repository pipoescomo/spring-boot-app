package com.grainger.cimprofile.interview.userprofile.services;

import com.grainger.cimprofile.interview.userprofile.controllers.resources.Profile;
import com.grainger.cimprofile.interview.userprofile.models.ProfileModel;
import com.grainger.cimprofile.interview.userprofile.repositories.ProfileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfileServiceImplTest {

  @Mock
  ProfileRepository profileRepository;

  @Mock
  KafkaTemplate<String, String> kafkaTemplate;

  @InjectMocks
  ProfileServiceImpl profileService;

  @Test
  void getProfile_shouldReturnProfileFromRepository() {
    // given
    UUID id = UUID.randomUUID();
    ProfileModel profileModel = ProfileModel.builder()
        .id(id)
        .name("name")
        .email("email")
        .build();
    when(profileRepository.findById(id)).thenReturn(Optional.of(profileModel));

    // when
    Optional<Profile> profile = profileService.getProfile(id);

    // then
    assertTrue(profile.isPresent());
    assertEquals(id, profile.get().id());
    assertEquals("name", profile.get().name());
    assertEquals("email", profile.get().email());
  }
}