package smartshare.commongateway.repository;

import org.springframework.data.repository.CrudRepository;
import smartshare.commongateway.model.redis.UserSessionDetail;


public interface UserSessionRepository extends CrudRepository<UserSessionDetail, String> {

    UserSessionDetail findByUserName(String userName);

}
