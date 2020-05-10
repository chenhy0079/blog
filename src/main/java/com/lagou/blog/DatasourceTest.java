package com.lagou.blog;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DatasourceTest {

    private static CuratorFramework client;

    private static String path = "/datasourceconfig";

    private DataSource dataSource;

    public static void main(String[] args) throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", 5000, 3000, retryPolicy);
        client.start();

        String source1 = "jdbc:mysql://localhost:3306/blog_system?serverTimezone=UTC|root|123456";
        String source2 = "jdbc:mysql://localhost:3306/bank?serverTimezone=UTC|root|123456";

        client.setData().forPath(path,source2.getBytes());

    }
}
