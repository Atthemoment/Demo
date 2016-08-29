package com.my.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * Created by liangpw on 2016/8/29.
 */
public class LongEventFactory implements EventFactory<LongEvent> {
    @Override
    public LongEvent newInstance() {
        return new LongEvent();
    }
}
