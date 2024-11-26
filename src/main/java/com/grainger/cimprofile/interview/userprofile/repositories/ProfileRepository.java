package com.grainger.cimprofile.interview.userprofile.repositories;

import com.grainger.cimprofile.interview.userprofile.models.ProfileModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProfileRepository extends CrudRepository<ProfileModel, Long> {

  Optional<ProfileModel> findById(UUID id);
}