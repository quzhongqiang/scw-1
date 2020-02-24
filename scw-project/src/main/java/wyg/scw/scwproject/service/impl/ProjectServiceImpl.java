package wyg.scw.scwproject.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wyg.scw.common.utils.AppDateUtils;
import wyg.scw.scwproject.bean.TProject;
import wyg.scw.scwproject.bean.TProjectImages;
import wyg.scw.scwproject.mapper.TProjectImagesMapper;
import wyg.scw.scwproject.mapper.TProjectMapper;
import wyg.scw.scwproject.service.ProjectService;
import wyg.scw.scwproject.vo.ProjectRedisStorageVo;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private TProjectMapper projectMapper;


    @Override
    public void createProject(ProjectRedisStorageVo bigVo) {
        //project
        TProject project = new TProject();
        BeanUtils.copyProperties(bigVo,project);
        project.setMoney((long)bigVo.getMoney());
        project.setStatus("0"); ////0 - 即将开始， 1 - 众筹中， 2 - 众筹成功， 3 - 众筹失败
        project.setDeploydate(AppDateUtils.getFormatTime());
        projectMapper.insertSelective(project);
        //project_images
        //头图
        List<TProjectImages> list = new ArrayList<>();
        TProjectImages headerImages = new TProjectImages();
        headerImages.setImgtype((byte) 0);
        headerImages.setImgurl(bigVo.getHeaderImage());
        headerImages.setProjectid(project.getId());
        list.add(headerImages);
        List<String> detailsImages = bigVo.getDetailsImage();
        for (String detailsImage : detailsImages) {
            TProjectImages images = new TProjectImages();
            images.setProjectid(project.getId());
            images.setImgtype((byte) 1);
            images.setImgurl(detailsImage);
            list.add(images);
        }








        projectImagesMapper.batchInsert(list);






        //project_tag
        //project_type
        //return


        //法人信息








    }
}
