package com.example.springCase.service.impl;

import com.example.springCase.bean.constant.error.ErrorEnum;
import com.example.springCase.bean.dto.*;
import com.example.springCase.bean.entity.UserDO;
import com.example.springCase.bean.constant.RedisConstant;
import com.example.springCase.dao.UserDao;
import com.example.springCase.exception.BizException;
import com.example.springCase.mapping.UserListMapping;
import com.example.springCase.service.LogService;
import com.example.springCase.service.PlatformService;
import com.example.springCase.service.SensitiveWordService;
import com.example.springCase.service.UserService;
import com.example.springCase.util.JwtUtil;
import com.example.springCase.util.RedisUtil;
import com.example.springCase.util.StringCharUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rjhy.base.error.ServiceException;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author tao.wu
 * @date 2022/4/5
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private StringCharUtil stringCharUtil;

    @Resource
    private LogService logService;

    @Resource
    private PlatformService platformService;

    @Resource
    private SensitiveWordService sensitiveWordService;

    @Override
    public UserListResponseDTO listPage(UserListDTO userListDto){
        PageHelper.startPage(userListDto.getPageNo(), userListDto.getPageSize());
        List<UserDO> userDOS = userDao.selectUserList(userListDto);

        PageInfo<UserDO> pageInfo = new PageInfo<>(userDOS);
        UserListResponseDTO userListResponseDTO = UserListMapping.buildUserListResponseDTO(pageInfo.getList());
        userListResponseDTO.setTotalNum(pageInfo.getTotal());
        return userListResponseDTO;
    }

    @Override
    public UserItemResponseDTO detail(Integer id){
        List<UserDO> userSingleList = userDao.selectUserDetail(id);
        if(CollectionUtils.isEmpty(userSingleList)){
            throw  new BizException(ErrorEnum.NOT_FOUND_USER.code(), ErrorEnum.NOT_FOUND_USER.message());
//            throw  new BizException(ErrorEnum.NOT_FOUND_USER.code(), ErrorEnum.NOT_FOUND_USER.message());
        }

        return buildUserItemResponseDTO(userSingleList);
    }

    /**
     * 构建UserItemResponseDTO
     * @param userSingleList
     * @return
     */
    private UserItemResponseDTO buildUserItemResponseDTO(List<UserDO> userSingleList){
        UserItemResponseDTO userItemResponseDTO = new UserItemResponseDTO();
        UserDO userDO = userSingleList.get(0);
        userItemResponseDTO.setId(userDO.getId());
        userItemResponseDTO.setUsername(userDO.getUsername());
        userItemResponseDTO.setAge(userDO.getAge());
        userItemResponseDTO.setAddress(userDO.getAddress());

        int platformId = userDO.getPlatformId();
        Map<Integer, String> platformMap = platformService.mapName();
        String platform = Objects.isNull(platformMap.get(platformId)) ? "" : platformMap.get(platformId);
        userItemResponseDTO.setPlatfrom(platform);

        return userItemResponseDTO;
    }

    @Override
    public void update(UserUpdateDTO userUpdateDTO){
        // 通过token获取用户id
        Integer userId = JwtUtil.getId(userUpdateDTO.getToken());

        // 查询是否存在
        List<UserDO> userDOS = userDao.selectUserDetail(userId);
        if(CollectionUtils.isEmpty(userDOS)){
            throw  new BizException(ErrorEnum.NOT_FOUND_USER.code(), ErrorEnum.NOT_FOUND_USER.message());
        }
        UserBuildDTO userBuildDTO = new UserBuildDTO();
        userBuildDTO.setId(userId);
        userBuildDTO.setAddress(userUpdateDTO.getAddress());
        userBuildDTO.setAge(userUpdateDTO.getAge());

        UserDO userDO = buildUserDO(userBuildDTO);
        userDao.updateUserDetail(userDO);
    }

    @Override
    public Object register(UserRegisterDTO userRegisterDTO){
        String username = userRegisterDTO.getUsername();
        String password = userRegisterDTO.getPassword();

        checkRegister(userRegisterDTO, password, username);

        String salt = StringCharUtil.getRandomString(5);
        String savePassword = generateSavePassword(password, salt);
        // 组装DO
        UserBuildDTO userBuildDTO = new UserBuildDTO();
        userBuildDTO.setUsername(username);
        userBuildDTO.setPassword(savePassword);
        userBuildDTO.setPlatformId(userRegisterDTO.getPlatformId());
        userBuildDTO.setSalt(salt);
        UserDO userDO = buildUserDO(userBuildDTO);

        // 插入数据库
        Integer insertCount = userDao.insertUser(userDO);
        if (insertCount == 0){
            throw  new BizException(ErrorEnum.ERROR_REGISTER_FAIL.code(), ErrorEnum.ERROR_REGISTER_FAIL.message());
        }
        return null;
    }

    /**
     * 检查注册参数
     * @param userRegisterDTO
     * @param password
     * @param username
     */
    private void checkRegister(UserRegisterDTO userRegisterDTO, String password, String username){
        // 判断密码和重复密码是否相同
        if (!password.equals(userRegisterDTO.getRepeatPassword())){
            throw  new BizException(ErrorEnum.NOT_SAME_PASSWORD.code(), ErrorEnum.NOT_SAME_PASSWORD.message());
        }
        // 判断是否有特殊字符
        boolean isSpecialChar = stringCharUtil.hasSpecialChar(username);
        if (isSpecialChar){
            throw  new BizException(ErrorEnum.ERROR_SPECIAL_STRING.code(), ErrorEnum.ERROR_SPECIAL_STRING.message());
        }
        // 判断是否有过相同的用户名
        Integer sameNameCount = userDao.countByUsername(username);
        if(sameNameCount > 0){
            throw  new BizException(ErrorEnum.EXIST_USERNAME.code(), ErrorEnum.EXIST_USERNAME.message());
        }
        // 判断用户名是否有敏感词
        Boolean haveSensitiveWord = sensitiveWordService.judgeSensitiveWord(username);
        if(haveSensitiveWord){
            throw  new BizException(ErrorEnum.ERROR_ABOUT_SENSITIVE_WORD.code(), ErrorEnum.ERROR_ABOUT_SENSITIVE_WORD.message());
        }
    }

    /**
     * 密码加盐生成保存在数据库里的字符串
     * @param password
     * @return
     */
    private String generateSavePassword(String password, String salt){
        String passwordSalt = password + salt;
        return DigestUtils.md5DigestAsHex(passwordSalt.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String login(UserLoginDTO userLoginDTO) {

        handleLogin(userLoginDTO.getIpAddress());

        String username = userLoginDTO.getUsername();

        UserListDTO userListDTO = new UserListDTO();
        userListDTO.setUsername(username);
        List<UserDO> userDOS = userDao.selectUserList(userListDTO);
        // 判断是否存在该用户名
        if (CollectionUtils.isEmpty(userDOS)){
            throw  new BizException(ErrorEnum.ERROR_NOT_EXIST_USER.code(), ErrorEnum.ERROR_NOT_EXIST_USER.message());
        }
        UserDO userDO = userDOS.get(0);
        // 根据当前过来的密码加表里的盐生成加密的密码
        String generateSavePassword = generateSavePassword(userLoginDTO.getPassword(), userDO.getSalt());
        // 判断密码是否一样
        if (!generateSavePassword.equals(userDO.getPassword())){
            throw  new BizException(ErrorEnum.LOGIN_FAIL.code(), ErrorEnum.LOGIN_FAIL.message());
        }

        log.info("username：{}, operate：login", username);
        // 增加日志记录
        LogInsertDTO logInsertDTO = new LogInsertDTO();
        logInsertDTO.setUsername(username);
        // todo
        logInsertDTO.setContent("登录系统");
        logInsertDTO.setAddress(userLoginDTO.getIpAddress());
        logService.insertLog(logInsertDTO);
        // 返回jwt的加密字符串
        return getJwtToken(userDOS.get(0));
    }

    @Override
    public Map<String, Object> centre(String token){
        Integer userId = JwtUtil.getId(token);
        String username = JwtUtil.getUserName(token);
        log.info("id is:{}, username is:{}", userId, username);

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", userId);
        userInfo.put("username", username);

        return userInfo;
    }

    /**
     * 组装DO
     * @param userBuildDTO
     * @return
     */
    private UserDO buildUserDO(UserBuildDTO userBuildDTO) {
        UserDO userDO = new UserDO();
        userDO.setId(userBuildDTO.getId());
        userDO.setUsername(userBuildDTO.getUsername());
        userDO.setPassword(userBuildDTO.getPassword());
        userDO.setAddress(userBuildDTO.getAddress());
        userDO.setAge(userBuildDTO.getAge());
        userDO.setPlatformId(userBuildDTO.getPlatformId());
        userDO.setSalt(userBuildDTO.getSalt());
        return userDO;
    }

    /**
     * 获取token
     * @param userDO
     * @return
     */
    private String getJwtToken(UserDO userDO){
        log.info("jwt save user data:{}", userDO);

        String jwtToken = JwtUtil.createJWT(userDO.getId().toString(), userDO, "{\"id\":\"123\"}", System.currentTimeMillis() + 1000*60*60*24);
        String redisJwtValue = "Bearer " + jwtToken;
        redisUtil.set(String.format(RedisConstant.CACHE_JWT_KEY, userDO.getUsername()), redisJwtValue, 1000*60*60*24);
        return jwtToken;
    }

    /**
     * 限制ip多少分钟内能登录几次
     * @param ipAddress
     */
    private void handleLogin(String ipAddress){
        if (StringUtil.isNullOrEmpty(ipAddress)){
            throw  new BizException(ErrorEnum.ERROR_IP_EMPTY.code(), ErrorEnum.ERROR_IP_EMPTY.message());
        }
        // 限制时间,暂定两分钟
        int cacheTime = RedisConstant.CACHE_LOGIN_TIME;
        // 尝试次数
        int tryTimes = RedisConstant.CACHE_LOGIN_TRY_TIMES;

        String redisKey = String.format(RedisConstant.CACHE_LOGIN_KEY, ipAddress.replace(".", "-"));
        Object redisValue = redisUtil.get(redisKey);
        if(!Objects.isNull(redisValue)){
            String redisStringValue = redisValue.toString();
            int redisIntValue = Integer.parseInt(redisStringValue);
            // 如果没有5次
            if (redisIntValue < tryTimes) {
                int saveRedisValue = redisIntValue + 1;
                // 计算剩余时间
                long expire = redisUtil.getExpire(redisKey);
                log.info("过期时间{}", expire);

                redisUtil.set(redisKey, saveRedisValue, cacheTime);
            }else{// 如果达到5次，抛异常
                throw  new BizException(ErrorEnum.LOGIN_TRY_LATER.code(), ErrorEnum.LOGIN_TRY_LATER.message());
            }
        }else {
            redisUtil.set(redisKey, 1, cacheTime);
        }
    }

}
