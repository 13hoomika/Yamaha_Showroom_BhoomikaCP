package com.bcp.yamaha.service.user;

import com.bcp.yamaha.dto.FollowUpDto;
import com.bcp.yamaha.dto.UserDto;
import com.bcp.yamaha.entity.FollowUpEntity;
import com.bcp.yamaha.entity.UserEntity;
import com.bcp.yamaha.repository.user.FollowUpRepository;
import com.bcp.yamaha.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FollowUpServiceImpl implements FollowUpService {

    @Autowired
    private FollowUpRepository followUpRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    @Override
    public boolean saveFollowUp(FollowUpDto dto) {
        FollowUpEntity entity = new FollowUpEntity();
        //converting STo to entity
        entity.setFollowupDate(dto.getFollowupDate() != null ? dto.getFollowupDate(): LocalDate.now());
        entity.setCallStatus(dto.getCallStatus());
        entity.setNotes(dto.getNotes());
//        entity.setRescheduleDate(dto.getRescheduleDate());

        //handle user relationship
        UserEntity user = userRepository.findById(dto.getUserId());
        if (user == null){
            log.error("User with Id {} not found", dto.getUserId());
        }
        entity.setUser(user);
        boolean isSaved = followUpRepository.save(entity);
        if (isSaved){
            log.info("Followup saved successfully");
        }else log.error("Failed to save followup !!");
        return isSaved;
    }


    @Override
    public List<FollowUpDto> getFollowUpLogsByUserId(int userId) {
        List<FollowUpEntity> entities = followUpRepository.findByUserId(userId);

        return entities.stream()
                .map(entity -> {
                    FollowUpDto dto = new FollowUpDto();
                    dto.setFollowUpId(entity.getFollowUpId());
                    dto.setUserId(entity.getUser().getUserId()); // Get ID from UserEntity
//                    dto.setUserName(entity.getUser().getUserName());
                    dto.setFollowupDate(entity.getFollowupDate());
                    dto.setCallStatus(entity.getCallStatus());
                    dto.setNotes(entity.getNotes());
//                    dto.setRescheduleDate(entity.getRescheduleDate());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<FollowUpDto> getAllFollowups() {
        List<FollowUpEntity> entityList = followUpRepository.findAllFollowUps();
        List<FollowUpDto> dtoList = new ArrayList<>();

        if (entityList != null){
            for(FollowUpEntity entity : entityList){
                FollowUpDto dto = new FollowUpDto();
                BeanUtils.copyProperties(entity,dto);
                //Manually map nested user ID
                if (entity.getUser() != null) {
                    dto.setUserId(entity.getUser().getUserId());
                    dto.setUserName(entity.getUser().getUserName());
                }
                dtoList.add(dto);
                log.debug("followup details: {}", dtoList);
            }
        }
        return  dtoList;
    }
}
