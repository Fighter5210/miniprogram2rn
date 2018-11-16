import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by WANGDUOYUAN on 2018/11/16.
 */
public class MainConvert {
    private static  final String rnFilePath = "/Users/wangduoyuan/工作/金山云/代码备份/小程序转RN/exchange.js";
    private static  final String jsFilePath = "/Users/wangduoyuan/工作/金山云/代码备份/小程序转RN/childrenFollowDetail.js";
    private static  final String wxmlFilePath = "/Users/wangduoyuan/工作/金山云/代码备份/小程序转RN/childrenFollowDetail.wxml";
    private static  final String wxssFilePath = "/Users/wangduoyuan/工作/金山云/代码备份/小程序转RN/childrenFollowDetail.wxss";

    public static void getRNFile(String filePath){
        try {
            String encoding="utf8";
            File file=new File(filePath);
            StringBuffer sb = new StringBuffer();
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                boolean flag = false;
                while((lineTxt = bufferedReader.readLine()) != null){

                    sb.append(lineTxt + "\n");

                    if(lineTxt.trim().contains("componentDidMount()")){
                        sb.append(getOnLoadAndOnReady(jsFilePath));
                    }

                    if(lineTxt.trim().contains("return")){
                        sb.append("'"+toUpperCaseHeadChar(getWxmlOrWxss(wxmlFilePath))+"'");
                        flag=true;
                    }

                    if("}".equals(lineTxt.trim())&&flag==true){
                        sb.append(getOnUnLoad(jsFilePath));
                        flag=false;
                    }

                    if(lineTxt.trim().contains("styles")){
                        sb.append("'"+getWxmlOrWxss(wxssFilePath)+"'");
                    }

                }
                System.out.println(sb.toString());
                read.close();
            }else{
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }

    }

    public static String getOnLoadAndOnReady(String filePath){

        StringBuffer sb = new StringBuffer();
        try {
            String encoding="utf8";
            File file=new File(filePath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                boolean flag=false;
                while((lineTxt = bufferedReader.readLine()) != null){
                    if("},".equals(lineTxt.trim())){
                        flag=false;
                    }
                    if(flag){
                        sb.append(lineTxt+"\n");
                    }
                    if(lineTxt.trim().contains("onLoad:")){
                        flag=true;
                    }

                    if(lineTxt.trim().contains("onReady:")){
                        flag=true;
                    }
                }
                read.close();
            }else{
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String getOnUnLoad(String filePath){

        StringBuffer sb = new StringBuffer();
        try {
            String encoding="utf8";
            File file=new File(filePath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                boolean flag=false;
                while((lineTxt = bufferedReader.readLine()) != null){
                    if("},".equals(lineTxt.trim())){
                        flag=false;
                    }
                    if(flag){
                        sb.append(lineTxt+"\n");
                    }
                    if(lineTxt.trim().contains("onUnLoad:")){
                        flag=true;
                    }
                }
                read.close();
            }else{
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String getWxmlOrWxss(String filePath){

        StringBuffer sb = new StringBuffer();
        try {
            String encoding="utf8";
            File file=new File(filePath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                    sb.append(lineTxt);
                }
                read.close();
            }else{
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String toUpperCaseHeadChar(String str){
        String regex = "(<|</)[a-z].*?";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find())
        {
            matcher.appendReplacement(sb,  matcher.group().toUpperCase());
        }
        matcher.appendTail(sb);
        return  sb.toString();
    }



    public static  void main(String[] str){
        getRNFile(rnFilePath);
    }
}