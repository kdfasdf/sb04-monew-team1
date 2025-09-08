package com.codeit.monew.activity_management;

import com.codeit.monew.activity_management.response_dto.UserActivityDto;
import com.codeit.monew.activity_management.service.UserActivityService;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@Sql("/activity-test-data.sql")
@SpringBootTest
public class UserActivityIntegrationTest {

  @Autowired
  UserActivityService userActivityService;

  @Test
  @DisplayName("유저의 활동을 조회한다.")
  public void getUserActivity() {
    // given
    UUID userId = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");

    // when
    UserActivityDto userActivity = userActivityService.getUserActivity(userId);

    // then
    System.out.println(userActivity);
  }

}
