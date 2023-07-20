package com.oyx.campus.controller;

import com.oyx.campus.bean.Msg;
import com.oyx.campus.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/file")
public class FileController {

	// 设置固定的日期格式
  /*  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");*/

    // 日志打印
    private Logger log = LoggerFactory.getLogger("FileController");
    // 文件上传 （可以多文件上传）
    @PostMapping("/upload")
    public Msg fileUploads(HttpServletRequest request, @RequestParam("file") MultipartFile[] file) throws IOException {
        System.out.println("图片个数："+file.length);
        String[] url=new String[file.length];
        int i=0;
        //循环保存文件
        for (MultipartFile f : file) {
            // 获取上传的文件名称
            String originName = f.getOriginalFilename();
            //指定图片存放路径
            String realPath=request.getServletContext().getRealPath("/")+"upload";
            System.out.println("存放图片路径的realPath为>>>>>>"+realPath);
            //给图片重命名
            String hz = originName.substring(originName.lastIndexOf("."));
            String uuid = UUID.randomUUID().toString();
            String newName=uuid+hz;
            //这里使用文件上传工具类实现上传
            if (FileUtils.upload(f, realPath, newName)) {
                // 打印日志
                log.info("上传成功，当前上传的文件保存在 {}",realPath);
               if(i<file.length){
                   url[i]=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+ request.getContextPath()+"/upload/"+newName;
                   System.out.println("url"+i+":"+url[i].toString());
                   i++;
               }

            }else{
                return Msg.fail("上传错误");
            }
        }
        // 自定义返回的统一的 JSON 格式的数据，可以直接返回这个字符串也是可以的。
        return Msg.success("上传成功").add("url",url);
    }

   /* @RequestMapping("/test/upload")
    public Msg fileUploadTest(@RequestParam("photo")MultipartFile photo, HttpSession session) throws IOException {
        System.out.println(photo.getName());//获取<input type="file" name="photo"><br>中name的属性值
        System.out.println(photo.getOriginalFilename());
        //获取文件名

        String originalFilename = photo.getOriginalFilename();
        //使用UUID随机生成图片名
        //获取图片后缀名
        String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString();
        System.out.println("UUID"+UUID.randomUUID());
        String newPhotoPath=uuid+substring;
        System.out.println(newPhotoPath);

        ServletContext servletContext = session.getServletContext();
        //设置上传路径保存的位置
        String path = servletContext.getRealPath("photo");
        File file = new File(path);
        if(!file.exists()){
            file.mkdir();
        }
        String photoRealPath=path+File.separator+newPhotoPath;
        try {
            // 上传的文件被保存了
            photo.transferTo(new File(photoRealPath));
            // 打印日志
            log.info("上传成功，当前上传的文件保存在 {}",photoRealPath);
            // 自定义返回的统一的 JSON 格式的数据，可以直接返回这个字符串也是可以的。
            return Msg.success("上传成功");
        } catch (IOException e) {
            log.error(e.toString());
        }
        // 待完成 —— 文件类型校验工作
        return Msg.fail("上传错误");

    }*/
}
