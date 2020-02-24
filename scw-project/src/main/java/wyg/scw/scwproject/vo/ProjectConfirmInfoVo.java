package wyg.scw.scwproject.vo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProjectConfirmInfoVo extends BaseVo{
    private String projectToken ; //项目token
    private String account;

    private String idcard;
    private Integer type;//提交时的类型：  0 提交 ， 1表示保存草稿
}

