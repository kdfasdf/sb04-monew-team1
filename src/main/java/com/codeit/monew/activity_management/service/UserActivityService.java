package com.codeit.monew.activity_management.service;

import com.codeit.monew.activity_management.response_dto.UserArticleViewDto;
import com.codeit.monew.activity_management.response_dto.CommentActivityDto;
import com.codeit.monew.activity_management.response_dto.CommentLikeActivityDto;
import com.codeit.monew.activity_management.response_dto.UserActivityDto;
import com.codeit.monew.activity_management.response_dto.UserSubscriptionDto;
import com.codeit.monew.article.entity.QArticle;
import com.codeit.monew.article.entity.QArticlesViewUser;
import com.codeit.monew.comment.entity.QComment;
import com.codeit.monew.comment.entity.QCommentLike;
import com.codeit.monew.interest.entity.QInterest;
import com.codeit.monew.interest.entity.QKeyword;
import com.codeit.monew.subscriptions.entity.QSubscription;
import com.codeit.monew.user.entity.QUser;
import com.codeit.monew.user.entity.User;
import com.codeit.monew.user.entity.UserStatus;
import com.codeit.monew.user.exception.UserErrorCode;
import com.codeit.monew.user.exception.UserException;
import com.codeit.monew.user.repository.UserRepository;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserActivityService {

  private final JPAQueryFactory queryFactory;
  private final UserRepository userRepository;

  public UserActivityDto getUserActivity(UUID userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND, Map.of("userId",userId)));

    if(user.getUserStatus() == UserStatus.DELETED) {
      throw new UserException(UserErrorCode.DELETED_USER, Map.of("userId", userId));
    }

    List<UserSubscriptionDto> subscriptions = getSubscriptions(userId);
    List<CommentActivityDto> comments = getRecentComments(userId);
    List<CommentLikeActivityDto> commentLikes = getRecentCommentLikes(userId);
    List<UserArticleViewDto> articleViews = getRecentArticleViews(userId);

    return new UserActivityDto(
        user.getId(),
        user.getEmail(),
        user.getNickname(),
        user.getCreatedAt(),
        subscriptions,
        comments,
        commentLikes,
        articleViews
    );

  }

  private List<UserSubscriptionDto> getSubscriptions(UUID userId) {
    QSubscription subscription = QSubscription.subscription;
    QInterest interest = QInterest.interest;
    QKeyword keyword = QKeyword.keyword1;

    return queryFactory
        .select(Projections.constructor(UserSubscriptionDto.class,
            subscription.id,
            interest.id,
            interest.name,
            JPAExpressions
                .select(keyword.keyword)
                .from(keyword)
                .where(
                    keyword.interest.id.eq(interest.id)
                ),
            interest.subCount,
            subscription.createdAt
            )
        ).from(subscription)
        .leftJoin(interest).on(subscription.interest.id.eq(interest.id))
        .where(subscription.user.id.eq(userId))
        .fetch();
  }

  private List<CommentActivityDto> getRecentComments(UUID userId) {
    QComment comment = QComment.comment;
    QArticle article = QArticle.article;

    return queryFactory
        .select(Projections.constructor(CommentActivityDto.class,
            comment.id,                               //id
            comment.article.id,                       //articleId
            comment.article.articleTitle,             //articleTitle
            comment.user.id,                          //userId
            comment.user.nickname,                    //userNickname
            comment.content,                          //content
            comment.likeCount,                        //likeCount
            comment.createdAt                        //createdAt
            )).from(comment)
        .join(comment.article, article).on(comment.article.eq(article))
        .where(comment.user.id.eq(userId))
        .orderBy(comment.createdAt.desc())
        .limit(10)
        .fetch();
  }

  private List<CommentLikeActivityDto> getRecentCommentLikes(UUID userId) {
    QCommentLike commentLike = QCommentLike.commentLike;
    QComment comment = QComment.comment;

    return queryFactory
        .select(Projections.constructor(CommentLikeActivityDto.class,
            commentLike.id,                           //id
            commentLike.createdAt,                    //createdAt
            commentLike.comment.id,                   //commentId
            commentLike.comment.article.id,           //commentUserId
            commentLike.comment.article.articleTitle, //commentUserTitle
            commentLike.user.id,                      //commentUserId
            commentLike.comment.content,              //commentContent
            commentLike.comment.likeCount,            //commentLikeCount
            commentLike.comment.createdAt            //commentCreatedAt
            )).from(commentLike)
        .join(commentLike.comment, comment).on(commentLike.comment.id.eq(comment.id))
        .where(commentLike.user.id.eq(userId))
        .orderBy(commentLike.createdAt.desc())
        .limit(10)
        .fetch();

  }

  private List<UserArticleViewDto> getRecentArticleViews(UUID userId) {
    QArticlesViewUser articleView = QArticlesViewUser.articlesViewUser;
    QArticle article = QArticle.article;

    return queryFactory
        .select(Projections.constructor(UserArticleViewDto.class,
            articleView.id,                           //id
            articleView.user.id,                      //viewdBy
            articleView.createdAt,                    //createdAt
            articleView.article.id,                   //articleId
            articleView.article.source,               //source
            articleView.article.sourceUrl,            //sourceUrl
            articleView.article.articleTitle,         //articleTitle
            articleView.article.createdAt,            //articlePublishedDate
            articleView.article.articleSummary,       //articleSummary
            articleView.article.articleCommentCount,  //articleCommentCount
            articleView.article.articleViewCount      //articleViewCount
            ))
        .from(articleView)
        .join(articleView.article, article).on(articleView.article.id.eq(article.id))
        .where(articleView.user.id.eq(userId))
        .orderBy(articleView.createdAt.desc())
        .limit(10)
        .fetch();
  }

}
