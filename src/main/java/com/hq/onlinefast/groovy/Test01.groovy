import com.hq.onlinefast.LiteflowMysqlApplication
import com.hq.onlinefast.bean.User
import com.hq.onlinefast.mapper.UserMapper
import com.hq.onlinefast.util.SpringUtil
import org.slf4j.Logger
import org.slf4j.LoggerFactory
def Logger log = LoggerFactory.getLogger(getClass())
log.info("进入groovy脚本中")
User user = new User();
List<User> users = user.selectAll()
log.info(users.toString())
defaultContext.setData("userList", users)
//UserMapper userMapper = LiteflowMysqlApplication.applicationContext.getBean(UserMapper.class)
//UserMapper userMapper =(UserMapper)SpringUtil.getBean("UserMapper")
//print(userMapper)
//List<User> userList = UserMapper.selectList(null)
//defaultContext.setData("userList", userList)
//log.info(userList.toString())