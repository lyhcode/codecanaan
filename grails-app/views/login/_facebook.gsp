<section>

    <legend><i class="icon icon-facebook-sign"></i> 使用 Facebook 帳號登入</legend>
    
    <ol>
        <li>請使用您個人的 <a href="http://www.facebook.com/r.php" target="_blank">Facebook 帳號</a>登入。</li>
        <li>請點選下方「Connect with Facebook」藍色按鈕，開始進行登入驗證。</li>
        <li>若您遇到「授權請求」時，請點選「同意」執行 CodeCanaan 應用程式。</li>
        <li>登入完成後，會自動重新回到壹學院。</li>
    </ol>
    
    <!--登入按鈕-->
    <div style="padding:30px">
	    <facebookAuth:connect permissions="email,user_about_me" />
    </div>
    
    <hr />
    
    <p>如果您真的沒有 Facebook 帳號，也沒有意願申請一組，那麼您可以填寫傳統的註冊表單，並使用<g:link controller="login" action="auth" params="[method: 'builtin']">一般會員</g:link>帳號登入。</p>
    
</section>
