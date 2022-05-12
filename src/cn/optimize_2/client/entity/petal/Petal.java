package cn.optimize_2.client.entity.petal;

import cn.optimize_2.client.Client;
import cn.optimize_2.client.entity.Entity;
import cn.optimize_2.client.entity.Flower;
import cn.optimize_2.utils.graphic.Texture;
import com.sun.javafx.geom.Vec2d;

public class Petal extends Entity {
    private double deg;
    private double deg2 = 0;
    private Integer dmg;
    private Integer dur;

    private Integer flowerId;

    private int fx, fy;

    private Client c;

    private Vec2d velocity;

    private int getDegree(int flowerId, int id) {
        if (flowerId != Client.getInstance().getFlower().getId())
            return 0;
        int degree = 0;
        for (Petal p : Client.getInstance().getMyPetals()) {
            if (id == p.getId()) {
                return degree;
            }
            degree += 360 / Client.getInstance().getMyPetals().size();
        }
        return 0;
    }

    public Petal(int flowerId, Texture texture, Integer id, Integer dmg, Integer dur) {
        super((int) (Client.getInstance().getFlowerById(flowerId).getX()),
                (int) (Client.getInstance().getFlowerById(flowerId).getY()),
                texture);
        this.flowerId = flowerId;
        this.dmg = dmg;
        this.dur = dur;
        setId(id);
    }

    public Petal(int flowerId, Texture texture, Integer id, Integer dmg, Integer dur, Client c) {
        this(flowerId, texture, id, dmg, dur);
        this.c = c;
    }

    @Override
    public void update(float delta) {
        deg = getDegree(flowerId, getId());
    }

    @Override
    public void input(Entity entity) {
        Flower f = c.getFlowerById(flowerId);
        if (c.getFlower().getId() != flowerId)
            return;
        deg2 += f.getRotationSpeed();
        if (deg2 >= 360)
            deg2 -= 360;
        Vec2d target = new Vec2d(f.getR() * Math.cos(deg + deg2), f.getR() * Math.sin(deg + deg2));
        // System.out.println(fx + " " + fy + " " + target.x + " " + target.y);

        if (new Vec2d(fx, fy).distance(target) <= 1) {
            this.fx = (int) target.x;
            this.fy = (int) target.y;
        }
        double speed = 3;
        velocity = new Vec2d((target.x - fx) / speed, (target.y - fy) / speed);
        fx += velocity.x;
        fy += velocity.y;

        setX(fx + c.getFlowerById(flowerId).getX());
        setY(fy + c.getFlowerById(flowerId).getY());
    }

    public Integer getFlowerId() {
        return flowerId;
    }

    public void setFlowerId(Integer flowerId) {
        this.flowerId = flowerId;
    }

    public Integer getDamage() { return dmg; }
}
