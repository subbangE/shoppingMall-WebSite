package com.myapp.team.option;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OptionMapper {

    List<Option> findAllOptions();

    // 옵션 추가(생성)
    void insertOption(Option option);

    // 옵션 수정
    void updateOption(Option option);

    // 옵션 삭제
    void deleteOption(int optionNo);

    // 옵션 조회
    Option findOptionById(int optionNo);

    // 상품별 옵션 리스트 조회
    List<Option> selectOptionListByProduct(int productNo);

}