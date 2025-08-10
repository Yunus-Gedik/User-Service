package org.yunusgedik.user.Model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class UserStats {
    @Id
    private Long userId; // Same as User ID

    @OneToOne
    @MapsId
    @JoinColumn(name = "userId")
    private User user;

    private Integer totalBookings = 0;
    private Integer totalCancellations = 0;
    private Integer totalWaitlisted = 0;
    private BigDecimal totalAmountSpent = BigDecimal.ZERO;
    private LocalDateTime lastBookingTime;
}