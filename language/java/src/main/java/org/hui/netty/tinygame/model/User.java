package org.hui.netty.tinygame.model;

public class User {
    private int userId;
    private String heroAvatar;

    /**
     * 当前血量
     */
    private int currHp;

    /**
     * 移动状态
     */
    private final MoveState moveState = new MoveState();

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getHeroAvatar() {
        return heroAvatar;
    }

    public void setHeroAvatar(String heroAvatar) {
        this.heroAvatar = heroAvatar;
    }

    public int getCurrHp() {
        return currHp;
    }

    public void setCurrHp(int currHp) {
        this.currHp = currHp;
    }

    public MoveState getMoveState() {
        return moveState;
    }
}
