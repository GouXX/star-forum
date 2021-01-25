package cn.gxx.starforum.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;

import java.util.List;

/**
 * @description: 动态权限组件配置
 * @author: Gxx
 * @time: 2021-01-25 09:48
 */
@Configuration
public class DynamicAccessControlConfig {

    @Bean
    public RoleVoter roleVoter() {
        return new RoleVoter();
    }

    @Bean
    public AccessDecisionManager affirmativeBased(List<AccessDecisionVoter<?>> decisionVoters) {
        return new AffirmativeBased(decisionVoters);
    }
}
