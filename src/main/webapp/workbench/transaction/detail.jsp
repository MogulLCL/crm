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

    <style type="text/css">
        .mystage{
            font-size: 20px;
            vertical-align: middle;
            cursor: pointer;
        }
        .closingDate{
            font-size : 15px;
            cursor: pointer;
            vertical-align: middle;
        }
    </style>

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript">

	//默认情况下取消和保存按钮是隐藏的
	var cancelAndSaveBtnDefault = true;
	var id=window.location.search.split("=")[1];
	var json,stages,knx,stage,data;

	$(function(){

		$("#remark").focus(function(){
			if(cancelAndSaveBtnDefault){
				//设置remarkDiv的高度为130px
				$("#remarkDiv").css("height","130px");
				//显示
				$("#cancelAndSaveBtn").show("2000");
				cancelAndSaveBtnDefault = false;
			}
		});

		$("#cancelBtn").click(function(){
			//显示
			$("#cancelAndSaveBtn").hide();
			//设置remarkDiv的高度为130px
			$("#remarkDiv").css("height","90px");
			cancelAndSaveBtnDefault = true;
		});

		$(".remarkDiv").mouseover(function(){
			$(this).children("div").children("div").show();
		});

		$(".remarkDiv").mouseout(function(){
			$(this).children("div").children("div").hide();
		});

		$(".myHref").mouseover(function(){
			$(this).children("span").css("color","red");
		});

		$(".myHref").mouseout(function(){
			$(this).children("span").css("color","#E6E6E6");
		});

		getPossibility();

		getStage();

		getTranId();

        getTranHistory();

        ListTranHistory();


    });

	function sx() {
		//阶段提示框
		$(".mystage").popover({
			trigger: 'manual',
			placement: 'bottom',
			html: 'true',
			animation: false
		}).on("mouseenter", function () {
			var _this = this;
			$(this).popover("show");
			$(this).siblings(".popover").on("mouseleave", function () {
				$(_this).popover('hide');
			});
		}).on("mouseleave", function () {
			var _this = this;
			setTimeout(function () {
				if (!$(".popover:hover").length) {
					$(_this).popover("hide")
				}
			}, 100);
		});
	}
	function getTranId() {
		$.ajax({
			url: "workbench/tran/gettranid",
			type: "get",
			data: {"id":id},
			datatype: "json",
			async: false,
			success: function (d) {
				//alert(d.data);
				data=d.data;
				$("#top-name").html(data.name+"&nbsp;<small>$"+data.money+"</small>");
				$("#date1").html(data.expecteddate+"&nbsp;");
				$("#owner").html(data.owner+"&nbsp;");
				$("#money").html(data.money);
				$("#name").html(data.name+"&nbsp;");
				$("#expecteddate").html(data.expecteddate+"&nbsp;");
				$("#customerid").html(data.customerid+"&nbsp;");
				$("#stage").html(data.stage+"&nbsp;");
				$("#knx").html(json[data.stage]+"&nbsp;");
				knx=parseInt(json[data.stage]);
				stage=data.stage;
				$("#type").html(data.type+"&nbsp;");
				$("#source").html(data.source+"&nbsp;");
				$("#activityid").html(data.activityid+"&nbsp;");
				$("#contactsid").html(data.contactsid+"&nbsp;");
				$("#createby").html(data.createby+"&nbsp;");
				$("#createtime").html(data.createtime+"&nbsp;");
				$("#editby").html(data.editby+"&nbsp;");
				$("#edittime").html(data.edittime+"&nbsp;");
				$("#description").html(data.description+"&nbsp;");
				$("#nextcontacttime").html(data.nextcontacttime+"&nbsp;");
				$("#contactsummary").html(data.contactsummary+"&nbsp;");
			}
		})
	}

	function getPossibility() {
		$.ajax({
			url: "workbench/tran/getpossibility",
			type: "get",
			datatype: "json",
			async: false,
			success: function (d) {
				json  = d.data;
			}
		})
	}

    function getStage() {
        $.ajax({
            url: "workbench/tran/getstage",
            type: "get",
            datatype: "json",
            async: false,
            success: function (d) {
                stages  = d.data;
            }
        })
    }

	function getTranHistory() {
	    $.ajax({
            url: "workbench/tran/gettranhistorybytranid",
            data: {"id":id},
            datatype: "json",
            type: "get",
            async: false,
            success: function (d) {
                var html="";
                $.each(d.data,function (i,a) {
                    html+='<tr>';
                    html+='<td>'+a.stage+'</td>';
                    html+='<td>'+a.money+'</td>';
                    html+='<td>'+json[a.stage]+'</td>';
                    html+='<td>'+a.expecteddate+'</td>';
                    html+='<td>'+a.createtime+'</td>';
                    html+='<td>'+a.createby+'</td>';
                    html+='</tr>';
                })
                $("#TranHistoryList").html(html);
            }
        })
    }

    function ListTranHistory() {
	    var html="阶段&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
        if(knx==0){
            $.each(stages,function (i,a) {
                var k=parseInt(json[a.text]);
                if(k==0) {
                    if (stage == a.value) {
                        html += '<span id="'+i+'" onclick="changeStage(\''+a.text+'\',\''+i+'\')" class="glyphicon glyphicon-remove mystage" data-toggle="popover" data-placement="bottom" data-content="' + a.text + '" style="color: #ff0000;"></span>'
                    } else {
                        html += '<span id="'+i+'" onclick="changeStage(\''+a.text+'\',\''+i+'\')" class="glyphicon glyphicon-remove mystage" data-toggle="popover" data-placement="bottom" data-content="' + a.text + '" style="color: #000000;"></span>'
                    }
                }else {
                    html += '<span id="'+i+'" onclick="changeStage(\''+a.text+'\',\''+i+'\')" class="glyphicon glyphicon-record mystage" data-toggle="popover" data-placement="bottom" data-content="' + a.text + '" style="color: #000000;"></span>'
                }
                if(stages.length-1!=i){
                    html+='-----------'
                }
            });
        }else {
            $.each(stages,function (i,a) {
                var k=parseInt(json[a.text]);
                if(k==0){
                    html += '<span id="'+i+'" onclick="changeStage(\''+a.text+'\',\''+i+'\')" class="glyphicon glyphicon-remove mystage" data-toggle="popover" data-placement="bottom" data-content="' + a.text + '" style="color: #000000;"></span>'
                }else {
                    if(k<knx){
                        html += '<span id="'+i+'" onclick="changeStage(\''+a.text+'\',\''+i+'\')" class="glyphicon glyphicon-ok-circle mystage" data-toggle="popover" data-placement="bottom" data-content="' + a.text + '" style="color: #90F790;"></span>'
                    }else if(k==knx){
                        html += '<span id="'+i+'" onclick="changeStage(\''+a.text+'\',\''+i+'\')" class="glyphicon glyphicon-map-marker mystage" data-toggle="popover" data-placement="bottom" data-content="' + a.text + '" style="color: #90f790;"></span>'
                    }else {
                        html += '<span id="'+i+'" onclick="changeStage(\''+a.text+'\',\''+i+'\')" class="glyphicon glyphicon-record mystage" data-toggle="popover" data-placement="bottom" data-content="' + a.text + '" style="color: #000000;"></span>'
                    }
                }
                if(stages.length-1!=i){
                    html+='-----------'
                }
            });
        }
        html+='&nbsp;&nbsp;&nbsp;2<span class="closingDate" id="date1">010-10-10</span>';
        $("#tttt").html(html);
        sx();
    }

    function changeStage(a,b){
		if(a==data.stage){
			return false;
		}
	    $.ajax({
			url: "workbench/tran/updatetran",
			data: {
				"id": id,
				"stage": a,
				"money": data.money,
				"expecteddate": data.expecteddate
			},
			dataType: "json",
			type: "post",
			success: function (d) {
				if(d.code==200){
					getTranId();
					getTranHistory();
					ListTranHistory();
					sx();
					return true;
				}
				alert("更新失败");
			}
		})
    }

