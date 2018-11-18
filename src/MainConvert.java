import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by WANGDUOYUAN on 2018/11/16.
 */
public class MainConvert {
    private static  final String rnFilePath = "/Users/wangduoyuan/工作/金山云/代码备份/小程序转RN/exchange.js";
    private static  final String jsFilePath = "/Users/wangduoyuan/工作/金山云/代码备份/小程序转RN/import/childrenFollowDetail.js";
    private static  final String wxmlFilePath = "/Users/wangduoyuan/工作/金山云/代码备份/小程序转RN/import/childrenFollowDetail.wxml";
    private static  final String wxssFilePath = "/Users/wangduoyuan/工作/金山云/代码备份/小程序转RN/import/childrenFollowDetail.wxss";
    private static  final String outFile = "/Users/wangduoyuan/工作/金山云/代码备份/小程序转RN/export/childrenFollowDetail.js";
    private static  final String className = "childrenFollowDetail";

    public static void convert2RNFile(String filePath){
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
                    if(lineTxt.trim().contains("文件名首字母大写")){
                        lineTxt = lineTxt.replace("文件名首字母大写",toUpperCaseHeadChar(className,"^[a-z].*?"));
                    }
                    sb.append(lineTxt + "\n");

                    if(lineTxt.trim().contains("componentDidMount()")){
                        sb.append(getOnLoadAndOnReady(jsFilePath));
                    }

                    if(lineTxt.trim().contains("return")){
                        sb.append(replaceWxmlState(replaceWxmlStyle(toUpperCaseHeadChar(getWxmlOrWxss(wxmlFilePath),"(<|</)[a-z].*?"))));
                        flag=true;
                    }

                    if("}".equals(lineTxt.trim())&&flag==true){
                        sb.append(getOnUnLoad(jsFilePath));
                        flag=false;
                    }

                    if(lineTxt.trim().contains("styles")){
                        sb.append(replaceWxssChar2str(replaceWxssDisplay(replaceWxssRod2Hump(replaceWxssSize(replaceWxssStyle(getWxmlOrWxss(wxssFilePath)))))));
                    }

                }
                System.out.println(sb.toString());
                read.close();
                File outfile = new File(outFile);
                PrintStream ps = new PrintStream(new FileOutputStream(outfile));
                ps.println(sb.toString());// 往文件里写入字符串

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

    public static String toUpperCaseHeadChar(String str,String regex){
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


    public static String replaceWxmlStyle(String str){
        String regex = "(class=\".*?\")";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find())
        {
            System.out.println("classgroup:"+matcher.group());
            String style = matcher.group().substring(matcher.group().indexOf("\"")+1,matcher.group().lastIndexOf("\""));
            matcher.appendReplacement(sb,  "style={styles."+style+"}");
        }
        matcher.appendTail(sb);
        return  sb.toString();
    }

    public static String replaceWxmlState(String str){
        String regex = "\\{\\{.*?\\}\\}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find())
        {
            String key = matcher.group().substring(matcher.group().indexOf("{{")+2,matcher.group().lastIndexOf("}}"));
            //System.out.println("group:"+matcher.group().substring(matcher.group().indexOf("\"")+1,matcher.group().lastIndexOf("\""))+"\n");
            matcher.appendReplacement(sb,  "{{this.state."+key+"}}");
        }
        matcher.appendTail(sb);
        return  sb.toString();
    }

    public static String replaceWxssStyle(String str){
        String regex = "(.*?\\{*?\\})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find())
        {
            System.out.println("style-group:"+matcher.group());
            String key = matcher.group().substring(matcher.group().indexOf(".")+1,matcher.group().lastIndexOf("{"));
            String content = matcher.group().substring(matcher.group().lastIndexOf("{")+1,matcher.group().lastIndexOf("}"));
            content = content.replaceAll(";",",");
            matcher.appendReplacement(sb,  key+":{"+content+"},");
        }
        matcher.appendTail(sb);
        return  sb.toString();
    }

    public static String replaceWxssSize(String str){
        String regex = "(\\d+)rpx";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find())
        {
            System.out.println("size-group:"+matcher.group());
            String size = matcher.group().substring(0,matcher.group().lastIndexOf("rpx"));
            matcher.appendReplacement(sb,  Integer.parseInt(size)/2+"");
        }
        matcher.appendTail(sb);
        return  sb.toString();
    }

    public static String replaceWxssRod2Hump(String str){
        String regex = "(-[a-z]*:)|(-[A-Z]*:)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find())
        {
            System.out.println("rod-group:"+matcher.group());
            String c = matcher.group().substring(matcher.group().indexOf("-")+1,matcher.group().length());
            c = Character.toUpperCase(c.charAt(0))+c.substring(1);
            matcher.appendReplacement(sb,  c);
        }
        matcher.appendTail(sb);
        return  sb.toString();
    }

    public static String replaceWxssDisplay(String str){
        String regex = "display:\\s*flex";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find())
        {
            System.out.println("display-group:"+matcher.group());
            String flex = matcher.group().substring(matcher.group().indexOf(":")+1,matcher.group().length());
            matcher.appendReplacement(sb,  flex+":1");
        }
        matcher.appendTail(sb);
        return  sb.toString();
    }

    public static String replaceWxssChar2str(String str){
        String regex = ":\\s*[^:{]*?,";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find())
        {
            System.out.println("char-group:"+matcher.group());
            String prop = matcher.group().substring(matcher.group().indexOf(":") + 1, matcher.group().indexOf(","));
            try {
                Integer.parseInt(prop.trim());
            }catch (Exception e){
                matcher.appendReplacement(sb, ":\"" + prop.trim() + "\"");
            }


        }
        matcher.appendTail(sb);
        return  sb.toString();
    }

    /**
     * 利用正则表达式判断字符串是否是数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }


    public static  void main(String[] str){
        convert2RNFile(rnFilePath);
        //System.out.println(getRNFile(rnFilePath)));
        //System.out.println(replaceState("{{dfdfdfd}}  {{ppppp}}"));
    }
}