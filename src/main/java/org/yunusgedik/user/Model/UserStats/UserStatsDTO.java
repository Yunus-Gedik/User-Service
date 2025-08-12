package org.yunusgedik.user.Model.UserStats;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserStatsDTO {
    private Long id;
    private Long userId;
    private int totalBookings;
    private int totalCancelled;
    private int totalWaitlisted;
    private BigDecimal totalSpent;
}
