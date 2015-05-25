package com.puluo.entity

import net.liftweb.mapper.LongMappedMapper
import net.liftweb.mapper.LongKeyedMapper
import net.liftweb.mapper.IdPK
import net.liftweb.mapper.LongKeyedMetaMapper
import net.liftweb.mapper.MappedLongForeignKey
import net.liftweb.mapper.MappedLong
import net.liftweb.mapper.MappedBoolean
import net.liftweb.mapper.MappedDouble
import net.liftweb.mapper.MappedString
import net.liftweb.mapper.UniqueIndex
import net.liftweb.mapper.MappedText
import net.liftweb.mapper.MappedInt
import net.liftweb.mapper.MappedEnum
import net.liftweb.mapper.Index
import net.liftweb.mapper.CreatedUpdated
import net.liftweb.mapper.CreatedTrait
import com.puluo.config.Configurations
import com.puluo.util.Strs
import com.puluo.dao.impl.DaoApi
import net.liftweb.mapper.QueryParam
import net.liftweb.mapper.QueryParam
import net.liftweb.mapper.By

trait UploadFile {
  /**
   * a unique identifier server assign to each uploaded file
   */
  def imageUUID: String
  /**
   * the uploaded file's original name
   */
  def imageName: String

}

trait UploadImage extends UploadFile {
  /**
   * accessable link from image server
   */
  def link:String = Configurations.imgHttpLink(imageUUID)
}

object UserImage extends UserImage
  with LongKeyedMetaMapper[UserImage] {
  override def dbTableName = "user_images"
  //constrains
  override def dbIndexes =  UniqueIndex(uuid,mobile)::super.dbIndexes
  def findByUUID(id:String) = {
    UserImage.find(By(uuid,id))
  }
}
class UserImage extends LongKeyedMapper[UserImage] with IdPK with UploadImage with CreatedTrait {
  def getSingleton = UserImage
  //fields
  object uuid extends MappedString(this, 128)
  object name extends MappedString(this, 50)
  object mobile extends MappedString(this,128)
  def imageUUID = uuid.get
  def imageName = name.get
  def user:PuluoUser = DaoApi.getInstance().userDao().getByMobile(mobile.get)
}

