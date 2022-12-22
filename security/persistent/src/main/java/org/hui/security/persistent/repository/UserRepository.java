package org.hui.security.persistent.repository;

import org.hui.security.persistent.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
