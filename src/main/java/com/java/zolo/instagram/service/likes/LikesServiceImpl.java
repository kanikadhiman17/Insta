package com.java.zolo.instagram.service.likes;

import com.java.zolo.instagram.domain.dto.likes.LikesDTO;
import com.java.zolo.instagram.domain.model.Comments;
import com.java.zolo.instagram.domain.model.Likes;
import com.java.zolo.instagram.domain.model.Posts;
import com.java.zolo.instagram.domain.model.User;
import com.java.zolo.instagram.repository.CommentsRepository;
import com.java.zolo.instagram.repository.LikesRepository;
import com.java.zolo.instagram.repository.PostsRepository;
import com.java.zolo.instagram.repository.UsersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikesServiceImpl implements LikesService{

    private final LikesRepository likesRepository;
    private final UsersRepository usersRepository;
    private final PostsRepository postsRepository;
    private final CommentsRepository commentsRepository;
    ModelMapper modelMapper = new ModelMapper();

    public LikesServiceImpl(LikesRepository likesRepository, UsersRepository usersRepository, PostsRepository postsRepository, CommentsRepository commentsRepository) {
        this.likesRepository = likesRepository;
        this.usersRepository = usersRepository;
        this.postsRepository = postsRepository;
        this.commentsRepository = commentsRepository;
    }

    @Override
    public Optional<String> likeAPost(long userId, long postId) {
        Optional<User> optionalUser = usersRepository.findById(userId);
        Optional<Posts> optionalPost = postsRepository.findById(postId);
        if(optionalPost.isEmpty())
            return Optional.empty();

        likesRepository.save(new Likes().setUser(optionalUser.get()).setTargetId(optionalPost.get()));
        return Optional.of("User "+optionalUser.get().getProfileName()+" liked the post with id " + postId);
    }

    @Override
    public Optional<String> likeAComment(long userId, long commentId) {
        Optional<User> optionalUser = usersRepository.findById(userId);
        Optional<Comments> optionalComment = commentsRepository.findById(commentId);
        if(optionalComment.isEmpty())
            return Optional.empty();

        likesRepository.save(new Likes().setUser(optionalUser.get()).setTargetId(optionalComment.get()));
        return Optional.of("User "+optionalUser.get().getProfileName()+" liked the comment with id " + commentId);
    }

    @Override
    public Optional<List<LikesDTO>> fetchLikesOnAPost(long postId) {
        Optional<Posts> optionalPost = postsRepository.findById(postId);
        if(optionalPost.isEmpty())
            return Optional.empty();

        List<Likes> likesList = likesRepository.getAllByPosts(postId);
        return Optional.of(likesList.stream().map(likes -> new LikesDTO(likes.getUser().getUserName(), likes.getLikedAt())).collect(Collectors.toList()));
    }

    @Override
    public Optional<List<LikesDTO>> fetchLikesOnAComment(long commentId) {
        Optional<Comments> optionalComment = commentsRepository.findById(commentId);
        if(optionalComment.isEmpty())
            return Optional.empty();

        List<Likes> likesList = likesRepository.getAllByComments(commentId);
        return Optional.of(likesList.stream().map(likes -> new LikesDTO(likes.getUser().getUserName(), likes.getLikedAt())).collect(Collectors.toList()));
    }

    @Override
    public Optional<String> unlikeAPost(long userId, long postId) {
        Optional<User> optionalUser = usersRepository.findById(userId);
        Optional<Likes> optionalLike = likesRepository.getByUserAndPost(userId, postId);
        if(optionalLike.isEmpty())
            return Optional.empty();
        likesRepository.delete(optionalLike.get());
        return Optional.of("User "+optionalUser.get().getProfileName()+" unliked the post with id " + postId);
    }

    @Override
    public Optional<String> unlikeAComment(long userId, long commentId) {
        Optional<User> optionalUser = usersRepository.findById(userId);
        Optional<Likes> optionalLike = likesRepository.getByUserAndComment(userId, commentId);
        if(optionalLike.isEmpty())
            return Optional.empty();
        likesRepository.delete(optionalLike.get());
        return Optional.of("User "+optionalUser.get().getProfileName()+" unliked the comment with id " + commentId);
    }
}
