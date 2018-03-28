package edu.knoldus.emp.impl

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.ServiceCall
import edu.knoldus.emp.api.{Employee, EmployeeService}

import scala.collection.mutable.ListBuffer
import scala.concurrent.Future

class EmployeeServiceImpl extends EmployeeService {

  val listOfEmployee = new ListBuffer[Employee]

  override def postEmployee(): ServiceCall[Employee, ListBuffer[Employee]] = ServiceCall {
    request =>
      val newList = Employee(request.id, request.name, request.organisation)
      listOfEmployee += newList
      Future.successful(listOfEmployee)
  }

  override def getEmployeeById(id: Int): ServiceCall[NotUsed, ListBuffer[Employee]] = ServiceCall {
    request =>
      Future.successful(listOfEmployee.filter(x => x.id == id))
  }

  override def deleteEmployeeById(id: Int): ServiceCall[NotUsed, ListBuffer[Employee]] = ServiceCall {
    request =>
      val idThatYouWantToDelete = listOfEmployee.filter(ele => ele.id == id)
      listOfEmployee --= idThatYouWantToDelete
      Future.successful(listOfEmployee)
  }

  override def updateEmployeeById(id: Int): ServiceCall[Employee, ListBuffer[Employee]] = ServiceCall {
    request =>
      val idThatYouWantToDelete = listOfEmployee.filter(ele => ele.id == id)
      listOfEmployee --= idThatYouWantToDelete
      val newList = Employee(request.id, request.name, request.organisation)
      listOfEmployee += newList
      Future.successful(listOfEmployee)
  }
}