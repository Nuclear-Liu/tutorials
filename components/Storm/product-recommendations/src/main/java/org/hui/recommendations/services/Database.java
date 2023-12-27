package org.hui.recommendations.services;

import org.hui.recommendations.domain.Sale;

import java.util.List;

class Database {
  private final LatencySimulator latency = new LatencySimulator(20, 10, 30, 1500, 1);

  public void save(String customerId, List<Sale> sale, int timeoutInMillis) {
    latency.simulate(timeoutInMillis);
  }
}