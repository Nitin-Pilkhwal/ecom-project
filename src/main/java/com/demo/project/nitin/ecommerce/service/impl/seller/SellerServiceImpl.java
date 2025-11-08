package com.demo.project.nitin.ecommerce.service.impl.seller;

import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import com.demo.project.nitin.ecommerce.dto.request.update.UpdateSellerDTO;
import com.demo.project.nitin.ecommerce.dto.response.ResponseDTO;
import com.demo.project.nitin.ecommerce.dto.response.SellerProfileDTO;
import com.demo.project.nitin.ecommerce.entity.Seller;
import com.demo.project.nitin.ecommerce.entity.User;
import com.demo.project.nitin.ecommerce.mapper.ResponseMapper;
import com.demo.project.nitin.ecommerce.mapper.UserMapper;
import com.demo.project.nitin.ecommerce.service.SellerService;
import com.demo.project.nitin.ecommerce.service.helper.UserHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.demo.project.nitin.ecommerce.utils.MappingUtil.mapNonNullUserFields;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final UserHelperService userHelperService;
    private final ResponseMapper responseMapper;
    private final UserMapper userMapper;

    @Override
    public ResponseDTO viewProfile(String userName) {
        User user = userHelperService.findByEmail(userName);
        SellerProfileDTO sellerProfileDTO = userMapper.toProfileDto((Seller) user);
        sellerProfileDTO.setImage(
                userHelperService.profileImage(user.getId().toString())
        );
        return responseMapper.toResponseDto(HttpStatus.OK,
                MessageKeys.SELLER_PROFILE_VIEW,
                sellerProfileDTO
        );
    }

    @Override
    public ResponseDTO updateProfile(String userName, UpdateSellerDTO dto) {
        User user = userHelperService.findByEmail(userName);
        userHelperService.validateSellerUpdate(user, dto);
        userMapper.updateEntityFromDto(dto, (Seller) user);
        userHelperService.updateUser(user);
        Map<String, Object> updatedFields = mapNonNullUserFields(dto);
        return responseMapper.toResponseDto(HttpStatus.OK,
                MessageKeys.SELLER_PROFILE_UPDATED,
                updatedFields
        );
    }
}
