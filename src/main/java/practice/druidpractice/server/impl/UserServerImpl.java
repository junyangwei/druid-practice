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
}
