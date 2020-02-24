package wyg.scw.scwuser.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import wyg.scw.scwuser.bean.TMember;
import wyg.scw.scwuser.bean.TMemberExample;
import wyg.scw.scwuser.exception.UserAcctException;
import wyg.scw.scwuser.mapper.TMemberMapper;
import wyg.scw.scwuser.service.UserService;
import wyg.scw.scwuser.vo.UserRegistVo;
import wyg.scw.scwuser.vo.UserRespVo;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private TMemberMapper memberMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void doRegist(UserRegistVo userRegistVo) {
        TMemberExample example = new TMemberExample();
        example.createCriteria().andLoginacctEqualTo(userRegistVo.getLoginacct());
        List<TMember> members = memberMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(members)){
            throw new UserAcctException("账户已存在");

        }
        TMember member = new TMember();
        userRegistVo.setUserpswd(passwordEncoder.encode(userRegistVo.getUserpswd()));
        BeanUtils.copyProperties(userRegistVo,member);
        member.setUsername(member.getLoginacct());
        member.setAuthstatus("0");
        int i = memberMapper.insertSelective(member);
        if (i==0){
            throw new UserAcctException("添加失败");
        }
    }

    @Override
    public UserRespVo doLogin(String loginacct, String userpswd) {
        TMemberExample example = new TMemberExample();
        example.createCriteria().andLoginacctEqualTo(loginacct);
        List<TMember> members = memberMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(members) || members.size()>1){
            throw new UserAcctException("账户错误");
        }
        TMember member = members.get(0);
        boolean matches = passwordEncoder.matches(userpswd, member.getUserpswd());
        if (!matches){
            throw  new UserAcctException("密码错误");
        }
        UserRespVo userRespVo = new UserRespVo();
        BeanUtils.copyProperties(member,userRespVo);
        return userRespVo;
    }
}
