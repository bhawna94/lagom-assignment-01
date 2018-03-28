package edu.knoldus.emp.api

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Service, ServiceCall}
import play.api.libs.json.{Format, Json}

import scala.collection.mutable.ListBuffer

trait EmployeeService extends Service {


  def postEmployee(): ServiceCall[Employee, ListBuffer[Employee]]

  def getEmployeeById(id: Int): ServiceCall[NotUsed, ListBuffer[Employee]]

  def deleteEmployeeById(id: Int): ServiceCall[NotUsed,ListBuffer[Employee]]

 def updateEmployeeById(id: Int): ServiceCall[Employee,ListBuffer[Employee]]
  override final def descriptor = {
    // val list1 = List(Employee(1,"bhawna","xyz"),Employee(2,"neel","abc"))
    import Service._
    // @formatter:off
    named("hello-lagom")
      .withCalls(
       restCall(Method.GET,"/api/get/:id",getEmployeeById _),
        restCall(Method.POST,"/api/post",postEmployee _),
        restCall(Method.DELETE,"/api/delete/:id",deleteEmployeeById _),
        restCall(Method.PUT,"/api/put/:id",updateEmployeeById _)
      ).withAutoAcl(true)
    // @formatter:on
  }
}




case class Employee(id:Int,name:String,organisation:String)
object Employee {
  implicit val format: Format[Employee] = Json.format[Employee]
}
