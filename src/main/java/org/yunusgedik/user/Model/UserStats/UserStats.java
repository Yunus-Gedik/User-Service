package org.yunusgedik.user.Model.UserStats;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yunusgedik.user.Model.User.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "userStats")
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

    public void incrementConfirmedCount(Double price) {
        totalBookings = (totalBookings == null ? 0 : totalBookings) + 1;
        totalAmountSpent = totalAmountSpent.add(BigDecimal.valueOf(price));
        lastBookingTime = LocalDateTime.now();
    }

    public void incrementCanceledCount() {
        totalCancellations = (totalCancellations == null ? 0 : totalCancellations) + 1;
    }

    public void incrementWaitlistedCount() {
        totalWaitlisted = (totalWaitlisted == null ? 0 : totalWaitlisted) + 1;
    }
}