package com.apitests.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Модель ответа при получении пользователя.
 * @JsonIgnoreProperties — лишние поля из ответа не ломают десериализацию.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponse {

    @JsonProperty("id")
    private int id;

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

    public int getId()        { return id; }
    public String getName()   { return name; }
    public String getUsername() { return username; }
    public String getEmail()  { return email; }
    public String getPhone()  { return phone; }
    public String getWebsite() { return website; }

    @Override
    public String toString() {
        return "UserResponse{id=" + id + ", name='" + name + "', email='" + email + "'}";
    }
}