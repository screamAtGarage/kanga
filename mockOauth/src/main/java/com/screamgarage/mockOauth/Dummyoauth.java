package com.screamgarage.mockOauth;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;
@Component
@Endpoint(id="token")
public class Dummyoauth {
    @ReadOperation
    public String oauth(){
        return "{\n" +
                "\"access_token\":\"MockOauth2TokenForLocaldevelopmentnTQ0NjJkZmQ5OTM2NDE1ZTZjNGZmZjI3\",\n" +
                "\"token_type\":\"bearer\",\n" +
                "\"expires_in\":35999,\n" +
                "\"scope\":\"read write\",\n" +
                "\"jti\":\"4d540b94-1854-45fa-b1d6-c2039d94b681\"\n" +
                "}";
    }

}

