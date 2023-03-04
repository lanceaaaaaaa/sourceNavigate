package com.luban.test;
import java.io.*;
public class RecoveryData {

    public final static String REGEX_ALL_BRACKETS = "\\<.*?\\>|\\(.*?\\)|\\（.*?\\）|\\[.*?\\]|\\【.*?\\】|\\{.*?\\}";

    public static void main(String[] args) throws Exception

    {

        File file = new File("D:\\info.log");


        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;

        while ((st = br.readLine()) != null){
            if(st.contains("INSERT into note_info")){
                //System.out.println(st);
                String st1 = br.readLine();
                //System.out.println(st1);
                String st2 = st1.replaceAll(REGEX_ALL_BRACKETS,"").replaceAll("==> Parameters: ","").replaceAll(" ","");
                String[] split = st2.split(",");
                StringBuilder sql = new StringBuilder("INSERT into note_info(id,noteId,noteContent,noteFrom,notex,notey,sourceId,targetId,rootId,contentEleId) VALUES(");
                for(int i=0; i<split.length; i++){
                    String str = split[i].replaceAll(",","，");
                    if(i==4 || i==5){
                        sql.append(split[i]);
                    }else {
                        sql.append("'").append(split[i]).append("'");
                    }
                    if(i < split.length -1){
                        sql.append(",");
                    }
                }
                sql.append(");");
                System.out.println(sql.toString());
            }
        }
    }
}
