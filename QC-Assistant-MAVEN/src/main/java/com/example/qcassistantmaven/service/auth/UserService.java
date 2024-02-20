package com.example.qcassistantmaven.service.auth;

import com.example.qcassistantmaven.domain.dto.auth.UserDisplayDto;
import com.example.qcassistantmaven.domain.dto.auth.UserRegistrationDto;
import com.example.qcassistantmaven.domain.dto.auth.UserRoleEditDto;
import com.example.qcassistantmaven.domain.entity.auth.RoleEntity;
import com.example.qcassistantmaven.domain.entity.auth.UserEntity;
import com.example.qcassistantmaven.domain.enums.RoleEnum;
import com.example.qcassistantmaven.exception.ExistingUsernameException;
import com.example.qcassistantmaven.exception.UnconfirmedPasswordException;
import com.example.qcassistantmaven.repository.auth.RoleRepository;
import com.example.qcassistantmaven.repository.auth.UserRepository;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private UserDetailsService userDetailsService;
    private ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void init(){
        initRoles();
        initAdmin();
    }

    private void initRoles() {
        if(this.roleRepository.count() == 0){
            RoleEntity user = new RoleEntity(RoleEnum.USER);
            RoleEntity mod = new RoleEntity(RoleEnum.MODERATOR);
            RoleEntity admin = new RoleEntity(RoleEnum.ADMINISTRATOR);
            this.roleRepository.save(user);
            this.roleRepository.save(mod);
            this.roleRepository.save(admin);
        }
    }

    private void initAdmin() {
        if(this.userRepository.count() == 0){
            UserEntity admin = new UserEntity();
            List<RoleEntity> roles = this.roleRepository.findAll();
            admin.setUsername("testadmin")
                    .setPassword(this.passwordEncoder
                            .encode("testadmin"))
                    .setRoles(roles);
            this.userRepository.save(admin);
        }
    }

    public List<UserDisplayDto> getAllUsers(){
        return this.userRepository.findAll().stream()
                .map(u -> this.modelMapper.map(u, UserDisplayDto.class))
                .collect(Collectors.toList());
    }

    public UserDisplayDto displayUser(Long id){
        return this.userRepository.findById(id)
                .map(u -> this.modelMapper.map(u, UserDisplayDto.class))
                .orElseThrow();
    }

    public void editUserRoles(UserRoleEditDto userRoleEditDto){
        UserEntity user = this.userRepository
                .findUserEntityByUsername(userRoleEditDto.getUsername())
                .orElseThrow();

        List<RoleEntity> roles = new ArrayList<>();
        roles.add(this.roleRepository
                .findRoleEntityByRole(RoleEnum.USER).get());
        if(userRoleEditDto.getRoleLevel().equals(RoleEnum.MODERATOR.name())){
            roles.add(this.roleRepository.
                    findRoleEntityByRole(RoleEnum.MODERATOR).get());
        }
        if(userRoleEditDto.getRoleLevel().equals(RoleEnum.ADMINISTRATOR.name())){
            roles.add(this.roleRepository.
                    findRoleEntityByRole(RoleEnum.MODERATOR).get());
            roles.add(this.roleRepository.
                    findRoleEntityByRole(RoleEnum.ADMINISTRATOR).get());
        }
        user.setRoles(roles);
        this.userRepository.save(user);
    }

    public void registerUser(UserRegistrationDto registrationDTO,
                             Consumer<Authentication> successfulLoginProcessor){

        validateUserRegistration(registrationDTO);

        UserEntity userEntity = new UserEntity()
                .setUsername(registrationDTO.getUsername())
                .setPassword(this.passwordEncoder
                        .encode(registrationDTO.getPassword()));
        List<RoleEntity> roles = List.of(this.roleRepository
                .findRoleEntityByRole(RoleEnum.USER).get());
        userEntity.setRoles(roles);

        userRepository.save(userEntity);

        UserDetails userDetails = userDetailsService
                .loadUserByUsername(registrationDTO.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );

        successfulLoginProcessor.accept(authentication);
    }

    private void validateUserRegistration(UserRegistrationDto registrationDTO){
        if(!registrationDTO.getPassword().equals(registrationDTO.getConfirmPassword())){
            throw new UnconfirmedPasswordException();
        }
        if(this.userRepository.findUserEntityByUsername(registrationDTO.getUsername())
                .isPresent()){
            throw new ExistingUsernameException();
        }
    }

    public int getCountOfAdmins() {
        return this.userRepository.findAllByRolesContaining(
                roleRepository.findRoleEntityByRole(RoleEnum.ADMINISTRATOR).get())
                .size();
    }

    public String getLastAdminUsername() {
        return this.userRepository.findAllByRolesContaining(roleRepository
                .findRoleEntityByRole(RoleEnum.ADMINISTRATOR).get())
                .get(0)
                .getUsername();
    }
}
