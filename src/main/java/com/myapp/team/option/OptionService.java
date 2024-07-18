package com.myapp.team.option;

import com.myapp.team.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionService {

    private final OptionMapper optionMapper;

    @Autowired
    public OptionService(OptionMapper optionMapper) {
        this.optionMapper = optionMapper;
    }

    // 옵션 추가(생성)
    public void insertOption(Product product, Option option) {
        option.setProduct(product);
        optionMapper.insertOption(option);
    }

    // 옵션 수정
    public void updateOption(Option option) {
        optionMapper.updateOption(option);
    }

    // 옵션 삭제
    public void deleteOption(int productNo) {
        optionMapper.deleteOption(productNo);
    }

    // 옵션 조회
//    public List<Option> findAllOptions() {
//        return OptionMapper.findAllOptions();
//    }

//    // 상품에 옵션 추가
//    public void addOptionToProduct(Product product, Option option) {
//        option.setProduct(product);
//        optionMapper.insertOption(option);
//    }

    // 상품별 옵션 리스트 조회
    public List<Option> selectOptionListByProduct(int productNo) {
        return optionMapper.selectOptionListByProduct(productNo);
        }
    }