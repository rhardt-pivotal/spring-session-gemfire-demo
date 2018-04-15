package io.pivotal.pa.demo.springsessiongemfiredemo;

//import io.pivotal.spring.cloud.service.common.GemfireServiceInfo;
//import org.apache.geode.cache.client.ClientCache;
//import org.apache.geode.cache.client.ClientCacheFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.cloud.Cloud;
//import org.springframework.cloud.CloudFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
//
//import java.net.URI;

//@Configuration
public class DefaultClientCacheConfiguration {

//    private static final String SECURITY_CLIENT = "security-client-auth-init";
//    private static final String SECURITY_USERNAME = "security-username";
//    private static final String SECURITY_PASSWORD = "security-password";
//
//    private static final Logger logger = LoggerFactory.getLogger(DefaultClientCacheConfiguration.class);
//
//    // Required to resolve property placeholders in Spring @Value annotations.
//    @Bean
//    static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
//        return new PropertySourcesPlaceholderConfigurer();
//    }
//
//    @Bean
//    ClientCache clientCache() {  // <5>
//
//        Cloud cloud = new CloudFactory().getCloud();
//        GemfireServiceInfo serviceInfo = (GemfireServiceInfo) cloud.getServiceInfos().get(0);
//        ClientCacheFactory factory = new ClientCacheFactory();
//        for (URI locator : serviceInfo.getLocators()) {
//            factory.addPoolLocator(locator.getHost(), locator.getPort());
//        }
//
//        factory.set(SECURITY_CLIENT, "io.pivotal.pa.demo.springsessiongemfiredemo.UserAuthInitialize.create");
//        factory.set(SECURITY_USERNAME, serviceInfo.getDevUsername());
//        factory.set(SECURITY_PASSWORD, serviceInfo.getDevPassword());
//        //factory.setPdxSerializer(pdxSerializer());
//        //factory.setPoolSubscriptionEnabled(true); // to enable CQ
//        ClientCache ret =  factory.create();
//        return ret;
//    }
}