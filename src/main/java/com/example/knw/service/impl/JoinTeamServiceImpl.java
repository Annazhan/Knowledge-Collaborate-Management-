package com.example.knw.service.impl;

//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;


/**
 * 创建用户权限
 *
 * @author qanna
 * @date 2021-03-23
 */
//@Service
//public class JoinTeamServiceImpl implements UserDetailsService, JoinTeamService {
//
//    @Autowired
//    UserService userService;
//
//    @Autowired
//    JoinTeamMapper joinTeamMapper;
//
//
//    @Override
//    @Transactional
//    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        KnwUser knwUser = userService.getUserByID(Integer.valueOf(s));
//        List<PeopleAuthEnum> authList = new ArrayList<>();
//
//        //UserDetails userDetails = new User(knwUser.getUserName(),knwUser.getPassword(),authList);
//        return null;
//    }
//
//    private List<PeopleAuthEnum> getAuthInTeam(Integer userID, Integer teamID){
//        JoinTeamExample joinTeamExample = new JoinTeamExample();
//        JoinTeamExample.Criteria criteria = joinTeamExample.createCriteria();
//        criteria.andJoinUserEqualTo(userID).andTeamIdEqualTo(teamID);
//        List<JoinTeam> joinTeams = joinTeamMapper.selectByExample(joinTeamExample);
//        if(joinTeams!=null){
//            JoinTeam joinTeam = joinTeams.get(0);
//            int auth = joinTeam.getAuth();
//            List<PeopleAuthEnum> authList = new ArrayList<>();
//            for(int i = 0; auth != 0; i++){
//                if((auth & 1) == 1) {
//                    authList.add(PeopleAuthEnum.values()[i]);
//                }
//                auth = auth >> 1;
//            }
//            return authList;
//        }
//        return null;
//    }
//
//}
