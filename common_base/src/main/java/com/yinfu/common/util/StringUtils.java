/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yinfu.common.util;


/**
 * String utilities
 */
public class StringUtils {


    /**
     * 将字符串转换为 int.
     *
     * @param input 输入的字串
     * @return 结果数字
     * @date 2005-07-29
     */
    public static int parseInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return 0;
    }

    /**
     * 转义正则特殊字符 （$()*+.[]?\^{},|）
     *
     * @param keyword
     * @return
     */
    public static String escapeExprSpecialWord(String keyword) {
        if (StringUtils.isNotEmpty(keyword)) {
            String[] fbsArr = {"\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|"};
            for (String key : fbsArr) {
                if (keyword.contains(key)) {
                    keyword = keyword.replace(key, "\\" + key);
                }
            }
        }
        return keyword;
    }

    public static boolean isNotEmpty(String str) {
        return ((str != null) && (str.trim().length() > 0));
    }

    /**
     * 将字符串 source 中的 oldStr 替换为 newStr, 并以大小写敏感方式进行查找
     *
     * @param source 需要替换的源字符串
     * @param oldStr 需要被替换的老字符串
     * @param newStr 替换为的新字符串
     */
    public static String replace(String source, String oldStr, String newStr) {
        return replace(source, oldStr, newStr, true);
    }

    /**
     * 将字符串 source 中的 oldStr 替换为 newStr, matchCase 为是否设置大小写敏感查找
     *
     * @param source    需要替换的源字符串
     * @param oldStr    需要被替换的老字符串
     * @param newStr    替换为的新字符串
     * @param matchCase 是否需要按照大小写敏感方式查找
     */
    public static String replace(String source, String oldStr, String newStr,
                                 boolean matchCase) {
        if (source == null) {
            return null;
        }
        // 首先检查旧字符串是否存在, 不存在就不进行替换
        if (source.toLowerCase().indexOf(oldStr.toLowerCase()) == -1) {
            return source;
        }
        int findStartPos = 0;
        int a = 0;
        while (a > -1) {
            int b = 0;
            String str1, str2, str3, str4, strA, strB;
            str1 = source;
            str2 = str1.toLowerCase();
            str3 = oldStr;
            str4 = str3.toLowerCase();
            if (matchCase) {
                strA = str1;
                strB = str3;
            } else {
                strA = str2;
                strB = str4;
            }
            a = strA.indexOf(strB, findStartPos);
            if (a > -1) {
                b = oldStr.length();
                findStartPos = a + b;
                StringBuffer bbuf = new StringBuffer(source);
                source = bbuf.replace(a, a + b, newStr) + "";
                // 新的查找开始点位于替换后的字符串的结尾
                findStartPos = findStartPos + newStr.length() - b;
            }
        }
        return source;
    }

    /**
     * 清除字符串结尾的空格.
     *
     * @param input String 输入的字符串
     * @return 转换结果
     */
    public static String trimTailSpaces(String input) {
        if (isEmpty(input)) {
            return "";
        }

        String trimedString = input.trim();

        if (trimedString.length() == input.length()) {
            return input;
        }

        return input.substring(0, input.indexOf(trimedString)
                + trimedString.length());
    }

    /**
     * Change the null string value to "", if not null, then return it self, use
     * this to avoid display a null string to "null".
     *
     * @param input the string to clear
     * @return the result
     */
    public static String clearNull(String input) {
        return isEmpty(input) ? "" : input;
    }

    /**
     * Return the limited length string of the input string (added at:April 10,
     * 2004).
     *
     * @param input     String
     * @param maxLength int
     * @return String processed result
     */
    public static String limitStringLength(String input, int maxLength) {
        if (isEmpty(input))
            return "";

        if (input.length() <= maxLength) {
            return input;
        } else {
            return input.substring(0, maxLength - 3) + "...";
        }

    }

    /**
     * 将字符串转换为一个 javascript 的 alert 调用. eg: htmlAlert("What?"); returns
     * <SCRIPT language="javascript">alert("What?")</SCRIPT>
     *
     * @param message 需要显示的信息
     * @return 转换结果
     */
    public static String scriptAlert(String message) {
        return "<SCRIPT language=\"javascript\">alert(\"" + message
                + "\");</SCRIPT>";
    }

    /**
     * 将字符串转换为一个 javascript 的 document.location 改变调用. eg: htmlAlert("a.jsp");
     * returns <SCRIPT
     * language="javascript">document.location="a.jsp";</SCRIPT>
     *
     * @param url 需要显示的 URL 字符串
     * @return 转换结果
     */
    public static String scriptRedirect(String url) {
        return "<SCRIPT language=\"javascript\">document.location=\"" + url
                + "\";</SCRIPT>";
    }

    /**
     * 返回脚本语句 <SCRIPT language="javascript">history.back();</SCRIPT>
     *
     * @return 脚本语句
     */
    public static String scriptHistoryBack() {
        return "<SCRIPT language=\"javascript\">history.back();</SCRIPT>";
    }

    /**
     * 滤除帖子中的危险 HTML 代码, 主要是脚本代码, 滚动字幕代码以及脚本事件处理代码
     *
     * @param content 需要滤除的字符串
     * @return 过滤的结果
     */
    public static String replaceHtmlCode(String content) {
        if (isEmpty(content)) {
            return "";
        }
        // 需要滤除的脚本事件关键字
        String[] eventKeywords = {"onmouseover", "onmouseout", "onmousedown",
                "onmouseup", "onmousemove", "onclick", "ondblclick",
                "onkeypress", "onkeydown", "onkeyup", "ondragstart",
                "onerrorupdate", "onhelp", "onreadystatechange", "onrowenter",
                "onrowexit", "onselectstart", "onload", "onunload",
                "onbeforeunload", "onblur", "onerror", "onfocus", "onresize",
                "onscroll", "oncontextmenu"};
        content = replace(content, "<script", "&ltscript", false);
        content = replace(content, "</script", "&lt/script", false);
        content = replace(content, "<marquee", "&ltmarquee", false);
        content = replace(content, "</marquee", "&lt/marquee", false);
        content = replace(content, "\r\n", "<BR>");
        // 滤除脚本事件代码
        for (int i = 0; i < eventKeywords.length; i++) {
            content = replace(content, eventKeywords[i],
                    "_" + eventKeywords[i], false); // 添加一个"_", 使事件代码无效
        }
        return content;
    }

    /**
     * 滤除 HTML 代码 为文本代码.
     */
    public static String replaceHtmlToText(String input) {
        if (isEmpty(input)) {
            return "";
        }
        return setBr(setTag(input));
    }

    /**
     * 滤除 HTML 标记.
     * 因为 XML 中转义字符依然有效, 因此把特殊字符过滤成中文的全角字符.
     *
     * @param s 输入的字串
     * @return 过滤后的字串
     * @author beansoft
     */
    public static String setTag(String s) {
        int j = s.length();
        StringBuffer stringbuffer = new StringBuffer(j + 500);
        char ch;
        for (int i = 0; i < j; i++) {
            ch = s.charAt(i);
            if (ch == '<') {
                //stringbuffer.append("<");
                stringbuffer.append("〈");
            } else if (ch == '>') {
                //stringbuffer.append(">");
                stringbuffer.append("〉");
            } else if (ch == '&') {
                //stringbuffer.append("&");
                stringbuffer.append("〃");
            } else if (ch == '%') {
                //stringbuffer.append("%%");
                stringbuffer.append("※");
            } else {
                stringbuffer.append(ch);
            }
        }

        return stringbuffer.toString();
    }

    /**
     * 滤除 BR 代码
     */
    public static String setBr(String s) {
        int j = s.length();
        StringBuffer stringbuffer = new StringBuffer(j + 500);
        for (int i = 0; i < j; i++) {

            if (s.charAt(i) == '\n' || s.charAt(i) == '\r') {
                continue;
            } else {
                stringbuffer.append(s.charAt(i));
            }
        }

        return stringbuffer.toString();
    }

    /**
     * 滤除空格
     */
    public static String setNbsp(String s) {
        int j = s.length();
        StringBuffer stringbuffer = new StringBuffer(j + 500);
        for (int i = 0; i < j; i++) {
            if (s.charAt(i) == ' ') {
                stringbuffer.append(" ");
            } else {
                stringbuffer.append(s.charAt(i) + "");
            }
        }
        return stringbuffer.toString();
    }

    /**
     * 判断字符串是否全是数字字符.
     *
     * @param input 输入的字符串
     * @return 判断结果, true 为全数字, false 为还有非数字字符
     */
    public static boolean isNumeric(String input) {
        if (isEmpty(input)) {
            return false;
        }

        for (int i = 0; i < input.length(); i++) {
            char charAt = input.charAt(i);

            if (!Character.isDigit(charAt)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 转换由表单读取的数据的内码(从 ISO8859 转换到 gb2312).
     *
     * @param input 输入的字符串
     * @return 转换结果, 如果有错误发生, 则返回原来的值
     */
    public static String toChi(String input) {
        try {
            byte[] bytes = input.getBytes("ISO8859-1");
            return new String(bytes, "GBK");
        } catch (Exception ex) {
        }
        return input;
    }

    /**
     * 转换由表单读取的数据的内码到 ISO(从 GBK 转换到ISO8859-1).
     *
     * @param input 输入的字符串
     * @return 转换结果, 如果有错误发生, 则返回原来的值
     */
    public static String toISO(String input) {
        return changeEncoding(input, "GBK", "ISO8859-1");
    }

    /**
     * 转换字符串的内码.
     *
     * @param input          输入的字符串
     * @param sourceEncoding 源字符集名称
     * @param targetEncoding 目标字符集名称
     * @return 转换结果, 如果有错误发生, 则返回原来的值
     */
    public static String changeEncoding(String input, String sourceEncoding,
                                        String targetEncoding) {
        if (input == null || input.equals("")) {
            return input;
        }

        try {
            byte[] bytes = input.getBytes(sourceEncoding);
            return new String(bytes, targetEncoding);
        } catch (Exception ex) {
        }
        return input;
    }

    /**
     * 将单个的 ' 换成 ''; SQL 规则:如果单引号中的字符串包含一个嵌入的引号,可以使用两个单引号表示嵌入的单引号.
     */

    public static String replaceSql(String input) {
        return replace(input, "'", "''");
    }

    /**
     * 对给定字符进行 URL 编码
     */
    public static String encode(String value) {
        if (isEmpty(value)) {
            return "";
        }

        try {
            value = java.net.URLEncoder.encode(value, "GB2312");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return value;
    }

    /**
     * 对给定字符进行 URL 解码
     *
     * @param value 解码前的字符串
     * @return 解码后的字符串
     */
    public static String decode(String value) {
        if (isEmpty(value)) {
            return "";
        }

        try {
            return java.net.URLDecoder.decode(value, "GB2312");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return value;
    }

    /**
     * 判断字符串是否未空, 如果为 null 或者长度为0, 均返回 true.
     */
    public static boolean isEmpty(String input) {
        return (input == null || input.length() == 0);
    }

    /**
     * 获得输入字符串的字节长度(即二进制字节数), 用于发送短信时判断是否超出长度.
     *
     * @param input 输入字符串
     * @return 字符串的字节长度(不是 Unicode 长度)
     */
    public static int getBytesLength(String input) {
        if (input == null) {
            return 0;
        }

        int bytesLength = input.getBytes().length;

        //System.out.println("bytes length is:" + bytesLength);

        return bytesLength;
    }

    /**
     * 检验字符串是否未空, 如果是, 则返回给定的出错信息.
     *
     * @param input    输入的字符串
     * @param errorMsg 出错信息
     * @return 空串返回出错信息
     */
    public static String isEmpty(String input, String errorMsg) {
        if (isEmpty(input)) {
            return errorMsg;
        } else {
            return "";
        }
    }

    /**
     * 得到文件的扩展名.
     *
     * @param fileName 需要处理的文件的名字.
     * @return the extension portion of the file's name.
     */
    public static String getExtension(String fileName) {
        if (fileName != null) {
            int i = fileName.lastIndexOf('.');
            if (i > 0 && i < fileName.length() - 1) {
                return fileName.substring(i + 1).toLowerCase();
            }
        }
        return "";
    }


    /**
     * 将 TEXT 文本转换为 HTML 代码, 已便于网页正确的显示出来.
     *
     * @param input 输入的文本字符串
     * @return 转换后的 HTML 代码
     */
    public static String textToHtml(String input) {
        if (isEmpty(input)) {
            return "";
        }

        input = replace(input, "<", "<");
        input = replace(input, ">", ">");

        input = replace(input, "\n", "<br>\n");
        input = replace(input, "\t", "      ");
        input = replace(input, "    ", "    ");

        return input;
    }

    public static String convertUnicode(String ori) {
        char aChar;
        int len = ori.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len; ) {
            aChar = ori.charAt(x++);
            if (aChar == '\\') {
                aChar = ori.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = ori.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);

        }
        return outBuffer.toString();
    }


    public static String format(String jsonStr) {
        int level = 0;
        StringBuffer jsonForMatStr = new StringBuffer();
        for (int i = 0; i < jsonStr.length(); i++) {
            char c = jsonStr.charAt(i);
            if (level > 0 && '\n' == jsonForMatStr.charAt(jsonForMatStr.length() - 1)) {
                jsonForMatStr.append(getLevelStr(level));
            }
            switch (c) {
                case '{':
                case '[':
                    jsonForMatStr.append(c + "\n");
                    level++;
                    break;
                case ',':
                    jsonForMatStr.append(c + "\n");
                    break;
                case '}':
                case ']':
                    jsonForMatStr.append("\n");
                    level--;
                    jsonForMatStr.append(getLevelStr(level));
                    jsonForMatStr.append(c);
                    break;
                default:
                    jsonForMatStr.append(c);
                    break;
            }
        }

        return jsonForMatStr.toString();

    }

    private static String getLevelStr(int level) {
        StringBuffer levelStr = new StringBuffer();
        for (int levelI = 0; levelI < level; levelI++) {
            levelStr.append("\t");
        }
        return levelStr.toString();
    }
}