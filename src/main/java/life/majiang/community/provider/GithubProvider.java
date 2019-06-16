package life.majiang.community.provider;


import java.io.IOException;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.GithubUser;
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
		
		// System.out.println("accessTokenDTO:" + JSON.toJSONString(accessTokenDTO));
		
		Request request = new Request.Builder()
				.url("https://github.com/login/oauth/access_token")
				.post(body)
				.build();

		try (Response response = client.newCall(request).execute()) {
			String str = response.body().string();
			 // System.out.println("str:" + str);
			 
			 String token = str.split("&")[0].split("=")[1];
			 System.out.println("fangfa1");
			return token;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// 通过access token 发送post请求 获取github用户信息  
	public GithubUser getUser (String acessToken) {
		OkHttpClient client = new OkHttpClient();
		

		Request request = new Request.Builder()
				.url("https://api.github.com/user")
				.addHeader("Authorization", "Bearer " + acessToken)
				.get()
				.build();

		try (Response response = client.newCall(request).execute()) {
			// 获取到用户数据丑 写入user实体类
			String str = response.body().string();
			
			// System.out.println("str: user:" + str);
			GithubUser githubUser = JSON.parseObject(str, GithubUser.class);
			// System.out.println("githubUser: :" + githubUser.getName());
			System.out.println("fangfa2");
			return githubUser;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
}
