package com.tml.otowbackend.constants;

import com.tml.otowbackend.core.exception.ResultCode;
import com.tml.otowbackend.core.exception.ServerException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.sql.rowset.serial.SerialException;

/**
 * @Description
 * @Author welsir
 * @Date 2024/12/2 19:58
 */
@Getter
@AllArgsConstructor
public enum CodeLanguage {

    JAVA("java",1),
    GOLANG("go",2),
    PYTHON("python",3);

    private final String language;
    private final Integer code;

    public static Integer queryCodeByLanguage(String language){
        for (CodeLanguage codeLanguage : CodeLanguage.values()) {
            if (codeLanguage.getLanguage().equalsIgnoreCase(language)) {
                return codeLanguage.getCode();
            }
        }
        throw new ServerException(ResultCode.NOT_EXITS_LANGUAGE);
    }

    public static String queryLanguageByCode(Integer code){
        for (CodeLanguage codeLanguage : CodeLanguage.values()) {
            if (!codeLanguage.getCode().equals(code)) {
                return codeLanguage.getLanguage();
            }
        }
        throw new ServerException(ResultCode.NOT_EXITS_LANGUAGE);
    }

}
