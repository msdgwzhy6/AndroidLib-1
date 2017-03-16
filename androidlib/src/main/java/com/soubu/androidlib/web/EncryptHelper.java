package com.soubu.androidlib.web;

import com.alibaba.fastjson.JSON;
import com.soubu.androidlib.util.SPUtil;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;

import static com.alibaba.fastjson.util.IOUtils.UTF8;
import static com.soubu.androidlib.util.ConvertUtil.bytes2Hex;

/**
 * 生成请求的sign的辅助类
 * <p>
 * 作者：余天然 on 2017/2/15 下午3:00
 */
public class EncryptHelper {

    public static String TAG_TOKEN = "TOKEN";

    public static String createSign(Request request) throws IOException {
        String macData = getMacData(request);
        String macKey = SPUtil.getValue(TAG_TOKEN, "");
        return hmacsha256(macData, macKey);
    }

    private static String getMacData(Request request) {
        if (request.method().equals("POST") || request.method().equals("PUT")) {
            return getBodyData(request);
        } else {
            return getUrlData(request);
        }
    }

    private static String getBodyData(Request request) {
        String paramsStr = readBodyString(request);
        StringBuffer sb = new StringBuffer();
        Map<String, String> map = (Map<String, String>) JSON.parse(paramsStr);
        if (map == null || map.size() == 0) {
            return "";
        }
        List<String> list = new ArrayList<>(map.keySet());
        Collections.sort(list);
        for (String s : list) {
            sb.append("&" + s + "=" + map.get(s));
        }
        return sb.substring(1, sb.length());
    }

    private static String getUrlData(Request request) {
        HttpUrl url = request.url();
        Set<String> set = url.queryParameterNames();
        if (set == null || set.size() == 0) {
            return "";
        }
        List<String> list = new ArrayList<>(set);
        Collections.sort(list);

        StringBuffer sb = new StringBuffer();
        for (String s : list) {
            sb.append("&" + s + "=" + url.queryParameter(s));
        }
        return sb.substring(1, sb.length());
    }

    private static String readBodyString(Request request) {
        try {
            RequestBody requestBody = request.body();
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }
            return buffer.readString(charset);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String hmacsha256(String macData, String macKey) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            byte[] secretByte = macKey.getBytes("UTF-8");
            byte[] dataBytes = macData.getBytes("UTF-8");
            SecretKey secret = new SecretKeySpec(secretByte, "HMACSHA256");
            mac.init(secret);
            byte[] doFinal = mac.doFinal(dataBytes);
            return bytes2Hex(doFinal);
        } catch (Exception e) {
            return "";
        }
    }
}
