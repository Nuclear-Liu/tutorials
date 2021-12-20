import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Hui.Liu
 * @since 2021-12-02 20:10
 */
public enum TTEnum {
    /** 无流程 */
    NONE(-99),

    /** 草稿 */
    DRAFT(-2),

    /** 发起中 */
    STARTING(-1),

    /** 审批中 */
    APPRING(0),

    /** 审批通过 */
    APPR_OK(1),

    /** 审批未通过 */
    APPR_FAIL(2),

    /** 强行终止 */
    FORCE_STOP(3),

    /** 废除 */
    CANCELED(4),

    /** 未审核 */
    UN_APPR(10),

    /** 已审核 */
    HAS_APPR(20);

    /** 编码 */
    private final int code;

    /**
     * 构造方法
     * @param code 编码
     */
    TTEnum(int code) {
        this.code = code;
    }

    /**
     * key -> code
     * value -> enum
     */
    private final static Map<Integer, TTEnum> ENUM_MAP;

    /**
     * 获取所有枚举放入map,无需每次都遍历枚举
     */
    static {
        ENUM_MAP = new HashMap<>();
        for (TTEnum statusEnum : TTEnum.values()) {
            ENUM_MAP.put(statusEnum.code, statusEnum);
        }
    }

    /**
     * 根据code获取枚举
     * @param code 编码
     * @return 枚举
     */
    public static TTEnum getEnumByCode (int code) {
        return ENUM_MAP.get(code);
    }

    /**
     * 获取编码
     * @return 编码
     */
    public int getCode() {
        return code;
    }

    public Integer getValue() {
        return getCode();
    }
}
//class TestEnum {
//    public static void main(String[] args) {
//        int value = 2;
//        TTEnum enumByCode = TTEnum.getEnumByCode(value);
//        System.out.println(enumByCode.getValue());
//        value = 44;
//        TTEnum enumByCode1 = TTEnum.getEnumByCode(value);
//        System.out.println(Objects.isNull(enumByCode1));
////        System.out.println(enumByCode1.getValue());
//        LocalDate interruptDate = LocalDate.now();
//        LocalDate endDate = interruptDate.plusYears(3).plusDays(-1);
//        System.out.println(interruptDate);
//        System.out.println(endDate);
//    }
//}