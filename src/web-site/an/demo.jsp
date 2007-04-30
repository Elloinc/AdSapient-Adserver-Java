<?php include "../inc/functions.jsp" ?>
<?php
	include "../mail_config.jsp";
	if (isset($_REQUEST['request'])) {
		if (empty($_REQUEST['Name'])) {
			$flag_name = true;			
			$flag = true;
		}	
			
		if (empty($_REQUEST['Phone'])) {
			$flag_phone = true;			
			$flag = true;
		}		
		if (empty($_REQUEST['Email']) || 
			!preg_match("/^[a-z0-9\\_\\.]+@[a-z0-9\\-]+\\.[a-z]+\\.?[a-z]{2,4}$/i",
						trim($_REQUEST['Email']))) {
			$flag_email = true;			
			$flag = true;
		}

		if($flag) {
			$mesg = "<b style='color: red'>Please, fill in all the fields!</b> ";
		}
		else {
			$header  = "Content-type: text/html; charset=windows-1251 \r\n";
			$header .= "From: {$email_from} <{$email_from}> \r\n";
			if (!empty($email_cc))   $header .= "Cc: {$email_cc} <{$email_cc}> \r\n";
			if (!empty($email_bcc)) $header .= "Bcc: {$email_bcc} <{$email_bcc}> \r\n";
			$body = "<html><head><title>AdSapient Ad Network Demo Request</title></head>
				<body> <br>
					<div style='margin-left: 100px'>
						Name: {$_REQUEST['Name']}<br>  
						Phone: {$_REQUEST['Phone']}<br>  
						Company: {$_REQUEST['Company']}<br> 
						Email:{$_REQUEST['Email']}<br>  
					</div>
				</body>
				</html>";
			mail($email_to, 'AdSapient Ad Network Demo Request', $body, $header) or die("Email error: contact with admin");
			header("Location:http://www.adsapient.com:8082/adsapient/");	
			exit(0);	
		}
	}	
	else {
		$mesg= "";
	}

?>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
	<link rel=stylesheet href="../adsapient.css" type="text/css">
	<title>AdSapient Ad Serving Solutions - Demonstration of AdSapient Ad Network</title>
	<meta name="description" content="Ad serving solutions: Banner ad management software and ad network software available both a hosted solution and as a standalone program. Smart demographic, psychographic, context and content targeting. ">
	<meta name="keywords" content="ad network software, advertising agency software, ad management, adserver, ad server, ad serve, ad serving, ad delivery, banner software, desktop advertising, desktop advertising software, advertising delivery, banner exchange software, ad banners, ad server, ad server solutions, ad-server, banner ad management, online ad software, ad delivery software, ad management, ad targetting, admanager, ad manager, adserve, advertising software, tracking software, ad tracking software, banner server, banner utility, banner serving, banner management software, ad serving technology, rotating banner ads, affiliate management, affiliate software, banner exchange, affiliate tracking, ad network, ad networks">
	<meta HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">	
	<META HTTP-EQUIV="expires" VALUE="Thu, 16 Mar 2000 11:00:00 GMT">
	<script language="javascript" type="text/javascript" src="../javascript/menu.js"></script>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="preloadImages();">
<center>
    <%@ include file="../tiles/ad.jsp" %>
<table cellspacing="0" cellpadding="0" width="865" border="0">
<tr><td align="center"><img alt="AdSapient Ad Serving Solutions" src="../images/logo.gif"></td>
<td align="right" valign="bottom"><map NAME="bg_Map">
		<area SHAPE="poly" ALT="contact" title="Contact" COORDS="288,20, 392,20, 381,0, 299,0" HREF="../contact.jsp"
			ONMOUSEOVER="changeImages('bg', '../images/bg-contact_over.gif'); return true;"
			ONMOUSEOUT="changeImages('bg', '../images/bg.gif'); return true;">
		<area SHAPE="poly" ALT="support" title="Support" COORDS="207,20, 196,0, 299,0, 288,20" HREF="../support.jsp"
			ONMOUSEOVER="changeImages('bg', '../images/bg-support_over.gif'); return true;"
			ONMOUSEOUT="changeImages('bg', '../images/bg.gif'); return true;">
		<area SHAPE="poly" ALT="products" title="Products" COORDS="93,20, 82,0, 195,0, 184,20" HREF="../products.jsp"
			ONMOUSEOVER="changeImages('bg', '../images/bg-products_over.gif'); return true;"
			ONMOUSEOUT="changeImages('bg', '../images/bg.gif'); return true;">
		<area SHAPE="poly" ALT="home" title="Home" COORDS="1,20, 12,0, 82,0, 93,20" HREF="../index.jsp"
			ONMOUSEOVER="changeImages('bg', '../images/bg-home_over.gif'); return true;"
			ONMOUSEOUT="changeImages('bg', '../images/bg.gif'); return true;">
	</map>
	<img NAME="bg" SRC="../images/bg.gif" WIDTH=392 HEIGHT=20 BORDER=0 USEMAP="#bg_Map"></td></tr>
