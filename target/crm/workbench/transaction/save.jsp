<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + 	request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<base href="<%=basePath%>">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="jquery/bs_typeahead/bootstrap3-typeahead.min.js"></script>
<script type="text/javascript">
	var json;
	$(function () {
		$("#create-customerName").typeahead({
			source: function (query, process) {
				$.get(
						"workbench/tran/getcustomername",
						{ "name" : query },
						function (data) {
							//alert(data);
							process(data.data);
						},
						"json"
				);
			},
			delay: 1500
		});

		$("#addTranForm").click(function () {
			$("#forms").submit();
		})

		getUserNmae();

		getStage();

		$(".time").datetimepicker({
			minView: "month",
			language:  'zh-CN',
			format: 'yyyy-mm-dd',
			autoclose: true,
			todayBtn: true,
			pickerPosition: "bottom-left"
		});

		$("#create-transactionStage").change(function () {
			var stage=$("#create-transactionStage").val();
			$("#create-possibility").val(json[stage]);
		})

		$("#aname").keydown(function (event) {
			if(event.keyCode==13){
				$.ajax({
					url: "workbench/tran/getactivitybyname",
					type: "get",
					data: {
						"aname": $.trim($("#aname").val())
					},
					datatype: "json",
					success: function (d) {
						var html="";
						$.each(d.data,function (i,a) {
							html+='<tr>';
							html+='<td><input type="radio" name="activity" value="'+a.id+'"/></td>';
							html+='<td id="'+a.id+'">'+a.name+'</td>';
							html+='<td>'+a.startdate+'</td>';
							html+='<td>'+a.enddate+'</td>';
							html+='<td>'+a.owner+'</td>';
							html+='</tr>';
						})
						$("#activitylist").html(html);
					}
				});
				return false;
			}
		})

		$("#addactivityid").click(function () {
			var $xz=$("input[name=activity]:checked");
			$("#activity").val($xz.val());
			$("#create-activitySrc").val($("#"+$xz.val()).html());
			$("#aname").val("");
			$("#activitylist").html("");
			$("#findMarketActivity").modal("hide");
		})

		$("#cname").keydown(function (event) {
			if(event.keyCode==13){
				$.ajax({
					url: "workbench/tran/getcontactsbyname",
					type: "get",
					data: {
						"name": $.trim($("#cname").val())
					},
					datatype: "json",
					success: function (d) {
						var html="";
						$.each(d.data,function (i,a) {
							html+='<tr>';
							html+='<td><input type="radio" name="contactstest" value="'+a.id+'"/></td>';
							html+='<td id="'+a.id+'">'+a.fullname+'</td>';
							html+='<td>'+a.email+'</td>';
							html+='<td>'+a.mphone+'</td>';
							html+='</tr>';
						})
						$("#contactslist").html(html);
					}
				});
				return false;
			}
		})

		$("#addcontacts").click(function () {
			var $xz=$("input[name=contactstest]:checked");
			//alert($xz.val());
			$("#contacts").val($xz.val());
			$("#create-contactsName").val($("#"+$xz.val()).html());
			$("#cname").val("");
			$("#contactslist").html("");
			$("#findContacts").modal("hide");
			//alert($("#contacts").val());
		})
	})

	function gethref() {
		window.location.href="workbench/transaction/index.jsp";
	}
	function getUserNmae() {
		$.ajax({
			url: "workbench/tran/getuser",
			type: "get",
			datatype: "json",
			success: function (d) {
				//alert(d.data);
				var html="";
				var id='${user.id}';
				$.each(d.data,function (i,a) {
				     html+='<option value="'+a.id+'">'+a.name+'</option>';
				});
				$("#create-transactionOwner").html(html);
				$("#create-transactionOwner").val(id);
			}
		})
	}

	function getStage() {
		$.ajax({
			url: "workbench/tran/getpossibility",
			type: "get",
			datatype: "json",
			success: function (d) {
				json  = d.data;
			}
		})
	}
</script>
</head>
<body>

	<!-- 查找市场活动 -->
	<div class="modal fade" id="findMarketActivity" role="dialog">
		<div class="modal-dialog" role="document" style="width: 80%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">查找市场活动</h4>
				</div>
				<div class="modal-body">
					<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
						<form class="form-inline" role="form">
						  <div class="form-group has-feedback">
						    <input type="text" class="form-control" style="width: 300px;" placeholder="请输入市场活动名称，支持模糊查询" id="aname">
						    <span class="glyphicon glyphicon-search form-control-feedback"></span>
						  </div>
						</form>
					</div>
					<table id="activityTable3" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
						<thead>
							<tr style="color: #B3B3B3;">
								<td></td>
								<td>名称</td>
								<td>开始日期</td>
								<td>结束日期</td>
								<td>所有者</td>
							</tr>
						</thead>
						<tbody id="activitylist">
