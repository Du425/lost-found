package com.du.lostfoundmasterfk.service.impl;

import com.du.lostfoundmasterfk.entity.TMessage;
import com.du.lostfoundmasterfk.dao.TMessageMapper;
import com.du.lostfoundmasterfk.service.ITMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Du425
 * @since 2021-12-05
 */
@Service
public class TMessageServiceImpl extends ServiceImpl<TMessageMapper, TMessage> implements ITMessageService {

}
