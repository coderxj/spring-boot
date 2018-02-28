package com.example.springboot.demo.controller;

import com.example.springboot.demo.Mapper.UserMapper;
import com.example.springboot.demo.bean.User;
import com.example.springboot.demo.singleton.SingletonMybatis;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/index") //在类上使用RequestMapping，里面设置的value就是方法的父路径
public class Controller {

    private static SqlSessionFactory sqlSessionFactory;
    static {
        sqlSessionFactory =  SingletonMybatis.getSqlSessionFactory();
    }
    @RequestMapping  //如果方法上的RequestMapping没有value，则此方法默认被父路径调用
    public String index(){
        return "hello spring boot";
    }
    //这里体现了restful风格的请求，按照请求的类型，来进行增删查改。
    //设计restful api（其实也就是URL），不要有冗余，例如不要写成getUsers，URL中
    //最好不要有动词。
    @RequestMapping(method = RequestMethod.GET,value = "/users")
    public List<User> getUsers(){
        List<User> listUsers;
        //获取一个连接
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            //得到映射器
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //调用接口中的方法去执行xml文件中的SQL语句
            listUsers = userMapper.getUsers();
            //要提交后才会生效
            sqlSession.commit();
        }finally {
            //最后记得关闭连接
            sqlSession.close();
        }

        return listUsers;
    }
    //这里用的是路径变量，就是{}括起来的，会当做变量读进来
    @RequestMapping(method = RequestMethod.GET,value = "/users/{userId}")
    public User getUser(@PathVariable int userId){
        User user;
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            user = userMapper.getById(userId);
            sqlSession.commit();
        }finally {
            sqlSession.close();
        }
        return user;
    }
    //RequestBody这个注解可以接收json数据
    @RequestMapping(method = RequestMethod.POST,value = "/user")
    public boolean setUser(@RequestBody String name){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            userMapper.insert(name);
            sqlSession.commit();
        }finally {
            sqlSession.close();
        }
        return true;
    }
    @RequestMapping(method = RequestMethod.PUT,value = "/user/{userid}/{name}")
    public boolean updateUser(@PathVariable int userid,@PathVariable String name){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            User user = new User(userid,name);
            userMapper.updateUser(user);
            sqlSession.commit();
        }finally {
            sqlSession.close();
        }
        return true;
    }

    @RequestMapping(method = RequestMethod.DELETE,value = "/users")
    public boolean deleteUsers(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            userMapper.deleteAllUsers();
            sqlSession.commit();
        }finally {
            sqlSession.close();
        }
        return true;
    }

    @RequestMapping(method = RequestMethod.DELETE,value = "/users/{userId}")
    public boolean deleteUser(@PathVariable int userId){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            userMapper.deleteUser(userId);
            sqlSession.commit();
        }finally {
            sqlSession.close();
        }
        return true;
    }


}
