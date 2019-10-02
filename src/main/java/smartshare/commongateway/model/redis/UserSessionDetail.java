package smartshare.commongateway.model.redis;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.Map;

@RedisHash(timeToLive = 100, value = "users")
public class UserSessionDetail {

    @Id
    private String id;
    @Indexed
    private String userName;
    @Indexed
    private String sessionId;
    private String role;

    public UserSessionDetail(Map body) {

        this.userName = body.get("userName").toString();
        this.sessionId = body.get("sessionId").toString();
        this.role = body.get("role").toString();
    }

    public UserSessionDetail() {
    }

    public String getUserName() {
        return userName;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "UserSessionDetail{" +
                "userName='" + userName + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}