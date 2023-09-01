<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${daoUrl}.${entityName}Mapper">
    <#list methodInfoList as pa>
    <!-- ${pa.comment} -->
    <select id="${pa.methodName}"
            parameterType="${pageParamUrl}.${entityName}SelectParam"
            resultType="${voUrl}.${entityName}Vo">

    </select>
    </#list>
</mapper>