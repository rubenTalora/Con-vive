
package com.convive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.convive.entity.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
