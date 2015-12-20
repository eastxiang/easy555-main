<html>
<body>
	<div class="container">
		<div class="login">
			<div class="title">==用户登录==</div>
			<div class="form">
				<div style="margin-right: 30px;">
					message:${message!""}<br>
	               	error:${error!""}
	               	
	            </div>
				<form id="loginForm" method="post" class="form-horizontal">

					<div class="control-group">
						<label for="username">用户名、邮箱或手机号</label>
						<div class="input-prepend">
							<span class="add-on icon-user"></span>
							<input type="text" id="username" name="username" value="" class="input-xlarge" >
						</div>
					</div>
					<div class="control-group">
						<label for="password">密码</label>
						<div class="input-prepend">
							<span class="add-on icon-key"></span>
							<input type="password" id="password" name="password"
							class="input-xlarge validate[required]" placeholder="请输入密码">
						</div>
					</div>
					
					<#if jcaptchaEbabled >
						<%-- jcaptchaEbabled 在JCaptchaValidateFilter设置 --%>
						<div class="control-group">
							<label for="jcaptchaCode">验证码</label>
							<div class="input-prepend">
								<span class="add-on icon-circle-blank"></span>
								<input id="jcaptchaCode" type="text"  name="jcaptchaCode"
								class="input-medium validate[required,ajax[ajaxJcaptchaCall]]" placeholder="请输入验证码">
							</div>
							<img class="jcaptcha-btn jcaptcha-img" style="margin-left: 10px;" src="${ctx}/jcaptcha.jpg" title="点击更换验证码">
							<a class="jcaptcha-btn btn btn-link">换一张</a>
						</div>
					</#if>

					<div class="control-group">
						<label class="checkbox remember"><input type="checkbox" name="rememberMe" value="true">下次自动登录</label>
						<input id="submitForm" type="submit" class="btn btn-login pull-left" value="登录">
					</div>
				</form>
			</div>
		</div> 
	</div>
</body>
</html>