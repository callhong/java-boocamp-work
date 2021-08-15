import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: nanCheng
 * @Date: 2021/08/15
 */
public class HttpClientUtils {

    /**
     * 创建默认的httpClient实例(CloseableHttpClient).
     */
    private static CloseableHttpClient httpclient = HttpClients.createDefault();

    public static String get(String url) throws IOException, ParseException {
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = httpclient.execute(httpGet);
        if (response.getCode() == 200) {
            //  *调用HttpResponse的getEntity()方法可获取HttpEntity对象
            HttpEntity resEntity = response.getEntity();
            String message = EntityUtils.toString(resEntity, "utf-8");
            //根据所需自行
            System.out.println("状态码：" + response.getCode() + "--" + response.getReasonPhrase());
            System.out.println("地区：" + response.getLocale());
            System.out.println("返回数据类型：" + resEntity.getContentType());
            System.out.println("响应内容：" + message);
            return message;
        } else {
            System.out.println("出错啦，快去找一下原因！状态码是：" + response.getCode());
        }
        return null;
    }

    public static String post(String url, Map<String, String> params) throws IOException, ParseException {
        HttpPost httpPost = new HttpPost(url);
        if (params != null && !params.isEmpty()) {
            List<NameValuePair> nvps = params.entrySet().stream().map(param -> new BasicNameValuePair(param.getKey(), param.getValue())).collect(Collectors.toList());
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));

        }

        CloseableHttpResponse response = httpclient.execute(httpPost);
        if (response.getCode() == 200) {
            //  *调用HttpResponse的getEntity()方法可获取HttpEntity对象
            HttpEntity resEntity = response.getEntity();
            String message = EntityUtils.toString(resEntity, "utf-8");
            //根据所需自行
            System.out.println("状态码：" + response.getCode());
            System.out.println("地区：" + response.getLocale());
            System.out.println("返回数据类型：" + resEntity.getContentType());
            System.out.println("响应内容：" + message);
            return message;
        } else {
            System.out.println("出错啦，快去找一下原因！状态码是：" + response.getCode());
        }
        return null;
    }


}
