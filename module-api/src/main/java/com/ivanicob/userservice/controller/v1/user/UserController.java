package com.ivanicob.userservice.controller.v1.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ivanicob.userservice.dto.model.user.UserDTO;
import com.ivanicob.userservice.dto.response.Response;
import com.ivanicob.userservice.model.User;
import com.ivanicob.userservice.service.UserService;
import com.ivanicob.userservice.util.UserServiceApiUtil;
import com.ivanicob.userservice.util.exceptions.NotParsableContentException;
import com.ivanicob.userservice.util.exceptions.UserInvalidDeleteException;
import com.ivanicob.userservice.util.exceptions.UserInvalidUpdateException;
import com.ivanicob.userservice.util.exceptions.UserNotFoundException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@Api(value = "User Service")
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "*")
public class UserController {
	
	@Autowired
	private UserService service;
	
	 @ApiOperation(value = "Route to find a User by your id in the API", response = UserDTO.class)
		@ApiResponses(value = {
		     @ApiResponse(code = 200, message = "ok"),
		     @ApiResponse(code = 400, message = "Bad Request"),
		     @ApiResponse(code = 401, message = "Not authorized"),
		     @ApiResponse(code = 403, message = "Not authenticated"),
		     @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
		     @ApiResponse(code = 500, message = "Interval Server Error")
	})	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<UserDTO>> findById(@RequestHeader(value=UserServiceApiUtil.HEADER_USER_SERVICE_API_VERSION, defaultValue="${api.version}")  String apiVersion, 
			@RequestHeader(value=UserServiceApiUtil.HEADER_API_KEY, defaultValue="${api.key}") String apiKey, 
			@PathVariable("id") Long userId, 
			@RequestParam(required = false) String fields) 
			throws UserNotFoundException {
		
		Response<UserDTO> response = new Response<>();
		User user = service.findById(userId);
		
		UserDTO dto = user.convertEntityToDTO();
		
		createSelfLink(user, dto);
		response.setData(dto);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(UserServiceApiUtil.HEADER_USER_SERVICE_API_VERSION, apiVersion);
		headers.add(UserServiceApiUtil.HEADER_API_KEY, apiKey);
		
		return new ResponseEntity<>(response, headers, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Route to find all Users in the API")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<List<UserDTO>>> findAll(@RequestHeader(value=UserServiceApiUtil.HEADER_USER_SERVICE_API_VERSION, defaultValue="${api.version}")  String apiVersion, 
			@RequestHeader(value=UserServiceApiUtil.HEADER_API_KEY, defaultValue="${api.key}") String apiKey, 
			@RequestParam(required = false) String fields) 
			throws UserNotFoundException {
		
		Response<List<UserDTO>> response = new Response<>();
		List<User> users = service.findAll();
		
		if (users.isEmpty())
			throw new UserNotFoundException("There are no Users registered");
		
		List<UserDTO> usersDTO = new ArrayList<>();
		users.stream().forEach(t -> usersDTO.add(t.convertEntityToDTO()));
		
		usersDTO.stream().forEach(dto -> {
			try {
				createSelfLinkInCollections(apiVersion, apiKey, dto);
			} catch (UserNotFoundException e) {
				log.error("There are no Users registered");
			}
		});
		
		response.setData(usersDTO);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(UserServiceApiUtil.HEADER_USER_SERVICE_API_VERSION, apiVersion);
		headers.add(UserServiceApiUtil.HEADER_API_KEY, apiKey);
		
		return new ResponseEntity<>(response, headers, HttpStatus.OK);
	}	
	
	@ApiOperation(value = "Route to create a new User in the API")
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<UserDTO>> create(@RequestHeader(value=UserServiceApiUtil.HEADER_USER_SERVICE_API_VERSION, defaultValue="${api.version}") String apiVersion, 
			@RequestHeader(value=UserServiceApiUtil.HEADER_API_KEY, defaultValue="${api.key}") String apiKey, 
			@Valid @RequestBody UserDTO dto, BindingResult result) 
			throws NotParsableContentException {
		
		Response<UserDTO> response = new Response<>();
		
		if(result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.addErrorMsgToResponse(error.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		
		User user = service.save(dto.convertDTOToEntity());
		UserDTO userDTO = user.convertEntityToDTO();
		
		createSelfLink(user, dto);
		response.setData(userDTO);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(UserServiceApiUtil.HEADER_USER_SERVICE_API_VERSION, apiVersion);
		
		return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Route to update a User by your id in the API")
	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<UserDTO>> update(@RequestHeader(value=UserServiceApiUtil.HEADER_USER_SERVICE_API_VERSION, defaultValue="${api.version}") String apiVersion, 
			@RequestHeader(value=UserServiceApiUtil.HEADER_API_KEY, defaultValue="${api.key}") String apiKey, 
			@Valid @RequestBody UserDTO dto, BindingResult result,
			@PathVariable("id") Long userId, 
			@RequestParam(required = false) String fields)
			throws UserNotFoundException, UserInvalidUpdateException, NotParsableContentException {
		
		Response<UserDTO> response = new Response<>();
		
		if(result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.addErrorMsgToResponse(error.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		
		User userDB = service.findById(userId);
		if (userDB.getId().compareTo(userId) != 0)
			throw new UserInvalidUpdateException("You don't have permission to change the user id=" + dto.getId());
		
		User user = service.save(dto.convertDTOToEntity(userDB));
		UserDTO userDTO = user.convertEntityToDTO();
		
		createSelfLink(user, dto);
		response.setData(userDTO);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(UserServiceApiUtil.HEADER_USER_SERVICE_API_VERSION, apiVersion);
		headers.add(UserServiceApiUtil.HEADER_API_KEY, apiKey);
		
		return new ResponseEntity<>(response, headers, HttpStatus.OK);		
	}	

	@ApiOperation(value = "Route to delete a User by your id in the API")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> delete(@RequestHeader(value=UserServiceApiUtil.HEADER_USER_SERVICE_API_VERSION, defaultValue="${api.version}") String apiVersion, 
			@RequestHeader(value=UserServiceApiUtil.HEADER_API_KEY, defaultValue="${api.key}") String apiKey,
			@PathVariable("id") Long userId,
			@RequestParam(required = false) String fields) 
			throws UserNotFoundException, UserInvalidDeleteException {
		
		Response<String> response = new Response<>();
		User userDB = service.findById(userId);
		
		if (userDB.getId().compareTo(userId) != 0)
			throw new UserInvalidDeleteException("You don't have permission to delete the user id=" + userId);
			
		service.deleteById(userDB.getId());
		response.setData("User id=" + userDB.getId() + " successfully deleted");
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(UserServiceApiUtil.HEADER_USER_SERVICE_API_VERSION, apiVersion);
		headers.add(UserServiceApiUtil.HEADER_API_KEY, apiKey);
		
		return new ResponseEntity<>(response, headers, HttpStatus.NO_CONTENT);
	}
	
	private void createSelfLink(User travel, UserDTO userDTO) {
		Link selfLink = WebMvcLinkBuilder.linkTo(UserController.class).slash(travel.getId()).withSelfRel();
		userDTO.add(selfLink);
	}
	
	private void createSelfLinkInCollections(String apiVersion, String apiKey, final UserDTO userDTO) throws UserNotFoundException {
		Link selfLink = linkTo(methodOn(UserController.class).findById(apiVersion, apiKey, userDTO.getId(), null)).withSelfRel().expand();
		userDTO.add(selfLink);
	}	

}
