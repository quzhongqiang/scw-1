package wyg.scw.scwproject.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import wyg.scw.scwproject.bean.TProjectInitiator;
import wyg.scw.scwproject.bean.TProjectInitiatorExample;

public interface TProjectInitiatorMapper {
    long countByExample(TProjectInitiatorExample example);

    int deleteByExample(TProjectInitiatorExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TProjectInitiator record);

    int insertSelective(TProjectInitiator record);

    List<TProjectInitiator> selectByExample(TProjectInitiatorExample example);

    TProjectInitiator selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TProjectInitiator record, @Param("example") TProjectInitiatorExample example);

    int updateByExample(@Param("record") TProjectInitiator record, @Param("example") TProjectInitiatorExample example);

    int updateByPrimaryKeySelective(TProjectInitiator record);

    int updateByPrimaryKey(TProjectInitiator record);
}