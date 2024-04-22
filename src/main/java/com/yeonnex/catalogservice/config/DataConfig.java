package com.yeonnex.catalogservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;

/**
 * 이 기능이 활성화되면 데이터의 생성, 변경, 삭제가 일어날 때마다 감사 이벤트가 생성된다.
 */
@Configuration
@EnableJdbcAuditing // 지속성 엔티티에 대한 감사를 활성화
public class DataConfig {
}
