<!DOCTYPE html><html><head><meta charset="utf-8"></head><body id="preview">
<h1><a id="Simple_App_Analitics_Backend_0"></a>Simple App Analitics Backend</h1>
<h2><a id="Installation_2"></a>Installation</h2>
<ul>
<li>Install <a href="http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html">JDK 8</a></li>
<li>Install <a href="https://maven.apache.org">Maven</a></li>
<li>Install MySQL and Tomcat 7 (For example <a href="https://www.apachefriends.org/ru/index.html">XAMPP</a>)</li>
<li>Configure Tomcat server in Maven:</li>
</ul>
<h4><a id="MAVEN_PATHconfsettingsxml_7"></a>%MAVEN_PATH%/conf/settings.xml</h4>
<pre><code class="language-sh">&lt;?xml version=<span class="hljs-string">"1.0"</span> encoding=<span class="hljs-string">"UTF-8"</span>?&gt;
&lt;settings ...&gt;
    &lt;servers&gt;

        &lt;server&gt;
            &lt;id&gt;TomcatServer&lt;/id&gt;
            &lt;username&gt;admin&lt;/username&gt;
            &lt;password&gt;password&lt;/password&gt;
        &lt;/server&gt;

    &lt;/servers&gt;
&lt;/settings&gt;
</code></pre>
<ul>
<li>Setup Tomcat 7 user:</li>
</ul>
<h4><a id="TOMCAT7_PATHconftomcatusersxml_23"></a>%TOMCAT7_PATH%/conf/tomcat-users.xml</h4>
<pre><code class="language-sh">&lt;?xml version=<span class="hljs-string">'1.0'</span> encoding=<span class="hljs-string">'utf-8'</span>?&gt;
&lt;tomcat-users&gt;

    &lt;role rolename=<span class="hljs-string">"manager-gui"</span>/&gt;
    &lt;role rolename=<span class="hljs-string">"manager-script"</span>/&gt;
    &lt;user username=<span class="hljs-string">"admin"</span> password=<span class="hljs-string">"password"</span> roles=<span class="hljs-string">"manager-gui,manager-script"</span> /&gt;

