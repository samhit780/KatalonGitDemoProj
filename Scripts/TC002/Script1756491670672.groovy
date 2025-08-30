import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.text.SimpleDateFormat

import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.keyword.builtin.SelectOptionByIndexKeyword
import com.kms.katalon.core.webui.keyword.builtin.SelectOptionByValueKeyword
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebDriver as Keys


//OpenBrowser
//WebUI.openBrowser('')
CustomWebDriver.startChromeWithCustomOptions()

WebUI.navigateToUrl('https://katalon-demo-cura.herokuapp.com/')

WebUI.maximizeWindow()

//Verify Page Title
String actualTitle = WebUI.getWindowTitle()

String expectedTitle = 'CURA Healthcare Service'

WebUI.verifyMatch(actualTitle, expectedTitle, false, FailureHandling.CONTINUE_ON_FAILURE)

//Click on Appointment Button
WebUI.click(findTestObject('Object Repository/HomePage/a_We Care About Your Health_btn-make-appointment'))

TestObject usernameText = findTestObject('Object Repository/LoginPage/input_Demo account_form-control')
//usernameText.addProperty("xpath", ConditionType.EQUALS, "//input[@placeholder='Username' and @aria-describedby='demo_username_label']")
WebUI.waitForElementVisible(usernameText, 10)

TestObject passwordText = new TestObject()
passwordText.addProperty("xpath", ConditionType.EQUALS, "//input[@placeholder='Password' and @aria-describedby='demo_password_label']")
WebUI.waitForElementVisible(passwordText, 10)

String username = WebUI.getAttribute(usernameText, 'value').trim()
String password = WebUI.getAttribute(passwordText, 'value').trim()


//Retrieve Username & Password
println('Username: ' + username + 'Password: ' + password)

//Set Username & Password
WebUI.setText(findTestObject('Object Repository/LoginPage/input_Username_txt-username'), username)
WebUI.setText(findTestObject('Object Repository/LoginPage/input_Password_txt-password'), password)
WebUI.click(findTestObject('Object Repository/LoginPage/button_Password_btn-login'))


//Make Appointment
WebUI.verifyElementText(findTestObject('Object Repository/MakeAppointment_Page/header_MakeAppointment'), 'Make Appointment')

WebUI.selectOptionByIndex(findTestObject('Object Repository/MakeAppointment_Page/dropdown_Facility'), 1)

WebUI.click(findTestObject('Object Repository/MakeAppointment_Page/chkbox_Apply for hospital readmission'))
WebUI.click(findTestObject('Object Repository/MakeAppointment_Page/input_Medicaid_radio_program_medicaid'))
WebUI.setText(findTestObject('Object Repository/MakeAppointment_Page/textarea_Comment_txt_comment'), 'Testing')

//Date Picker
WebUI.click(findTestObject('Object Repository/MakeAppointment_Page/input_Visit Date (Required)_txt_visit_date'))

TestObject dateHeader = new TestObject()
dateHeader.addProperty("xpath", ConditionType.EQUALS, "(.//*[normalize-space(text()) and normalize-space(.)='«'])[1]/following::th[1]")
WebUI.waitForElementVisible(dateHeader, 10)

String monthYear = WebUI.getText(dateHeader)
println('Date Header: ' + monthYear)

String date = "10-October-2025"
String eDay = date.split("-")[0]
String eMonth = date.split("-")[1]
String eYear = date.split("-")[2]

while(!monthYear.equals(eMonth+" "+eYear)) {
	WebUI.click(findTestObject('Object Repository/MakeAppointment_Page/DatePicker/calendar_nextArrow'))
	monthYear = WebUI.getText(dateHeader)
	println('Month Year: ' + monthYear)
	
	WebUI.click(findTestObject('Object Repository/MakeAppointment_Page/DatePicker/td_day', [('value') : eDay]))
}


//Button Appointment
WebUI.click(findTestObject('Object Repository/MakeAppointment_Page/button_Comment_btn-book-appointment'))


//Verifying Appointment Confirmation
String headingText = WebUI.getText(findTestObject('Object Repository/AppointmentConfirmationPage/heading_AppointmentConfirmation'))
WebUI.verifyEqual(headingText, 'Appointment Confirmation')

//Change Date Format
SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MMMM-yyyy", Locale.ENGLISH)
SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy")

//Convert inpuut to Date object
Date parsedDate = inputFormat.parse(date)
String actualConvertedDate = outputFormat.format(parsedDate)

println('Converted Date: ' + actualConvertedDate)

//Verify Date
String visitDate = WebUI.getText(findTestObject('Object Repository/AppointmentConfirmationPage/txt_visit_date'))
WebUI.verifyEqual(actualConvertedDate, visitDate)

//Close Browser
//WebUI.closeBrowser()

