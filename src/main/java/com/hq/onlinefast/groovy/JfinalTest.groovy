import com.hq.onlinefast.LiteflowMysqlApplication
import com.hq.onlinefast.bean.User
import com.hq.onlinefast.mapper.UserMapper
import com.hq.onlinefast.util.SpringUtil
import com.jfinal.plugin.activerecord.Db
import com.jfinal.plugin.activerecord.Record
import org.slf4j.Logger
import org.slf4j.LoggerFactory
def Logger log = LoggerFactory.getLogger(getClass())
log.info("进入groovy脚本中")
User user = new User();
List<User> users = user.selectAll()
log.info(users.toString())
Integer a=0
defaultContext.setData("userList", users)
Record user0 = new Record().set("name", "James").set("age", 25);
log.info("jfinal0:{}",user0.toString())
Db.save("user", user0);
Record user1 = Db.findById("user", 1)
log.info("jfinal1:{}",user1)
