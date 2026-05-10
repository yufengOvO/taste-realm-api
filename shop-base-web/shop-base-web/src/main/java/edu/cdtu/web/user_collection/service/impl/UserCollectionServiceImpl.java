package edu.cdtu.web.user_collection.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.cdtu.web.user_collection.entity.UserCollection;
import edu.cdtu.web.user_collection.mapper.UserCollectionMapper;
import edu.cdtu.web.user_collection.service.UserCollectionService;
import org.springframework.stereotype.Service;

@Service
public class UserCollectionServiceImpl extends ServiceImpl<UserCollectionMapper, UserCollection> implements UserCollectionService {
}
