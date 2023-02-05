package com.zz.io.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @Author zhangzhen
 * @create 2023/2/5 20:21
 */
public class TimeClient {

    public static void main(String[] args) {
        int port = 8080;
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            socket = new Socket("127.0.0.1", port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println("QUERY TIME ORDER");
            String resp = in.readLine();
            System.out.println("Now is:" + resp);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseUtil.close(in, out, socket);
        }
    }

}
