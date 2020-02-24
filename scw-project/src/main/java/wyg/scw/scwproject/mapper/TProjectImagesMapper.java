package wyg.scw.scwproject.mapper;


import org.apache.ibatis.annotations.Param;
import wyg.scw.scwproject.bean.TProjectImages;
import wyg.scw.scwproject.bean.TProjectImagesExample;

import java.util.List;

public interface TProjectImagesMapper {
    long countByExample(TProjectImagesExample example);

    int deleteByExample(TProjectImagesExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TProjectImages record);

    int insertSelective(TProjectImages record);

    List<TProjectImages> selectByExample(TProjectImagesExample example);

    TProjectImages selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TProjectImages record, @Param("example") TProjectImagesExample example);

    int updateByExample(@Param("record") TProjectImages record, @Param("example") TProjectImagesExample example);

    int updateByPrimaryKeySelective(TProjectImages record);

    int updateByPrimaryKey(TProjectImages record);
    //批量插入项目图片的方法
	void batchInsertProjectImags(@Param("projectImgs") List<TProjectImages> projectImgs);
}