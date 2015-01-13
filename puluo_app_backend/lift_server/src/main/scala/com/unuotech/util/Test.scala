package com.puluo.util

import scala.xml.Node

object Test {
  def main(args:Array[String]):Unit = {
    val body =  <div class="conn">
      <form method="post" action="/account/sign_up">
        <div id="user_login">
          <h2>新用户注册</h2>
          <div>
            <span>用户名</span>
            <input id="txtEmail" type="text" maxlength="48" value=""/>
            <p>请设定您的电子邮件</p>
          </div>
          <div>
            <span>请设置密码</span>
            <input id="password" type="password" value=""/>
            <p>密码可以由数字、字母、特殊符号组成，长度为6-16位</p>
          </div>
          <div>
            <span>请确认密码</span>
            <input id="password_confirm" type="password" value=""/>
            <p>请在此输入相同的密码</p>
          </div>
          <div>
            <span>验证码</span><input id="code" type="text" name="code"/>
            <p>请输入下面图片中的验证码 （不区分大小写）</p>
          </div>
          <p id="agreement"><input type="checkbox" name="agreement"/><a target="_blank" href="/account/aggreement">我已阅读并同意</a></p>
          <div class="zhuce">
            <span class="mar"><span class="auth_code">1554</span></span><input class="button1" type="submit"/>
          </div>
        </div>
        <div id="user_register">
          <div id="content">
            注册成为我们的用户：
            <dl>
              <dd>最先接收第一手时尚信息</dd>
              <dd>最先得知我们的优惠活动</dd>
              <dd>最方便快捷的购物体验</dd>
              <dd>最准确的无误的物流查询</dd>
            </dl>
          </div>
        </div>
        <div class="clearit"></div>
      </form>
    </div>
    val test = <span>test</span>
    def process(node:Node):Seq[Node] = {
      
      node.attributes.asAttrMap.get("id") match {
        case Some(id) => {
          id match {
            case "txtEmai" => test
            case "password" => test
            case "password_confirm" => test
            case _ => node.child.map(process(_)).flatten
          }
        }
        case None => Seq(node)
      }
    }
    println(process(body).toString)
  }
}