</table>
</center>

<center>
<table cellspacing="0" cellpadding="0" >
<tr>
<td width="3"><img src="../images/angle1.gif"></td>
<td width="200" height="3"style="background-image: url(../images/vert.gif);"></td>
<td><img src="../images/angle7.gif"></td>
<td width="656" height="3"style="background-image: url(../images/vert.gif);"></td>
<td width="3"><img src="../images/angle2.gif"></td>
</tr>

<tr>
<td height="174" width="3" style="background-image: url(../images/hor.gif);"></td>
<td width="200" height="174" valign="top" style="background-color: D1DFFF;">
<table border="0" width="100%">
<?php echo(getMenu('../','an')); ?>
<tr><td><hr></td></tr>
<?php echo(getMenu('../','bm')); ?>
<tr><td><hr></td></tr>
<?php echo(getMenu('../','aux')); ?>
</table>
</td>

<td height="174" width="3" style="background-image: url(../images/hor.gif);"></td>
<td width="656" height="174" valign="top" style="background-color: #FFE8D0;">
<table border="0"  cellspacing="10" width="100%">
<tr><td  colspan="2" class="title2">Demo</td></tr>
<tr><td colspan="2" class="promotext">
&nbsp;&nbsp;&nbsp;You are a minute away from viewing an online fully functional demo of AdSapient Ad Network. 
Just fill out the form below and click on submit. We hereby reserve the right to try to contact you once to inquire about the results of this demonstration.
If you are hesitant about revealing your contact information read our <a class="promotext2" href="../privacy.jsp">privacy policy</a>.
<br/><br/>&nbsp;&nbsp;&nbsp;The default admin login to AdSapient Demo is:<br/>
<b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;login: admin
<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;password: admin</b>
</td></tr>
<tr><td colspan="2" class="promotext">&nbsp;&nbsp;&nbsp; <?= $mesg ?></td></tr>
<?php if(!$flag && isset($_REQUEST['request'])) echo "<!--" ;?>
<form action="demo.jsp" method="POST">
<input name="request" value="1" type="hidden">
<tr>
<td class="promotext">Company</td>
<td class="promotext"><font face="Verdana,Arial,helvetica" size="1">
<input class="ffField2" name="Company" type="text" value="<?= $_REQUEST['Company']?>" size="30" maxlength="200"></font>
</td>
</tr>
<tr>
<td class="promotext" <?php if($flag_name) echo "style='color: red;'"?>>Name<span class="ffRequiredFlag">*</span></td>
<td class="promotext"><font face="Verdana,Arial,helvetica" size="1"><input class="ffField2" name="Name" type="text" value="<?= $_REQUEST['Name']?>" size="30" maxlength="200"></font>
</td>
</tr>
<tr>
<td class="promotext"<?php if($flag_phone) echo "style='color: red;'"?>>Phone<span class="ffRequiredFlag">*</span></td>
<td class="promotext"><font face="Verdana,Arial,helvetica" size="1"><input class="ffField2" name="Phone" type="text" value="<?= $_REQUEST['Phone']?>" size="30" maxlength="200"></font>
</td>
</tr>
<tr>
<td class="promotext"<?php if($flag_email) echo "style='color: red;'"?>>Email<span class="ffRequiredFlag">*</span><?php if($flag_email) echo " not valid"?></td>
<td class="promotext"><font face="Verdana,Arial,helvetica" size="1"><input class="ffField2" name="Email" type="text" value="<?= $_REQUEST['Email']?>" size="30" maxlength="200"></font>
</td>
</tr>

<tr><td></td><td><input type="submit" value="Submit">
<br><br><br><br><br><br><br><br><br><br><br><br>
</td></tr>
<tr><td></td></tr>
</form>
<?php if(!$flag && isset($_REQUEST['request'])) echo "-->" ;?>

</table>
</td>
<td height="174" width="3" style="background-image: url(../images/hor.gif);"></td>
</tr>
<tr>
<td width="3"><img src="../images/angle4.gif"></td>
<td height="3"style="background-image: url(../images/vert.gif);"></td>
<td width="3"><img src="../images/angle5.gif"></td>
<td height="3"style="background-image: url(../images/vert.gif);"></td>
<td width="3"><img src="../images/angle3.gif"></td>
</tr>
</table>
</center>

<%@ include file="../tiles/footnote.jsp" %>
<br>


</body>
</html>

