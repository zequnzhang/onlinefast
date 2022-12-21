import com.fasterxml.jackson.databind.node.ObjectNode
import org.slf4j.Logger
import org.slf4j.LoggerFactory
def Logger log = LoggerFactory.getLogger(getClass())
objectNode.put(_meta.tag,_meta.tag)
log.info(objectNode.toString())
log.info("slotIndex:{}",_meta.slotIndex)
log.info("nodeId:{}",_meta.nodeId)
log.info("tag:{}",_meta.tag)