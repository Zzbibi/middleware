package com.zz.io.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @Author zhangzhen
 * @create 2023/2/5 20:33
 */
public class CloseUtil {

    private CloseUtil() {}

    public static void close(BufferedReader in, PrintWriter out, Socket socket) {
        if (null != in) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (null != out) {
            out.close();
        }
        if (null != socket) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
