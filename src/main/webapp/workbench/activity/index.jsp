<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" isELIgnored="false"%>
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
<link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
<script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
<script type="text/javascript" src="jquery/bs_pagination/en.js"></script>

<script type="text/javascript">

	$(function(){
		$(".time").datetimepicker({
			minView: "month",
			language:  'zh-CN',
			format: 'yyyy-mm-dd',
			autoclose: true,
			todayBtn: true,
			pickerPosition: "bottom-left"
		});
		$("#addBtn").click(function () {
			$.ajax({
				url: "workbench/activity/getuser",
				type: "get",
				dataType: "json",
				success :function (data) {

					var html="";
					$.each(data.data,function (i,d) {
						html+="<option value='"+d.id+"'>"+d.name+"</option>";
					})
					$("#createOwner").html(html);
					$("#createOwner").val("${user.id}")
					$("#createActivityModal").modal("show");
				}
			})
		})

		$("#saveBnt").click(function () {
			$.ajax({
				url: "workbench/activity/save",
				data: {
					"owner": $("#createOwner").val(),
					"name": $("#create-marketActivityName").val(),
					"startdate": $("#create-startTime").val(),
					"enddate": $("#create-endTime").val(),
					"cost": $("#create-cost").val(),
					"description": $("#create-describe").val()
				},
				type: "post",
				dataType: "json",
				success :function (data) {
					if(data.code==200) {
						pageList(1,$("#activityPage").bs_pagination("getOption","rowsPerPage"));
					}
					else
						alert("添加活动失败！");
				}
			})
		})

		$("#searchBtn").click(function () {
			$("#hidden-name").val($("#seacrh-Name").val());
			$("#hidden-owner").val($("#seacrh-Owner").val());
			$("#hidden-startdate").val($("#seacrh-startTime").val());
			$("#hidden-enddate").val($("#seacrh-endTime").val());
			pageList(1,$("#activityPage").bs_pagination("getOption","rowsPerPage"));
		})
		pageList(1,5);

		$("#qx").click(function () {
			$("input[id='xz']").prop("checked",this.checked);
		})
		$("#datalist").on("click",$("input[id='xz']"),function () {
			$("#qx").prop("checked",$("input[id='xz']").length==$("input[id='xz']:checked").length);
		})


        //
		$("#deleteBtn").click(function () {
			var $xz=$("input[id=xz]:checked");
			if($xz.length==0){
				alert("请至少选择一条数据");
				return false;
			}
			var param="";
			for (var i=0;i<$xz.length;i++) {
				param += "id=" + $($xz[i]).val();
				if (i < $xz.length - 1)
					param += "&";
			}

			if(confirm("确定删除所选择的数据")) {
				$.ajax({
					url: "workbench/activity/delete",
					data: param,
					type: "post",
					dataType: "json",
					success: function (d) {
						if (d.code == 200) {
							pageList(1,$("#activityPage").bs_pagination("getOption","rowsPerPage"));
						} else {
							alert("删除失败！");
						}
					}
				})
			}
		})

		$("#editBtn").click(function () {
			var $xz=$("input[id='xz']:checked");
			if($xz.length==0){
				alert("请选择一条需要修改的数据！")
			}else if($xz.length>1){
				alert("同时只能修改一条数据！")
			}else {
				$.ajax({
					url: "workbench/activity/getuserandactivity",
					data: {
						"id": $xz.val()
					},
					type: "get",
					dataType: "json",
					success: function (d) {
						var html="";
						$.each(d.data.uList,function (i,d1) {
							html+="<option value='"+d1.id+"'>"+d1.name+"</option>";
						})
						$("#edit-Owner").html(html);
						var adata=d.data.a;
						$("#edit-id").val(adata.id);
						$("#edit-Owner").val(adata.owner);
						$("#edit-Name").val(adata.name);
						$("#edit-startTime").val(adata.startdate);
						$("#edit-endTime").val(adata.enddate);
						$("#edit-describe").val(adata.description);
						$("#edit-cost").val(adata.cost);
						$("#editActivityModal").modal("show");
					}
				})
			}
		})

		$("#edit").click(function () {
			$.ajax({
				url: "workbench/activity/edit",
				data:{
					"id": $("#edit-id").val(),
					"owner": $("#edit-Owner").val(),
			        "name": $("#edit-Name").val(),
			        "starttime": $("#edit-startTime").val(),
			        "endtime": $("#edit-endTime").val(),
			        "description": $("#edit-describe").val(),
			        "cost": $("#edit-cost").val()
				},
				type: "post",
				dataType: "json",
				success: function (d) {
					if(d.data.code=200){
						pageList($("#activityPage").bs_pagination("getOption","currentPage"),
								$("#activityPage").bs_pagination("getOption","rowsPerPage"));
					}else {
						alert("修改数据失败！");
					}
				}
			})
		})

		$("#clearBtn").click(function () {
			$("#hidden-name").val("");
			$("#hidden-owner").val("");
			$("#hidden-startdate").val("");
			$("#hidden-enddate").val("");
			pageList(1,$("#activityPage").bs_pagination("getOption","rowsPerPage"));
		})

	});

	function pageList(pageNo, pageSize) {
		$("#qx").prop("checked",false);
		$("#seacrh-Name").val($("#hidden-name").val());
		$("#seacrh-Owner").val($("#hidden-owner").val());
		$("#seacrh-startTime").val($("#hidden-startdate").val());
		$("#seacrh-endTime").val($("#hidden-enddate").val());
		$.ajax({
			url: "workbench/activity/pagelist",
			data: {
				"pageno": pageNo,
				"pagesize": pageSize,
				"owner": $.trim($("#seacrh-Owner").val()),
				"name": $.trim($("#seacrh-Name").val()),
				"startdate": $.trim($("#seacrh-startTime").val()),
				"enddate": $.trim($("#seacrh-endTime").val())
			},
			type: "get",
			dataType: "json",
			async: false,
			success :function (data) {
				var html="";
				var datalist=data.data;
				if(data.data.size!=0) {
					$.each(data.data.list, function (i, a) {
						html += '<tr class="active">'
						html += '<td><input type="checkbox" id="xz" value="'+a.id+'"/></td>'
						html += '<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'/workbench/activity/detail.jsp?id='+a.id+'\';">' + a.name + '</a></td>'
						html += '<td>' + a.owner + '</td>'
						html += '<td>' + a.startdate + '</td>'
						html += '<td>' + a.enddate + '</td>'
						html += '</tr>'
					})
				}else {
					html+="<p>没有数据<p>"
				}
				$("#datalist").html(html);

				$("#activityPage").bs_pagination({
					currentPage: datalist.pageNum, // 页码
					rowsPerPage: datalist.pageSize, // 每页显示的记录条数
					maxRowsPerPage: 20, // 每页最多显示的记录条数
					totalPages: datalist.pages, // 总页数
					totalRows: datalist.total, // 总记录条数

					visiblePageLinks: 3, // 显示几个卡片

					showGoToPage: true,
					showRowsPerPage: true,
					showRowsInfo: true,
					showRowsDefaultInfo: true,

					onChangePage : function(event, data){
						pageList(data.currentPage , data.rowsPerPage);
					}
				});

			}
		})
	}
