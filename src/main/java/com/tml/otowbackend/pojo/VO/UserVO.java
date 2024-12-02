package com.tml.otowbackend.pojo.VO;

import com.tml.otowbackend.pojo.DO.User;
import lombok.Builder;
import lombok.Data;

/**
 * @Description
 * @Author welsir
 * @Date 2024/10/27 21:52
 */
@Data
@Builder
public class UserVO {

    private Long id;
    private String uid;
    private String avatar;
    private String nickname;
    private String email;
    private String phoneNum;


    public static UserVO convert(User user){
        return UserVO.builder()
                .id(user.getId())
                .uid(user.getUid())
                .nickname(user.getNickname())
                .avatar(user.getAvatar())
                .email(user.getEmail())
                .phoneNum(user.getPhoneNum())
                .build();

    }
}
