package fr.emirashotel.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.emirashotel.Main;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.*;

@Builder
public class SQLConfig {

    private String url;
    private String username;
    private String password;

    public void save(String file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();


        OutputStream outputStream = new FileOutputStream("src/main/resources/sql/config.json");

        objectMapper.writeValue(outputStream, this);
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static SQLConfig fromJson(JsonNode jsonNode) {
        return SQLConfig.builder()
                .url(jsonNode.get("url").asText())
                .username(jsonNode.get("username").asText())
                .password(jsonNode.get("password").asText()).build();
    }
}
