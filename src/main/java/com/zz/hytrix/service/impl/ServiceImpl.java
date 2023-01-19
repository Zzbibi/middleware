package com.zz.hytrix.service.impl;

import com.zz.hytrix.model.Request;
import com.zz.hytrix.model.Response;
import com.zz.hytrix.service.IService;

/**
 * @Author zhangzhen
 * @create 2023/1/19 21:43
 */
public class ServiceImpl implements IService {

    @Override
    public Response sayHello(Request request) {
        System.out.println("Hello world!");
        return new Response();
    }

}
