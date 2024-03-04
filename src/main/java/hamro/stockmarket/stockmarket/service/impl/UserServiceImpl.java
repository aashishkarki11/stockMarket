package hamro.stockmarket.stockmarket.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hamro.stockmarket.stockmarket.Google.dto.UserDetailsDto;
import hamro.stockmarket.stockmarket.Google.service.ServiceImpl.TokenOptimizationService;
import hamro.stockmarket.stockmarket.entity.User;
import hamro.stockmarket.stockmarket.enums.Role;
import hamro.stockmarket.stockmarket.exception.NotFoundException;
import hamro.stockmarket.stockmarket.repository.UserRecordRepo;
import hamro.stockmarket.stockmarket.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the UserService interface providing methods to manage users.
 * Author: [Aashish Karki]
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
  private final TokenOptimizationService tokenOptimizationService;
  private final ObjectMapper objectMapper = new ObjectMapper();
  private final UserRecordRepo userRecordRepo;

  /**
   * Constructs a UserServiceImpl object with the specified dependencies.
   *
   * @param tokenOptimizationService The service for optimizing token operations.
   * @param userRecordRepo           The repository for managing user records.
   */
  public UserServiceImpl(TokenOptimizationService tokenOptimizationService,
      UserRecordRepo userRecordRepo) {
    this.tokenOptimizationService = tokenOptimizationService;
    this.userRecordRepo = userRecordRepo;
  }

  /**
   * Creates a new user based on the provided ID token.
   *
   * @param id The ID token for the user.
   */
  @Override
  public void createUser(String id) {
    try {
      User user = new User();
      UserDetailsDto userDetailsDto = tokenOptimizationService.decodeIdToken(id);

      String userDetail = objectMapper.writeValueAsString(userDetailsDto);
      user.setUserDetails(userDetail);

      User isUserPresent = userRecordRepo.findByEmail(userDetailsDto.getEmail());
      if (isUserPresent == null) {
        user.setRole(Role.USER);
        user.setIsActive(userDetailsDto.getEmailVerified().toString());
        user.setEmail(userDetailsDto.getEmail());
        userRecordRepo.save(user);
      }
    } catch (JsonProcessingException e) {
      throw new NotFoundException("Error creating user");
    }
  }
}
