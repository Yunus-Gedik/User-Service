package org.yunusgedik.user.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yunusgedik.user.Model.User.User;

public interface UserStatsRepository extends JpaRepository<User, Long> {
}
