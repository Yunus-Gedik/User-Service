package org.yunusgedik.user.Service;

import org.springframework.stereotype.Service;
import org.yunusgedik.user.Model.Kafka.BookingEvent;
import org.yunusgedik.user.Model.User.User;
import org.yunusgedik.user.Repository.UserStatsRepository;

@Service
public class UserStatsService {
    private final UserStatsRepository userStatsRepository;

    public UserStatsService(UserStatsRepository userStatsRepository) {
        this.userStatsRepository = userStatsRepository;
    }

    public void consumeBookingKafkaEvent(BookingEvent event) {
        User user = userStatsRepository.findById(event.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));

        switch (event.getStatus()) {
            case CONFIRMED -> user.getStats().incrementConfirmedCount(event.getPrice());
            case CANCELLED -> user.getStats().incrementCanceledCount();
            case WAITLISTED -> user.getStats().incrementWaitlistedCount();
        }

        userStatsRepository.save(user);
    }
}
