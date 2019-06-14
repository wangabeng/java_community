package life.majiang.community.provider;


import java.io.IOException;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import life.majiang.community.dto.AccessTokenDTO;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;



@Component
public class GithubProvider {
//	AccessToken accessToken
	public String getAccessToken (AccessTokenDTO accessTokenDTO) {
		// do post请求
		OkHttpClient client = new OkHttpClient();
		// String jsonStr = "{'client_id': '840e14b3990e1a66b140','client_secret': 'f0bbc0eecef590d7abbd24515320573630701364'}";
		
		MediaType mediaType = MediaType.get("application/json; charset=utf-8");
		RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDTO), mediaType);
		
		System.out.println("accessTokenDTO:" + JSON.toJSONString(accessTokenDTO));
		
		Request request = new Request.Builder()
				.url("https://github.com/login/oauth/access_token")
				.post(body)
				.build();

		try (Response response = client.newCall(request).execute()) {
			String str = response.body().string();
			 System.out.println("str:" + str);
			return str;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
