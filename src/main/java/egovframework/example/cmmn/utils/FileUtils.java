package egovframework.example.cmmn.utils;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
 
import javax.servlet.http.HttpServletRequest;
 
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import egovframework.example.sample.service.SampleVO;
 
@Component("fileUtils")
public class FileUtils {
     
    public List<Map<String,Object>> parseInsertFileInfo(SampleVO vo, HttpServletRequest req) throws Exception{
    	
    	String regi_id = vo.getId();
    	
    	String filePath = req.getSession().getServletContext().getRealPath("/")+"\\fileUpload\\"+regi_id+"\\";
    	System.out.println("파일 주소1 : "+req.getSession().getServletContext().getContextPath()+"\\fileUpload\\"+regi_id+"\\");
    	System.out.println("파일 주소2 : "+req.getSession().getServletContext().getRealPath("/")+"\\fileUpload\\"+regi_id+"\\");
    	
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)req;
        Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
         
        MultipartFile multipartFile = null;
        String originalFileName = null;
        String originalFileExtension = null;
        String storedFileName = null;
         
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map<String, Object> listMap = null;
         
        File file = new File(filePath);
        if(file.exists() == false){
            file.mkdirs();
        }
         
        while(iterator.hasNext()){
            multipartFile = multipartHttpServletRequest.getFile(iterator.next());
            if(multipartFile.isEmpty() == false){
                originalFileName = multipartFile.getOriginalFilename();
                originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
                storedFileName = CommonUtils.getRandomString() + originalFileExtension;
                 
                file = new File(filePath + storedFileName);
                multipartFile.transferTo(file);
                 
                listMap = new HashMap<String,Object>();
                listMap.put("REGI_ID", regi_id);
                listMap.put("ORI_FNAME", originalFileName);
                listMap.put("STO_FNAME", storedFileName);
                listMap.put("FSIZE", multipartFile.getSize());
                list.add(listMap);
            }
        }
        return list;
    }
}
