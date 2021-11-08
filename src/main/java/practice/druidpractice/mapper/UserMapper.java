package practice.druidpractice.mapper;

import org.springframework.stereotype.Repository;
import practice.druidpractice.po.User;

/**
 * @author junyangwei
 * @date 2021-11-08
 */
@Repository
public interface UserMapper {
    User selectUser(int id);
}
