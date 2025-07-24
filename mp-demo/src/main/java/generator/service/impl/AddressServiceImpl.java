package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.Address;
import generator.service.AddressService;
import generator.mapper.AddressMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【address】的数据库操作Service实现
* @createDate 2025-07-24 17:58:00
*/
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address>
    implements AddressService{

}




