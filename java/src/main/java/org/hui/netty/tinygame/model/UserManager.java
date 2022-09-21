package org.hui.netty.tinygame.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户管理器
 */
public final class UserManager {
    public static final Logger LOGGER = LoggerFactory.getLogger(UserManager.class);
    /**
     * 用户字典
     */
    public static final Map<Integer, User> USER_MAP = new HashMap<>();

    private UserManager() {
    }

    /**
     * add new user to USER_MAP.
     *
     * @param user new user
     */
    static public void addUser(User user) {
        if (null != user) {
            USER_MAP.put(user.getUserId(), user);
        }
    }

    /**
     * remove user by userId.
     *
     * @param userId user's id
     */
    static public void removeUserById(int userId) {
        USER_MAP.remove(userId);
    }

    static public Collection<User> userCollection() {
        return USER_MAP.values();
    }

}
