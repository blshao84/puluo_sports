package com.puluo.snippet.util

import net.liftweb.mapper.view.SortedMapperPaginatorSnippet
import net.liftweb.mapper.MetaMapper
import com.puluo.config.Configurations
import scala.xml.NodeSeq
import net.liftweb.http.S
import net.liftweb.mapper.MappedField
import net.liftweb.mapper.Mapper
import scala.xml.Text

abstract class SortedChineseMapperPaginator[T <: Mapper[T]](meta: MetaMapper[T],
  initialSort: net.liftweb.mapper.MappedField[_, T],
  headers: (String, MappedField[_, T])*) extends SortedMapperPaginatorSnippet[T](meta, initialSort, headers: _*) {
  def isAscending:Boolean = false
  //Descending by default
  override def sort = (headers.indexWhere { case (_, `initialSort`) => true; case _ => false }, isAscending)
  override def currentXml: NodeSeq =
    if (count == 0)
      Text("没有可以显示的记录")
    else
      Text(S.?("",
        Array(recordsFrom, recordsTo, count).map(_.asInstanceOf[AnyRef]): _*))
  override def itemsPerPage = 10
  override def pagesXml(pages: Seq[Int], sep: NodeSeq): NodeSeq = {
    val currentPage = S.param("offset").map(_.toInt / itemsPerPage).getOrElse(1)
    pages.toList map { n =>
      pageXml(n * itemsPerPage, Text(n + 1 toString))
    } match {
      case one :: Nil => one
      case first :: rest => {
        val allPages = first :: rest
        val start = currentPage - (currentPage%5)
        val end = start + 6
        val lastFive = allPages.size - 5
        
        val pagesOnDisplay = allPages.map(p=>{
          val pageNum = getPageNumFromNode(p.toString)
          if((pageNum>=start && pageNum <= end)||(pageNum>=lastFive)) (Some((p,pageNum)))
          else None
        }).flatten
        val pagesOnDisplayWithDots = pagesOnDisplay.foldLeft(List[(NodeSeq,Int)]())((res,p)=>{
          if(res.isEmpty) res:::List(p) else {
            val last = res.last
            if(p._2!=last._2+1) res:::List((Text("......"),0),p) else res:::List(p)
          }
        }).map(_._1)
        val (f::r) = pagesOnDisplayWithDots
        r.foldLeft(f) {
          case (a, b) => a ++ sep ++ b
        }
      }
      case Nil => Nil
    }
  }
  private def getPageNumFromNode(p:String) = {
    p.split('>').last.split('<').head.toInt
  }
}