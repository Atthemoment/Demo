package com.my.zookeeper.election;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by liangpw on 2016/8/25.
 */
public class Worker implements LeaderElectable{

    private String name;

    public Worker(String name) {
        this.name = name;
    }

    private ZooKeeperLeaderElection leaderElection;
    private static final String ELECTION_PATH="/testE";
    private ScheduledExecutorService executor;
    private AtomicInteger count=new AtomicInteger();

    public void startUp() throws Exception {
        System.out.println(name+"-----start!!!!!");
        leaderElection=new ZooKeeperLeaderElection(this);
        leaderElection.start(ELECTION_PATH);
    }

    public void stop()throws Exception{
        leaderElection.stop();
    }

    @Override
    public void electedLeader()throws Exception{
        System.out.println(name+"-----do Work!!!!!");
        executor=Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(()->{
            System.out.println("work..."+ count.incrementAndGet());
        },1,2, TimeUnit.SECONDS);
    }

    @Override
    public void revokedLeadership()throws Exception{
       System.out.println(name+"-----stop Work!!!!!");
       if(!executor.isShutdown()){
           executor.shutdown();
       }
    }

}
