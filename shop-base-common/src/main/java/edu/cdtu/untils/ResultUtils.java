package edu.cdtu.untils;

import edu.cdtu.status.StatusCode;
//数据返回处理工具类
//当方法名字相同为方法复用，提供什么信息返回什么信息
public class ResultUtils {
//    实例化resultvo方法
    public static ResultVo Vo(String msg, int code, Object data) {
        return new ResultVo(msg, code, data);
    }
    /**
     *
     成功无参数返回
     * @return
     */
    public static ResultVo succcess() {
        return Vo(null, StatusCode.SUCCESS_CODE, null);
  }
//  成功返回信息
    public static ResultVo success(String msg){
        return Vo(msg,StatusCode.SUCCESS_CODE,null);
   }
    /**
     *
     返回带数据
//     * @param msg
//     * @param data
     * @return
     */
    public static ResultVo success(String msg,Object data){
        return Vo(msg,StatusCode.SUCCESS_CODE,data);
   }
//   成功返回信息，状态码，数据
    public static ResultVo success(String msg,int code,Object data){
        return Vo(msg,code,data);
   }

    /**
     *
     错误返回
     * @return
     */
//    单独返回错误码
    public static ResultVo error(){
        return Vo(null,StatusCode.ERROR_CODE,null);
  }
//  返回错误信息
    public static ResultVo error(String msg){
        return Vo(msg,StatusCode.ERROR_CODE,null);
   }
//   返回错误信息，状态码，数据
    public static ResultVo error(String msg,int code,Object data){
        return Vo(msg,code,data);
   }
//   错误返回信息，和状态码
    public static ResultVo error(String msg,int code){
        return Vo(msg,code,null);
   }
//   错误返回信息和数据
    public static ResultVo error(String msg,Object data){
        return Vo(msg, StatusCode.ERROR_CODE,data);
    }
}
