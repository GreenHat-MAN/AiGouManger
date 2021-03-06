<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>XXX网络商城</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" />
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
	</head>

	<body>
		<div class="container-fluid">

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

			<!--
            	描述：轮播条
            -->
			<div class="container-fluid">
				<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
					<!-- Indicators -->
					<ol class="carousel-indicators">
						<li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
						<li data-target="#carousel-example-generic" data-slide-to="1"></li>
						<li data-target="#carousel-example-generic" data-slide-to="2"></li>
					</ol>

					<!-- Wrapper for slides -->
					<div class="carousel-inner" role="listbox">
						<div class="item active">
							<img src="${pageContext.request.contextPath}/img/ad/banner02.jpg">
							<div class="carousel-caption">

							</div>
						</div>
						<div class="item">
							<img src="${pageContext.request.contextPath}/img/ad/banner03.jpg">
							<div class="carousel-caption">

							</div>
						</div>
						<div class="item">
							<img src="${pageContext.request.contextPath}/img/ad/banner04.jpg">
							<div class="carousel-caption">

							</div>
						</div>
					</div>

					<!-- Controls -->
					<a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
						<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
						<span class="sr-only">Previous</span>
					</a>
					<a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
						<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
						<span class="sr-only">Next</span>
					</a>
				</div>
			</div>
			<!--

            	描述：商品显示
            -->
			<div class="container-fluid">
				<div class="col-md-12">
					<h2>热门商品&nbsp;&nbsp;<img src="${pageContext.request.contextPath}/img/title2.jpg"/></h2>
				</div>
				<div class="col-md-2" style="border:1px solid #E7E7E7;border-right:0;padding:0;">
					<img src="products/hao/big01.jpg" width="205" height="404" style="display: inline-block;"/>
				</div>
				<div class="col-md-10">
					<div class="col-md-6" style="text-align:center;height:200px;padding:0px;">
						<a href="product_info.htm">
							<img src="products/hao/middle01.jpg" width="516px" height="200px" style="display: inline-block;">
						</a>
					</div>
				
					<!-- 热门商品 -->
					<c:forEach items="${hotProList }" var="hotPro">
						<div class="col-md-2" style="text-align:center;height:200px;padding:10px 0px;">
							<a href="${pageContext.request.contextPath}/ProductServlet?method=ProductInfo&pid=${hotPro.pid}">
								<img src="${hotPro.pimage }" width="130" height="130" style="display: inline-block;">
							</a>	
							<p><a href="${pageContext.request.contextPath}/ProductServlet?method=ProductInfo&pid=${hotPro.pid}" style='color:#666'>${hotPro.pname }</a></p>
							<p><font color="#E4393C" style="font-size:16px">¥${hotPro.shop_price }</font></p>
						</div>
					</c:forEach>
				
				</div>
			</div>
			<!--

            	描述：广告部分
            -->
            <div class="container-fluid">
				<img src="products/hao/ad.jpg" width="100%"/>
			</div>
			<!--

            	描述：商品显示
            -->
			<div class="container-fluid">
				<div class="col-md-12">
					<h2>最新商品&nbsp;&nbsp;<img src="${pageContext.request.contextPath}/img/title2.jpg"/></h2>
				</div>
				<div class="col-md-2" style="border:1px solid #E7E7E7;border-right:0;padding:0;">
					<img src="products/hao/big01.jpg" width="205" height="404" style="display: inline-block;"/>
				</div>
				<div class="col-md-10">
					<div class="col-md-6" style="text-align:center;height:200px;padding:0px;">
						<a href="product_info.htm">
							<img src="products/hao/middle01.jpg" width="516px" height="200px" style="display: inline-block;">
						</a>
					</div>
				
					<!-- 最新商品 -->
					<c:forEach items="${newProList }" var="newPro">
						<div class="col-md-2" style="text-align:center;height:200px;padding:10px 0px;">
							<a href="${pageContext.request.contextPath}/ProductServlet?method=ProductInfo&pid=${newPro.pid}">
								<img src="${newPro.pimage }" width="130" height="130" style="display: inline-block;">
							</a>	
							<p><a href="${pageContext.request.contextPath}/ProductServlet?method=ProductInfo&pid=${newPro.pid}" style='color:#666'>${newPro.pname }</a></p>
							<p><font color="#E4393C" style="font-size:16px">¥${newPro.shop_price }</font></p>
						</div>
					</c:forEach>
				
			</div>
			</div>			
			<!--
            	描述：页脚部分
            -->
			<div class="container-fluid">
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
						<li><a>支付方式</a></li>
						<li><a>配送方式</a></li>
						<li><a>服务声明</a></li>
						<li><a>广告声明</a></li>
					</ul>
				</div>
				<div style="text-align: center;margin-top: 5px;margin-bottom:20px;">
					Copyright &copy; 2005-2020 爱购网 版权所有
				</div>
			</div>
		</div>
	</body>

</html>