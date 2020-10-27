package com.java.zolo.instagram.service;

import com.java.zolo.instagram.domain.dto.comments.CommentsDTO;
import com.java.zolo.instagram.domain.dto.comments.PostedCommentDTO;
import com.java.zolo.instagram.domain.dto.comments.PostedReplyDTO;
import com.java.zolo.instagram.domain.model.Comments;
import com.java.zolo.instagram.domain.model.Posts;
import com.java.zolo.instagram.domain.model.User;
import com.java.zolo.instagram.repository.CommentsRepository;
import com.java.zolo.instagram.repository.PostsRepository;
import com.java.zolo.instagram.repository.UsersRepository;
import com.java.zolo.instagram.sampleData.CommentsSampleData;
import com.java.zolo.instagram.sampleData.PostsImagesSampleData;
import com.java.zolo.instagram.sampleData.UserSampleData;
import com.java.zolo.instagram.service.comments.CommentsServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class CommentsServiceUnitTest {

    @InjectMocks
    CommentsServiceImpl commentsService;

    @Mock
    CommentsRepository commentsRepository;

    @Mock
    UsersRepository usersRepository;

    @Mock
    PostsRepository postsRepository;

    @Test
    public void postComment_success() {
        Posts post = PostsImagesSampleData.generatePost();
        User user = UserSampleData.generateUser();
        CommentsDTO commentsDTO = CommentsSampleData.generateCommentsDTO();
        Comments comment = CommentsSampleData.generateComment();

        given(usersRepository.findById(user.getId())).willReturn(Optional.of(user));
        given(postsRepository.findById(post.getId())).willReturn(Optional.of(post));
        given(commentsRepository.save(new Comments().setUser(user).setPost(post).setContent(commentsDTO.getContent()))).willReturn(comment);

        Optional<PostedCommentDTO> postedCommentDTO = commentsService.postComment(commentsDTO, 12345l, 100l);

        assertEquals(post.getId(), postedCommentDTO.get().getPostId());
        assertEquals(user.getUserName(), postedCommentDTO.get().getUserName());
        assertEquals(commentsDTO.getContent(), postedCommentDTO.get().getContent());
    }

    @Test
    public void postComment_failure() {
        User user = UserSampleData.generateUser();
        CommentsDTO commentsDTO = CommentsSampleData.generateCommentsDTO();
        given(usersRepository.findById(user.getId())).willReturn(Optional.of(user));
        given(postsRepository.findById(100l)).willReturn(Optional.empty());

        Optional<PostedCommentDTO> postedCommentDTO = commentsService.postComment(commentsDTO, user.getId(), 100l);

        assertEquals(Optional.empty(), postedCommentDTO);
    }

    @Test
    public void postReply_success() {
        Comments comment = CommentsSampleData.generateComment();
        User user = UserSampleData.generateUser();
        CommentsDTO commentsDTO = CommentsSampleData.generateCommentsDTO();
        Comments replyComment = CommentsSampleData.generateReplyComment();

        given(usersRepository.findById(user.getId())).willReturn(Optional.of(user));
        given(commentsRepository.findById(comment.getId())).willReturn(Optional.of(comment));
        given(commentsRepository.save(new Comments().setUser(user).setPost(comment.getPost()).setContent(commentsDTO.getContent()).setReply(comment))).willReturn(replyComment);

        Optional<PostedReplyDTO> postedReplyDTO = commentsService.replyToAComment(commentsDTO, user.getId(), comment.getId());

        assertEquals(comment.getPost().getId(), postedReplyDTO.get().getPostId());
        assertEquals(comment.getId(), postedReplyDTO.get().getParentCommentId());
        assertEquals(user.getUserName(), postedReplyDTO.get().getUserName());
        assertEquals(commentsDTO.getContent(), postedReplyDTO.get().getContent());
    }

    @Test
    public void postReply_failure() {
        User user = UserSampleData.generateUser();
        CommentsDTO commentsDTO = CommentsSampleData.generateCommentsDTO();
        given(usersRepository.findById(user.getId())).willReturn(Optional.of(user));
        given(commentsRepository.findById(200l)).willReturn(Optional.empty());

        Optional<PostedReplyDTO> postedReplyDTO = commentsService.replyToAComment(commentsDTO, user.getId(), 200l);

        assertEquals(Optional.empty(), postedReplyDTO);
    }

    @Test
    public void fetchParentComments_success() {
        Posts post = PostsImagesSampleData.generatePost();
        Comments comment = CommentsSampleData.generateComment();
        List<Comments> commentsList = new ArrayList<>();
        commentsList.add(comment);

        given(postsRepository.findById(post.getId())).willReturn(Optional.of(post));
        given(commentsRepository.fetchAllByPostId(post.getId())).willReturn(commentsList);

        Optional<List<PostedCommentDTO>> postedCommentDTOs = commentsService.fetchParentComments(post.getId());

        assertEquals(comment.getContent(), postedCommentDTOs.get().get(0).getContent());
        assertEquals(comment.getPost().getId(), postedCommentDTOs.get().get(0).getPostId());
        assertEquals(comment.getUser().getUserName(), postedCommentDTOs.get().get(0).getUserName());
    }

    @Test
    public void fetchParentComments_failure() {
        given(postsRepository.findById(101l)).willReturn(Optional.empty());

        Optional<List<PostedCommentDTO>> postedCommentDTOs = commentsService.fetchParentComments(101l);
        assertEquals(Optional.empty(), postedCommentDTOs);
    }

    @Test
    public void fetchReplyComments_success() {
        Comments replyComment = CommentsSampleData.generateReplyComment();
        Comments comment = CommentsSampleData.generateComment();
        List<Comments> replyCommentsList = new ArrayList<>();
        replyCommentsList.add(replyComment);

        given(commentsRepository.findById(comment.getId())).willReturn(Optional.of(comment));
        given(commentsRepository.getAllByReply(comment)).willReturn(replyCommentsList);

        Optional<List<PostedReplyDTO>> postedReplyDTOs = commentsService.fetchReplyComments(comment.getId());

        assertEquals(replyComment.getContent(), postedReplyDTOs.get().get(0).getContent());
        assertEquals(replyComment.getPost().getId(), postedReplyDTOs.get().get(0).getPostId());
        assertEquals(replyComment.getUser().getUserName(), postedReplyDTOs.get().get(0).getUserName());
        assertEquals(replyComment.getReply().getId(), postedReplyDTOs.get().get(0).getParentCommentId());
    }

    @Test
    public void fetchReplyComments_failure() {
        given(commentsRepository.findById(201l)).willReturn(Optional.empty());

        Optional<List<PostedReplyDTO>> postedReplyDTOs = commentsService.fetchReplyComments(201l);
        assertEquals(postedReplyDTOs, postedReplyDTOs);
    }

    @Test
    public void updateComment_success() {
        Comments comment = CommentsSampleData.generateComment();
        CommentsDTO commentsDTO = CommentsSampleData.generateCommentsDTO();

        given(commentsRepository.findById(comment.getId())).willReturn(Optional.of(comment));
        given(commentsRepository.save(comment)).willReturn(comment);

        Optional<PostedCommentDTO> postedCommentDTO = commentsService.updateComment(comment.getId(), commentsDTO);
        assertEquals(commentsDTO.getContent(), postedCommentDTO.get().getContent());
    }

    @Test
    public void updateComment_failure() {
        CommentsDTO commentsDTO = CommentsSampleData.generateCommentsDTO();

        given(commentsRepository.findById(201l)).willReturn(Optional.empty());

        Optional<PostedCommentDTO> postedCommentDTO = commentsService.updateComment(201l, commentsDTO);
        assertEquals(Optional.empty(), postedCommentDTO);
    }

    @Test
    public void deleteComment_success() {
        Comments comment = CommentsSampleData.generateComment();

        given(commentsRepository.findById(comment.getId())).willReturn(Optional.of(comment));

        Optional<String> deleteStatus = commentsService.deleteComment(comment.getId());
        assertEquals("Comment with Id "+comment.getId()+" deleted!", deleteStatus.get());
    }

    @Test
    public void deleteComment_failure() {
        given(commentsRepository.findById(201l)).willReturn(Optional.empty());

        Optional<String> deleteStatus = commentsService.deleteComment(201l);
        assertEquals(Optional.empty(), deleteStatus);
    }

}
