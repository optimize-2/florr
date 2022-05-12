package cn.optimize_2.client.event;

import cn.optimize_2.client.entity.petal.Petal;

public class PetalHitEvent {
    private Petal source;

    public PetalHitEvent(Petal source){
        this.source = source;
    }

    public Petal getSource() {
        return source;
    }

    public void setSource(Petal source) {
        this.source = source;
    }
}
