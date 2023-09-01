package ${enumUrl};

/**   
 * @Description:TODO(${enumName}-枚举)
 * @date ${createDate}
 * @version: ${version}
 * @author: ${author}
 * 
 */
public enum ${enumName}Enum {

<#list enumItemInfos as ci>
    ${ci.enumName}("${ci.enumValue}"),
</#list>
    ;

    private String status;

    ${enumName}Enum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
	