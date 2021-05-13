package com.example.knw.service;

import com.example.knw.pojo.Project;

/**
 * 有关项目的服务
 *
 * @author qanna
 * @date 2021-04-26
 */
public interface ProjectService {
    boolean addProjectToTeam(Project project, Integer chargeID);
}
