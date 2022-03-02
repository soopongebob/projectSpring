package com.trafficLight.soo.controller;

import com.trafficLight.soo.entity.Comment;
import com.trafficLight.soo.entity.Post;
import com.trafficLight.soo.entity.User;
import com.trafficLight.soo.service.CommentService;
import com.trafficLight.soo.service.PostService;
import com.trafficLight.soo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;
    private final CommentService commentService;

    /**
     * 게시판 리스트
     * @param model
     * @return "post/list"
     */
    @GetMapping("/post/list")
    public String postList(Model model,
                           @PageableDefault(page = 0, size = 5, sort="postIdx", direction = Sort.Direction.DESC)
                           Pageable pageable){
        //페이징
        Page<PostListForm> posts = postService.findAll(pageable);
        model.addAttribute("posts", posts);
        return "post/list";
    }

    /**
     * 글쓰기
     * @param model
     * @return "post/write"
     */
    @GetMapping("/post/write")
    public String postWriteForm(Model model){
        model.addAttribute("postWriteForm", new PostWriteForm());
        return "post/write";
    }

    /**
     * 글쓰기 저장
     * @param postWriteForm
     * @param user
     * @return "redirect:/post/list"
     */
    @PostMapping("/post/write")
    public String postWrite(@Valid PostWriteForm postWriteForm, @AuthenticationPrincipal User user){
        System.out.println("글 저장 -----");
        System.out.println("user.getUserId : " + user.getUserId());
        Optional<User> writer = userService.getUser(user.getUserId());
        Post post = Post.builder()
                .subject(postWriteForm.getSubject())
                .content(postWriteForm.getContent())
                .viewCount(0L)
                .user(writer.get())
                .comments(null)
                .build();
        postService.save(post);

        return "redirect:/post/list";
    }

    /**
     * 글 보기
     * @param postIdx
     * @param model
     * @param user
     * @return "post/view"
     */
    @GetMapping("/post/view/{postIdx}")
    public String postView(@PathVariable("postIdx") Long postIdx, Model model, @AuthenticationPrincipal User user){
        System.out.println("postIdx = " + postIdx);

        Post getPost = postService.findByPostIdx(postIdx);
        String ldt = getPost.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-mm-dd HH:mm"));
        PostViewForm post = new PostViewForm(
                getPost.getPostIdx(),
                getPost.getSubject(),
                getPost.getContent(),
                getPost.getUser().getUsername(),
                getPost.getViewCount(),
                ldt,
                getPost.getComments()
        );

        System.out.println("authenticationPrincipal username : " + user.getUsername());
        String auth = (user.getUsername().equals(getPost.getUser().getUsername())) ? "own" : "";
        System.out.println("auth = " + auth);
        model.addAttribute("auth", auth);
        model.addAttribute("post", post);
        model.addAttribute("comments", post.getComments());

        CommentForm commentForm = new CommentForm();
        commentForm.setPostIdx(postIdx);
        model.addAttribute("commentForm", commentForm);

        return "post/view";
    }

    /**
     * 글 수정
     * @param postIdx
     * @param model
     * @return "post/edit"
     */
    @GetMapping("/post/edit/{postIdx}")
    public String postEdit(@PathVariable("postIdx") Long postIdx, Model model){
        System.out.println("---수정---");
        Post post = postService.findByPostIdx(postIdx);
        PostEditForm postEditForm = new PostEditForm(
                post.getPostIdx(),
                post.getSubject(),
                post.getContent()
        );
        System.out.println("content : " + postEditForm.getContent());
        System.out.println("subject : " + postEditForm.getSubject());
        model.addAttribute("postEditForm", postEditForm);

        return "post/edit";
    }

    /**
     * 수정 글 저장
     * @param postIdx
     * @param postEditForm
     * @return "redirect:/post/view?postIdx="+postIdx
     */
    @PostMapping("/post/edit/{postIdx}")
    public String postEdit(@PathVariable("postIdx") Long postIdx, @Valid PostEditForm postEditForm){
        System.out.println("----------수정 저장--------");
        System.out.println("postIdx : " + postIdx);
        System.out.println("postEditForm : " + postEditForm.getSubject());
        System.out.println("postEditForm : " + postEditForm.getContent());
        Post post = postService.findByPostIdx(postIdx);
        post.editPost(
                postEditForm.getSubject(),
                postEditForm.getContent()
        );
        postService.save(post);

        return "redirect:/post/view/"+postIdx;
    }

    @GetMapping("/post/delete/{postIdx}")
    public String postDelete(@PathVariable("postIdx") Long postIdx){
        System.out.println("-----삭제-----");
        postService.delete(postIdx);
        return "redirect:/post/list";
    }

    /**
     * 댓글 저장
     */
    @PostMapping("/post/comment/{postIdx}")
    public String commentSave(@PathVariable("postIdx") Long postIdx,
                              @Valid CommentForm commentForm,
                              @AuthenticationPrincipal User authUser){
        System.out.println("postIdx : " + postIdx);
        Post post = postService.findByPostIdx(postIdx);
        Optional<User> user = userService.getUser(authUser.getUserId());
        System.out.println("post객체 : " + post.getPostIdx());
        System.out.println("user객체 : " + user.get().getUserId());
        Comment comment = new Comment(commentForm.getContent());
        comment.setPost(post);
        comment.setUser(user.get());
        commentService.save(comment);

        return "redirect:/post/view/"+postIdx;
    }
}
