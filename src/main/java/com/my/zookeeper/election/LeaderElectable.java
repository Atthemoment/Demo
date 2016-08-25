package com.my.zookeeper.election;

/**
 * Created by liangpw on 2016/8/25.
 */
public interface LeaderElectable {
    void electedLeader()throws Exception;
    void revokedLeadership()throws Exception;
}
