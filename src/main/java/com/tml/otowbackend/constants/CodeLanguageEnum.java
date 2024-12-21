package com.tml.otowbackend.constants;

import com.tml.otowbackend.core.exception.ResultCode;
import com.tml.otowbackend.core.exception.ServerException;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description
 * @Author welsir
 * @Date 2024/12/2 19:58
 */
@Getter
@AllArgsConstructor
public enum CodeLanguageEnum {

    JAVA("Java",1),
    GOLANG("Go",2),
    PYTHON("Python",3),
    C_PLUS("C++",4),
    PHP("Php",5);

    private final String language;
    private final Integer code;

    public static Integer queryCodeByLanguage(String language){
        for (CodeLanguageEnum codeLanguage : CodeLanguageEnum.values()) {
            if (codeLanguage.getLanguage().equalsIgnoreCase(language)) {
                return codeLanguage.getCode();
            }
        }
        throw new ServerException(ResultCode.NOT_EXITS_LANGUAGE);
    }

    public static String queryLanguageByCode(Integer code){
        for (CodeLanguageEnum codeLanguage : CodeLanguageEnum.values()) {
            if (codeLanguage.getCode().equals(code)) {
                return codeLanguage.getLanguage();
            }
        }
        throw new ServerException(ResultCode.NOT_EXITS_LANGUAGE);
    }

}
