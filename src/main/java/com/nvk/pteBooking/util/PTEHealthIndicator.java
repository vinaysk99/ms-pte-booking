package com.nvk.pteBooking.util;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class PTEHealthIndicator implements HealthIndicator {

  @Override
  public Health health() {
    boolean isComponentHealthy = true;
    // Health check logic...

    if (isComponentHealthy) {
      return Health.up().build();
    }

    return Health.down()
             .withException(null)
             .withDetail("optional.field.to.be.added.to.response", "someDetails")
             .build();
  }
}
