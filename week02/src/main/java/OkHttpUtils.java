import okhttp3.*;

import java.io.IOException;

/**
 * @Author: nanCheng
 * @Date: 2021/08/15
 */
public class OkHttpUtils {

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private static final OkHttpClient CLIENT = new OkHttpClient();

    public static String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = CLIENT.newCall(request).execute()) {
            return response.body().string();
        }
    }



    public static String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = CLIENT.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
