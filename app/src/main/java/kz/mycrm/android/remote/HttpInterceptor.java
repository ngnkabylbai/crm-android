package kz.mycrm.android.remote;

import java.io.IOException;

import kz.mycrm.android.MycrmApp;
import kz.mycrm.android.db.entity.Token;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HttpInterceptor implements Interceptor {

	private void setAuthHeader(Request.Builder builder, String token) {
		if (token != null) //Add Auth token to each request if authorized
			builder.header("Authorization", String.format("Bearer %s", token));
	}

	@Override
	public Response intercept(Chain chain) throws IOException {
		Request request = chain.request();

		//Build new request
		Request.Builder builder = request.newBuilder();
		builder.header("Accept", "application/json"); //if necessary, say to consume JSON

		Token token = MycrmApp.database.TokenDao().getToken();
		if(token != null) {
			String tokenString = token.token; //save token of this request for future
			setAuthHeader(builder, tokenString); //write current token to request
		}

		request = builder.build(); //overwrite old request
		Response response = chain.proceed(request); //perform request, here original request will be executed

		return response;
	}
}