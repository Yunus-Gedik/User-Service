package org.yunusgedik.user.Service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.yunusgedik.user.Model.Kafka.BookingEvent;

@Service
public class BookingEventConsumer {

    private final UserStatsService userStatsService;

    public BookingEventConsumer(UserStatsService userStatsServiceService) {
        this.userStatsService = userStatsServiceService;
    }

    @KafkaListener(topics = "booking-events", groupId = "${spring.kafka.consumer.group-id.stats}")
    public void consumeBookingEvent(BookingEvent event) {
        userStatsService.consumeBookingKafkaEvent(event);
    }
}