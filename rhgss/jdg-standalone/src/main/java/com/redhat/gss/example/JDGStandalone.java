/*
 * To the extent possible under law, Red Hat, Inc. has dedicated all
 * copyright to this software to the public domain worldwide, pursuant
 * to the CC0 Public Domain Dedication. This software is distributed
 * without any warranty.
 *
 * See <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package com.redhat.gss.example;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.cache.CacheMode;

public class JDGStandalone {

    public static void main(String[] args) throws Exception {
        simple();
    }

    public static void simple() throws Exception {
        GlobalConfiguration globalConfig =
            new GlobalConfigurationBuilder()
            .globalJmxStatistics().allowDuplicateDomains(true)
            .transport()
            .defaultTransport()
            .build();
        Configuration defaultCacheConfig =
            new ConfigurationBuilder()
            .clustering()
            .cacheMode(CacheMode.DIST_ASYNC)
            .build();
        DefaultCacheManager cm = new DefaultCacheManager(globalConfig, defaultCacheConfig);
        Cache cache = cm.getCache();
        System.out.println("sleep 20 sec");
        try {
            Thread.sleep(20000);
        } catch (InterruptedException ignore) { }
        cm.stop();
    }
} 
