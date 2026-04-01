package com.apitests.models.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Модель тела запроса для создания пользователя.
 * @JsonInclude — null поля не попадают в JSON.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateUserRequest {

    @JsonProperty("name")
    private String name;

    @JsonProperty("username")
    private String username;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("website")
    private String website;

    private CreateUserRequest() {}

    public String getName()     { return name; }
    public String getUsername() { return username; }
    public String getEmail()    { return email; }
    public String getPhone()    { return phone; }
    public String getWebsite()  { return website; }

    // Builder паттерн — удобное создание объекта
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final CreateUserRequest request = new CreateUserRequest();

        public Builder name(String name)         { request.name = name;         return this; }
        public Builder username(String username) { request.username = username; return this; }
        public Builder email(String email)       { request.email = email;       return this; }
        public Builder phone(String phone)       { request.phone = phone;       return this; }
        public Builder website(String website)   { request.website = website;   return this; }

        public CreateUserRequest build() { return request; }
    }

    @Override
    public String toString() {
        return "CreateUserRequest{name='" + name + "', username='" + username +
                "', email='" + email + "', phone='" + phone + "', website='" + website + "'}";
    }
}