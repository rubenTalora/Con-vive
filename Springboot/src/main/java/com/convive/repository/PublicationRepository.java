
package com.convive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.convive.entity.Publication;

public interface PublicationRepository extends JpaRepository<Publication, Long> {
}
