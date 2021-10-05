package net.cryls.backendcursojava.services;

import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cryls.backendcursojava.UserRepository;
import net.cryls.backendcursojava.entities.UserEntity;
import net.cryls.backendcursojava.shared.dto.UserDTO;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDTO createUser(UserDTO userDTO) {


        if(userRepository.findUserByEmail(userDTO.getEmail())!=null) throw new RuntimeException("El email ya existe");
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDTO, userEntity);
        
        userEntity.setEncryptedPassword("testpassword");
        
        UUID userId = UUID.randomUUID();
        userEntity.setUserId(userId.toString());
        

        UserEntity storedUserDetail = userRepository.save(userEntity);

        userRepository.save(userEntity);

        UserDTO userToReturn = new UserDTO();
        BeanUtils.copyProperties(storedUserDetail, userToReturn);
        return userToReturn;

        
    }

}
