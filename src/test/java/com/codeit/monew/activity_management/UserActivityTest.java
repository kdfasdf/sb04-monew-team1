//package com.codeit.monew.activity_management;
//
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//import com.codeit.monew.activity_management.response_dto.UserActivityDto;
//import com.codeit.monew.activity_management.service.UserActivityService;
//import com.codeit.monew.article.entity.Article;
//import com.codeit.monew.comment.entity.Comment;
//import com.codeit.monew.interest.entity.Interest;
//import com.codeit.monew.subscriptions.entity.Subscription;
//import com.codeit.monew.user.entity.User;
//import com.codeit.monew.user.entity.UserStatus;
//import com.codeit.monew.user.repository.UserRepository;
//import java.util.List;
//import javax.swing.Spring;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.util.ReflectionTestUtils;
//
//@ExtendWith(MockitoExtension.class)
//public class UserActivityTest {
//
//  @Mock
//  private UserRepository userRepository;
//
//
//  @InjectMocks
//  private UserActivityService userActivityService;
//
//  @Test
//  @DisplayName("사용자 활동 내역 필드 검증")
//  void validateUserActivityDtoField() {
//    //given
//    User user = User.builder()
//        .email("testuser@test.com")
//        .nickname("testUser")
//        .password("password")
//        .userStatus(UserStatus.ACTIVE)
//        .build();
//
//    ReflectionTestUtils.setField(user, "userId", "550e8400-e29b-41d4-a716-446655440001");
//
//    //when
//    UserActivityDto userActivity = userActivityService.getUserActivity(user.getId());
//
//    //then
//    assertThat(userActivity).hasFieldOrProperty("id");
//    assertThat(userActivity).hasFieldOrProperty("email");
//    assertThat(userActivity).hasFieldOrProperty("nickname");
//    assertThat(userActivity).hasFieldOrProperty("createdAt");
//    assertThat(userActivity).hasFieldOrProperty("subscriptions");
//    assertThat(userActivity).hasFieldOrProperty("comments");
//    assertThat(userActivity).hasFieldOrProperty("commentLikes");
//    assertThat(userActivity).hasFieldOrProperty("articleViews");
//
//  }
//
////  private void setUserActivity(User user) {
////    List<Interest> interests = getInterests();
////    List<Subscription> subscriptions = getSubscriptions(user, interests);
////    List<Comment> comments = getComments(user);
////    List<Article> articles = getArticles(comments);
////  }
////
////  private List<Interest> getInterests() {
////    Interest firstInterest = Interest.builder()
////        .name("Technology")
////        .subCount(150)
////        .build();
////
////    ReflectionTestUtils.setField(firstInterest, "id", "6ba7b810-9dad-11d1-80b4-00c04fd430c1");
////
////    Interest secondInterest = Interest.builder()
////        .name("Sports")
////        .subCount(80)
////        .build();
////
////    ReflectionTestUtils.setField(secondInterest, "id", "6ba7b811-9dad-11d1-80b4-00c04fd430c1");
////
////    return List.of(firstInterest, secondInterest);
////  }
////
////  private List<Article> getArticles() {
////    Article firstArticle = Article.builder()
////
////  private List<Subscription> getSubscriptions(User user, List<Interest> interests) {
////    Subscription firstSubscription = Subscription.builder()
////        .user(user)
////        .interest(interests.get(0))
////        .build();
////
////    ReflectionTestUtils.setField(firstSubscription, "id", "'123e4567-e89b-12d3-a456-426614174001'");
////
////    Subscription secondSubscription = Subscription.builder()
////        .user(user)
////        .interest(interests.get(1))
////        .build();
////
////    ReflectionTestUtils.setField(secondSubscription, "id", "123e4567-e89b-12d3-a456-426614174002");
////
////    return List.of(firstSubscription, secondSubscription);
////  }
////
////  private List<Comment> getComments(User user) {
////    List<Article>
////
////    ('c9bf9e57-1685-4c89-bafb-ff5af830be8a', 'Spring Framework 정말 유용하네요!', false, 3, NULL, '550e8400-e29b-41d4-a716-446655440001', '9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d', '2024-01-03 10:30:00'),
////    ('c9bf9e57-1685-4c89-bafb-ff5af830be8b', '월드컵 경기 정말 감동적이었습니다', false, 5, NULL, '550e8400-e29b-41d4-a716-446655440001', '9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6e', '2024-01-03 15:30:00'),
////    ('c9bf9e57-1685-4c89-bafb-ff5af830be8c', '유럽 여행 정보 감사합니다', false, 2, NULL, '550e8400-e29b-41d4-a716-446655440001', '9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6f', '2024-01-03 17:30:00'),
////    'c9bf9e57-1685-4c89-bafb-ff5af830be8e', '추가 정보도 부탁드려요', false, 0, NULL, '550e8400-e29b-41d4-a716-446655440001', '9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d', '2024-01-03 18:00:00');
////
////    Comment firstComment = Comment.builder()
////
////    return List.of(firstComment, secondComment);
////  }
//
//}
