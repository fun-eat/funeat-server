package com.funeat.member.domain;

import static com.funeat.member.exception.MemberErrorCode.MEMBER_UPDATE_ERROR;

import com.funeat.member.domain.bookmark.RecipeBookmark;
import com.funeat.member.domain.favorite.RecipeFavorite;
import com.funeat.member.domain.favorite.ReviewFavorite;
import com.funeat.member.exception.MemberException.MemberUpdateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import org.springframework.util.StringUtils;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    private String profileImage;

    private String platformId;

    @OneToMany(mappedBy = "member")
    private List<ReviewFavorite> reviewFavorites = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<RecipeFavorite> recipeFavorites;

    @OneToMany(mappedBy = "member")
    private List<RecipeBookmark> recipeBookmarks;

    protected Member() {
    }

    public Member(final String nickname, final String profileImage, final String platformId) {
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.platformId = platformId;
    }

    public static Member createGuest() {
        return new Member("Guest", "", "-1");
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getPlatformId() {
        return platformId;
    }

    public List<ReviewFavorite> getReviewFavorites() {
        return reviewFavorites;
    }

    public List<RecipeFavorite> getRecipeFavorites() {
        return recipeFavorites;
    }

    public List<RecipeBookmark> getRecipeBookmarks() {
        return recipeBookmarks;
    }

    public void modifyProfile(final String nickname, final String profileImage) {
        if (!StringUtils.hasText(nickname) || Objects.isNull(profileImage)) {
            throw new MemberUpdateException(MEMBER_UPDATE_ERROR);
        }
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

    public void modifyNickname(final String nickname) {
        if (!StringUtils.hasText(nickname)) {
            throw new MemberUpdateException(MEMBER_UPDATE_ERROR);
        }
        this.nickname = nickname;
    }

    public boolean isGuest() {
        return platformId.equals("-1");
    }
}
