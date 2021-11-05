package org.hui.design.tank;

import java.awt.*;
import java.util.Random;

public class Tank {
    private int x = 200;
    private int y = 200;
    private Dir dir = Dir.DOWN;
    private static final int SPEED = 2;
    private boolean moving = true;
    private TankFrame tankFrame = null;
    private Group group = Group.BAD;

    private Random random = new Random();

    static int WIDTH = ResourceMgr.tankD.getWidth();
    static int HEIGHT = ResourceMgr.tankD.getHeight();
    private boolean living = true;

    public Tank(int x, int y, Dir dir, Group group, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tankFrame = tankFrame;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public void paint(Graphics g) {
        if (!living)  {
            tankFrame.tanks.remove(this);
        } else {
            move();
            switch (dir) {
                case LEFT:
                    g.drawImage(ResourceMgr.tankL, x, y, null);
                    break;
                case UP:
                    g.drawImage(ResourceMgr.tankU, x, y, null);
                    break;
                case RIGHT:
                    g.drawImage(ResourceMgr.tankR, x, y, null);
                    break;
                case DOWN:
                    g.drawImage(ResourceMgr.tankD, x, y, null);
                    break;
            }
        }
    }

    private void move() {
        if (moving) {
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
            if (random.nextInt(10) > 8 && this.group == Group.BAD) this.fire();
        }
    }

    public void fire() {
        int bX = (int) (this.x + Tank.WIDTH/2.0 - Bullet.WIDTH/2.0);
        int bY = (int) (this.y + Tank.HEIGHT/2.0 - Bullet.HEIGHT/2.0);
        tankFrame.bullets.add(new Bullet(bX, bY, this.dir, this.group, this.tankFrame));
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void die() {
        this.living = false;
    }
}
