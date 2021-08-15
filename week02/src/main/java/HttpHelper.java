import org.apache.hc.core5.http.ParseException;

import java.io.IOException;

/**
 * @Author: nanCheng
 * @Date: 2021/08/15
 */
public class HttpHelper {

    public static void main(String[] args) throws IOException, ParseException {
        String okHttpResult = OkHttpUtils.get("http://localhost:8801");
        System.out.println("OkHttp访问结果：" + okHttpResult);

        String httpClientResult = HttpClientUtils.get("http://localhost:8801");
        System.out.println("HttpClient访问结果：" + httpClientResult);
    }
}
