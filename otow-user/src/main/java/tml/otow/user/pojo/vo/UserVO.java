package tml.otow.user.pojo.vo;

import lombok.Builder;
import lombok.Data;
import tml.otow.user.pojo.DO.UserDO;

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
    private Integer point;


    public static UserVO convert(UserDO userDO){
        return UserVO.builder()
                .id(userDO.getId())
                .uid(userDO.getUid())
                .nickname(userDO.getNickname())
                .avatar(userDO.getAvatar())
                .email(userDO.getEmail())
                .point(userDO.getPoint())
                .phoneNum(userDO.getPhoneNum())
                .build();

    }
}