</script>

</head>
<body>

	<!-- 返回按钮 -->
	<div style="position: relative; top: 35px; left: 10px;">
		<a href="javascript:void(0);" onclick="window.history.back();"><span class="glyphicon glyphicon-arrow-left" style="font-size: 20px; color: #DDDDDD"></span></a>
	</div>

	<!-- 大标题 -->
	<div style="position: relative; left: 40px; top: -30px;">
		<div class="page-header">
			<h3 id="top-name">&nbsp;</h3>
		</div>
		<div style="position: relative; height: 50px; width: 250px;  top: -72px; left: 700px;">
			<button type="button" class="btn btn-default" onclick="window.location.href='workbench/transaction/edit.jsp';"><span class="glyphicon glyphicon-edit"></span> 编辑</button>
			<button type="button" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
		</div>
	</div>

	<!-- 阶段状态 -->
	<div style="position: relative; left: 40px; top: -50px;" id="tttt">
<%--		<span id="aaaa" class="glyphicon glyphicon-ok-circle mystage" data-toggle="popover" data-placement="bottom" data-content="01资质审查" style="color: #90F790;"></span>--%>
<%--		-------------%>
<%--		<span class="glyphicon glyphicon-ok-circle mystage" data-toggle="popover" data-placement="bottom" data-content="02需求分析" style="color: #90F790;"></span>--%>
<%--		-------------%>
<%--		<span class="glyphicon glyphicon-ok-circle mystage" data-toggle="popover" data-placement="bottom" data-content="03价值建议" style="color: #90F790;"></span>--%>
<%--		-------------%>
<%--		<span class="glyphicon glyphicon-map-marker mystage" data-toggle="popover" data-placement="bottom" data-content="04确定决策者" style="color: #90F790;"></span>--%>
<%--		-------------%>
<%--		<span class="glyphicon glyphicon-map-marker mystage" data-toggle="popover" data-placement="bottom" data-content="05提案/报价" style="color: #90F790;"></span>--%>
<%--		-------------%>
<%--		<span class="glyphicon glyphicon-record mystage" data-toggle="popover" data-placement="bottom" data-content="06谈判/复审"></span>--%>
<%--		-------------%>
<%--		<span class="glyphicon glyphicon-record mystage" data-toggle="popover" data-placement="bottom" data-content="07成交"></span>--%>
<%--		-------------%>
<%--		<span class="glyphicon glyphicon-record mystage" data-toggle="popover" data-placement="bottom" data-content="08丢失的线索"></span>--%>
<%--		-------------%>
<%--		<span class="glyphicon glyphicon-record mystage" data-toggle="popover" data-placement="bottom" data-content="09因竞争丢失关闭"></span>--%>
<%--		-------------%>
<%--        <span id="90" class="glyphicon glyphicon-ok-circle mystage" data-toggle="popover" data-placement="bottom" data-content="111" style="color: #90F790;"></span>--%>
	</div>

	<!-- 详细信息 -->
	<div style="position: relative; top: 0px;">
		<div style="position: relative; left: 40px; height: 30px;">
			<div style="width: 300px; color: gray;">所有者</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b id="owner">&nbsp;</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">金额</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b id="money">&nbsp;</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 10px;">
			<div style="width: 300px; color: gray;">名称</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b id="name">&nbsp;</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">预计成交日期</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b id="expecteddate">&nbsp;</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 20px;">
			<div style="width: 300px; color: gray;">客户名称</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b id="customerid">&nbsp;</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">阶段</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b id="stage">&nbsp;</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 30px;">
			<div style="width: 300px; color: gray;">类型</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b id="type">&nbsp;</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">可能性</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b id="knx">&nbsp;</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 40px;">
			<div style="width: 300px; color: gray;">来源</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b id="source">&nbsp;</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">市场活动源</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b id="activityid">&nbsp;</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 50px;">
			<div style="width: 300px; color: gray;">联系人名称</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b id="contactsid">&nbsp;</b></div>
			<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 60px;">
			<div style="width: 300px; color: gray;">创建者</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b id="createby">&nbsp;&nbsp;</b><small style="font-size: 10px; color: gray;" id="createtime"></small></div>
			<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 70px;">
			<div style="width: 300px; color: gray;">修改者</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b id="editby">&nbsp;&nbsp;</b><small style="font-size: 10px; color: gray;" id="edittime"></small></div>
			<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 80px;">
			<div style="width: 300px; color: gray;">描述</div>
			<div style="width: 630px;position: relative; left: 200px; top: -20px;">
				<b id="description">
					&nbsp;
				</b>
			</div>
			<div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 90px;">
			<div style="width: 300px; color: gray;">联系纪要</div>
			<div style="width: 630px;position: relative; left: 200px; top: -20px;">
				<b id="contactsummary">
					&nbsp;&nbsp;
				</b>
			</div>
			<div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 100px;">
			<div style="width: 300px; color: gray;">下次联系时间</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b id="nextcontacttime">&nbsp;</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
	</div>

	<!-- 备注 -->
	<div style="position: relative; top: 100px; left: 40px;">
		<div class="page-header">
			<h4>备注</h4>
		</div>

		<!-- 备注1 -->
		<div class="remarkDiv" style="height: 60px;">
			<img title="zhangsan" src="image/user-thumbnail.png" style="width: 30px; height:30px;">
			<div style="position: relative; top: -40px; left: 40px;" >
				<h5>哎呦！</h5>
				<font color="gray">交易</font> <font color="gray">-</font> <b>动力节点-交易01</b> <small style="color: gray;"> 2017-01-22 10:10:10 由zhangsan</small>
				<div style="position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;">
					<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-edit" style="font-size: 20px; color: #E6E6E6;"></span></a>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-remove" style="font-size: 20px; color: #E6E6E6;"></span></a>
				</div>
			</div>
		</div>

		<!-- 备注2 -->
		<div class="remarkDiv" style="height: 60px;">
			<img title="zhangsan" src="image/user-thumbnail.png" style="width: 30px; height:30px;">
			<div style="position: relative; top: -40px; left: 40px;" >
				<h5>呵呵！</h5>
				<font color="gray">交易</font> <font color="gray">-</font> <b>动力节点-交易01</b> <small style="color: gray;"> 2017-01-22 10:20:10 由zhangsan</small>
				<div style="position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;">
					<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-edit" style="font-size: 20px; color: #E6E6E6;"></span></a>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-remove" style="font-size: 20px; color: #E6E6E6;"></span></a>
				</div>
			</div>
		</div>

		<div id="remarkDiv" style="background-color: #E6E6E6; width: 870px; height: 90px;">
			<form role="form" style="position: relative;top: 10px; left: 10px;">
				<textarea id="remark" class="form-control" style="width: 850px; resize : none;" rows="2"  placeholder="添加备注..."></textarea>
				<p id="cancelAndSaveBtn" style="position: relative;left: 737px; top: 10px; display: none;">
					<button id="cancelBtn" type="button" class="btn btn-default">取消</button>
					<button type="button" class="btn btn-primary">保存</button>
				</p>
			</form>
		</div>
	</div>

	<!-- 阶段历史 -->
	<div>
		<div style="position: relative; top: 100px; left: 40px;">
			<div class="page-header">
				<h4>阶段历史</h4>
			</div>
			<div style="position: relative;top: 0px;">
				<table id="activityTable" class="table table-hover" style="width: 900px;">
					<thead>
						<tr style="color: #B3B3B3;">
							<td>阶段</td>
							<td>金额</td>
							<td>可能性</td>
							<td>预计成交日期</td>
							<td>创建时间</td>
							<td>创建人</td>
						</tr>
					</thead>
					<tbody id="TranHistoryList">
						<tr>
							<td>资质审查</td>
							<td>5,000</td>
							<td>10</td>
							<td>2017-02-07</td>
							<td>2016-10-10 10:10:10</td>
							<td>zhangsan</td>
						</tr>
						<tr>
							<td>需求分析</td>
							<td>5,000</td>
							<td>20</td>
							<td>2017-02-07</td>
							<td>2016-10-20 10:10:10</td>
							<td>zhangsan</td>
						</tr>
						<tr>
							<td>谈判/复审</td>
							<td>5,000</td>
							<td>90</td>
							<td>2017-02-07</td>
							<td>2017-02-09 10:10:10</td>
							<td>zhangsan</td>
						</tr>
					</tbody>
				</table>
			</div>

		</div>
	</div>

	<div style="height: 200px;"></div>

</body>
</html>
