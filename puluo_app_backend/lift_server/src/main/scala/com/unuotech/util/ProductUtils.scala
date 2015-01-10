package com.unuotech.util

import scala.xml.{ NodeSeq, Text }
import net.liftweb.common.Loggable
import net.liftweb.util.CssSel
import net.liftweb.common.Full
import net.liftweb.http.S
import net.liftweb.common.Box
import net.liftweb.common.Failure
import net.liftweb.util.Helpers._
import net.liftweb.http._
import net.liftweb.mapper.By
import com.unuotech.model.sales.Stock
import com.unuotech.config.Configurations

/**
 * common helpers for getting Stocks and displaying them
 */
trait StockHelpers extends Loggable {
  protected def many(stocks: List[Stock]) = stocks.map(a => single(a))

  /**
   * In reality i'd build these types of functions as a type class,
   * but that's really too much complexity to be introducted at this level
   */
  protected def single(stock: Stock): CssSel = {
    logger.info("display stock:"+stock)
    val product = stock.product.obj.get
    val sid = stock.id.get
    val link = ("/stock?id="+sid)
    ".name [href]" #> link &
    ".name *" #> product.name.get &
      ".detail *" #> Text(product.description.get) &
      ".picLink [href]" #> link &
      ".picBox [src]" #> (stock.largeOrSmallerImageLinks.head) &
      ".price" #> stock.price
  }
  
  protected def single(box: Box[Stock]): CssSel =
    single(box.openOr(new Stock))

  protected def boxToNotice[T](sucsess: String, warning: String)(f: => Box[T]) {
    f match {
      case Full(value) =>
        S.notice(sucsess)
      case Failure(msg, _, _) =>
        S.error(msg)
      case _ =>
        S.warning(warning)
    }
  }
}
