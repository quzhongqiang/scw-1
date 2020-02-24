package wyg.scw.scwuser.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import wyg.scw.scwuser.bean.TMemberProjectFollow;
import wyg.scw.scwuser.bean.TMemberProjectFollowExample;

public interface TMemberProjectFollowMapper {
    long countByExample(TMemberProjectFollowExample example);

    int deleteByExample(TMemberProjectFollowExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TMemberProjectFollow record);

    int insertSelective(TMemberProjectFollow record);

    List<TMemberProjectFollow> selectByExample(TMemberProjectFollowExample example);

    TMemberProjectFollow selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TMemberProjectFollow record, @Param("example") TMemberProjectFollowExample example);

    int updateByExample(@Param("record") TMemberProjectFollow record, @Param("example") TMemberProjectFollowExample example);

    int updateByPrimaryKeySelective(TMemberProjectFollow record);

    int updateByPrimaryKey(TMemberProjectFollow record);
}