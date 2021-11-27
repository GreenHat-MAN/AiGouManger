<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>

<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>购物车</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css"
	type="text/css" />
<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"
	type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css" type="text/css" />
<style>
body {
	margin-top: 20px;
	margin: 0 auto;
}

.carousel-inner .item img {
	width: 100%;
	height: 300px;
}

.container .row div {
	/* position:relative;
	 float:left; */
	
}

font {
	color: #3164af;
	font-size: 18px;
	font-weight: normal;
	padding: 0 10px;
}
</style>
</head>

<body>


	<!--
            	描述：菜单栏
            -->
	<div class="container-fluid">
		<div class="col-md-4">
			<img src="${pageContext.request.contextPath}/img/logo2.png" />
		</div>
		<div class="col-md-5">
			<img src="${pageContext.request.contextPath}/img/header.png" />
		</div>
		<div class="col-md-3" style="padding-top: 20px">
			<ol class="list-inline">
					<c:if test="${empty user }">
						<li><a href="${pageContext.request.contextPath}/jsp/login.jsp">登录</a></li>
						<li><a href="${pageContext.request.contextPath}/UserServlet?method=registUI">注册</a></li>
					</c:if>
					<c:if test="${!empty user }">
					  <li><a href="javascript:;">${user.username}</a></li>
					</c:if>
						<li><a href="${pageContext.request.contextPath}/CartServlet?method=getCartInfo">购物车</a></li>
						<li><a href="${pageContext.request.contextPath}/OrderServlet?method=findMyOrder">我的订单</a></li>
					<c:if test="${!empty user }">
					  <li><a href="${pageContext.request.contextPath}/UserServlet?method=logout">注销</a></li>
					</c:if>
					</ol>
		</div>
	</div>
	<!--
            	描述：导航条
            -->
	<div class="container-fluid">
		<nav class="navbar navbar-inverse">
			<div class="container-fluid">
				<!-- Brand and toggle get grouped for better mobile display -->
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed"
						data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
						aria-expanded="false">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp">首页</a>
				</div>

				<!-- Collect the nav links, forms, and other content for toggling -->
				<div class="collapse navbar-collapse"
					id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav">
								<li class="active"><a href="${pageContext.request.contextPath}/ProductServlet?method=ProductList">手机数码<span class="sr-only">(current)</span></a></li>
								<li><a href="#">户外运动</a></li>
								<li><a href="#">电脑办公</a></li>
								<li><a href="#">家具家居</a></li>
								<li><a href="#">鞋靴箱包</a></li>
								<li><a href="#">图书音像</a></li>
								<li><a href="#">母婴孕婴</a></li>
								<li><a href="#">汽车用品</a></li>
							</ul>
					<form class="navbar-form navbar-right" role="search">
						<div class="form-group">
							<input type="text" class="form-control" placeholder="Search">
						</div>
						<button type="submit" class="btn btn-default">Submit</button>
					</form>

				</div>
				<!-- /.navbar-collapse -->
			</div>
			<!-- /.container-fluid -->
		</nav>
	</div>


	<div class="container">
		<div class="row">

			<div style="margin: 0 auto; margin-top: 10px; width: 950px;">
				<strong style="font-size: 16px; margin: 5px 0;">订单详情</strong>
				<table class="table table-bordered">
					<tbody>
						<tr class="warning">
							<th>图片</th>
							<th>商品</th>
							<th>价格</th>
							<th>数量</th>
							<th>小计</th>
							<th>操作</th>
						</tr>
						<c:if test="${fn:length(cartList)==0}">
						<tr><td colspan="6" style="text-align: center"> 购物车还没有任何商品快去选购吧！</td></tr>
						</c:if>
						
						<c:forEach items="${cartList}" var="item">
							<tr class="active">
								<td width="60" width="40%"><img
									src="${pageContext.request.contextPath}/${item.product.pimage}"
									width="70" height="60"></td>
								<td width="30%"><a target="_blank">${item.product.pname }</a></td>
								<td width="20%">￥${item.product.shop_price }</td>
								<td width="10%"><input type="number" name="quantity"
									maxlength="4" min="1" max="9999999" size="10" pid="${item.product.pid }" class="count" value="${item.count }"></td>
								<td width="15%"><span class="subtotal">￥${item.product.shop_price*item.count }</span></td>
								<td><a href="javascript:;" value="${item.product.pid }" class="delete">删除</a></td>
							</tr>


						</c:forEach>

					</tbody>
				</table>
			</div>
		</div>

		<div style="margin-right: 130px;">
			<div style="text-align: right;">
				<em style="color: #ff6600;"> 
				<c:if test="${!empty user }">
					 欢迎您：${user.username}
					</c:if>
				<c:if test="${empty user }">
					登录后确认是否享有优惠
					</c:if>
				&nbsp;&nbsp; </em> 赠送积分: <em
					style="color: #ff6600;" id=point>596</em>&nbsp; 商品金额: <strong
					style="color: #ff6600;" id="money">￥596.00元</strong>
			</div>
			<div
				style="text-align: right; margin-top: 10px; margin-bottom: 10px;">
				<a
					href="${pageContext.request.contextPath}/CartServlet?method=cartEmpty"
					id="clear" class="clear">清空购物车</a> 
					<a
					href="${pageContext.request.contextPath}/OrderServlet?method=submitOrder"> <%--提交表单 --%>
					<input type="submit" width="100" value="提交订单" name="submit"
					border="0"
					style="background: url('${pageContext.request.contextPath}/img/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0);
						height:35px;width:100px;color:white;">
				</a>
			</div>
		</div>

	</div>

	<div style="margin-top: 50px;">
		<img src="${pageContext.request.contextPath}/img/footer.jpg"
			width="100%" height="78" alt="我们的优势" title="我们的优势" />
	</div>

	<div style="text-align: center; margin-top: 5px;">
		<ul class="list-inline">
			<li><a href="${pageContext.request.contextPath}/jsp/info.jsp">关于我们</a></li>
			<li><a>联系我们</a></li>
			<li><a>招贤纳士</a></li>
			<li><a>法律声明</a></li>
			<li><a>友情链接</a></li>
			<li><a target="_blank">支付方式</a></li>
			<li><a target="_blank">配送方式</a></li>
			<li><a>服务声明</a></li>
			<li><a>广告声明</a></li>
		</ul>
	</div>
	<div style="text-align: center; margin-top: 5px; margin-bottom: 20px;">
		Copyright &copy; 2005-2020 爱购网 版权所有</div>

</body>
<script type="text/javascript">
	$(function() {
		let countMoney=0
		//金额计算
		$(".subtotal").each(function(a,b){
			let sum=$(b).text().replace('￥','')
			countMoney+= parseFloat(sum)
			
		})
		$("#point").text(parseInt(countMoney))
		$("#money").text("￥"+countMoney)
		
		//删除
		$(".delete").click(function(){
			var flag=confirm("确定删除该商品？")
			if(flag){
				let pid=$(this).attr("value")
				$.post("${pageContext.request.contextPath}/CartServlet?method=cartDel",{pid:pid},function(msg){
						alert(msg)
						window.location.reload()
					})
			}
			
		})
		
		//修改数量
		$(".count").change(function(){
				let count=$(this).val()
				let pid=$(this).attr("pid")
				$.post("${pageContext.request.contextPath}/CartServlet?method=cartCount",{pid:pid,count:count},function(msg){
						alert(msg)
						window.location.reload()
					})
			
		})
	})
</script>
</html>