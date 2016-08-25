package com.my.zookeeper.operator;

import com.alibaba.fastjson.JSON;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by liangpw on 2016/8/25.
 */
public class CURDDemo {
    private CuratorFramework zk;
    @Before
    public void setUp(){
        zk = CuratorFrameworkFactory.newClient("172.26.7.23:2181", 15000, 10000, new ExponentialBackoffRetry(5000, 3));
        zk.start();
    }
    @After
    public void shutDown(){
        zk.close();
    }

    @Test
    public void testCreate()throws Exception{
        String path="/testE/aa";
        String lpw= JSON.toJSONString(new Person("lpw","26"));
        if(zk.checkExists().forPath(path)==null){
            zk.create().withMode(CreateMode.PERSISTENT).forPath(path,lpw.getBytes("utf-8"));
        }else{
            byte[] data=zk.getData().forPath(path);
            String json=new String(data,"utf-8");
            System.out.println(json);
            Person person=JSON.parseObject(json,Person.class);
            System.out.println(person);
        }
    }
    @Test
    public void testDelete()throws Exception{
        String path="/testE/aa";
        if(zk.checkExists().forPath(path)!=null){
            zk.delete().forPath(path);
        }
    }
}
