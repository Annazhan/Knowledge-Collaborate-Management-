package com.example.knw.service.impl;

import com.example.knw.dao.ProjectMapper;
import com.example.knw.exception.ProjectAlreadyExistException;
import com.example.knw.pojo.Project;
import com.example.knw.pojo.ProjectExample;
import com.example.knw.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 实现项目服务接口
 *
 * @author qanna
 * @date 2021-04-26
 */
@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    ProjectMapper projectMapper;

    @Override
    public boolean addProjectToTeam(Project project, Integer chargeID){
        ProjectExample example = new ProjectExample();
        ProjectExample.Criteria criteria = example.createCriteria();
        criteria.andTeamIdEqualTo(project.getTeamId());
        criteria.andProjectNameEqualTo(project.getProjectName());
        if(projectMapper.selectByExample(example).size() != 0){
            throw new ProjectAlreadyExistException();
        }

        project.setCreateTime(new Date());
        project.setChargeId(chargeID);
        project.setIsFinish(false);
        project.setIsDelete(false);
        try{
            projectMapper.insertSelective(project);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