<%--							<tr>--%>
<%--								<td><input type="radio" name="activity"/></td>--%>
<%--								<td>发传单</td>--%>
<%--								<td>2020-10-10</td>--%>
<%--								<td>2020-10-20</td>--%>
<%--								<td>zhangsan</td>--%>
<%--							</tr>--%>
<%--							<tr>--%>
<%--								<td><input type="radio" name="activity"/></td>--%>
<%--								<td>发传单</td>--%>
<%--								<td>2020-10-10</td>--%>
<%--								<td>2020-10-20</td>--%>
<%--								<td>zhangsan</td>--%>
<%--							</tr>--%>
						</tbody>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" id="addactivityid" >提交</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 查找联系人 -->
	<div class="modal fade" id="findContacts" role="dialog">
		<div class="modal-dialog" role="document" style="width: 80%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">查找联系人</h4>
				</div>
				<div class="modal-body">
					<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
						<form class="form-inline" role="form">
						  <div class="form-group has-feedback">
						    <input type="text" class="form-control" style="width: 300px;" placeholder="请输入联系人名称，支持模糊查询" id="cname">
						    <span class="glyphicon glyphicon-search form-control-feedback"></span>
						  </div>
						</form>
					</div>
					<table id="activityTable" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
						<thead>
							<tr style="color: #B3B3B3;">
								<td></td>
								<td>名称</td>
								<td>邮箱</td>
								<td>手机</td>
							</tr>
						</thead>
						<tbody id="contactslist">

						</tbody>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" id="addcontacts" >提交</button>
				</div>
			</div>
		</div>
	</div>

	<div style="position:  relative; left: 30px;">
		<h3>创建交易</h3>
		<div style="position: relative; top: -40px; left: 70%;">
			<button type="button" class="btn btn-primary" id="addTranForm">保存</button>
			<button type="button" class="btn btn-default">取消</button>
		</div>
		<hr style="position: relative; top: -40px;">
	</div>
	<form action="workbench/tran/add" method="post" class="form-horizontal" role="form" style="position: relative; top: -30px;" id="forms"  >

		<div class="form-group">

			<label for="create-transactionOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<select class="form-control" id="create-transactionOwner" name="owner">

				</select>
			</div>
			<label for="create-amountOfMoney" class="col-sm-2 control-label">金额</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-amountOfMoney" name="money">
			</div>
		</div>

		<div class="form-group">
			<label for="create-transactionName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-transactionName" name="name">
			</div>
			<label for="create-expectedClosingDate" class="col-sm-2 control-label">预计成交日期<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control time" id="create-expectedClosingDate" name="expecteddate">
			</div>
		</div>

		<div class="form-group">
			<label for="create-accountName" class="col-sm-2 control-label">客户名称<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-customerName" placeholder="支持自动补全，输入客户不存在则新建" name="customerid">
			</div>
			<label for="create-transactionStage" class="col-sm-2 control-label">阶段<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
			  <select class="form-control" id="create-transactionStage" name="stage">
			  	<option></option>
			  	<c:forEach items="${stage}" var="i">
					<option value="${i.value}">${i.text}</option>
				</c:forEach>
			  </select>
			</div>
		</div>

		<div class="form-group">
			<label for="create-transactionType" class="col-sm-2 control-label">类型</label>
			<div class="col-sm-10" style="width: 300px;">
				<select class="form-control" id="create-transactionType" name="type">
				  <option></option>
				  <c:forEach items="${transactionType}" var="i">
					  <option value="${i.value}">${i.text}</option>
				  </c:forEach>
				</select>
			</div>
			<label for="create-possibility" class="col-sm-2 control-label">可能性</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-possibility">
			</div>
		</div>

		<div class="form-group">
			<label for="create-clueSource" class="col-sm-2 control-label">来源</label>
			<div class="col-sm-10" style="width: 300px;">
				<select class="form-control" id="create-clueSource" name="source">
				  <option></option>
				  <c:forEach items="${source}" var="i">
					  <option value="${i.value}">${i.text}</option>
				  </c:forEach>
				</select>
			</div>
			<label for="create-activitySrc" class="col-sm-2 control-label">市场活动源&nbsp;&nbsp;<a href="javascript:void(0);" data-toggle="modal" data-target="#findMarketActivity"><span class="glyphicon glyphicon-search"></span></a></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-activitySrc">
				<input type="hidden" id="activity" value="" name="activityid"/>
			</div>
		</div>

		<div class="form-group">
			<label for="create-contactsName" class="col-sm-2 control-label">联系人名称&nbsp;&nbsp;<a href="javascript:void(0);" data-toggle="modal" data-target="#findContacts"><span class="glyphicon glyphicon-search"></span></a></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control"  id="create-contactsName">
				<input type="hidden" id="contacts" value="" name="contactsid"/>
			</div>
		</div>

		<div class="form-group">
			<label for="create-describe" class="col-sm-2 control-label">描述</label>
			<div class="col-sm-10" style="width: 70%;">
				<textarea class="form-control" rows="3" id="create-describe" name="description"></textarea>
			</div>
		</div>

		<div class="form-group">
			<label for="create-contactSummary" class="col-sm-2 control-label">联系纪要</label>
			<div class="col-sm-10" style="width: 70%;">
				<textarea class="form-control" rows="3" id="create-contactSummary" name="contactsummary"></textarea>
			</div>
		</div>

		<div class="form-group">
			<label for="create-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control time" id="create-nextContactTime" name="nextcontacttime">
			</div>
		</div>

	</form>
</body>
</html>
