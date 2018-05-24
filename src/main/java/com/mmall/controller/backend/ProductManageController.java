package com.mmall.controller.backend;

import com.google.common.collect.Maps;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServiceResponse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IFileService;
import com.mmall.service.IProductService;
import com.mmall.service.IUserService;
import com.mmall.util.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/admin/product")
public class ProductManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IProductService iProductService;

    @Autowired
    private IFileService iFileService;
    /**
     * 产品的新增或者更新
     *
     * @param session
     * @param product
     * @return
     */
    @RequestMapping("save.do")
    @ResponseBody
    public ServiceResponse saveProduct(HttpSession session, Product product) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户不存在，登录之后再做此操作");
        }
        if (iUserService.checkAdmin(user).isSuccess()) {
            //填充增加或者更新产品的业务逻辑
            return iProductService.saveOrUpdateProduct(product);
        }
        return ServiceResponse.createByErrorMessage("无权限操作");
    }

    /**
     * 商品上下架
     * @param session
     * @param productId
     * @param status
     * @return
     */
    @RequestMapping("status.do")
    @ResponseBody
    public ServiceResponse setStatusSale(HttpSession session,Integer productId,Integer status){
     User user = (User)session.getAttribute(Const.CURRENT_USER);
     if(user == null){
         return ServiceResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录再做此操作");
     }
     if(iUserService.checkAdmin(user).isSuccess()){
         //更新产品状态
         return iProductService.updateStatusSale(productId,status);
     }
     return ServiceResponse.createByErrorMessage("无权限做此操作");
    }

    /**
     * 获取商品详情detail
     * @param session
     * @param productId
     * @return
     */
    @RequestMapping("detail.do")
    @ResponseBody
    public ServiceResponse getProductDetail(HttpSession session,Integer productId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServiceResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"请先登录再做此操作");
        }
        if(iUserService.checkAdmin(user).isSuccess()){
            //获取商品详情返回的列表
            return iProductService.manageProductDetail(productId);
        }
        return ServiceResponse.createByErrorMessage("无权限做此操作");
    }

    /**
     * 获取商品列表
     * @param session
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("list.do")
    @ResponseBody
    public ServiceResponse getProductList(HttpSession session,
                                          @RequestParam(value = "pageNum",defaultValue ="1") Integer pageNum,
                                          @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){

        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServiceResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        if(iUserService.checkAdmin(user).isSuccess()){
            //查询商品列表
            return iProductService.getProductList(pageNum,pageSize);
        }
        return ServiceResponse.createByErrorMessage("无权限做此操作");
    }

    /**
     * 商品搜索
     * @param session
     * @param productId
     * @param productName
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("search.do")
    @ResponseBody
    public ServiceResponse productSearch(HttpSession session,Integer productId,
                                         String productName,@RequestParam(value = "pageNum",defaultValue ="1") Integer pageNum,
                                         @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServiceResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        if(iUserService.checkAdmin(user).isSuccess()){
            //填充业务逻辑
            return iProductService.productSearch(productId,productName,pageNum,pageSize);
        }
        return ServiceResponse.createByErrorMessage("无权限做此操作");
    }

    @RequestMapping("upload.do")
    @ResponseBody
    public ServiceResponse upload(HttpSession session,
                                  @RequestParam(value = "upload_file",required = false) MultipartFile file,
                                  HttpServletRequest request){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServiceResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        if(iUserService.checkAdmin(user).isSuccess()){
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetName = iFileService.upload(file,path);
            String url = PropertiesUtil.getProperties("ftp.server.http.prefix")+targetName;

            Map fileMap = Maps.newHashMap();
            fileMap.put("uri",targetName);
            fileMap.put("url",url);

            return ServiceResponse.createBySuccessData(fileMap);
        }
        return ServiceResponse.createByErrorMessage("无权限做此操作");
    }

    @RequestMapping("richtext_img_upload.do")
    @ResponseBody
    public Map richtextImgUpload(HttpSession session,
                                 @RequestParam(value = "upload_file",required = false) MultipartFile file,
                                 HttpServletRequest request,
                                 HttpServletResponse response){
        Map resultMap = Maps.newHashMap();
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            resultMap.put("success",false);
            resultMap.put("msg","请登录再做此操作");
            return resultMap;
        }
        if(iUserService.checkAdmin(user).isSuccess()){
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetName = iFileService.upload(file,path);
            if(StringUtils.isBlank(targetName)){
                resultMap.put("success",false);
                resultMap.put("msg","上传文件失败");
                return resultMap;
            }
            String url = PropertiesUtil.getProperties("ftp.server.http.prefix")+targetName;
            resultMap.put("success",true);
            resultMap.put("msg","上传成功");
            resultMap.put("file_path",url);
            response.addHeader("Access-Control-Allow-Headers","X-File-Name");
            return resultMap;
        }
        resultMap.put("success",false);
        resultMap.put("msg","无权限做此操作");
        return resultMap;
    }
}
