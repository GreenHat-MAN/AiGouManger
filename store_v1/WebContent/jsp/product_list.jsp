<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- jstl -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>商品列表</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
		<!-- 引入自定义css文件 style.css -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css"/>

		<style>
			body {
				margin-top: 20px;
				margin: 0 auto;
				width: 100%;
			}
			.carousel-inner .item img {
				width: 100%;
				height: 300px;
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
				<div class="col-md-3" style="padding-top:20px">
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
							<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
								<span class="sr-only">Toggle navigation</span>
								<span class="icon-bar"></span>
								<span class="icon-bar"></span>
								<span class="icon-bar"></span>
							</button>
							<a class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp">首页</a>
						</div>

						<!-- Collect the nav links, forms, and other content for toggling -->
						<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
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


		<div class="row" style="width:1210px;margin:0 auto;">
			<div class="col-md-12">
				<ol class="breadcrumb">
					<li><a href="${pageContext.request.contextPath}/index.jsp">首页</a></li>
				</ol>
			</div>
			
			
			<c:forEach items="${pageBean.data}" var="stu">
			
			<div class="col-md-2">
				<a href="${pageContext.request.contextPath}/ProductServlet?method=ProductInfo&pid=${stu.pid}">
					<img src="${stu.pimage}" width="170" height="170" style="display: inline-block;">
				</a>
				<p><a href="${pageContext.request.contextPath}/ProductServlet?method=ProductInfo&pid=${stu.pid}" style='color:green'>${stu.pname}</a></p>
				<p><font color="#FF0000">商城价：&yen;${stu.shop_price}</font></p>
			</div>
			
			</c:forEach>
			
		</div>

		<!--分页 -->
		<div style="width:380px;margin:0 auto;margin-top:50px;">
	<ul class="pagination" style="text-align:center; margin-top:10px;">
	<li class="disabled">
	<a href="${pageContext.request.contextPath}/ProductServlet?method=ProductList&pageIndex=1&pageSize=${pageBean.pageSize}"><span aria-hidden="true">&laquo;</span></a></li>
	<c:if test="${pageBean.pageIndex>1}">
		<li><a href="${pageContext.request.contextPath}/ProductServlet?method=ProductList&pageIndex=${pageBean.pageIndex-1}&pageSize=${pageBean.pageSize}">上一页</a></li>
	</c:if>

    
    <c:forEach begin="${pageBean.startPage}" end="${pageBean.endPage}" var="n" >
    <li><a href="${pageContext.request.contextPath}/ProductServlet?method=ProductList&pageIndex=${n}&pageSize=${pageBean.pageSize}">${n}</a></li>
    </c:forEach>



	<c:if test="${pageBean.pageIndex<pageBean.pageCount}">
		<li><a href="${pageContext.request.contextPath}/ProductServlet?method=ProductList&pageIndex=${pageBean.pageIndex+1}&pageSize=${pageBean.pageSize}">下一页</a></li>
	</c:if>
	<li>
	<a href="${pageContext.request.contextPath}/ProductServlet?method=ProductList&pageIndex=${pageBean.pageCount}&pageSize=${pageBean.pageSize}"><span aria-hidden="true">&raquo;</span></a>
	</li>
	</ul>
		</div>
		<!-- 分页结束=======================        -->

		<!--
       		商品浏览记录:
        -->
		<div style="width:1210px;margin:0 auto; padding: 0 9px;border: 1px solid #ddd;border-top: 2px solid #999;height: 246px;">

			<h4 style="width: 50%;float: left;font: 14px/30px " 微软雅黑 ";">浏览记录</h4>
			<div style="width: 50%;float: right;text-align: right;"><a href="">more</a></div>
			<div style="clear: both;"></div>

			<div style="overflow: hidden;">

				<ul style="list-style: none;">
					<li style="width: 150px;height: 216;float: left;margin: 0 8px 0 0;padding: 0 18px 15px;text-align: center;"><img src="${pageContext.request.contextPath}/products/1/cs10001.jpg" width="130px" height="130px" /></li>
				</ul>

			</div>
		</div>
		<div style="margin-top:50px;">
			<img src="${pageContext.request.contextPath}/img/footer.jpg" width="100%" height="78" alt="我们的优势" title="我们的优势" />
		</div>

		<div style="text-align: center;margin-top: 5px;">
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
		<div style="text-align: center;margin-top: 5px;margin-bottom:20px;">
			Copyright &copy; 2005-2020 爱购网 版权所有
		</div>

	</body>

</html>