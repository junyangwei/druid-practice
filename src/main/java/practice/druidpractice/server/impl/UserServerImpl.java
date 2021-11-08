package practice.druidpractice.server.impl;

import org.springframework.stereotype.Service;
import practice.druidpractice.mapper.UserMapper;
import practice.druidpractice.po.User;
import practice.druidpractice.server.UserServer;

import javax.annotation.Resource;

/**
 * @author junyangwei
 * @date 2021-11-08
 */
@Service("userServer")
public class UserServerImpl implements UserServer {
    @Resource
    private UserMapper userMapper;

    @Override
    public User getUser(int id) {
        return this.userMapper.selectUser(id);
    }

    /**
     * 写入数据测试
     */
    @Override
    public int insertUser() {
        User user = new User();
        user.setUsername("test1108");
        user.setPassword("test1108password");
        user.setNickname("test1108nickname");
        user.setPhone("19000000100");
        return this.userMapper.insertUser(user);
    }

    /**
     * 删除数据测试
     * @param id 用户ID
     */
    @Override
    public int deleteUser(int id) {
        return this.userMapper.deleteUser(id);
    }

    /**
     * 更新数据测试
     */
    @Override
    public int updateUser(int id, String nickname) {
        return this.userMapper.updateUser(id, nickname);
    }
}
