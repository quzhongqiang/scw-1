package wyg.scw.scwproject.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import wyg.scw.scwproject.bean.TProjectTag;
import wyg.scw.scwproject.bean.TProjectTagExample;

public interface TProjectTagMapper {
    long countByExample(TProjectTagExample example);

    int deleteByExample(TProjectTagExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TProjectTag record);

    int insertSelective(TProjectTag record);

    List<TProjectTag> selectByExample(TProjectTagExample example);

    TProjectTag selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TProjectTag record, @Param("example") TProjectTagExample example);

    int updateByExample(@Param("record") TProjectTag record, @Param("example") TProjectTagExample example);

    int updateByPrimaryKeySelective(TProjectTag record);

    int updateByPrimaryKey(TProjectTag record);
}