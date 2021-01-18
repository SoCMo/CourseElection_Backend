package com.spring.CourseElection.tools;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Base64;

/**
 * program: EncoderTool
 * description: 编码与解码相关
 * author: SoCMo
 * create: 2020/4/29
 */
public class EncoderTool {

    /**
     * @Description: 字符串转码
     * @Param: [in]
     * @return: java.lang.String
     * @Author: SoCMo
     * @Date: 2020/5/1
     */
    public static String base64encoder(String in) {
        return new String(Base64.getEncoder().encode(in.getBytes()), StandardCharsets.UTF_8);
    }

    /**
     * @Description: String解码为整型
     * @Param: [in]
     * @return: java.lang.Integer
     * @Author: SoCMo
     * @Date: 2020/4/29
     */
    public static Integer base64decoderInteger(String in) throws ParseException {
        Base64.Decoder decoder = Base64.getDecoder();
        String result = new String(decoder.decode(in), StandardCharsets.UTF_8);
        if (!result.matches("[0-9]+")) {
            throw new ParseException("非整型字符串", 500);
        }
        return Integer.valueOf(result);
    }
}
