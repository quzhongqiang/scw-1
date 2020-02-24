package wyg.scw.scwuser.service;

import wyg.scw.scwuser.vo.UserRegistVo;
import wyg.scw.scwuser.vo.UserRespVo;

public interface UserService {
    void doRegist(UserRegistVo userRegistVo);

    UserRespVo doLogin(String loginacct, String userpswd);
}
