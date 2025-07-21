package com.codewithyugam.ecom.repositories;

import com.codewithyugam.ecom.entities.Profile;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
}