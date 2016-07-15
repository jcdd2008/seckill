package com.seckill;

import java.io.File;

/**
 * Created by niemengquan on 2016/7/14.
 */
public class Test {
    public  static void main(String[] args){
        File file=new File("D:/dsl");
        for(File f:file.listFiles()){
            System.out.println("11");
        }
        boolean delete = file.delete();
        System.out.print(delete );
        System.out.print(file.exists() );
    }
}
