package org.hui.design.tank;

import java.awt.*;

public class Bullet {
    private static final int SPEED = 10;
    private int x;
    private int y;
    public static int WIDTH = ResourceMgr.bulletD.getWidth();
    public static int HEIGHT = ResourceMgr.bulletD.getHeight();
    private Dir dir;

    private boolean living = true;

    TankFrame tankFrame = null;
    private Group group = Group.BAD;

    public Bullet(int x, int y, Dir dir, Group group, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tankFrame = tankFrame;
    }

    public void paint(Graphics g) {
        if (!living) {
            tankFrame.bullets.remove(this);
        } else {
            move();
            switch (dir) {
                case LEFT:
                    g.drawImage(ResourceMgr.bulletL, x, y, null);
                    break;
                case UP:
                    g.drawImage(ResourceMgr.bulletU, x, y, null);
                    break;
                case RIGHT:
                    g.drawImage(ResourceMgr.bulletR, x, y, null);
                    break;
                case DOWN:
                    g.drawImage(ResourceMgr.bulletD, x, y, null);
                    break;
            }
        }
    }

    private void move() {
        switch (dir) {
            case LEFT:
                x -= SPEED;
                break;
            case UP:
                y -= SPEED;
                break;
            case RIGHT:
                x += SPEED;
                break;
            case DOWN:
                y += SPEED;
                break;
        }
        if (x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) {
            this.living = false;
        }
    }

    public void collideWith(Tank tank) {
        if (this.group == tank.getGroup()) return;
        // TODO: 用一个 reat 来记录子弹的位置
        Rectangle rect1 = new Rectangle(this.x, this.y, WIDTH, HEIGHT);
        Rectangle rect2 = new Rectangle(tank.getX(), tank.getY(), Tank.WIDTH, Tank.HEIGHT);
        if (rect1.intersects(rect2)) {
            this.tankFrame.explodes.add(new Explode(tank.getX(), tank.getY(), tankFrame));
            tank.die();
            this.die();
        }
    }

    private void die() {
        this.living = false;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
