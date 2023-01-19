package com.zz.hytrix;

import com.zz.hytrix.command.MyHystrixCommand;
import com.zz.hytrix.model.Request;
import com.zz.hytrix.service.impl.ServiceImpl;

/**
 * @Author zhangzhen
 * @create 2023/1/19 21:37
 */
public class Demo {

    public static void main(String[] args) {

        MyHystrixCommand myHystrixCommand = new MyHystrixCommand(new ServiceImpl(), new Request());
        myHystrixCommand.execute();

    }

}
