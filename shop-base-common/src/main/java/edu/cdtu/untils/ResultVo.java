package edu.cdtu.untils;
import lombok.AllArgsConstructor;
import lombok.Data;

//data用于生成get和set方法
//AllArgsConstructor生成有参构造
@Data
@AllArgsConstructor
public class ResultVo<T> {
//        返回的信息
        private String msg;
        private int code;
        private T data;
}
