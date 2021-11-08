package practice.druidpractice.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import practice.druidpractice.po.User;

/**
 * @author junyangwei
 * @date 2021-11-08
 */
@Repository
public interface UserMapper {
    /**
     * 查询用户信息
     * @param id 用户ID
     * @return 用户信息
     */
    User selectUser(int id);

    /**
     * 写入用户信息
     * @param user 用户类对象
     * @return int
     */
    int insertUser(User user);

    /**
     * 删除用户信息
     * @param id 用户ID
     * @return int
     */
    int deleteUser(int id);

    /**
     * 更新用户信息
     * @param id 用户ID
     * @param nickname 昵称
     * @return int
     */
    int updateUser(@Param("id") int id,
                   @Param("nickname") String nickname);
}
