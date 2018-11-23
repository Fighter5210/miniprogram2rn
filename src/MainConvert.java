import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by WANGDUOYUAN on 2018/11/16.
 */
public class MainConvert {



    public static void convert2RNFile(String fileName){
         String rnFilePath = "D:\\工作相关\\项目开发\\小程序转RN\\exchange\\exchange.js";
         String jsFilePath = "D:\\工作相关\\项目开发\\小程序转RN\\import\\"+fileName+"\\"+fileName+".js";
         String wxmlFilePath = "D:\\工作相关\\项目开发\\小程序转RN\\import\\" + fileName + "\\"+fileName+".wxml";
         String wxssFilePath = "D:\\工作相关\\项目开发\\小程序转RN\\import\\" + fileName + "\\" + fileName + ".wxss";
         fileName =toUpperCaseHeadChar(fileName,"^[a-z].*?");
         String outFile = "D:\\工作相关\\项目开发\\小程序转RN\\export\\" + fileName +
                ".js";

        try {
            String encoding="utf8";
            File file=new File(rnFilePath);
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
                        //temStr = replaceBlock4Wxml(temStr);
                        temStr = replaceSimpleStr(temStr,"<!--.*[^-->]-->","");
                        temStr = replaceSimpleStr(temStr,"/\\*.*[^\\*/]\\*/","");
                        sb.append(temStr);
                        flag=true;
                    }

                    if("}".equals(lineTxt.trim())&&flag==true){
                        sb.append(getAfterOnUnLoad4JS(jsFilePath));
                        flag=false;
                    }

                    if(lineTxt.trim().contains("styles")){
                        String temStr = replaceChar2str4Wxss(replaceDisplay4Wxss(replaceRod2Hump4Wxss
                                (replaceSize4Wxss(replaceStyle4Wxss(getWxmlOrWxss
                                        (wxssFilePath))))));
                        temStr = replaceSimpleStr(temStr,"<!--.*[^-->]-->","");
                        temStr = replaceSimpleStr(temStr,"/\\*.*[^\\*/]\\*/","");
                        temStr = replaceMarginPadding4Wxss(temStr);
                        sb.append(temStr);
                    }

                }
                System.out.println(sb.toString());
                read.close();
                File outfile = new File(outFile);
                PrintStream ps = new PrintStream(new FileOutputStream(outfile));
                ps.println(sb.toString());// 往文件里写入字符串

            }else{
                System.out.println("找不到指定的文件"+rnFilePath);
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错"+rnFilePath);
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
                System.out.println("找不到指定的文件"+filePath);
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错"+filePath);
            e.printStackTrace();
        }
        return replaceFunction4Js(sb.toString());
    }

    public static String getAfterOnUnLoad4JS(String filePath){

        StringBuffer sb = new StringBuffer();
        String returnStr="";
        try {
            String encoding="utf8";
            File file=new File(filePath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt;
                int i=0;
                while((lineTxt = bufferedReader.readLine()) != null){

                    if(i>=2){
                        sb.append(lineTxt+"\n");
                    }
                    if(lineTxt.trim().contains("onUnload:")){
                        i++;
                    }
                    if(i==1&&"},".equals(lineTxt.trim())){
                        i++;
                    }

                }

                returnStr = sb.toString().substring(0,sb.toString().lastIndexOf("})"));
                read.close();
            }else{
                System.out.println("找不到指定的文件"+filePath);
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错"+filePath);
            e.printStackTrace();
        }
        return replaceFunction4Js(returnStr);
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
                    sb.append(lineTxt+"\n");
                }
                read.close();
            }else{
                System.out.println("找不到指定的文件"+filePath);
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错"+filePath);
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
        String regex = "(class=\".*?\")|(class='.*?')";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find())
        {
            System.out.println("classgroup:"+matcher.group());
            String positionStr = "\"";
            if(matcher.group().contains("'")){
                positionStr="'";
            }
            String style = matcher.group().substring(matcher.group().indexOf(positionStr)+1,
                    matcher.group().lastIndexOf(positionStr));
            matcher.appendReplacement(sb,  "style={styles."+style+"}");
        }
        matcher.appendTail(sb);
        return  sb.toString();
    }

    public static String replaceState4Wxml(String str){
        String regex = "\\{\\{[^\\}\\}].*?\\}\\}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find())
        {
            System.out.println("state-group:"+matcher.group());
            String key = matcher.group().substring(matcher.group().indexOf("{{")+2,matcher.group().lastIndexOf("}}"));
            matcher.appendReplacement(sb,  "{this.state."+key+"}");
        }
        matcher.appendTail(sb);
        return  sb.toString();
    }

    public static String replaceStyle4Wxss(String str){
        //String regex = ".*?\\s*\{\n*?\n\\s*\}";
        String regex = ".*?\\s*(?<=\\{)[^}]*(\\s?=*})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find())
        {
            System.out.println("style-group:"+matcher.group());
            String key = matcher.group().substring(matcher.group().indexOf(".")+1,matcher.group().lastIndexOf("{"));
            if(key.contains(".")){
                key=key.replaceAll("\\s*\\.","_");
            }
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
        String regex = "(-[a-z].*\\s*:)";
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
        return  sb.toString();
    }

    public static String replaceSimpleStr(String str,String regex,String replaceStr){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find())
        {
            System.out.println("simple-group:"+matcher.group());
            matcher.appendReplacement(sb, replaceStr);


        }
        matcher.appendTail(sb);
        return  sb.toString();
    }

    public static String replaceFunction4Js(String str){
        String regex = ":\\s*function\\s*\\(.*?\\)\\s*\\{";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find())
        {
            System.out.println("function-group:"+matcher.group());
            String functionName = matcher.group().substring(0,matcher.group().indexOf(":"));
            String param = matcher.group().substring(matcher.group().indexOf("(") + 1, matcher

                    .group().indexOf(")"));
            matcher.appendReplacement(sb, functionName + "=(" + param + ")=>{");


        }
        matcher.appendTail(sb);
        return  sb.toString();
    }

