package com.example.qcassistantmaven.util.interceptor;

import com.example.qcassistantmaven.domain.enums.RoleEnum;
import com.example.qcassistantmaven.service.auth.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminAbsencePreventionInterceptor implements HandlerInterceptor {

    private UserService userService;
    private static final String TARGET_URI = "/users/edit";
    private static final String TARGET_METHOD = "POST";

    private static final String USERNAME_FIELD_NAME = "username";
    private static final String ROLE_FIELD_NAME = "roleLevel";

    private static final String MESSAGE = "Cannot revoke admin role of last remaining admin";

    @Autowired
    public AdminAbsencePreventionInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        if(!request.getMethod().equals(TARGET_METHOD) ||
                !request.getRequestURI().equals(TARGET_URI) ||
                userService.getCountOfAdmins() > 1){

            return true;
        }

        String adminUsername = userService.getLastAdminUsername();
        String requestUsername = request.getParameter(USERNAME_FIELD_NAME);

        if(!adminUsername.equals(requestUsername)){
            return true;
        }

        if(!request.getParameter(ROLE_FIELD_NAME)
                .equals(RoleEnum.ADMINISTRATOR.name())){
            response.sendError(HttpServletResponse.SC_FORBIDDEN, MESSAGE);
            return false;
        }

        return true;
    }
}
