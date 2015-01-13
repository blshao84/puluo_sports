package com.puluo.util

import cn.emay.sdk.client.api.Client
import cn.emay.sdk.client.api.MO
import cn.emay.sdk.client.api.StatusReport
import cn.emay.sdk.client.listener.ReceiveMessageListener
import cn.emay.sdk.communication.socket.ResponseMsg
import com.puluo.config.Configurations
import net.liftweb.common.Loggable
import scala.collection.JavaConversions._
import net.liftweb.util.Mailer
import net.liftweb.util.Mailer.From
import net.liftweb.util.Mailer.Subject
import net.liftweb.util.Mailer.To
import scala.xml.Text

object SMSClient extends Loggable {
  lazy private val client = new Client(Configurations.smsSerial, Configurations.smsPassword)

  def init = {
    if (!StringUtil.isEmpty(Configurations.smsSerial)) {
      val status = registEx
      if (status == 0) {
        logger.info("成功初始化短信服务")
      } else logger.error("初始化短信服务失败，短信服务将不会开启！")
    } else {
      logger.info("短信服务没有开启")
    }
  }
  /**
   * 软件注销
   * 1、软件注销后像发送短信、接受上行短信接口都无法使用
   * 2、软件可以重新注册、注册完成后软件序列号的金额保持注销前的状态
   */
  def logout = {
    try {
      val a = client.logout()
      logger.info("SMS 登出:" + a)
      a
    } catch {
      case e: Exception => {
        e.printStackTrace()
        -1
      }
    }
  }
  /**
   * 软件序列号注册、或则说是激活、软件序列号首次使用必须激活
   * registEx(String serialpass)
   * 1、serialpass 软件序列号密码、密码长度为6位的数字字符串、软件序列号和密码请通过亿美销售人员获取
   */
  def registEx = try {
    val i = client.registEx(Configurations.smsPassword)
    logger.info("testTegistEx:" + i)
    i
  } catch {
    case e: Exception => {
      e.printStackTrace()
      -1
    }
  }
  /**
   * 发送短信、可以发送定时和即时短信
   * sendSMS(String[] mobiles,String smsContent, String addSerial, int smsPriority)
   * 1、mobiles 手机数组长度不能超过1000
   * 2、smsContent 最多500个汉字或1000个纯英文、请客户不要自行拆分短信内容以免造成混乱、亿美短信平台会根据实际通道自动拆分、计费以实际拆分条数为准、亿美推荐短信长度70字以内
   * 3、addSerial 附加码(长度小于15的字符串) 用户可通过附加码自定义短信类别,或添加自定义主叫号码( 联系亿美索取主叫号码列表)
   * 4、优先级范围1~5，数值越高优先级越高(相对于同一序列号)
   * 5、其它短信发送请参考使用手册自己尝试使用
   */
  def sendSMS(mobiles: Seq[String], content: String, email: Option[String] = None, priority: Int = Configurations.smsPriority) = {
    try {
      if (priority >= 0) {
        val msg = "【大庆优诺】"+content + "退订回复TD"
        val i = if (!mobiles.isEmpty) {
          val i = client.sendSMS(mobiles.toArray, msg, priority)
          logger.info("发送短信(%s)至%s, 返回状态=%s".format(msg, mobiles, i))
          if (msg.size > Configurations.maxSMSLength) {
            logger.warn("短信长度为%s，超过最大长度".format(msg.size))
          }
          i
        } else {
          logger.info("电话号码为空，没有发送短信")
          -1
        }
        if (email.isDefined) {
          try {
            val title = "来自普罗体育的通知"
            Mailer.sendMail(From(Configurations.emailFrom), Subject(title), To(email.get), Text(msg))
            logger.info("成功发送email至%s".format(email.get))
          } catch {
            case e: Exception => {
              logger.error("发送email到%s失败".format(email.get))
              logger.error(e.getStackTraceString)
            }
          }
        }
        i
      } else 0
    } catch {
      case e: Exception => {
        e.printStackTrace()
        -1
      }
    }
  }

  /**
   * 发送定时短信
   * sendScheduledSMSEx(String[] mobiles, String smsContent,String sendTime,String srcCharset)
   * 1、mobiles 手机数组长度不能超过1000
   * 2、smsContent 最多500个汉字或1000个纯英文、请客户不要自行拆分短信内容以免造成混乱、亿美短信平台会根据实际通道自动拆分、计费以实际拆分条数为准、亿美推荐短信长度70字以内
   * 3、sendTime 定时短信发送时间 定时时间格式为：年年年年月月日日时时分分秒秒，例如20090801123030 表示2009年8月1日12点30分30秒该条短信会发送到用户手机
   * 4、srcCharset 字符编码，默认为"GBK"
   * 5、其它定时短信发送请参考使用手册自己尝试使用
   */
  def sendScheduledSMS(mobiles: Seq[String], content: String, time: String) = {
    try {
      val i = client.sendScheduledSMSEx(mobiles.toArray, content, time, "UTF-8")
      logger.info("发送短信(%s)至%s, 返回状态=%s".format(content, mobiles, i))
      i
    } catch {
      case e: Exception => {
        e.printStackTrace()
        -1
      }
    }
  }

  /**
   * 企业详细信息注册
   * registDetailInfo(String eName, String linkMan, String phoneNum,String mobile, String email, String fax, String address,String postcode)
   * 1、eName 企业名称(最多60字节)
   * 2、linkMan 联系人姓名(最多20字节)
   * 3、phoneNum 联系电话(最多20字节)
   * 4、mobile 联系手机(最多15字节)
   * 5、email 电子邮件(最多60字节)
   * 6、fax 联系传真(最多20字节)
   * 7、address 公司地址(最多60字节)
   * 8、postcode 邮政编码(最多6字节)
   * 9、以上参数信息都必须填写、每个参数都不能为空
   */
  def registDetailInfo = {
    try {
      val a = client.registDetailInfo("大庆优诺信息科技有限公司", "邵宝麟", "", "18646655333", "b.l.shao84@gmail.com", "", "黑龙江省大庆市高新区科技孵化期一期工程3号孵化器805室", "")
      logger.info("register detail info, status:" + a)
      a
    } catch {
      case e: Exception => {
        e.printStackTrace()
        -1
      }
    }
  }

  /**
   * 修改软件序列号密码、注意修改软件序列号密码以后不需要重新注册(激活)
   * serialPwdUpd(String serialPwd, String serialPwdNew)
   * 1、serialPwd 旧密码
   * 2、serialPwdNew 新密码、长度为6位的数字字符串
   */
  def serialPwdUpd(newPassword: String) = {
    try {
      val a = client.serialPwdUpd(Configurations.smsPassword, newPassword)
      logger.info("update password status:" + a)
      a
    } catch {
      case e: Exception => {
        e.printStackTrace()
        -1
      }
    }
  }

  /**
   * 序列号的余额
   * 0.1代表一条
   */
  def getBalance: Double = client.getBalance()

  /**
   * 发送一条短信所需的费用
   */
  def getEachFee = client.getEachFee()

  /**
   * 1、从EUCP平台接收手机用户上行的短信
   * 2、返回值list中的每个元素为一个Mo对象
   * 3、Mo具体数据结构参考使用手册
   */
  def GetMO = {
    try {
      val unreads = client.getMO()
      logger.info("received %s unread sms".format(unreads.size))
    } catch {
      case e: Exception => {
        List.empty[MO]
      }
    }
  }

}