&lt;/tomcat-users&gt;
</code></pre>
<ul>
<li><em>(Optional)</em> Change the properties in <em>properties</em> section from pom.xml</li>
<li>Run Command Prompt and execute</li>
</ul>
<pre><code class="language-sh">mvn tomcat7:deploy
</code></pre>
<h2><a id="Usage_39"></a>Usage</h2>
<p>All data is sent and received as JSON.</p>
<h3><a id="User_management_41"></a>User management</h3>
<pre><code class="language-sh">POST /accounts
</code></pre>
<p>Use this call to register a new user.<br>
The request is a JSON object with the following fields.</p>
<table class="table table-striped table-bordered">
<thead>
<tr>
<th>Header</th>
<th>Value</th>
</tr>
</thead>
<tbody>
<tr>
<td>username</td>
<td>Unique name must be unique</td>
</tr>
<tr>
<td>password</td>
<td>Must be from 3 to 15 characters length</td>
</tr>
</tbody>
</table>
<h3><a id="Signing_in_51"></a>Signing in</h3>
<p>The following values are needed for sign in.</p>
<ul>
<li>username</li>
<li>password</li>
</ul>
<p>To obtain these keys it is needed to register user.</p>
<pre><code class="language-sh">/oauth/token?grant_<span class="hljs-built_in">type</span>=password&amp;username=&lt;username&gt;&amp;password=&lt;password&gt;
</code></pre>
<p>User this call to sign in.<br>
The server response is a JSON object with the following fields.</p>
<table class="table table-striped table-bordered">
<thead>
<tr>
<th>Header</th>
<th>Value</th>
</tr>
</thead>
<tbody>
<tr>
<td>access_token</td>
<td>The token you need to use for private api authentication</td>
</tr>
<tr>
<td>token_type</td>
<td>OAuth2 token type</td>
</tr>
<tr>
<td>refresh_token</td>
<td>Use refresh token if access token is expired</td>
</tr>
<tr>
<td>rexpires_in</td>
<td>Lifetime of access_token</td>
</tr>
<tr>
<td>scope</td>
<td>User permissions</td>
</tr>
<tr>
<td>jti</td>
<td>identifier for the JWT</td>
</tr>
</tbody>
</table>
<h3><a id="Public_API_70"></a>Public API</h3>
<h4><a id="Requirements_71"></a>Requirements</h4>
<p>The following keys are needed when submitting events.</p>
<ul>
<li>application id</li>
<li>private key</li>
</ul>
<p>To obtain these keys it is needed to register/login user and create your game.</p>
<h4><a id="Authentication_77"></a>Authentication</h4>
<p>Authentication is handled by specifying the <em>Authorization</em> header for the request.</p>
<p>The authentication value is a HMAC SHA-256 digest of the MD5 Hash from the raw body content from the request using the private key.<br>
<em>Authorization</em> header example:</p>
<table class="table table-striped table-bordered">
<thead>
<tr>
<th>Header</th>
<th>Value</th>
</tr>
</thead>
<tbody>
<tr>
<td>Authorization</td>
<td>Application ID:HMAC HASH</td>
</tr>
</tbody>
</table>
<h4><a id="Routes_85"></a>Routes</h4>
<pre><code class="language-sh">POST /public/app/&lt;applocation id&gt;/install
</code></pre>
<p>The install call should be requested when a first session starts.<br>
The server response is a JSON object with the following fields.</p>
<table class="table table-striped table-bordered">
<thead>
<tr>
<th>Field</th>
<th>Description</th>
</tr>
</thead>
<tbody>
<tr>
<td>id</td>
<td>Installation id</td>
</tr>
<tr>
<td>data</td>
<td>Installation data</td>
</tr>
<tr>
<td>appID</td>
<td>Application ID</td>
</tr>
</tbody>
</table>
<pre><code class="language-sh">POST /public/app/&lt;applocation id&gt;/install/&lt;install ID/session
</code></pre>
<p>The session call should be requested when a new session starts.<br>
The server response is a JSON object with the following fields.</p>
<table class="table table-striped table-bordered">
<thead>
<tr>
<th>Field</th>
<th>Description</th>
</tr>
</thead>
<tbody>
<tr>
<td>id</td>
<td>Session id</td>
</tr>
<tr>
<td>appID</td>
<td>appication ID</td>
</tr>
</tbody>
</table>
<pre><code class="language-sh">POST /public/application/&lt;applocation id&gt;/session/&lt;session id&gt;/event
</code></pre>
<p>Use this call for submitting events.<br>
The request is a JSON object with the following fields.</p>
<table class="table table-striped table-bordered">
<thead>
<tr>
<th>Field</th>
<th>Description</th>
</tr>
</thead>
<tbody>
<tr>
<td>name</td>
<td>Unique name which identificates event</td>
</tr>
<tr>
<td>data</td>
<td>list of key-value pairs</td>
</tr>
</tbody>
</table>
<p>data field have the following fields</p>
<table class="table table-striped table-bordered">
<thead>
<tr>
<th>Field</th>
<th>Description</th>
</tr>
</thead>
<tbody>
<tr>
<td>name</td>
<td>Unique name which identificates data</td>
</tr>
<tr>
<td>value</td>
<td>data value</td>
</tr>
</tbody>
</table>
<p>The server response is a JSON object with the following fields.</p>
<table class="table table-striped table-bordered">
<thead>
<tr>
<th>Field</th>
<th>Description</th>
</tr>
</thead>
<tbody>
<tr>
<td>id</td>
<td>Session id</td>
</tr>
<tr>
<td>appID</td>
<td>appication ID</td>
</tr>
</tbody>
</table>
<h3><a id="Private_API_124"></a>Private API</h3>
<h4><a id="Requirements_125"></a>Requirements</h4>
<p>The OAuth2 <em>access_token</em> is needed when submitting this events.<br>
To obtain these token it is needed to register and then login.</p>
<h4><a id="Authentication_128"></a>Authentication</h4>
<p>Authentication is handled by specifying the <em>Authorization</em> header for the request.</p>
<p>The authentication value is Bearer &lt;<em>access_token</em>&gt;<br>
<em>Authorization</em> header example:</p>
<table class="table table-striped table-bordered">
<thead>
<tr>
<th>Header</th>
<th>Value</th>
</tr>
</thead>
<tbody>
<tr>
<td>Authorization</td>
<td>Bearer &lt;<em>access_token</em>&gt;</td>
</tr>
</tbody>
</table>
<h4><a id="Routes_136"></a>Routes</h4>
<pre><code class="language-sh">POST /private/application
</code></pre>
<p>Use this call for creating a new application.<br>
The server request is a JSON object with the following fields.</p>
<table class="table table-striped table-bordered">
<thead>
<tr>
<th>Header</th>
<th>Value</th>
</tr>
</thead>
<tbody>
<tr>
<td>name</td>
<td>Application title</td>
</tr>
</tbody>
</table>
<pre><code class="language-sh">GET /private/application/&lt;application id&gt;
</code></pre>
<p>Use this call to retrieve application with given application id.<br>
The server response is a JSON object with the following fields.</p>
<table class="table table-striped table-bordered">
<thead>
<tr>
<th>Header</th>
<th>Value</th>
</tr>
</thead>
<tbody>
<tr>
<td>id</td>
<td>Application id</td>
</tr>
<tr>
<td>name</td>
<td>Application title</td>
</tr>
<tr>
<td>ownerID</td>
<td>The user which created the application</td>
</tr>
<tr>
<td>privateKey</td>
<td>Application private key</td>
</tr>
</tbody>
</table>
<pre><code class="language-sh">DELETE /private/application/&lt;application id&gt;
</code></pre>
<p>This call should be requested when you need to delete the application</p>
<pre><code class="language-sh">GET /private/application/&lt;application id&gt;/session/&lt;session id&gt;
</code></pre>
<p>Use this call to retrieve session with given session id.<br>
The server response is a JSON object with the following fields.</p>
<table class="table table-striped table-bordered">
<thead>
<tr>
<th>Header</th>
<th>Value</th>
</tr>
</thead>
<tbody>
<tr>
<td>id</td>
<td>Application id</td>
</tr>
<tr>
<td>appID</td>
<td>Application id</td>
</tr>
</tbody>
</table>
<pre><code class="language-sh">GET /private/application/&lt;application id&gt;/session
</code></pre>
<p>Use this call to retrieve a list of sessions of application.<br>
The server response is a list of JSON objects with the following fields.</p>
<table class="table table-striped table-bordered">
<thead>
<tr>
<th>Header</th>
<th>Value</th>
</tr>
</thead>
<tbody>
<tr>
<td>id</td>
<td>Application id</td>
</tr>
<tr>
<td>appID</td>
<td>Application id</td>
</tr>
</tbody>
</table>
<pre><code class="language-sh">DELETE /private/application/&lt;application id&gt;/session/&lt;session id&gt;
</code></pre>
<p>This call should be requested when you need to delete the session</p>
<pre><code class="language-sh">GET /private/application/&lt;application id&gt;/session/&lt;session id&gt;/event/&lt;event id&gt;
</code></pre>
<p>Use this call to retrieve event with given event id.<br>
The server response is a JSON object with the following fields.</p>
<table class="table table-striped table-bordered">
<thead>
<tr>
<th>Field</th>
<th>Description</th>
</tr>
</thead>
<tbody>
<tr>
<td>name</td>
<td>Unique name which identificates event</td>
</tr>
<tr>
<td>data</td>
<td>list of key-value pairs</td>
</tr>
</tbody>
</table>
<p>data field have the following fields</p>
<table class="table table-striped table-bordered">
<thead>
<tr>
<th>Field</th>
<th>Description</th>
</tr>
</thead>
<tbody>
<tr>
<td>name</td>
<td>Unique name which identificates data</td>
</tr>
<tr>
<td>value</td>
<td>data value</td>
</tr>
</tbody>
</table>
<pre><code class="language-sh">GET /private/roles
</code></pre>
<p>This call retrieves all applications belongs to logged user<br>
The response is a JSON list of  objects with the following fields.</p>
<table class="table table-striped table-bordered">
<thead>
<tr>
<th>Header</th>
<th>Value</th>
</tr>
</thead>
<tbody>
<tr>
<td>profile</td>
<td>Profile JSON object</td>
</tr>
<tr>
<td>app</td>
<td>Appication JSON object</td>
</tr>
<tr>
<td>role</td>
<td>String wich represents role name</td>
</tr>
</tbody>
</table>
<p>The profile field has following fields.</p>
<table class="table table-striped table-bordered">
<thead>
<tr>
<th>Header</th>
<th>Value</th>
</tr>
</thead>
<tbody>
<tr>
<td>id</td>
<td>User profile id</td>
</tr>
<tr>
<td>name</td>
<td>User profile name</td>
</tr>
</tbody>
</table>
<p>the app field has following fields.</p>
<table class="table table-striped table-bordered">
<thead>
<tr>
<th>Header</th>
<th>Value</th>
</tr>
</thead>
<tbody>
<tr>
<td>id</td>
<td>Application id</td>
</tr>
<tr>
<td>name</td>
<td>Application name</td>
</tr>
<tr>
<td>ownerID</td>
<td>Application owner profile id</td>
</tr>
<tr>
<td>privateKey</td>
<td>application private key</td>
</tr>
</tbody>
</table>
<pre><code class="language-sh">GET /private/application/&lt;application id&gt;/metrics/dau
GET /private/application/&lt;application id&gt;/metrics/wau
GET /private/application/&lt;application id&gt;/metrics/mau
</code></pre>
<p>User this call to get (dau=daily, wau=weekly, mau-monthly) active users from server.<br>
You have to add the following parameters:</p>
<table class="table table-striped table-bordered">
<thead>
<tr>
<th>Parameter</th>
<th>Description</th>
</tr>
</thead>
<tbody>
<tr>
<td>from</td>
<td>Date from (pattern: MM-dd-yyyy,default:01-01-2017)</td>
</tr>
<tr>
<td>to</td>
<td>Date to (pattern: MM-dd-yyyy,default:12-31-2017)</td>
</tr>
</tbody>
</table>
<pre><code class="language-sh">GET /private/application/&lt;application id&gt;/metrics/retention
GET /private/application/&lt;application id&gt;/metrics/retention/new
GET /private/application/&lt;application id&gt;/metrics/retention/existing
</code></pre>
<p>User this call to get users retention(/new=only new users, /existing=only existed users) from server.<br>
You have to add the following parameters:</p>
<table class="table table-striped table-bordered">
<thead>
<tr>
<th>Parameter</th>
<th>Description</th>
</tr>
</thead>
<tbody>
<tr>
<td>day</td>
<td>Integer value</td>
</tr>
</tbody>
</table>

</body></html>
