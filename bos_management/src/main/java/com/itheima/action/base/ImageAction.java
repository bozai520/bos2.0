package com.itheima.action.base;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.domain.base.Promotion;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class ImageAction extends ActionSupport implements ModelDriven<Promotion> {

	//模型驱动
	private Promotion promotion = new Promotion();
	@Override
	public Promotion getModel() {
		// TODO Auto-generated method stub
		return promotion;
	}
	
	
	//上传图片
	private File imgFile;
	private String imgFileFileName;
	private String imgFileContentType;
	
	/**
	 * 上传图片
	 * @return
	 * @throws IOException 
	 */
	@SuppressWarnings("all")
	@Action(value="image_upload",results={@Result(name="success",type="json")})
	public String uploadImage() throws IOException{
		//获取到绝对路径
		//获取到的是项目名称
		//获取到绝对路径
		String realPath = ServletActionContext.getServletContext().getRealPath("/upload");
		System.out.println("我是绝对路径：：：："+realPath);
		String contextPath = ServletActionContext.getRequest().getContextPath()+"/upload/";		
		System.out.println("我是相对路径"+contextPath);
		
		//生成随机的文件名称
		String uuid = UUID.randomUUID().toString();
		
		String ext = imgFileFileName.substring(imgFileFileName.lastIndexOf("."));
		
		//生成的文件名：
		String filename = uuid + ext;
		
		//获取到保存路径
		String savePath = realPath +"/"+ filename;
		
		//或者直接获取到File对象
		File file = new File(realPath, filename);
		
		//文件的上传保存
		FileUtils.copyFile(imgFile, file);
		
		//通知浏览器文件上传成功
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("error",0);
		//返回相对路径
		map.put("url",contextPath+"/"+filename);
		
		//将数据压栈
		ActionContext.getContext().getValueStack().push(map);
		
		return SUCCESS;
	}
	/**
	 * 管理图片
	 * @return
	 */
	@Action(value = "image_manage", results = { @Result(name = "success", type = "json") })
	public String manageImg(){
		
		//根目录路径，可以指定绝对路径，比如 /var/www/upload/
		String rootPath = ServletActionContext.getServletContext().getRealPath("/") + "upload/";
		
		//根目录URL，可以指定绝对路径，比如 http://www.yoursite.com/upload/
		String rootUrl  = ServletActionContext.getRequest().getContextPath() + "/upload/";
		
		//图片扩展名
		String[] fileTypes = new String[]{"gif", "jpg", "jpeg", "png", "bmp"};
		
		//获取到文件夹地下的所有文件
		File[] listFiles = new File(rootPath).listFiles();
		
		// 遍历目录取的文件信息
		List<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>();
		
		//遍历所有的文件
		if(listFiles != null){
			for (File file : listFiles) {
				Map<String, Object> hash = new HashMap<String, Object>();
				String fileName = file.getName();
				if (file.isDirectory()) {
					hash.put("is_dir", true);
					hash.put("has_file", (file.listFiles() != null));
					hash.put("filesize", 0L);
					hash.put("is_photo", false);
					hash.put("filetype", "");
				} else if (file.isFile()) {
					String fileExt = fileName.substring(
							fileName.lastIndexOf(".") + 1).toLowerCase();
					hash.put("is_dir", false);
					hash.put("has_file", false);
					hash.put("filesize", file.length());
					hash.put("is_photo", Arrays.<String> asList(fileTypes)
							.contains(fileExt));
					hash.put("filetype", fileExt);
				}
				hash.put("filename", fileName);
				hash.put("datetime",
						new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file
								.lastModified()));
				fileList.add(hash);
			}
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("moveup_dir_path", "");
		result.put("current_dir_path", rootPath);
		result.put("current_url", rootUrl);
		result.put("total_count", fileList.size());
		result.put("file_list", fileList);
		ActionContext.getContext().getValueStack().push(result);

		return SUCCESS;
	}

	//提供getter和setter方法
	public File getImgFile() {
		return imgFile;
	}
	
	public void setImgFile(File imgFile) {
		this.imgFile = imgFile;
	}
	
	public String getImgFileFileName() {
		return imgFileFileName;
	}
	
	public void setImgFileFileName(String imgFileFileName) {
		this.imgFileFileName = imgFileFileName;
	}
	
	public String getImgFileContentType() {
		return imgFileContentType;
	}
	
	public void setImgFileContentType(String imgFileContentType) {
		this.imgFileContentType = imgFileContentType;
	}
}