//    public static String replaceBlock4Wxml(String str){
//
//        String regex = "(<Block\\s*wx:if\\s*=\\s*.*?>)|(<Block\\s*wx:else\\s*>)|(</Block>)";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(str);
//        StringBuffer sb = new StringBuffer();
//        while (matcher.find())
//        {
//            System.out.println("block-group:"+matcher.group());
//            String group = matcher.group();
//
//            String content;
//            String replaceStr;
//            if(group.contains("if")){
//                content = matcher.group().substring(group.indexOf("\"")+1,group
//                        .indexOf("}"));
//                replaceStr = content+"?(";
//            }else if(group.contains("else")){
//                replaceStr="):(";
//            }else{
//                replaceStr=")}";
//            }
//            matcher.appendReplacement(sb, replaceStr);
//        }
//        matcher.appendTail(sb);
//        return  sb.toString();
//    }

    public static String replaceMarginPadding4Wxss(String str){

        String regex = "(margin\\s*:.*?,)|(padding\\s*:.*?,)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find())
        {
            System.out.println("margin-padding-group:"+matcher.group());
            String group = matcher.group();
            String key="";
            if(group.contains("margin")){
                key = "margin";
            }else if(group.contains("padding")){
                key = "padding";
            }
            String replaceStr;
            String value = group.replaceAll("(margin\\s*:)|(padding\\s*:)|(,)|(\")","");
            String[] valueArr= value.split("\\s+");
            if(valueArr.length==2){
                replaceStr = key+"Top:"+valueArr[0]+",\n"+key+"Bottom:"+valueArr[0]+",\n" +
                        key + "Left:" + valueArr[1] + ",\n" + key + "Right:" +
                        valueArr[1] + ",";
                matcher.appendReplacement(sb, replaceStr);
            }else if(valueArr.length==4){
                replaceStr = key+"Top:"+valueArr[0]+",\n"+key+"Right:"+valueArr[1]+",\n" +
                        key + "Bottom:" + valueArr[2] + "\n" + key + "Left:" + valueArr[3]
                        + ",";
                matcher.appendReplacement(sb, replaceStr);
            }

        }
        matcher.appendTail(sb);
        return  sb.toString();
    }

    public static List<String> getFileNameList(String filePath,List<String> fileNameList){
        File file=new File(filePath);
        // 获得该文件夹内的所有文件
        File[] array = file.listFiles();
        String fileName = "";
        for(int i=0;i<array.length;i++)
        {
            if(array[i].isFile())//如果是文件
            {
                fileName = array[i].getName();
                fileName = fileName.substring(0,fileName.indexOf("."));
                // 只输出文件名字
                System.out.println(fileName);
                if(!fileNameList.contains(fileName)) {
                    fileNameList.add(fileName);
                }
            } else if(array[i].isDirectory())//如果是文件夹
            {
                //文件夹需要调用递归 ，深度+1
                getFileNameList(array[i].getPath(),fileNameList);
            }


        }
        return  fileNameList;
    }

    public static  void main(String[] str){
        List<String> fileNameList = getFileNameList("D:\\工作相关\\项目开发\\小程序转RN\\import\\",new
                ArrayList<>());
        if(fileNameList!=null){
            for(String fileName:fileNameList){
                System.out.println("文件名输出"+fileName);
                convert2RNFile(fileName);
            }
        }
    }
}