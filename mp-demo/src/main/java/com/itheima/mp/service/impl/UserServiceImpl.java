package com.itheima.mp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.itheima.mp.domain.po.Address;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.vo.AddressVO;
import com.itheima.mp.domain.vo.UserVO;
import com.itheima.mp.mapper.UserMapper;
import com.itheima.mp.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    @Transactional
    public void deductMoneyById(Long id, Integer money) {
        // 1.查询用户
        User user = getById(id);
        // 2.校验用户状态
        if (user == null || user.getStatus() == 2) {
            throw new RuntimeException("用户状态异常！");
        }
        // 3.校验余额是否充足
        if (user.getBalance() < money) {
            throw new RuntimeException("用户余额不足！");
        }
        // 4.扣减余额
        int newBalance = user.getBalance() - money;
        lambdaUpdate()
                .set(newBalance == 0, User::getBalance, newBalance)
                .set(User::getStatus, 2)
                .eq(User::getId, id)
                .eq(User::getBalance, user.getBalance()) // 乐观锁加锁
                .update();
//        baseMapper.deductBalance(id, money);
    }

    @Override
    public List<User> queryUsers(String name, Integer status, Integer minBalance, Integer maxBalance) {
        // 当为复杂查询建议使用 lambdaQuery 进行查询
        // 简单查询建议直接使用预设的 list，getById 等方法
        return lambdaQuery()
                .like(name != null, User::getUsername, name)
                .eq(status != null, User::getStatus, status)
                .ge(minBalance != null, User::getBalance, minBalance)
                .le(maxBalance != null, User::getBalance, maxBalance)
                .list();
    }

    @Override
    public UserVO getUserWithAddress(Long id) {
        // 1. 获取User对象
        User user = getById(id);
        if (user == null || user.getStatus() == 2) {
            throw new RuntimeException("用户状态异常");
        }

        // 2. 获取地址信息
        List<Address> addresses = Db.lambdaQuery(Address.class)
                .eq(Address::getUserId, user.getId())
                .list();

        // 3. 将数据装配到 VO
        // 3.1 装载 UserVO
        UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);
        // 3.2 装载 AddressVo
        if (CollUtil.isNotEmpty(addresses)) {
            userVO.setAddressVOS(BeanUtil.copyToList(addresses, AddressVO.class));
        }

        return userVO;
    }

    @Override
    public List<UserVO> queryUserAndAddressByIds(List<Long> ids) {
        // 1. 获取 user 数组
        List<User> users = listByIds(ids);

        // 2. 提取用户的id
        List<Long> userIds = users.stream().map(User::getId).collect(Collectors.toList());
        // 2.1 根据用户 id 查询地址
        List<Address> addresses = Db.lambdaQuery(Address.class)
                .in(Address::getUserId, userIds)
                .list();
        // 2.2 转换为地址VO
        List<AddressVO> addressVOs = BeanUtil.copyToList(addresses, AddressVO.class);

        Map<Long, List<AddressVO>> addressVOsMap = new HashMap<>(0);
        // 2.3 将查询出的 address 根据用户的 id 分组
        if (CollUtil.isNotEmpty(addressVOs)) {
            addressVOsMap = addressVOs.stream().collect(Collectors.groupingBy(AddressVO::getUserId));
        }

        // 3. 转换 VO 返回
        ArrayList<UserVO> resultList = new ArrayList<>(users.size());
        for (User user : users) {
            // 3.1 将 addressVO 放入 users 中
            UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);
            resultList.add(userVO);

            // 3.2 将 addressVO 放入 userVO 中
            userVO.setAddressVOS(addressVOsMap.get(user.getId()));
        }

        return resultList;
    }
}
