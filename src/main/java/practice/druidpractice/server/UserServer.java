package practice.druidpractice.server;

import practice.druidpractice.po.User;

/**
 * @author junyangwei
 * @date 2021-11-08
 */
public interface UserServer {
    User getUser(int id);
}
