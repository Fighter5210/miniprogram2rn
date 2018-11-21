import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by WANGDUOYUAN on 2018/11/16.
 */
public class MainConvert {

    private static  final String fileName = "childrenFollowDetail";

    private static  final String rnFilePath = "D:\\工作相关\\项目开发\\小程序转RN\\import\\exchange.js";
    private static  final String jsFilePath = "D:\\工作相关\\项目开发\\小程序转RN\\import\\"+fileName+".js";
    private static  final String wxmlFilePath = "D:\\工作相关\\项目开发\\小程序转RN\\import\\"+fileName+".wxml";
    private static  final String wxssFilePath = "D:\\工作相关\\项目开发\\小程序转RN\\import\\" + fileName + ".wxss";
    private static  final String outFile = "D:\\工作相关\\项目开发\\小程序转RN\\export\\" + fileName +
            ".js";

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
                        lineTxt = lineTxt.replace("文件名首字母大写",toUpperCaseHeadChar(fileName,"^[a-z].*?"));
                    }
                    sb.append(lineTxt + "\n");

                    if(lineTxt.trim().contains("componentDidMount()")){
                        String tmpStr=getOnLoadAndOnReady4JS
                                (jsFilePath);
                        tmpStr = replaceSimpleStr(tmpStr,"this.setData","this.setState");
                        tmpStr = replaceSimpleStr(tmpStr,"this.data","this.state");
                        sb.append(tmpStr);
                    }

                    if(lineTxt.trim().contains("return")){
                        String temStr = replaceStyle4Wxml(toUpperCaseHeadChar(getWxmlOrWxss
                                        (wxmlFilePath),"(<|</)[a-z].*?"));
                        temStr = replaceState4Wxml(temStr);
                        temStr = replaceState4Wxml(temStr);
                        temStr = replaceSimpleStr(temStr,"<!--.*?-->","");
                        temStr = replaceSimpleStr(temStr,"/\\*.*?\\*/","");
                        sb.append(temStr);
                        flag=true;
                    }

                    if("}".equals(lineTxt.trim())&&flag==true){
                        sb.append(getOnUnLoad4JS(jsFilePath));
                        flag=false;
                    }

                    if(lineTxt.trim().contains("styles")){
                        String temStr = replaceChar2str4Wxss(replaceDisplay4Wxss(replaceRod2Hump4Wxss
                                (replaceSize4Wxss(replaceStyle4Wxss(getWxmlOrWxss
                                        (wxssFilePath))))));
                        temStr = replaceSimpleStr(temStr,"<!--.*-->","");
                        temStr = replaceSimpleStr(temStr,"/.*?/","");
                        sb.append(temStr);
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

    public static String getOnLoadAndOnReady4JS(String filePath){

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

    public static String getOnUnLoad4JS(String filePath){

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


    public static String replaceStyle4Wxml(String str){
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

    public static String replaceState4Wxml(String str){
        String regex = "\\{\\{.*?\\}\\}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find())
        {
            System.out.println("state-group:"+matcher.group());
            String key = matcher.group().substring(matcher.group().indexOf("{{")+2,matcher.group().lastIndexOf("}}"));
            matcher.appendReplacement(sb,  "{{this.state."+key+"}}");
        }
        matcher.appendTail(sb);
        return  sb.toString();
    }

    public static String replaceStyle4Wxss(String str){
        String regex = ".*?\\{*?\\}";
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

    public static String replaceSize4Wxss(String str){
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

    public static String replaceRod2Hump4Wxss(String str){
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

    public static String replaceDisplay4Wxss(String str){
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

    public static String replaceChar2str4Wxss(String str){
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
                matcher.appendReplacement(sb, ":\"" + prop.trim() + "\",");
            }


        }
        matcher.appendTail(sb);
        System.out.println("输出:"+sb.toString());
        return  sb.toString();
    }

    public static String replaceSimpleStr(String str,String regex,String replaceStr){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find())
        {
            System.out.println("setData-group:"+matcher.group());
            matcher.appendReplacement(sb, replaceStr);


        }
        matcher.appendTail(sb);
        System.out.println("输出:"+sb.toString());
        return  sb.toString();
    }



    public static  void main(String[] str){
        convert2RNFile(rnFilePath);
        //System.out.println(getRNFile(rnFilePath)));
        //System.out.println(replaceState("{{dfdfdfd}}  {{ppppp}}"));
    }
}