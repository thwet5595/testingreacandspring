package com.thwet.react.test.web;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thwet.react.test.dto.CommentDto;
import com.thwet.react.test.dto.PostDto;
import com.thwet.react.test.entity.Comment;
import com.thwet.react.test.entity.Post;
import com.thwet.react.test.entity.User;
import com.thwet.react.test.response.PostAndCommentResponse;
import com.thwet.react.test.service.CommentService;
import com.thwet.react.test.service.PostService;
import com.thwet.react.test.service.UserService;

@Controller
public class PostController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PostService postService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private CommentService commentService;
	
//	@Autowired
//    private HttpServletRequest request;

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String showUser(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("Enter user success::::");
		List<Post> posts = postService.findAll();
		model.addAttribute("posts", posts);
		return "posts";
	}

	@RequestMapping(value = "/user/posts/all", method = RequestMethod.GET)
	@ResponseBody
	public List<PostDto> showPosts(Model model) {
		List<Post> postList = postService.findAll();
		List<PostDto> postDtos = postList.stream().map(posts -> postService.convertDto(posts))
				.collect(Collectors.toList());
		return postDtos;
	}

	@RequestMapping(value = "/user/my/questions", method = RequestMethod.GET)
	@ResponseBody
	public List<PostDto> showMyPosts(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		List<Post> postList = postService.findPostByUserId(user.getId());
		List<PostDto> postDtos = postList.stream().map(posts -> postService.convertDto(posts))
				.collect(Collectors.toList());
		return postDtos;
	}
	
	@RequestMapping(value = "/user/add/posts", method = RequestMethod.POST)
	@ResponseBody
	public List<PostDto> addPost(HttpServletRequest request, HttpServletResponse response, Model model,
			@Validated @RequestBody PostDto postDto, BindingResult result) {
		logger.info("Enter user success::::");
		logger.info("Post title::"+postDto.getTitle()+"::Post question::"+postDto.getQuestions());
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		Post post = new Post();
		post.setTitle(postDto.getTitle());
		post.setDeleted("0");
		post.setQuestions(postDto.getQuestions());
		post.setUser(user);
		postService.save(post);
		List<Post> postList = postService.findAll();
		List<PostDto> postDtos = postList.stream().map(posts -> postService.convertDto(posts))
				.collect(Collectors.toList());
		return postDtos;
	}
	
	@RequestMapping(value = "/user/posts/details/{id}", method = RequestMethod.GET)
	public String showPostsDetails(HttpServletRequest request, HttpServletResponse response,Model model,@PathVariable("id") String id) {
		logger.info("Enter posts details::"+id);
		model.addAttribute("postId", id);
		int postId = Integer.parseInt(id);
		Post post = postService.findById(postId);
		model.addAttribute("postTitle", post.getTitle());
		model.addAttribute("postNames", post.getQuestions());
		model.addAttribute("user", post.getUser());
		return "postsDetails";
	}
	
	@RequestMapping(value = "/user/posts/postId/{id}/details", method = RequestMethod.GET)
	@ResponseBody
	public PostAndCommentResponse showPostsDetails(Model model,@PathVariable("id") String id) {
		logger.info("Enter post details response :::>>>");
		PostAndCommentResponse postAndCommentResponse = new PostAndCommentResponse();
		int postId = Integer.parseInt(id);
		Post post = postService.findById(postId);
		postAndCommentResponse.setId(""+post.getId());
		postAndCommentResponse.setQuestions(post.getQuestions());
		postAndCommentResponse.setTitle(post.getTitle());
		
		List<Comment> commentsList = commentService.findByPostId(postId);
		logger.info("Comments List size is::::"+commentsList.size());
		List<CommentDto> commentDtos = commentsList.stream().map(comments -> commentService.convertDto(comments))
				.collect(Collectors.toList());
		postAndCommentResponse.setCommentDto(commentDtos);
		return postAndCommentResponse;
	}
	
	@RequestMapping(value = "/user/addComment/{postId}", method = RequestMethod.POST)
	@ResponseBody
	public List<CommentDto> addComment(HttpServletRequest request, HttpServletResponse response, Model model,@PathVariable("postId") String postId,
			@Validated @RequestBody CommentDto commentDto, BindingResult result) {
		logger.info("Enter user success::::");
		logger.info("Post title::"+commentDto.getComments()+":::Post id is::"+postId);
		Post post = new Post();
		post.setId(Integer.parseInt(postId));
		
		Comment comment = new Comment();
		comment.setComments(commentDto.getComments());
		comment.setPost(post);
		commentService.save(comment);
		List<Comment> commentsList = commentService.findByPostId(Integer.parseInt(postId));
		logger.info("Comments List size is::::"+commentsList.size());
		List<CommentDto> commentDtos = commentsList.stream().map(comments -> commentService.convertDto(comments))
				.collect(Collectors.toList());
		return commentDtos;
	}
}