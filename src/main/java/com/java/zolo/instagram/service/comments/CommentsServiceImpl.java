package com.java.zolo.instagram.service.comments;

import com.java.zolo.instagram.domain.dto.comments.CommentsDTO;
import com.java.zolo.instagram.domain.dto.comments.PostedCommentDTO;
import com.java.zolo.instagram.domain.dto.comments.PostedReplyDTO;
import com.java.zolo.instagram.domain.model.Comments;
import com.java.zolo.instagram.domain.model.Posts;
import com.java.zolo.instagram.domain.model.User;
import com.java.zolo.instagram.repository.CommentsRepository;
import com.java.zolo.instagram.repository.PostsRepository;
import com.java.zolo.instagram.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentsServiceImpl implements CommentsService{
    private final CommentsRepository commentsRepository;
    private final PostsRepository postsRepository;
    private final UsersRepository usersRepository;

    public CommentsServiceImpl(CommentsRepository commentsRepository, PostsRepository postsRepository, UsersRepository usersRepository) {
        this.commentsRepository = commentsRepository;
        this.postsRepository = postsRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public Optional<PostedCommentDTO> postComment(CommentsDTO commentsDTO, long userId, long postId) {
        Optional<User> optionalUser = usersRepository.findById(userId);
        Optional<Posts> optionalPost = postsRepository.findById(postId);
        if(optionalPost.isEmpty())
            return Optional.empty();

        Comments comment = commentsRepository.save(new Comments().setUser(optionalUser.get()).setPost(optionalPost.get()).setContent(commentsDTO.getContent()));
        return Optional.of(new PostedCommentDTO(postId, optionalUser.get().getUserName(), comment.getContent(), comment.getCommentedAt()));
    }

    @Override
    public Optional<PostedReplyDTO> replyToAComment(CommentsDTO commentsDTO, long userId, long commentId) {
        Optional<User> optionalUser = usersRepository.findById(userId);
        Optional<Comments> optionalComment = commentsRepository.findById(commentId);
        if(optionalComment.isEmpty())
            return Optional.empty();

        Comments comment = commentsRepository.save(new Comments().setUser(optionalUser.get()).setPost(optionalComment.get().getPost()).setContent(commentsDTO.getContent()).setReply(optionalComment.get()));
        return Optional.of(new PostedReplyDTO(optionalComment.get().getPost().getId(), commentId, optionalUser.get().getUserName(), comment.getContent(), comment.getCommentedAt()));
    }

    @Override
    public Optional<List<PostedCommentDTO>> fetchComments(long postId) {
        Optional<Posts> optionalPost = postsRepository.findById(postId);
        if(optionalPost.isEmpty())
            return Optional.empty();
        List<Comments> comments = commentsRepository.getAllByPost(optionalPost.get());
        return Optional.of(comments.stream().map(comment -> new PostedCommentDTO(comment.getPost().getId(), comment.getUser().getUserName(), comment.getContent(), comment.getCommentedAt())).collect(Collectors.toList()));
    }

    @Override
    public Optional<PostedCommentDTO> updateComment(long commentId, CommentsDTO commentDTO) {
        Optional<Comments> optionalComment = commentsRepository.findById(commentId);
        if(optionalComment.isEmpty())
            return Optional.empty();
        optionalComment.get().setContent(commentDTO.getContent());
        Comments updatedComment = commentsRepository.save(optionalComment.get());
        return Optional.of(new PostedCommentDTO(updatedComment.getPost().getId(), updatedComment.getUser().getUserName(), updatedComment.getContent(), updatedComment.getCommentedAt()));
    }

    @Override
    public Optional<String> deleteComment(long commentId) {
        Optional<Comments> optionalComment = commentsRepository.findById(commentId);
        if(optionalComment.isEmpty())
            return Optional.empty();
        commentsRepository.deleteById(commentId);
        return Optional.of("Comment with Id "+commentId+" deleted!");
    }
}
