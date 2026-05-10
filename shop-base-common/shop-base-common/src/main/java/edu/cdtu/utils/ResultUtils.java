package edu.cdtu.utils;

import edu.cdtu.status.StatusCode;

/**
 * 数据返回工具类
 */
public class ResultUtils {
    /**
     * 无参数返回
     *
     * @return
     */
    public static ResultVo Vo(String msg, int code, Object data) {
        return new ResultVo(msg, code, data);
    }

    //    成功方法
    public static ResultVo succcess() {
        // 无参数。
        // 该方法返回一个带有默认成功代码的 ResultVo 对象。
        return Vo(null, StatusCode.SUCCESS_CODE, null);
        // 传入 null 的 msg、StatusCode 类中的 SUCCESS_CODE 常量，以及 null 的 data，

    }

    public static ResultVo success(String msg) {
        // 接受一个参数：msg。
        // 该方法返回一个带有提供成功消息和默认成功代码的 ResultVo 对象。
        return Vo(msg, StatusCode.SUCCESS_CODE, null);
        // 传入提供的 msg、StatusCode 类中的 SUCCESS_CODE 常量，以及 null 的 data，

    }

    public static ResultVo success(String msg, Object data) {
        // 接受两个参数：msg 和 data。
        // 该方法返回一个带有提供成功消息、默认成功代码和数据的 ResultVo 对象。
        return Vo(msg, StatusCode.SUCCESS_CODE, data);
        // 传入提供的 msg、StatusCode 类中的 SUCCESS_CODE 常量，以及 data 参数，

    }

    public static ResultVo success(String msg, int code, Object data) {
        // 接受三个参数：msg、code 和 data。
        // 该方法返回一个带有提供成功消息、成功代码和数据的 ResultVo 对象。
        return Vo(msg, code, data);
        // 传入提供的 msg、code 和 data 参数，

    }


    //    失败方法
    public static ResultVo error() {
        // 定义一个名为 error 的公共静态方法，无参数。
        // 该方法返回一个带有默认错误代码的 ResultVo 对象。
        return Vo(null, StatusCode.ERROR_CODE, null);
        //传入 null 的 msg、StatusCode 类中的 ERROR_CODE 常量，以及 null 的 data，

    }

    public static ResultVo error(String msg) {
        // 接受一个参数：msg。
        // 该方法返回一个带有提供错误消息和默认错误代码的 ResultVo 对象。
        return Vo(msg, StatusCode.ERROR_CODE, null);
        // 传入提供的 msg、StatusCode 类中的 ERROR_CODE 常量，以及 null 的 data，

    }

    public static ResultVo error(String msg, int code, Object data) {
        // 接受三个参数：msg、code 和 data。
        // 该方法返回一个带有提供错误消息、错误代码和数据的 ResultVo 对象。
        return Vo(msg, code, data);
        // 传入提供的 msg、code 和 data 参数，

    }

    public static ResultVo error(String msg, int code) {
        // 接受两个参数：msg 和 code。
        // 该方法返回一个带有提供错误消息和错误代码（但无数据）的 ResultVo 对象。
        return Vo(msg, code, null);
        // 传入提供的 msg、code 参数以及 null 的 data，

    }

    public static ResultVo error(String msg, Object data) {
        // 接受两个参数：msg 和 data。
        // 该方法返回一个带有提供错误消息、默认错误代码和数据的 ResultVo 对象。
        return Vo(msg, StatusCode.ERROR_CODE, data);
        // 传入提供的 msg、StatusCode 类中的 ERROR_CODE 常量，以及 data 参数，

    }
}