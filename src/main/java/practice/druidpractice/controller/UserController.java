package practice.druidpractice.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.druidpractice.server.UserServer;

import javax.annotation.Resource;

/**
 * @author junyangwei
 * @date 2021-11-08
 */
@RestController
@RequestMapping("user")
public class UserController {
    @Resource(name = "userServer")
    private UserServer userServer;

    @GetMapping("/get")
    public Object getUser(@Param("id") int id) {
        return this.userServer.getUser(id);
    }

    @GetMapping("/insert")
    public Object insertUser() {
        return this.userServer.insertUser();
    }

    @GetMapping("/delete")
    public Object deleteUser(@Param("id") int id) {
        return this.userServer.deleteUser(id);
    }

    @GetMapping("/update")
    public Object updateUser(@Param("id") int id, @Param("nickname") String nickname) {
        return this.userServer.updateUser(id, nickname);
    }

}
