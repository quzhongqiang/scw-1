package wyg.scw.scwproject.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import wyg.scw.scwproject.exception.OssTemplateException;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@Data
@ToString
public class OssTemplate {
    private  String scheme;
    private String endpoint;
    private String accessKeyId ;
    private String accessKeySecret;
    private String bucketName;

    public String uploadImg(MultipartFile file){
        //判断上传文件是否为空
        if (StringUtils.isEmpty(file)){
            throw new OssTemplateException("没有上传文件");
        }
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(scheme+endpoint, accessKeyId, accessKeySecret);
        //判断bucket是否存在
        boolean bucketExist = ossClient.doesBucketExist(bucketName);
        if (!bucketExist){
            ossClient.createBucket(bucketName);
        }

        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            String fileName = UUID.randomUUID().toString().replace("_","") + file.getOriginalFilename();
//            String filePath = new DateTime().toString("yyyy/MM//dd");
//            fileName = filePath + "/" + fileName;
            log.debug("fileName:" + fileName);
            ossClient.putObject(bucketName,fileName , inputStream);
            //http://wygoss.http://oss-cn-shanghai.aliyuncs.com/2020/02//21/91f10322-f2e6-4696-9c03-e2d353919fd09ac1d12aa32d7375a41780f9 - 副本.jpg
            String urlStr = "https://" + bucketName + "." + endpoint + "/" + fileName;
            // 关闭OSSClient。
            ossClient.shutdown();
            return urlStr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
