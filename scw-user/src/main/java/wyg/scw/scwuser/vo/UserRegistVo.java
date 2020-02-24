package wyg.scw.scwuser.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ApiModel("用户注册信息类")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistVo {

    @ApiModelProperty("注册账号")
    private String loginacct;//
    @ApiModelProperty("注册密码")
    private String userpswd;//
    @ApiModelProperty("注册邮箱")
    private String email;//
    @ApiModelProperty("注册账号类型")
    private String usertype;//
    //验证码
    @ApiModelProperty("验证码")
    private String code;

}
