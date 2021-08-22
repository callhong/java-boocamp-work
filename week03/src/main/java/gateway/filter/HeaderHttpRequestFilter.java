package gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public class HeaderHttpRequestFilter implements HttpRequestFilter {

    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        if (fullRequest.headers().get("jwt-token") != null){
            // 登录用户
            fullRequest.headers().set("userId", "ha001");
        }
        fullRequest.headers().set("mao", "soul");
    }
}
