package org.hui.recommendations.services;

import org.hui.recommendations.domain.Sale;

import java.util.List;

public class DatabaseClient {
  private final int timeoutInMillis;

  private final Database stubDb;

  public DatabaseClient(int timeoutInMillis) {
    this.timeoutInMillis = timeoutInMillis;
    this.stubDb = new Database();
  }

  public void save(String customerId, List<Sale> sales) {
    stubDb.save(customerId, sales, timeoutInMillis);
  }  
}