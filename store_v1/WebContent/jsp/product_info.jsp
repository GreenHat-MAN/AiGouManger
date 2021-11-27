<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- jstl -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>商品详情信息</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
		<!-- 引入自定义css文件 style.css -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css"/>

		<style>
			body {
				margin-top: 20px;
				margin: 0 auto;
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

<c:forEach items="${prolist}" var="pro">
		<div class="container">
			<div class="row">
				<div style="border: 1px solid #e4e4e4;width:930px;margin-bottom:10px;margin:0 auto;padding:10px;margin-bottom:10px;">
					<a href="${pageContext.request.contextPath}/index.jsp">首页&nbsp;&nbsp;&gt;</a>
	
				</div>
				<div style="margin:0 auto;width:950px;">
					<div class="col-md-6">
						<img style="opacity: 1;width:250px;height:250px;" title="" class="medium" src="${pro.pimage}">
					</div>
				
					<div class="col-md-6">
						<div><strong>${pro.pname}</strong></div>
						<div style="border-bottom: 1px dotted #dddddd;width:350px;margin:10px 0 10px 0;">
							<div>编号：${pro.pid}</div>
							<input id="pid" type="hidden" value="${pro.pid}">
						</div>

						<div style="margin:10px 0 10px 0;">亿家价: <strong style="color:#ef0101;">￥：${pro.shop_price}元/份</strong> 参 考 价： <del>￥${pro.market_price}元/份</del>
						</div>

						<div style="margin:10px 0 10px 0;">促销: <a target="_blank" title="限时抢购 (2014-07-30 ~ 2015-01-01)" style="background-color: #f07373;">限时抢购</a> </div>

						<div style="padding:10px;border:1px solid #e7dbb1;width:330px;margin:15px 0 10px 0;;background-color: #fffee6;">
							<div style="margin:5px 0 10px 0;">白色</div>

							<div style="border-bottom: 1px solid #faeac7;margin-top:20px;padding-left: 10px;">购买数量:
								<input id="quantity" name="quantity" value="1" maxlength="4" size="10" type="text"> </div>

							<div style="margin: 20px 0 10px 0;; text-align: center;">
							<%--加入到购物车 --%>
							<a href="javascript:;"> <input id="add"
								style="background: url('${pageContext.request.contextPath}/img/product.gif') no-repeat scroll 0 -600px rgba(0, 0, 0, 0);height:36px;width:127px;"
								value="加入购物车" type="button">
							</a> &nbsp;收藏商品
						</div>
					</div>	
				</div>
				
				<div class="clear"></div>
				<div style="width:950px;margin:0 auto;">
					<div style="background-color:#d3d3d3;width:930px;padding:10px 10px;margin:10px 0 10px 0;">
						<h6>商品介绍</h6>
						<strong>${pro.pdesc}</strong>
					</div>

				</div>

			</div>
		</div>
</div>
</c:forEach>
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
	<script type="text/javascript">
		
		$(function(){
			//添加购物车
			$("#add").click(function(){
				//获取编号
				let pid=$("#pid").val()
				let count=$("#quantity").val()
				$.post("${pageContext.request.contextPath}/CartServlet?method=cartAdd",{pid:pid,count:count},function(msg){
					alert(msg)
				})
			})
		})
	
	</script>

</html>