package com.lagou.blog.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Component
public class DatasourceConfig implements  InvocationHandler , FactoryBean<DataSource> {


    CuratorFramework client;

    private String path = "/datasourceconfig";

    private DataSource dataSource;


    @PostConstruct
    public void createClient() throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", 5000, 3000, retryPolicy);
        client.start();

        String source = "jdbc:mysql://localhost:3306/blog_system?serverTimezone=UTC|root|123456";

        client.setData().forPath(path,source.getBytes());

        byte[] bytes = client.getData().forPath(path);
        String[] sourceConfig = new String(bytes).split("\\|");

        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(sourceConfig[0]);
        druidDataSource.setUsername(sourceConfig[1]);
        druidDataSource.setPassword(sourceConfig[2]);

        dataSource = druidDataSource;
        serverUpload();

    }


    @Override
    public synchronized Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Object invoke = null;

        synchronized (path){
           invoke = method.invoke(dataSource, args);
        }

        return invoke;
    }

    private void serverUpload() throws Exception {

        NodeCache nodeCache = new NodeCache(client,path);
        //BUILD_INITIAL_CACHE 代表使用同步的方式进行缓存初始化。
        nodeCache.start(false);
        nodeCache.getListenable().addListener(new NodeCacheListener(){

            @Override
            public void nodeChanged() throws Exception {
                System.out.println("更新节点----");

                byte[] bytes = nodeCache.getCurrentData().getData();
                String[] sourceConfig = new String(bytes).split("\\|");

                DruidDataSource druidDataSource = new DruidDataSource();
                druidDataSource.setUrl(sourceConfig[0]);
                druidDataSource.setUsername(sourceConfig[1]);
                druidDataSource.setPassword(sourceConfig[2]);

                dataSource = druidDataSource;
            }
        });
    }

    @Override
    public DataSource getObject() throws Exception {
        return (DataSource) Proxy.newProxyInstance(DatasourceConfig.class.getClassLoader(),new Class[]{DataSource.class},this);
    }

    @Override
    public Class<?> getObjectType() {
        return DataSource.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