</script>
</head>
<body>

    <input type="hidden" id="hidden-name" value=""/>
	<input type="hidden" id="hidden-owner" value=""/>
	<input type="hidden" id="hidden-startdate" value=""/>
	<input type="hidden" id="hidden-enddate" value=""/>

	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">

					<form class="form-horizontal" role="form">

						<input type="hidden" id="edit-id" />
						<div class="form-group">
							<label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-Owner">

								</select>
							</div>
							<label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-Name" >
							</div>
						</div>

						<div class="form-group">
							<label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="edit-startTime" >
							</div>
							<label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="edit-endTime" >
							</div>
						</div>

						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-cost" >
							</div>
						</div>

						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-describe"></textarea>
							</div>
						</div>

					</form>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal" id="edit">更新</button>
				</div>
			</div>
		</div>
	</div>



	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>
				<div class="modal-body">

					<form class="form-horizontal" role="form">

						<div class="form-group">
							<label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="createOwner">

								</select>
							</div>
                            <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-marketActivityName">
                            </div>
						</div>

						<div class="form-group">
							<label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-startTime" />
							</div>
							<label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-endTime" />
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-describe"></textarea>
							</div>
						</div>

					</form>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal" id="saveBnt">保存</button>
				</div>
			</div>
		</div>
	</div>





	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">

			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">

				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text" id="seacrh-Name">
				    </div>
				  </div>

				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="seacrh-Owner">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input class="form-control time" type="text" id="seacrh-startTime" />
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input class="form-control time" type="text" id="seacrh-endTime">
				    </div>
				  </div>

				  <button type="button" id="searchBtn" class="btn btn-default">查询</button>

				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" id="addBtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default"  id="editBtn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="deleteBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
					<button type="button" class="btn btn-default"  id="clearBtn"><span class="glyphicon glyphicon-pencil"></span>清除搜索参数</button>
				</div>

			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="qx" /></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id="datalist">

					</tbody>
				</table>
			</div>

			<div style="height: 50px; position: relative;top: 30px;">
				<div id="activityPage">

				</div>
			</div>

		</div>

	</div>
</body>
</html>
