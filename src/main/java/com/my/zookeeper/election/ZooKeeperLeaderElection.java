package com.my.zookeeper.election;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * Created by liangpw on 2016/8/25.
 */
public class ZooKeeperLeaderElection implements LeaderLatchListener{
    private LeaderElectable leaderElectable;
    public ZooKeeperLeaderElection(LeaderElectable leaderElectable) {
        this.leaderElectable = leaderElectable;
    }
    private CuratorFramework zk;
    private LeaderLatch leaderLatch;
    private LeadershipStatus status=LeadershipStatus.NOT_LEADER;

    public void start(String latchPath)throws Exception{
        zk = CuratorFrameworkFactory.newClient("172.26.7.23:2181", 15000, 10000, new ExponentialBackoffRetry(5000, 3));
        zk.start();
        leaderLatch=new LeaderLatch(zk,latchPath);
        leaderLatch.addListener(this);
        leaderLatch.start();
    }

    public void stop()throws Exception{
        leaderLatch.close();
        zk.close();
    }

    @Override
    public synchronized void  isLeader() {
        if (!leaderLatch.hasLeadership()) {
            return;
        }
        System.out.println("We have gained leadership");
        updateLeadershipStatus(true);
    }

    @Override
    public synchronized void notLeader() {
        if (leaderLatch.hasLeadership()) {
            return;
        }
        System.out.println("We have lost leadership");
        updateLeadershipStatus(false);
    }

    private void updateLeadershipStatus(Boolean isLeader){
        try {
            if (isLeader && status == LeadershipStatus.NOT_LEADER) {
                status = LeadershipStatus.LEADER;
                leaderElectable.electedLeader();
            } else if (!isLeader && status == LeadershipStatus.LEADER) {
                status = LeadershipStatus.NOT_LEADER;
                leaderElectable.revokedLeadership();
            }
        }catch (Exception e){
            System.out.println(e);
        }

    }


    enum LeadershipStatus{
        LEADER,NOT_LEADER
    }
}
