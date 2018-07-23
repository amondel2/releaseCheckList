package com.amondel.checklist

class ExtendTagsTagLib {
	static namespace="ps"
    static defaultEncodeAs = [taglib:'html']
	static encodeAsForTags = [renderMonthlyBox: 'raw',renderCheckAllBox: 'raw']
	def springSecurityService
	
	def getUserFName = {attrs,body->
		User user = springSecurityService.currentUser
		  def name =  user ? user.username : ""
		  out << body() <<name
	}
//	
//	def renderMonthlyBox = {attrs,body->
//
//		def hmCal = new GregorianCalendar(attrs.year,attrs.month,1)
//		def hmeCal = new GregorianCalendar(attrs.year,attrs.month,1)
//		hmeCal.add(hmCal.DAY_OF_MONTH, hmCal.getActualMaximum(hmCal.DAY_OF_MONTH))
//		hmeCal.add(hmCal.SECOND, -1)
//		def output=""
//		def hmd = HouseMonth.withCriteria {
//			eq("house",attrs.hm)
//			months{
//				between('startDate',hmCal.getTime(),hmeCal.getTime())
//			}
//		}?.getAt(0)
//		if(hmd) {
//			output = "<input type='checkbox' name='${attrs.hm.number}myCheckbox${attrs.month}' hn='${attrs.hm.number}' hmdm='${hmd.months.id}' hmhn='${attrs.hm.id}' year='${attrs.year}' month='${attrs.month}' id='${attrs.hm.number}myCheckbox${attrs.month}' class='dueMonthCheckBox'" + (hmd.paid ? "checked" :  '')  +  '/>'
//		} else {
//			output = "<button id='${attrs.hm.number}addMonthsBtn' class='dueMonthAddButton' hnid='${attrs.hm.id}' hn='${attrs.hm.number}' year='${attrs.year}' month='${attrs.month}' name='${attrs.hm.number}addMonthsBtn'>Add Due Month</button>"
//		}
//		out << body() << output
//	}
//	
//	def renderCheckAllBox = { attrs, body ->
//		out << body() <<  """
//		<ul class='nav navbar-nav'><li><a href='#' style="padding:0px;">${attrs.header}</a></li><li>
//		<li class='dropdown'>
//		  <a class='dropdown-toggle' data-toggle='dropdown' href='#' style="padding:0px;">
//			<span class="checkBox_Bass"></span>
//			<span class='caret'></span></a>
//			<ul class='dropdown-menu'>
//				  <li class="checkAllBtn" orient="${attrs.orient}"><a href='#'>All</a></li>
//				  <li class="checkNoneBtn" orient="${attrs.orient}"><a href='#'>None</a></li>
//			</ul>
//		  </li>
//		</ul>
//	 """	
//	
//	}
	
}
