package com.java.zolo.instagram.service.postImages;

import com.java.zolo.instagram.domain.dto.postImages.PostImagesDTO;
import com.java.zolo.instagram.domain.dto.postImages.PostUpdateDTO;
import com.java.zolo.instagram.domain.model.Images;
import com.java.zolo.instagram.domain.model.Posts;
import com.java.zolo.instagram.domain.model.User;
import com.java.zolo.instagram.repository.ImagesRepository;
import com.java.zolo.instagram.repository.PostsRepository;
import com.java.zolo.instagram.repository.UsersRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PostImagesServiceImpl  implements PostImagesService {

    public final ImagesRepository imagesRepository;
    public final PostsRepository postsRepository;
    public final UsersRepository usersRepository;

    public PostImagesServiceImpl(ImagesRepository imagesRepository, PostsRepository postsRepository, UsersRepository usersRepository) {
        this.imagesRepository = imagesRepository;
        this.postsRepository = postsRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    @Transactional
    public Optional<PostImagesDTO> uploadPostAndImages(PostImagesDTO postImagesDTO, long userId) {
        Optional<User> user = usersRepository.findById(userId);
        if(user.isEmpty())
            return Optional.empty();
        // TODO: getDeclaredFields
        if(postImagesDTO.getImageURLs().size()==0)
            return Optional.empty();

        // Save Post
        Posts post = postsRepository.save(new Posts()
                .setUser(user.get())
                .setCaption(postImagesDTO.getCaption())
                .setLocation(postImagesDTO.getLocation()));

        // Save Images
        postImagesDTO.getImageURLs().forEach(imageURL -> imagesRepository.save(new Images().setPost(post).setImageUrl(imageURL)));
        return Optional.of(postImagesDTO);
    }

    @Override
    public Optional<List<PostImagesDTO>> fetchPostsFromUser(long userId) {
        Optional<User> optionalUser = usersRepository.findById(userId);
        if(optionalUser.isEmpty())
            return Optional.empty();

        // Return PostImagesDTO
        List<Posts> postList = postsRepository.getAllByUser(optionalUser.get());
        List<PostImagesDTO> postImagesDTOList = postList.stream().map(post -> new PostImagesDTO()
                .setCaption(post.getCaption())
                .setLocation(post.getLocation())
                .setImageURLs(imagesRepository.getAllByPost(post).stream().map(Images::getImageUrl).collect(Collectors.toList()))).collect(Collectors.toList());
        return Optional.of(postImagesDTOList);
    }

    @Override
    public Optional<PostImagesDTO> fetchPostsFromId(long postId) {
        Optional<Posts> optionalPost = postsRepository.findById(postId);
        if(optionalPost.isEmpty())
            return Optional.empty();
        PostImagesDTO postImageDTO = new PostImagesDTO().setCaption(optionalPost.get().getCaption())
                .setLocation(optionalPost.get().getLocation()).setImageURLs(imagesRepository.getAllByPost(optionalPost.get()).stream().map(Images::getImageUrl).collect(Collectors.toList()));
        return Optional.of(postImageDTO);
    }

    @Override
    public Optional<PostImagesDTO> editPost(long postId, PostUpdateDTO postUpdateDTO) {
        Optional<Posts> optionalPost = postsRepository.findById(postId);
        if(optionalPost.isEmpty())
            return Optional.empty();

        Posts post = optionalPost.get();
        if(postUpdateDTO.getLocation() != null) {
            post.setLocation(postUpdateDTO.getLocation());
        }
        if(postUpdateDTO.getCaption() != null) {
            post.setCaption(postUpdateDTO.getCaption());
        }
        post = postsRepository.save(post);
        PostImagesDTO postImageDTO = new PostImagesDTO().setCaption(post.getCaption())
                .setLocation(post.getLocation()).setImageURLs(imagesRepository.getAllByPost(post).stream().map(Images::getImageUrl).collect(Collectors.toList()));
        return Optional.of(postImageDTO);
    }

    @Override
    public Optional<String> deletePost(long postId) {
        Optional<Posts> optionalPost = postsRepository.findById(postId);
        if(optionalPost.isEmpty())
            return Optional.empty();
        postsRepository.deleteById(postId);
        return Optional.of("Post with id "+postId+" deleted!");
    }
}
