package com.nvk.pteBooking.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

public class MongoConfig {

  /**
   * Configuration for development and testing environments.
   */
  @Configuration
  @EnableMongoRepositories(
    basePackages = {"com.nvk.pteBooking.repository"})
  @EnableTransactionManagement
  @Profile({"dev", "test","sandbox"})
  public static class MongoConfigDev extends AbstractMongoConfiguration {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Value("${server.name}")
    private String serverName;

    @Value("${spring.data.mongodb.uri}")
    private String mongoDbUri;

    @Override
    protected String getDatabaseName() {
      return serverName + "-" + activeProfile;
    }

    @Override
    public MongoClient mongoClient() {
      String[] addresses = mongoDbUri.split(",");
      List<ServerAddress> servers = new ArrayList<>();
      for (String address : addresses) {
        String[] split = address.trim().split(":");
        servers.add(new ServerAddress(split[0].trim(), Integer.parseInt(split[1].trim())));
      }
      return new MongoClient(servers);
    }
  }

  /**
   * Configuration for production deployments.
   */
  @Configuration
  @EnableMongoRepositories(
    basePackages = {"com.nvk.pteBooking.repository"})
  @EnableTransactionManagement
  @ConditionalOnExpression("!'${spring.profiles.active}'.equals('dev') "
                             + "&& !'${spring.profiles.active}'.equals('test')"
                             + " && !'${spring.profiles.active}'.equals('sandbox') ")
  public static class MongoConfigProd extends AbstractMongoConfiguration {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Value("${server.name}")
    private String serverName;

    @Value("${spring.data.mongodb.uri}")
    private String mongoDbUri;

    @Value("${db.password}")
    private String dbPassword;

    @Override
    protected String getDatabaseName() {
      return serverName + "-" + activeProfile;
    }

    /**
     * Configuration bean for MongoDB.
     * @return Configured client options.
     */
    @Bean
    public MongoClientOptions mongoClientOptions() {
      return MongoClientOptions.builder().sslEnabled(true).sslInvalidHostNameAllowed(true).build();
    }

    @Override
    @Bean
    public MongoClient mongoClient() {
      MongoCredential credential = MongoCredential.createCredential(
        serverName, getDatabaseName(), dbPassword.toCharArray());

      String[] addresses = mongoDbUri.split(",");
      List<ServerAddress> servers = new ArrayList<>();
      for (String address : addresses) {
        String[] split = address.trim().split(":");
        servers.add(new ServerAddress(split[0].trim(), Integer.parseInt(split[1].trim())));
      }

      return new MongoClient(servers, Collections.singletonList(credential),
        MongoClientOptions.builder().sslEnabled(true).sslInvalidHostNameAllowed(true).build());
    }
  }
}
