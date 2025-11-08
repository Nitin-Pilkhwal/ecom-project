package com.demo.project.nitin.ecommerce.service.impl.seller;

import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import com.demo.project.nitin.ecommerce.dto.response.CategoryChainResponseDTO;
import com.demo.project.nitin.ecommerce.dto.response.ResponseDTO;
import com.demo.project.nitin.ecommerce.entity.Category;
import com.demo.project.nitin.ecommerce.mapper.ResponseMapper;
import com.demo.project.nitin.ecommerce.repository.CategoryRepo;
import com.demo.project.nitin.ecommerce.service.SellerCategoryService;
import com.demo.project.nitin.ecommerce.utils.MappingUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.demo.project.nitin.ecommerce.utils.CommonUtil.getEffectivePageable;

@Service
@Slf4j
@RequiredArgsConstructor
public class SellerCategoryServiceImpl implements SellerCategoryService {

    private final ResponseMapper responseMapper;
    private final CategoryRepo categoryRepo;

    @Override
    public ResponseDTO getAllCategories(Pageable pageable) {
        Pageable effectivePageable = getEffectivePageable(pageable);
        Page<Category> leafCategories = categoryRepo.findByIsLeafTrue(effectivePageable);
        Page<CategoryChainResponseDTO> categoryResponseDTOList = leafCategories.map(MappingUtil::toFullDTO);
        return responseMapper.toResponseDto(HttpStatus.OK, MessageKeys.CATEGORY_FETCHED,categoryResponseDTOList);
    }
}
