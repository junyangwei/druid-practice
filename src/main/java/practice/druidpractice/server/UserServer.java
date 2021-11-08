package practice.druidpractice.server;

import practice.druidpractice.po.User;

/**
 * @author junyangwei
 * @date 2021-11-08
 */
public interface UserServer {
    User getUser(int id);

    /**
     * 写入数据测试
     */
    int insertUser();

    /**
     * 删除数据测试
     * @param id 用户ID
     */
    int deleteUser(int id);

    /**
     * 更新数据测试
     */
    int updateUser(int id, String nickname);
}
