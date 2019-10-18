package com.microsoft.bot.sample.spring;

import java.io.IOException;

import com.microsoft.bot.rest.credentials.ServiceClientCredentials;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class TokenCredentials implements ServiceClientCredentials {
    private final String token;

    public TokenCredentials(String token) {
        this.token = token;
    }

    /**
     * Apply the credentials to the HTTP client builder.
     *
     * @param clientBuilder the builder for building up an {@link OkHttpClient}
     */
    @Override
    public void applyCredentialsFilter(OkHttpClient.Builder clientBuilder) {
        clientBuilder.interceptors().add(new BearerTokenInterceptor(this.token));

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        clientBuilder.addInterceptor(logging);
    }

    public class BearerTokenInterceptor implements Interceptor {
        /**
         * The token HTTP client pipeline.
         */
        private String bearerToken;

        /**
         * Initialize a TokenCredentialsFilter class with a
         * TokenCredentials credential.
         *
         * @param bearerToken token
         */
        BearerTokenInterceptor(String bearerToken) {
            this.bearerToken = bearerToken;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {

            Request newRequest = chain.request().newBuilder()
                .header("Authorization", "Bearer " + this.bearerToken)
                .build();
            return chain.proceed(newRequest);
        }
    }
}
