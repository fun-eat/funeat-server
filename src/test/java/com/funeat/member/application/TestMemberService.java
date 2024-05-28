package com.funeat.member.application;

import com.funeat.auth.dto.SignUserDto;
import com.funeat.auth.dto.UserInfoDto;
import com.funeat.common.ImageUploader;
import com.funeat.member.persistence.MemberRepository;
import com.funeat.recipe.persistence.RecipeRepository;
import com.funeat.review.persistence.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestMemberService extends MemberService {

    public TestMemberService(final MemberRepository memberRepository, final ReviewRepository reviewRepository,
                             final RecipeRepository recipeRepository, final ImageUploader imageUploader) {
        super(memberRepository, reviewRepository, recipeRepository, imageUploader);
    }

    @Override
    @Transactional
    public SignUserDto findOrCreateMember(final UserInfoDto userInfoDto) {
        return super.findOrCreateMember(userInfoDto);
    }
}
