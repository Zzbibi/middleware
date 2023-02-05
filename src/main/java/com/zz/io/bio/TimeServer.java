package com.zz.io.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author zhangzhen
 * @create 2023/2/5 19:58
 */
public class TimeServer {

    public static void main(String[] args) throws IOException {
        int port = 8080;
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("The time server is start on port:" + port);

            Socket socket = null;
            TimeServerHandlerExecutePool executePool =
                    new TimeServerHandlerExecutePool(10, 20, 1000);
            while (true) {
                /**
                 * 接受客户端连接
                 */
                socket = serverSocket.accept();
                executePool.execute(new TimeServerHandler(socket));
            }
        } finally {
            if (null != serverSocket) {
                serverSocket.close();
            }
            System.out.println("The time server is closed");
        }
    }

}
