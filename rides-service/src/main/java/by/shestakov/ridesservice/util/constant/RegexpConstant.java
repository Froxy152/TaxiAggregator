package by.shestakov.ridesservice.util.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RegexpConstant {
    public static final String EXTRACT_EXCEPTION_MESSAGE_FROM_ANOTHER_SERVICE = "message\":\"(.*?)\"";
}
