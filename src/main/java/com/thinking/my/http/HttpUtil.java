package com.thinking.my.http;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.zip.GZIPOutputStream;

/**
 * @Description
 * @Author liyong
 * @Date 2021/2/5 2:37 下午
 **/
public class HttpUtil {

    public static String doGet(String httpUrl){

       return null;
    }

    //test
    public static void testUploadStressData(File file) {
        CloseableHttpResponse response = null;

        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
//            HttpPost httpPost = new HttpPost("http://quake.sankuai.com/api/uploadStressData.do");
//            HttpPost httpPost = new HttpPost("http://quake-datacenter.sankuai.com/api/uploadStressData.do");
            HttpPost httpPost = new HttpPost("http://quake-datacenter.ep.test.sankuai.com/api/uploadStressData.do");

            ContentType contentType = ContentType.create("text/plain", Charset.forName("UTF-8"));

            HttpEntity entity = MultipartEntityBuilder.create()
                    .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                    .setCharset(Charset.forName("UTF-8"))
                    .addBinaryBody("file", file, ContentType.create("application/octet-stream", Consts.UTF_8), file.getName())
                    .addTextBody("businessUnit", "基础研发平台", contentType)
                    .addTextBody("productLine", "可配置api系统buffalo", contentType)
                    .addTextBody("dataType", "3", contentType)
                    .addTextBody("operateType", "1", contentType)
//                    .addTextBody("dataName", fileName, contentType)
                    .addTextBody("misId", "lihaodi", contentType).build();

            httpPost.setEntity(entity);
            httpPost.addHeader("content-type", "application/json;charset=utf-8");
            response = httpClient.execute(httpPost);

            // 解析返回结果,判断上传是否成功
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                String result = EntityUtils.toString(responseEntity);
                JSONObject json = JSONObject.parseObject(result);

                if (json.getBoolean("success")) {
                    System.out.println("上传成功");
                    // do something
                } else {
                    String  msg = json.getString("msg");
                    System.out.println(String.format("上传失败, 原因:%s", msg));
                    // do something
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭response
            try {
                if (null != response) {
                    response.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将上传的file压缩成gz格式并写入目的文件
     */
    private static void writeGZipFile(File file, File desFile) {

        try (InputStream in = new FileInputStream(file);
             OutputStream out = new GZIPOutputStream(new FileOutputStream(desFile))) {
            IOUtils.copy(in, out);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
//        String inputFile = "/Users/dayong/Desktop/SQL_demo.csv";
//        String outputFile = "/Users/dayong/Desktop/SQL_demo.csv.gz";
//
//        File file = new File(inputFile);
//        System.out.println(file.length());
//        System.out.println(file.getTotalSpace());
//        System.out.println(file.getName());
//        String fileName = file.getName();
//        String suffix = fileName.substring(fileName.lastIndexOf("."));
//        System.out.println(suffix);
//        File outFile = new File(outputFile);
//        writeGZipFile(file,outFile);
//
//        testUploadStressData(outFile);
//        outFile.delete();
        testUploadStressData();

    }


    public static void testUploadStressData() {
        CloseableHttpResponse response = null;

        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
//            HttpPost httpPost = new HttpPost("http://quake.sankuai.com/api/uploadStressData.do");
            HttpPost httpPost = new HttpPost("http://quake-datacenter.ep.test.sankuai.com/api/uploadStressData.do");

            File file = new File("/Users/dayong/Desktop/SQL_demo.csv.gz");

            ContentType contentType = ContentType.create("text/plain", Charset.forName("UTF-8"));

            HttpEntity entity = MultipartEntityBuilder.create()
                    .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                    .setCharset(Charset.forName("UTF-8"))
                    .addBinaryBody("file", file, ContentType.create("application/octet-stream", Consts.UTF_8), file.getName())
                    .addTextBody("businessUnit", "基础研发平台", contentType)
                    .addTextBody("productLine", "可配置api系统buffalo", contentType)
                    .addTextBody("dataType", "3", contentType)
                    .addTextBody("operateType", "1", contentType)
                    .addTextBody("misId", "lihaodi", contentType).build();
            httpPost.setEntity(entity);

            response = httpClient.execute(httpPost);
            // 解析返回结果,判断上传是否成功
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                String result = EntityUtils.toString(responseEntity,Consts.UTF_8);
                JSONObject json = JSONObject.parseObject(result);

                if (json.getBoolean("success")) {
                    System.out.println("上传成功");
                    // do something
                } else {
                    String msg = json.getString("msg");
                    System.out.println(String.format("上传失败, 原因:%s", msg));
                    // do something
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭response
            try {
                if (null != response) {
                    response.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
