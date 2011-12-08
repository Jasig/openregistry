package org.openregistry.core.service;

import org.aspectj.lang.Aspects;
import org.jasig.openregistry.test.repository.MockSystemOfRecordRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openregistry.aspect.SoRSpecificationThreadLocalAspect;
import org.openregistry.core.authorization.Authority;
import org.openregistry.core.authorization.Group;
import org.openregistry.core.authorization.User;
import org.openregistry.core.authorization.jpa.JpaAuthorityImpl;
import org.openregistry.core.authorization.jpa.JpaGroupImpl;
import org.openregistry.core.authorization.jpa.JpaUserImpl;
import org.openregistry.core.service.authorization.AuthorizationService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nah67
 * Date: 9/8/11
 * Time: 5:07 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-authorizationServices-context.xml"})
public class DefaultAuthorizationServiceIntegrationTests {
//    @Inject
//    private AuthorizationService authorizationService;
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    private User user;
//    private User user1;
//    private User user2;
//    private User user3;
//
//    private Group group;
//    private Group group1;
//    private Group group2;
//    private Group group3;
//
//    private Authority authority;
//    private Authority authority1;
//    private Authority authority2;
//    private Authority authority3;
//
//    @Before
//    public void dataBaseSetUp() throws Exception {
//        System.out.println("Before Test Starts: set User Value");
//
//        this.user = new JpaUserImpl();
//        this.user.setUserName("TEST");
//        this.user.setPassword("TEST");
//        this.user.setDescription("TEST USER");
//        this.user.setEnabled(true);
//
//        this.user1 = new JpaUserImpl();
//        this.user1.setUserName("TEST1");
//        this.user1.setPassword("TEST");
//        this.user1.setDescription("TEST USER1");
//        this.user1.setEnabled(true);
//
//        this.user2 = new JpaUserImpl();
//        this.user2.setUserName("TEST2");
//        this.user2.setPassword("TEST");
//        this.user2.setDescription("TEST USER2");
//        this.user2.setEnabled(true);
//
//        this.user3 = new JpaUserImpl();
//        this.user3.setUserName("TEST3");
//        this.user3.setPassword("TEST");
//        this.user3.setDescription("TEST USER3");
//        this.user3.setEnabled(true);
//
//        this.group = new JpaGroupImpl();
//        this.group.setGroupName("Test Group");
//        this.group.setDescription("Test Group");
//        this.group.setEnabled(true);
//
//        this.group1 = new JpaGroupImpl();
//        this.group1.setGroupName("Test Group1");
//        this.group1.setDescription("Test Group1");
//        this.group1.setEnabled(true);
//
//        this.group2 = new JpaGroupImpl();
//        this.group2.setGroupName("Test Group2");
//        this.group2.setDescription("Test Group2");
//        this.group2.setEnabled(true);
//
//        this.group3 = new JpaGroupImpl();
//        this.group3.setGroupName("Test Group3");
//        this.group3.setDescription("Test Group3");
//        this.group3.setEnabled(true);
//
//
//        this.authority = new JpaAuthorityImpl();
//        this.authority.setAuthorityName("TEST_AUTHORITY");
//        this.authority.setDescription("TEST_AUTH_DESCRIPTION");
//
//        this.authority1 = new JpaAuthorityImpl();
//        this.authority1.setAuthorityName("TEST_AUTHORITY1");
//        this.authority1.setDescription("TEST_AUTH_DESCRIPTION1");
//
//        this.authority2 = new JpaAuthorityImpl();
//        this.authority2.setAuthorityName("TEST_AUTHORITY2");
//        this.authority2.setDescription("TEST_AUTH_DESCRIPTION2");
//
//        this.authority3 = new JpaAuthorityImpl();
//        this.authority3.setAuthorityName("TEST_AUTHORITY3");
//        this.authority3.setDescription("TEST_AUTH_DESCRIPTION3");
//    }

//    @Test
//    public void testSaveUser() throws Exception {
//       //this.authorizationService
//       User userTest=this.authorizationService.saveUser(this.user);
//       System.out.println("User Id:" + userTest.getId());
//    }
//
//    @Test
//    public void testSaveGroup() throws Exception {
//       //this.authorizationService
//       Group groupTest =this.authorizationService.saveGroup(this.group);
//       System.out.println("Group Id:" + groupTest.getId());
//    }
//
//    @Test
//    public void testSaveAuthority() throws Exception {
//       //this.authorizationService
//       Authority testAuthority =this.authorizationService.saveAuthority(this.authority);
//       System.out.println("Authority Id:" + testAuthority.getId());
//    }
//
//
//    @Test
//    public void testFindAllUsers() throws Exception {
//       //this.authorizationService
//
//
//        //User blah =
//        this.authorizationService.saveUser(this.user);
//        this.authorizationService.saveUser(this.user1);
//        this.authorizationService.saveUser(this.user2);
//        this.authorizationService.saveUser(this.user3);
//
//       //this.authorizationService.
//       //System.out.println("User Id:" + blah.getId());
//       List<User> users = this.authorizationService.findAllUsers();
//       for (User user1: users){
//           System.out.println("[USER] id: " + user1.getId());
//           System.out.println("[USER] name: " + user1.getUserName());
//           System.out.println("[USER] password: " + user1.getPassword());
//           System.out.println("[USER] description: " + user1.getDescription());
//           System.out.println("[USER] enabled: " + user1.isEnabled());
//       }
//    }
//
//    @Test
//    public void testFindAllGroups() throws Exception {
//       //this.authorizationService
//        this.authorizationService.saveGroup(this.group);
//        this.authorizationService.saveGroup(this.group1);
//        this.authorizationService.saveGroup(this.group2);
//        this.authorizationService.saveGroup(this.group3);
//
//       List<Group> groups= this.authorizationService.findAllGroups();
//       for (Group group1: groups){
//           System.out.println("[Group] id:" + group1.getId());
//           System.out.println("[Group] name:" + group1.getGroupName());
//           System.out.println("[Group] description:" + group1.getDescription());
//           System.out.println("[Group] enabled: " + group1.isEnabled());
//       }
//    }
//
//    @Test
//    public void testFindAllAuthorities() throws Exception {
//       //this.authorizationService
//        this.authorizationService.saveAuthority(this.authority);
//        this.authorizationService.saveAuthority(this.authority1);
//        this.authorizationService.saveAuthority(this.authority2);
//        this.authorizationService.saveAuthority(this.authority3);
//
//       List<Authority> authorities= this.authorizationService.findAllAuthorities();
//       for (Authority authority1: authorities){
//           System.out.println("[Authority] id:" + authority1.getId());
//           System.out.println("[Authority] name:" + authority1.getAuthorityName());
//           System.out.println("[Authority] description:" + authority1.getDescription());
//       }
//    }
//
//    @Test
//    public void testSearchUserByName() throws Exception{
//        System.out.println("####################################TEST USER######################################");
//        this.authorizationService.saveUser(this.user);
//        this.authorizationService.saveUser(this.user1);
//        this.authorizationService.saveUser(this.user2);
//        this.authorizationService.saveUser(this.user3);
//
//       //this.authorizationService.
//       //System.out.println("User Id:" + blah.getId());
//       List<User> users = this.authorizationService.findUserByName("TEST");
//       for (User user1: users){
//           System.out.println("[USER] id: " + user1.getId());
//           System.out.println("[USER] name: " + user1.getUserName());
//           System.out.println("[USER] password: " + user1.getPassword());
//           System.out.println("[USER] description: " + user1.getDescription());
//           System.out.println("[USER] enabled: " + user1.isEnabled());
//       }
//    }
//
//    @Test
//    public void testSearchGroupByName() throws Exception{
//        System.out.println("####################################TEST GROUP######################################");
//        this.authorizationService.saveGroup(this.group);
//        this.authorizationService.saveGroup(this.group1);
//        this.authorizationService.saveGroup(this.group2);
//        this.authorizationService.saveGroup(this.group3);
//
//       List<Group> groups= this.authorizationService.findGroupByName("Test Group");
//       for (Group group1: groups){
//           System.out.println("[Group] id:" + group1.getId());
//           System.out.println("[Group] name:" + group1.getGroupName());
//           System.out.println("[Group] description:" + group1.getDescription());
//           System.out.println("[Group] enabled: " + group1.isEnabled());
//       }
//    }
//
//    @Test
//    public void testSearchAuthorityByName() throws Exception{
//        System.out.println("####################################TEST AUTHORITY######################################");
//        this.authorizationService.saveAuthority(this.authority);
//        this.authorizationService.saveAuthority(this.authority1);
//        this.authorizationService.saveAuthority(this.authority2);
//        this.authorizationService.saveAuthority(this.authority3);
//
//       List<Authority> authorities= this.authorizationService.findAuthorityByName("TEST_AUTHORITY");
//       for (Authority authority1: authorities){
//           System.out.println("[Authority] id:" + authority1.getId());
//           System.out.println("[Authority] name:" + authority1.getAuthorityName());
//           System.out.println("[Authority] description:" + authority1.getDescription());
//       }
//
//    }

//
//     @Test
//    public void testUserGroupAuthorityRelationship() throws Exception {
//
//        Authority authToBeAssigned = this.authorizationService.saveAuthority(authority);
//         System.out.println("Authority id:" + authToBeAssigned.getId());
//        Group groupToBeAssigned = this.authorizationService.saveGroup(group);
//         System.out.println("Group id:" + groupToBeAssigned.getId());
//        User userToBeAssigned = this.authorizationService.saveUser(user);
//         System.out.println("User id:" + userToBeAssigned.getId());
//
//        this.authorizationService.addAuthorityToGroup(groupToBeAssigned,authToBeAssigned);
//        this.authorizationService.addGroupToUser(userToBeAssigned,groupToBeAssigned);
//
//
//
//
//
//       List<User> users= this.authorizationService.findUserByName("TEST");//findAllUsers();
//       for (User user11: users){
//           System.out.println("[USER] id: " + user11.getId());
//           System.out.println("[USER] name: " + user11.getUserName());
//           System.out.println("[USER] password: " + user11.getPassword());
//           System.out.println("[USER] description: " + user11.getDescription());
//           System.out.println("[USER] enabled: " + user11.isEnabled());
//
//           System.out.println("--------------------------------Check Groups of User--------------------------------");
//
//           //Set<Group> groups = user11.getUserGroups();
//           //Set<Group> groupsOfUser = this.authorizationService.findGroupOfUser(user11);
//           Set<Group> groupsOfUser = this.authorizationService.findGroupOfUser(new Long(1));
//
//           if(null != groupsOfUser){
//               for(Group group11 : groupsOfUser){
//                   System.out.println("[Group] id:" + group11.getId());
//                   System.out.println("[Group] name:" + group11.getGroupName());
//                   System.out.println("[Group] description:" + group11.getDescription());
//                   System.out.println("[Group] enabled: " + group11.isEnabled());
//
//                   System.out.println("-------------------------------- Check Authorities of Group --------------------------------");
//                   Set<Authority> authoritiesOfGroups = this.authorizationService.findAuthoritiesOfGroup(group11);
//                   if(null != authoritiesOfGroups && authoritiesOfGroups.size() >0){
//                       for(Authority authority11 : authoritiesOfGroups){
//                            System.out.println("[Authority] id:" + authority11.getId());
//                            System.out.println("[Authority] name:" + authority11.getAuthorityName());
//                            System.out.println("[Authority] description:" + authority11.getDescription());
//                       }
//                   }else{
//                           System.out.println("Empty Authority List");
//                   }
//               }
//           }
//
//       }
//                   System.out.println("-------------------------------- Check Authorities of Group with a different group id--------------------------------");
//                   Set<Authority> authoritiesOfGroups = this.authorizationService.findAuthoritiesOfGroup(new Long(1));
//                   if(null != authoritiesOfGroups && authoritiesOfGroups.size() >0){
//                       for(Authority authority11 : authoritiesOfGroups){
//                            System.out.println("[Authority] id:" + authority11.getId());
//                            System.out.println("[Authority] name:" + authority11.getAuthorityName());
//                            System.out.println("[Authority] description:" + authority11.getDescription());
//                       }
//                   }else{
//                           System.out.println("Empty Authority List");
//                   }
//         System.out.println("$$$$$$$$$$$$$$$$$$$ Test Deletion $$$$$$$$$$$$$$$$");
//         //delete a user
//         this.authorizationService.deleteUser(user);
//
//         List<User> lstUser = this.authorizationService.findAllUsers();
//         for(User userAfterDeletion : lstUser){
//             System.out.println("User ID After Deletion:" + userAfterDeletion.getId());
//         }
//
//         List<Group> setGroups = this.authorizationService.findAllGroups();
//         for(Group groupAfterDeletion : setGroups){
//            System.out.println("Group ID after deleting user: " + groupAfterDeletion.getId());
//         }
//
//
//    }

}
